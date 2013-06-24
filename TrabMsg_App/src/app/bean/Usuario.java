/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

/**
 *
 * @author Guilherme Gehling
 */
public class Usuario {

    private int cod_usr;
    private String login;
    private int logado;

    public Usuario() {
    }

    public Usuario(int cod_usr, String login, int logado) {
        this.cod_usr = cod_usr;
        this.login = login;
        this.logado = logado;
    }

    public int getCod_usr() {
        return cod_usr;
    }

    public void setCod_usr(int cod_usr) {
        this.cod_usr = cod_usr;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getLogado() {
        return logado;
    }

    public void setLogado(int logado) {
        this.logado = logado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.cod_usr;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.cod_usr != other.cod_usr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return login;
    }
}
