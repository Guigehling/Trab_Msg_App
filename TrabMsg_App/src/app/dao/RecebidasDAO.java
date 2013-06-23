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
import app.bean.Recebidas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme Gehling
 */
public class RecebidasDAO {

    private Context context;

    public RecebidasDAO(Context context) {
        this.context = context;
    }

    public void create(Recebidas mensagens) {
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cod_msg", mensagens.getCod_msg());
        valores.put("conteudo", mensagens.getConteudo());
        valores.put("remetente", mensagens.getRemetente());
        conn.insert("recebidas", null, valores);
        conn.close();
    }

    public List<Recebidas> listAll() {
        List<Recebidas> lista = new ArrayList<Recebidas>();
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM recebidas", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recebidas mensagem = new Recebidas();
            mensagem.setCod_msg(cursor.getInt(0));
            mensagem.setConteudo(cursor.getString(1));
            mensagem.setRemetente(cursor.getInt(2));
            lista.add(mensagem);
            cursor.moveToNext();
        }
        conn.close();
        return lista;
    }
}
