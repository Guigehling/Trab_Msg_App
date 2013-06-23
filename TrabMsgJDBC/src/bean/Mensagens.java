/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Guilherme Gehling
 */
public class Mensagens {

    private int cod_msg;
    private String conteudo;
    private Usuario emitente;
    private Usuario destinatario;

    public Mensagens() {
    }

    public Mensagens(int cod_msg, String conteudo, Usuario emitente, Usuario remetente) {
        this.cod_msg = cod_msg;
        this.conteudo = conteudo;
        this.emitente = emitente;
        this.destinatario = remetente;
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

    public Usuario getEmitente() {
        return emitente;
    }

    public void setEmitente(Usuario emitente) {
        this.emitente = emitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatatio) {
        this.destinatario = destinatatio;
    }

    @Override
    public String toString() {
        return conteudo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.cod_msg;
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
        final Mensagens other = (Mensagens) obj;
        if (this.cod_msg != other.cod_msg) {
            return false;
        }
        return true;
    }
}