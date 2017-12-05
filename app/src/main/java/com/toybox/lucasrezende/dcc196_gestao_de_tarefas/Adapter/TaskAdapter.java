package com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Banco.TaskContract;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Banco.TaskDbHelper;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.R;

/**
 * Created by LucasRezende on 28/11/2017.
 */

public class TaskAdapter extends CursorAdapter {

    private TaskDbHelper tarefasDBHelper;
    private static String Tag = "Task Adapter";


    public TaskAdapter(Context context, Cursor c) {
        super(context, c, 0);
        tarefasDBHelper = TaskDbHelper.getInstance(context);
    }

    @Override //layout de visualizaçao do adapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.layout_tarefa,viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtTituloTarefa = (TextView) view.findViewById(R.id.txtTituloTarefaView);
        String titulo = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.Tarefa.COLUMN_NAME_TITULO));
        txtTituloTarefa.setText( "   " + titulo);
        TextView txtStatus = (TextView) view.findViewById(R.id.txtStatusTarefa);
        String status = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.Tarefa.COLUMN_NAME_STATUS));
        txtStatus.setText("Status: " + status);
    }

    public TaskDbHelper getTarefasDBHelper() {
        return tarefasDBHelper;
    }
}
