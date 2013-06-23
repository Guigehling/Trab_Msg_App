/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmsBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import rmsDao.UsuarioRmsDao;

/**
 *
 * @author Guilherme Gehling
 */
public class UsuarioRmsBean {

    private int cod_user;
    private String login, senha;

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(cod_user);
        dos.writeUTF(login);
        dos.writeUTF(senha);
        byte[] ret = baos.toByteArray();
        dos.close();
        baos.close();
        return ret;
    }

    public void setBytes(byte[] valores) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(valores);
        DataInputStream dis = new DataInputStream(bais);
        this.cod_user = dis.readInt();
        this.login = dis.readUTF();
        this.senha = dis.readUTF();
        dis.close();
        bais.close();
    }

    public UsuarioRmsBean validaLogin() {
        UsuarioRmsBean usuario;
        UsuarioRmsDao usuarioDAO = new UsuarioRmsDao();
        usuario = usuarioDAO.retrieve();
        if (usuario == null) {
            return null;
        } else {
            if ("".equals(usuario.getLogin())) {
                return null;
            } else {
                return usuario;
            }
        }
    }

    public int getCod_user() {
        return cod_user;
    }

    public void setCod_user(int cod_user) {
        this.cod_user = cod_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
