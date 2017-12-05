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
 * Created by LucasRezende on 03/12/2017.
 */

public class TagAdapter extends CursorAdapter {

    private TaskDbHelper tagsDBHelper;
    private static String Tag = "Task Adapter";


    public TagAdapter(Context context, Cursor c) {
        super(context, c, 0);
        tagsDBHelper = TaskDbHelper.getInstance(context);
    }

    @Override //layout de visualizaçao do adapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.layout_tags,null, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtTag = (TextView) view.findViewById(R.id.txtTag);
        String tag = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.Tags.COLUMN_NAME_TAG));
        txtTag.setText("tag:" + tag);
    }

    public TaskDbHelper getTarefasDBHelper() {
        return tagsDBHelper;
    }
}
