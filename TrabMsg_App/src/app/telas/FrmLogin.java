package app.telas;

import android.app.Activity;
import android.app.AlertDialog;
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

public class FrmLogin extends Activity {

    private String msg;
    private Handler manipulador = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            testLogin();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmlogin);
        UsuarioDAO usrDAO = new UsuarioDAO(this);
        Usuario usr = usrDAO.retrive();
        if (usr != null) {
            Intent intent = new Intent(this, Opcoes.class);
            startActivity(intent);
        }
    }

    public void onClickbtLogin(View v) throws InterruptedException {
        Thread t = new Thread(new ConexaoWWW());
        t.start();
    }

    public void testLogin() {
        if ("Ok".equals(msg)) {
            EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
            UsuarioDAO usrDAO = new UsuarioDAO(this);
            Usuario usr = new Usuario();
            usr.setLogin(txtLogin.getText().toString());
            usr.setCod_usr(1);
            usr.setLogado(1);
            usrDAO.create(usr);
            Intent intent = new Intent(this, Opcoes.class);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this).setTitle("Aviso!!").setMessage("Falha na Conexão!").setNeutralButton("OK", null).show();
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
                EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
                EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
                String login, senha;
                login = txtLogin.getText().toString();
                senha = txtSenha.getText().toString();
                OutputStream os = httpConn.getOutputStream();
                os.write(("acao=Logar&login=" + login + "&senha=" + senha).getBytes());
                os.close();
                String ret = httpConn.getResponseMessage();
                int code = httpConn.getResponseCode();
                DataInputStream dis = new DataInputStream(httpConn.getInputStream());
                msg = dis.readUTF();
                manipulador.sendEmptyMessage(0);
            } catch (Exception ex) {
                msg = "Erro";
                manipulador.sendEmptyMessage(0);
                ex.printStackTrace();
            }
        }
    }
}
