/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author Guilherme Gehling
 */
public class BancoDados extends SQLiteOpenHelper {

    public BancoDados(Context context) {
        super(context, "BDAndroid", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqld) {
        sqld.execSQL("CREATE TABLE enviadas (cod_msg INTEGER PRIMARY KEY AUTOINCREMENT, conteudo TEXT, destinatario INTEGER,"
                + "FOREIGN KEY (destinatario) REFERENCES departamento (cod_usr));");
        sqld.execSQL("CREATE TABLE recebidas (cod_msg INTEGER PRIMARY KEY AUTOINCREMENT, conteudo TEXT, remetente INTEGER, "
                + "FOREIGN KEY (remetente) REFERENCES usuario (cod_usr));");
        sqld.execSQL("CREATE TABLE usuario (cod_usr INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT NOT NULL, senha TEXT, logado INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqld, int i, int i1) {
        sqld.execSQL("DROP TABLE IF EXISTS enviadas;");
        sqld.execSQL("DROP TABLE IF EXISTS recebidas;");
        sqld.execSQL("DROP TABLE IF EXISTS usuario;");
        this.onCreate(sqld);
    }
}
