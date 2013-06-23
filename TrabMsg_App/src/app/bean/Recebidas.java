/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

/**
 *
 * @author Guilherme Gehling
 */
public class Recebidas {

    private int cod_msg;
    private String conteudo;
    private int remetente;

    public Recebidas() {
    }

    public Recebidas(int cod_msg, String conteudo, int remetente) {
        this.cod_msg = cod_msg;
        this.conteudo = conteudo;
        this.remetente = remetente;
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

    public int getRemetente() {
        return remetente;
    }

    public void setRemetente(int remetente) {
        this.remetente = remetente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.cod_msg;
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
        final Recebidas other = (Recebidas) obj;
        if (this.cod_msg != other.cod_msg) {
            return false;
        }
        return true;
    }
}
