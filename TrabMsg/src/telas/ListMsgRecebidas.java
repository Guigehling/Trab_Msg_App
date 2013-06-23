/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import rmsBean.RecebidasRmsBean;
import rmsBean.UsuarioRmsBean;
import rmsDao.RecebidasRmsDAO;
import rmsDao.UsuarioRmsDao;

/**
 *
 * @author Guilherme Gehling
 */
public class ListMsgRecebidas extends List implements CommandListener {

    private MIDlet mid;
    private Command opcVoltar;
    private Displayable frmVoltar;
    private String URL = "http://localhost:8080/TrabMsgWeb/ServletMsg";

    public ListMsgRecebidas(String title, Displayable frmVoltar, MIDlet mid) {
        super(title, List.EXCLUSIVE);
        this.mid = mid;
        this.frmVoltar = frmVoltar;
        this.opcVoltar = new Command("Voltar", Command.EXIT, 0);
        this.addCommand(opcVoltar);
        Thread t = new Thread(new ListMsgRecebidas.ConexaoWWW());
        t.start();
        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        Display display = Display.getDisplay(mid);
        if (c == opcVoltar) {
            display.setCurrent(frmVoltar);
        }
    }

    private class ConexaoWWW implements Runnable {

        public void run() {
            HttpConnection httpConn = null;
            RecebidasRmsBean msgBean = new RecebidasRmsBean();
            RecebidasRmsDAO msgDAO = new RecebidasRmsDAO();
            UsuarioRmsBean userBean = new UsuarioRmsBean();
            UsuarioRmsDao userDAO = new UsuarioRmsDao();
            Vector vetor = new Vector();
            int contaRecebidas = 0;
            try {
                httpConn = (HttpConnection) Connector.open(URL, Connector.READ_WRITE);
                httpConn.setRequestMethod(HttpConnection.POST);
                httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpConn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
                httpConn.setRequestProperty("Content-Language", "pt-BR");
                httpConn.setRequestProperty("Accept", "application/octet-stream");
                httpConn.setRequestProperty("Connection", "close");
                OutputStream os = httpConn.openOutputStream();
                userBean = userDAO.retrieve();
                os.write(("acao=Recebidas&login=" + userBean.getLogin() + "&opc=Listar").getBytes());
                os.close();
                String msg = httpConn.getResponseMessage();
                int code = httpConn.getResponseCode();
                if (code == HttpConnection.HTTP_OK) {
                    DataInputStream dis = httpConn.openDataInputStream();
                    String mensagen = null;
                    do {
                        mensagen = dis.readUTF();
                        if (!"FIM".equals(mensagen)) {
                            append(mensagen, null);
                            vetor = msgDAO.todasRecebidas();
                            if (vetor != null) {
                                if (vetor.size() <= contaRecebidas) {
                                    msgBean.setConteudo(mensagen);
                                    msgDAO.insert(msgBean);
                                }
                            } else {
                                msgBean.setConteudo(mensagen);
                                msgDAO.insert(msgBean);
                            }
                            contaRecebidas++;
                        }
                    } while (!"FIM".equals(mensagen) && mensagen != null);
                    dis.close();
                } else {
                    System.out.println("Código invalido: " + msg);
                }
            } catch (Exception ex) {
                vetor = msgDAO.todasRecebidas();
                if (vetor != null) {
                    for (int i = 0; i < vetor.size(); i++) {
                        append(vetor.elementAt(i).toString(), null);
                    }
                }
                ex.printStackTrace();
            }
        }
    }
}
