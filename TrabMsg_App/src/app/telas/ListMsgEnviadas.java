/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.telas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import app.bean.Enviadas;
import app.bean.Usuario;
import app.dao.EnviadasDAO;
import app.dao.RecebidasDAO;
import app.dao.UsuarioDAO;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme Gehling
 */
public class ListMsgEnviadas extends ListActivity {

    private List lista = new ArrayList();
    Usuario usr = new Usuario();
    UsuarioDAO usrDAO = new UsuarioDAO(this);
    Enviadas env = new Enviadas();
    EnviadasDAO envDAO = new EnviadasDAO(this);
    private Handler manipulador = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            atualiza();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usr = usrDAO.retrive();
        Thread t = new Thread(new ConexaoWWW());
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulistmsgenviadas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btLimparEnviadas:
                EnviadasDAO enviadas = new EnviadasDAO(this);
                enviadas.delete();
                new AlertDialog.Builder(this).setTitle("Aviso!!").setMessage("Mensagens Excluidas!").show();
                //new AlertDialog.Builder(this).setTitle("Aviso!!").setMessage("Mensagens Excluidas!").setNeutralButton("OK", null).show();
                Intent intent = new Intent(this, Opcoes.class);
                startActivity(intent);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void atualiza() {
        setListAdapter(new ArrayAdapter(this, R.layout.listmsgenviadas, lista));
    }

    private class ConexaoWWW implements Runnable {

        public void run() {
            int contaEnviadas = 0;
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
                OutputStream os = httpConn.getOutputStream();
                os.write(("acao=Enviadas&login=" + usr.getLogin() + "&opc=Listar").getBytes());
                os.close();
                String msg = httpConn.getResponseMessage();
                int code = httpConn.getResponseCode();
                if (code == httpConn.HTTP_OK) {
                    DataInputStream dis = new DataInputStream(httpConn.getInputStream());
                    String mensagen = null;
                    lista = new ArrayList();
                    do {
                        mensagen = dis.readUTF();
                        if (!"FIM".equals(mensagen)) {
                            Log.i(ConexaoWWW.class.getName(), String.format("Leitura: %s", mensagen));
                            lista = envDAO.listAll();
                            if (lista != null) {
                                if (lista.size() <= contaEnviadas) {
                                    env.setConteudo(mensagen);
                                    envDAO.create(env);
                                }
                            } else {
                                env.setCod_msg(contaEnviadas);
                                env.setConteudo(mensagen);
                                envDAO.create(env);
                            }
                            contaEnviadas++;
                        }
                    } while (!"FIM".equals(mensagen) && mensagen != null);
                    manipulador.sendEmptyMessage(0);
                    dis.close();
                } else {
                    System.out.println("Código invalido: " + msg);
                }
            } catch (Exception ex) {
                lista = envDAO.listAll();
                ex.printStackTrace();
            }
        }
    }
}
