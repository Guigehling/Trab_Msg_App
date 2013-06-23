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
import app.bean.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme Gehling
 */
public class UsuarioDAO {

    private Context context;

    public UsuarioDAO(Context context) {
        this.context = context;
    }

    public void create(Usuario usuario) {
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        ContentValues valores = new ContentValues();
        //valores.put("cod_user", usuario.getCod_usr());
        valores.put("login", usuario.getLogin());
        valores.put("logado", usuario.getLogado());
        conn.insert("usuario", null, valores);
        conn.close();
    }

    public Usuario retrive() {
        Usuario usr = new Usuario();
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM usuario", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Usuario usuario = new Usuario();
            usuario.setCod_usr(cursor.getInt(0));
            usuario.setLogin(cursor.getString(1));
            usr = usuario;
            cursor.moveToNext();
        }
        conn.close();
        return usr;
    }

    public List<Usuario> listContact(Usuario logado) {
        List<Usuario> lista = new ArrayList<Usuario>();
        BancoDados bd = new BancoDados(context);
        SQLiteDatabase conn = bd.getWritableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM usuario where cod_usr <> " + logado.getCod_usr(), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Usuario usuario = new Usuario();
            usuario.setCod_usr(cursor.getInt(0));
            usuario.setLogin(cursor.getString(1));
            lista.add(usuario);
            cursor.moveToNext();
        }
        conn.close();
        return lista;
    }
}
