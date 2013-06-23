/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import rmsBean.UsuarioRmsBean;
import rmsDao.UsuarioRmsDao;

/**
 *
 * @author Guilherme Gehling
 */
public class FrmNovaMsg extends Form implements CommandListener {

    private MIDlet mid;
    private Command opcVoltar;
    private Command opcSalvar;
    private TextField txtDestinatario, txtConteudo;
    private Displayable frmVoltar;
    private String URL = "http://localhost:8080/TrabMsgWeb/ServletMsg";

    public FrmNovaMsg(String title, Displayable frmVoltar, MIDlet mid) {
        super(title);
        this.mid = mid;
        this.frmVoltar = frmVoltar;
        this.opcVoltar = new Command("Voltar", Command.EXIT, 0);
        this.opcSalvar = new Command("Salvar", Command.ITEM, 1);
        this.addCommand(opcVoltar);
        this.addCommand(opcSalvar);
        this.txtDestinatario = new TextField("Para: ", "", 10, TextField.ANY);
        append(this.txtDestinatario);
        this.txtConteudo = new TextField("Mensagem: ", "", 50, TextField.ANY);
        append(this.txtConteudo);
        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        Display display = Display.getDisplay(mid);
        if (c == opcVoltar) {
            display.setCurrent(frmVoltar);
        } else if (c == opcSalvar) {
            Thread t = new Thread(new FrmNovaMsg.ConexaoWWW());
            t.start();
        }
    }

    private class ConexaoWWW implements Runnable {

        public void run() {
            HttpConnection httpConn = null;
            UsuarioRmsBean userBean = new UsuarioRmsBean();
            UsuarioRmsDao userDAO = new UsuarioRmsDao();
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
                os.write(("acao=Enviadas&login=" + userBean.getLogin() + "&dest=" + txtDestinatario.getString() + "&conteudo=" + txtConteudo.getString() + "&opc=Salvar").getBytes());
                os.close();
                String msg = httpConn.getResponseMessage();
                Display display = Display.getDisplay(mid);
                if ("OK".equals(msg)) {
                    display.setCurrent(frmVoltar);
                }
            } catch (Exception ex) {
                Display display = Display.getDisplay(mid);
                Alert alert = new Alert("Informação");
                alert.setString("Sem conexão!");
                display.setCurrent(alert);
                ex.printStackTrace();
            }
        }
    }
}
