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

/**
 *
 * @author Guilherme Gehling
 */
public class RecebidasRmsBean {

    private int cod_msg;
    private String conteudo;

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(cod_msg);
        dos.writeUTF(conteudo);
        byte[] ret = baos.toByteArray();
        dos.close();
        baos.close();
        return ret;
    }

    public void setBytes(byte[] valores) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(valores);
        DataInputStream dis = new DataInputStream(bais);
        this.cod_msg = dis.readInt();
        this.conteudo = dis.readUTF();
        dis.close();
        bais.close();
    }

    public RecebidasRmsBean() {
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

    public String toString() {
        return conteudo;
    }
}
