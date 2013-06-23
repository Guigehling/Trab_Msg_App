/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

/**
 *
 * @author Guilherme Gehling
 */
public class Enviadas {

    private int cod_msg;
    private String conteudo;
    private int destinatario;

    public Enviadas() {
    }

    public Enviadas(int cod_msg, String conteudo, int destinatario) {
        this.cod_msg = cod_msg;
        this.conteudo = conteudo;
        this.destinatario = destinatario;
    }

    public int getCod_msg() {
        return cod_msg;
    }

    public void setCod_msg(int cod_msg) {
        this.cod_msg = cod_msg;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(int destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.cod_msg;
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
        final Enviadas other = (Enviadas) obj;
        if (this.cod_msg != other.cod_msg) {
            return false;
        }
        return true;
    }
}
