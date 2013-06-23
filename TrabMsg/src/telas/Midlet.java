/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStoreException;
import rmsBean.UsuarioRmsBean;
import rmsDao.EnviadasRmsDAO;
import rmsDao.UsuarioRmsDao;

/**
 * @author Guilherme Gehling
 */
public class Midlet extends MIDlet {

    public void startApp() {
        UsuarioRmsBean usuario = new UsuarioRmsBean();
        usuario = usuario.validaLogin();
        if (usuario == null) {
            Display display = Display.getDisplay(this);
            FrmLogin frmLogin = new FrmLogin("Login", this);
            display.setCurrent(frmLogin);
        } else {
            Display display = Display.getDisplay(this);
            FrmInicial frmInicial = new FrmInicial("Menu", this);
            display.setCurrent(frmInicial);
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
