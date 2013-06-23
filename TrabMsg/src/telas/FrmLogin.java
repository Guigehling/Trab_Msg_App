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
import rmsBean.UsuarioRmsBean;
import rmsDao.UsuarioRmsDao;

/**
 *
 * @author Guilherme Gehling
 */
public class FrmLogin extends Form implements CommandListener {

    private Midlet mid;
    private Command opcLogar;
    private Command opcSair;
    private TextField txtLogin, txtSenha;
    private String URL = "http://localhost:8080/TrabMsgWeb/ServletMsg";

    public FrmLogin(String title, Midlet mid) {
        super(title);
        this.mid = mid;
        this.opcLogar = new Command("Logar", Command.ITEM, 0);
        this.opcSair = new Command("Sair", Command.EXIT, 1);
        this.addCommand(opcLogar);
        this.addCommand(opcSair);
        this.setCommandListener(this);
        this.txtLogin = new TextField("Nome: ", "", 10, TextField.ANY);
        append(this.txtLogin);
        this.txtSenha = new TextField("Senha: ", "", 10, TextField.PASSWORD);
        append(this.txtSenha);
    }

    public void commandAction(Command c, Displayable d) {
        Display display = Display.getDisplay(mid);
        if (c == opcSair) {
            mid.destroyApp(true);
            mid.notifyDestroyed();
        } else if (c == opcLogar) {
            Thread t = new Thread(new ConexaoWWW());
            t.start();
        }
    }

    private class ConexaoWWW implements Runnable {

        public void run() {
            HttpConnection httpConn = null;
            try {
                httpConn = (HttpConnection) Connector.open(URL, Connector.READ_WRITE);
                httpConn.setRequestMethod(HttpConnection.POST);
                httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpConn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
                httpConn.setRequestProperty("Content-Language", "pt-BR");
                httpConn.setRequestProperty("Accept", "application/octet-stream");
                httpConn.setRequestProperty("Connection", "close");
                OutputStream os = httpConn.openOutputStream();
                String login, senha;
                login = txtLogin.getString();
                senha = txtSenha.getString();
                os.write(("acao=Logar&login=" + login + "&senha=" + senha).getBytes());
                os.close();
                String msg = httpConn.getResponseMessage();
                Display display = Display.getDisplay(mid);
                if ("OK".equals(msg)) {
                    display.setCurrent(new FrmInicial("Menu", mid));
                    UsuarioRmsBean usrBean = new UsuarioRmsBean();
                    UsuarioRmsDao usrDao = new UsuarioRmsDao();
                    usrBean.setLogin(login);
                    usrBean.setSenha(senha);
                    usrDao.insert(usrBean);
                }
            } catch (Exception ex) {
                Display display = Display.getDisplay(mid);
                Alert alert = new Alert("Informação");
                alert.setString("Erro na conexão!");
                display.setCurrent(alert);
                ex.printStackTrace();
            }
        }
    }
}
