/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Guilherme Gehling
 */
public class Usuario {

    private int cod_user;
    private String login, senha;

    public Usuario() {
    }

    public Usuario(int cod_user, String login, String senha) {
        this.cod_user = cod_user;
        this.login = login;
        this.senha = senha;
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

    public boolean validaSenha(String senha) {
        boolean ret = false;
        try {
            if (this.senha.equals(senha)) {
                ret = true;
            }
        } catch (NullPointerException ex) {
        }
        return ret;
    }

    @Override
    public String toString() {
        return login;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.cod_user;
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
        if (this.cod_user != other.cod_user) {
            return false;
        }
        return true;
    }
}
