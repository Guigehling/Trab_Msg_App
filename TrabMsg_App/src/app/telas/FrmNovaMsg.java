/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import app.bean.Usuario;
import app.dao.UsuarioDAO;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Guilherme Gehling
 */
public class FrmNovaMsg extends Activity {

    private String retorno;
    private UsuarioDAO usrDAO = new UsuarioDAO(this);
    private Usuario usr = new Usuario();
    private Handler manipulador = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            envia();
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.frmnovamsg);
    }

    public void onClickBtEnviar(View v) {
        usr = usrDAO.retrive();
        Thread t = new Thread(new FrmNovaMsg.ConexaoWWW());
        t.start();
    }

    public void envia() {
        if ("OK".equals(retorno)) {
            Intent intent = new Intent(this, Opcoes.class);
            startActivity(intent);
        }
    }

    private class ConexaoWWW implements Runnable {

        public void run() {
            try {
                URL urlObj = new URL("http://192.168.0.101:8080/TrabMsgWeb/ServletMsg");
                HttpURLConnection httpConn = (HttpURLConnection) urlObj.openConnection();
                httpConn.setDoInput(true);
                httpConn.setDoOutput(true);
                httpConn.setUseCaches(false);
                httpConn.setRequestMethod("POST");
                httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpConn.setRequestProperty("Content-Language", "pt-BR");
                httpConn.setRequestProperty("Accept", "application/octet-stream");
                httpConn.setRequestProperty("Connection", "close");

                EditText txtcontato = (EditText) findViewById(R.id.txtContato);
                EditText txtmensagem = (EditText) findViewById(R.id.txtMensagem);
                String destinatario, conteudo;
                destinatario = txtcontato.getText().toString();
                conteudo = txtmensagem.getText().toString();
                OutputStream os = httpConn.getOutputStream();
                os.write(("acao=Enviadas&login=" + usr.getLogin() + "&dest=" + destinatario + "&conteudo=" + conteudo + "&opc=Salvar").getBytes());
                os.close();
                String ret = httpConn.getResponseMessage();
                int code = httpConn.getResponseCode();
                if (code == httpConn.HTTP_OK) {
                    DataInputStream dis = new DataInputStream(httpConn.getInputStream());
                    retorno = dis.readUTF();
                    dis.close();
                }
                manipulador.sendEmptyMessage(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
