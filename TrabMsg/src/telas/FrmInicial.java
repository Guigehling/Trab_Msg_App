/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import rmsDao.EnviadasRmsDAO;
import rmsDao.RecebidasRmsDAO;
import rmsDao.UsuarioRmsDao;

/**
 *
 * @author Guilherme Gehling
 */
public class FrmInicial extends Form implements CommandListener {

    private Midlet mid;
    private Command opcSair;
    private Command opcOk;
    private ChoiceGroup cgrpMenu;
    private String URL = "http://localhost:8080/TrabMsgWeb/ServletMsg";

    public FrmInicial(String title, Midlet mid) {
        super(title);
        this.mid = mid;
        this.opcSair = new Command("Sair", Command.EXIT, 0);
        this.addCommand(opcSair);
        this.opcOk = new Command("Ok", Command.ITEM, 1);
        this.addCommand(opcOk);
        this.setCommandListener(this);
        this.cgrpMenu = new ChoiceGroup("Menu", Choice.EXCLUSIVE);
        this.cgrpMenu.append("Enviar Msg", null);
        this.cgrpMenu.append("Msg Recebidas", null);
        this.cgrpMenu.append("Msg Eviadas", null);
        this.cgrpMenu.append("Limpar Cache", null);
        append(this.cgrpMenu);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == opcSair) {
            mid.destroyApp(true);
            mid.notifyDestroyed();
        } else if (c == opcOk) {
            Display display = Display.getDisplay(mid);
            switch (this.cgrpMenu.getSelectedIndex()) {
                case 0:
                    display.setCurrent(new FrmNovaMsg("Enviar Mensagens", this, mid));
                    break;
                case 1:
                    display.setCurrent(new ListMsgRecebidas("Recebidas", this, mid));
                    break;
                case 2:
                    display.setCurrent(new ListMsgEnviadas("Enviadas", this, mid));
                    break;
                case 3:
                    Alert alert = new Alert("Informação");
                    alert.setString("Limpando Informações.");
                    display.setCurrent(alert);
                    EnviadasRmsDAO e = new EnviadasRmsDAO();
                    e.clear();
                    RecebidasRmsDAO r = new RecebidasRmsDAO();
                    r.clear();
                    UsuarioRmsDao u = new UsuarioRmsDao();
                    u.clear();
                    break;
            }
        }
    }
}
