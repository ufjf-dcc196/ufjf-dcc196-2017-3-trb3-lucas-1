package com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TaskDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Feira.db";
    public static final String Tag = "Banco";
    private static TaskDbHelper instance = null;


    public static TaskDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new TaskDbHelper(context);
        }
        return instance;
    }

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(TaskContract.SQL_CREATE_TAREFAS);
            sqLiteDatabase.execSQL(TaskContract.SQL_CREATE_TAGS);
            sqLiteDatabase.execSQL(TaskContract.SQL_CREATE_COMPOSICAO);
            Log.e(Tag, "Tabelas Criadas Com Sucesso");
        }catch (Exception e){
            Log.e(Tag, "On Create");
            Log.e(Tag , e.getLocalizedMessage());
            Log.e(Tag , e.getStackTrace().toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_TAREFAS);
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_TAGS);
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_COMPOSOCAO);
        onCreate(sqLiteDatabase);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion,newVersion);
    }

    public void onDrop(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_TAREFAS);
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_TAGS);
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_COMPOSOCAO);
    }

    public void DropAll(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_TAREFAS);
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_TAGS);
        sqLiteDatabase.execSQL(TaskContract.SQL_DROP_COMPOSOCAO);
        onCreate(sqLiteDatabase);
    }
}
