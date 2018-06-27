package com.example.diogo.agenda.Uteis;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseUtil extends SQLiteOpenHelper {
    private static final String NOME_BASE_DE_DADOS   = "SISTEMA.db";
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    public DatabaseUtil(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder stringBuilderCreateTable = new StringBuilder();

        stringBuilderCreateTable.append(" CREATE TABLE tb_pessoa (");
        stringBuilderCreateTable.append("        id_pessoa      INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        ds_nome        TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        ds_endereco    TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_sexo        TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        dt_nascimento  TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_estadoCivil TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_ativo       INT     NOT NULL )           ");

        db.execSQL(stringBuilderCreateTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_pessoa");
        onCreate(db);
    }

    public SQLiteDatabase GetConexaoDataBase(){
        return this.getWritableDatabase();
    }
}
