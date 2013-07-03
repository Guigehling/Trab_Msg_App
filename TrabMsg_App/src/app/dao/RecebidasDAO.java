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
import app.bean.Usuario;
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
        valores.put("conteudo", mensagens.getConteudo());
        valores.put("remetente", mensagens.getRemetente());
        conn.insert("recebidas", null, valores);
        conn.close();
    }

    public Recebidas retrive() {
        Recebidas rec = null;
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM usuario", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recebidas recebida = new Recebidas();
            recebida.setCod_msg(cursor.getInt(0));
            recebida.setConteudo(cursor.getString(1));
            recebida.setRemetente(cursor.getInt(1));
            rec = recebida;
            cursor.moveToNext();
        }
        conn.close();
        return rec;
    }

    public void update(Recebidas recebida) {
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cod_msg", recebida.getCod_msg());
        valores.put("conteudo", recebida.getConteudo());
        valores.put("remetente", recebida.getRemetente());
        conn.update("recebidas", valores, null, null);
        conn.close();
    }

    public void delete() {
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        conn.delete("recebidas", null, null);
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
