/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import app.bd.BancoDados;
import app.bean.Enviadas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme Gehling
 */
public class EnviadasDAO {

    private Context context;

    public EnviadasDAO(Context context) {
        this.context = context;
    }

    public void create(Enviadas mensagem) {
        BancoDados bd = new BancoDados(this.context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("conteudo", mensagem.getCod_msg());
        valores.put("destinatario", mensagem.getDestinatario());
        conn.insert("enviadas", null, valores);
        conn.close();
    }

    public List<Enviadas> listAll() {
        List<Enviadas> lista = new ArrayList<Enviadas>();
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM enviadas", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Enviadas mensagem = new Enviadas();
            mensagem.setCod_msg(cursor.getInt(0));
            mensagem.setConteudo(cursor.getString(1));
            mensagem.setDestinatario(cursor.getInt(2));
            lista.add(mensagem);
            cursor.moveToNext();
        }
        conn.close();
        return lista;
    }
}