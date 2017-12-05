package com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Adapter.TaskAdapter;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Banco.TaskContract;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LucasRezende on 02/12/2017.
 */

public class TaskHelper {

    private static TaskHelper instance = null;
    private static String Tag = "TaskHelper";
    TaskAdapter taskAdapter = null;
    private String[] statusList = {"A fazer", "Em execução" , "Bloqueada",  "Concluída" };
    private String[] dificuldadeList = {"1","2","3","4","5"};


    public static TaskHelper getInstance() {
        if (instance == null) {
            instance = new TaskHelper();
        }
        return instance;
    }

    public void initTaskAdapter(Context baseContext) {
       taskAdapter = new TaskAdapter(baseContext,null);

    }

    public String[] getStatusList() {
        return statusList;
    }

    public void setStatusList(String[] statusList) {
        this.statusList = statusList;
    }

    public String[] getDificuldadeList() {
        return dificuldadeList;
    }

    public void setDificuldadeList(String[] dificuldadeList) {
        this.dificuldadeList = dificuldadeList;
    }

    public TaskAdapter getTaskAdapter() {
        return taskAdapter;
    }

    public void setTaskAdapter(TaskAdapter taskAdapter) {
        this.taskAdapter = taskAdapter;
    }

    public void PushTask() {
        try {
            SQLiteDatabase db = getTaskAdapter().getTarefasDBHelper().getReadableDatabase();
            String[] visao = {
                    TaskContract.Tarefa._ID,
                    TaskContract.Tarefa.COLUMN_NAME_TITULO,
                    TaskContract.Tarefa.COLUMN_NAME_DESCRICAO,
                    TaskContract.Tarefa.COLUMN_NAME_DIFICULDADE,
                    TaskContract.Tarefa.COLUMN_NAME_STATUS
            };
            Cursor c = db.query(TaskContract.Tarefa.TABLE_NAME, visao, null, null, null, null, TaskContract.Tarefa.COLUMN_NAME_STATUS);
            taskAdapter.changeCursor(c);
        } catch (Exception e) {
            Log.e(Tag, "M-pushTask");
            Log.e(Tag, e.getLocalizedMessage());
            Log.e(Tag, e.getStackTrace().toString());
        }
    }

    public void filtraTag(long idTag) {
        try {
            if(idTag == 1) {
                PushTask();
            }else {
                SQLiteDatabase db = getTaskAdapter().getTarefasDBHelper().getReadableDatabase();
                String rawQuery = "SELECT a." + TaskContract.Tarefa._ID
                        + ", " + TaskContract.Tarefa.COLUMN_NAME_TITULO
                        + ", " + TaskContract.Tarefa.COLUMN_NAME_STATUS
                        + " FROM " + TaskContract.Tarefa.TABLE_NAME + " AS a "
                        + " INNER JOIN " + TaskContract.Composicao.TABLE_NAME + " AS b "
                        + " INNER JOIN " + TaskContract.Tags.TABLE_NAME + " AS c "
                        + " ON a." + TaskContract.Tarefa._ID + " = b." + TaskContract.Composicao.COLUMN_NAME_ID_TAREFA
                        + " AND b." + TaskContract.Composicao.COLUMN_NAME_ID_TAG + " = c." + TaskContract.Tags._ID
                        + " WHERE c." + TaskContract.Tags._ID + " = " + idTag;
                ;
                Cursor c = db.rawQuery(rawQuery, null);
                taskAdapter.changeCursor(c);
            }
        } catch (Exception e) {
            Log.e(Tag, "M-filtraTag");
            Log.e(Tag, e.getLocalizedMessage());
            Log.e(Tag, e.getStackTrace().toString());
        }
    }

    public long AddNewTask(Task newTask) {
        try {
            SQLiteDatabase db = getTaskAdapter().getTarefasDBHelper().getReadableDatabase();
            ContentValues dataToInsert = new ContentValues();
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_TITULO, newTask.getTitulo());
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_DESCRICAO, newTask.getDescricao());
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_STATUS, newTask.getStatus());
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_DIFICULDADE, newTask.getDificuldade());
            long id = db.insert(TaskContract.Tarefa.TABLE_NAME, null, dataToInsert);
            PushTask();
            return  id;
        } catch (Exception e) {
            Log.e(Tag, "M-AddNewTask");
            Log.e(Tag, e.getLocalizedMessage());
            Log.e(Tag, e.getStackTrace().toString());
        }
        return -1;
    }

    public Task SearchForTask(int id_task) {
        try {
            SQLiteDatabase db = getTaskAdapter().getTarefasDBHelper().getReadableDatabase();
            Task newTask = new Task();
            String[] visao = {
                    TaskContract.Tarefa._ID,
                    TaskContract.Tarefa.COLUMN_NAME_TITULO,
                    TaskContract.Tarefa.COLUMN_NAME_DESCRICAO,
                    TaskContract.Tarefa.COLUMN_NAME_STATUS,
                    TaskContract.Tarefa.COLUMN_NAME_DIFICULDADE,
            };
            String selecao = TaskContract.Tarefa._ID + " = ? ";
            String[] arg = {String.valueOf(id_task)};
            Cursor c = db.query(TaskContract.Tarefa.TABLE_NAME, visao, selecao, arg, null, null, null);
            // verifica se o cursos retornou alguma resultado
            if(c!=null){
                c.moveToFirst();
                newTask.setId(c.getInt(0));
                newTask.setTitulo(c.getString(1));       
                newTask.setDescricao(c.getString(2));      
                newTask.setStatus(c.getString(3));      
                newTask.setDificuldade(c.getInt(4));
            }
            return newTask;
        } catch (Exception e) {
            Log.e(Tag, "M-SearchForTask");
            Log.e(Tag, e.getLocalizedMessage());
            Log.e(Tag, e.getStackTrace().toString());
        }
        return null;
    }

    public void DeleteTask(Task myTask) {
        try {
            SQLiteDatabase db = getTaskAdapter().getTarefasDBHelper().getReadableDatabase();
            String where = TaskContract.Tarefa._ID + " = ? ";
            String[] arg = {String.valueOf(myTask.getId())};
            db.delete(TaskContract.Tarefa.TABLE_NAME,where,arg);
        } catch (Exception e) {
            Log.e(Tag, "M-DeleteTask");
            Log.e(Tag, e.getLocalizedMessage());
            Log.e(Tag, e.getStackTrace().toString());
        }
    }

    public void UpdateTask(Task myTask) {
        try {
            SQLiteDatabase db = getTaskAdapter().getTarefasDBHelper().getReadableDatabase();
            ContentValues dataToInsert = new ContentValues();
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_TITULO, myTask.getTitulo());
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_DESCRICAO, myTask.getDescricao());
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_STATUS, myTask.getStatus());
            dataToInsert.put(TaskContract.Tarefa.COLUMN_NAME_DIFICULDADE, myTask.getDificuldade());
            String where = TaskContract.Tarefa._ID + " = ? ";
            String[] arg = {String.valueOf(myTask.getId())};
            db.update(TaskContract.Tarefa.TABLE_NAME,dataToInsert,where,arg);
        } catch (Exception e) {
            Log.e(Tag, "M-UpdateTask");
            Log.e(Tag, e.getLocalizedMessage());
            Log.e(Tag, e.getStackTrace().toString());
        }
    }

    public void CriaComposicaoDeTags(long id_task, long id_tag) {
        try {
            SQLiteDatabase db = getTaskAdapter().getTarefasDBHelper().getReadableDatabase();
            ContentValues dataToInsert = new ContentValues();
            dataToInsert.put(TaskContract.Composicao.COLUMN_NAME_ID_TAG, id_tag);
            dataToInsert.put(TaskContract.Composicao.COLUMN_NAME_ID_TAREFA, id_task);
            long id = db.insert(TaskContract.Composicao.TABLE_NAME, null, dataToInsert);
            PushTask();
        } catch (Exception e) {
            Log.e(Tag, "M-CriaComposicaoDeTags");
            Log.e(Tag, e.getLocalizedMessage());
            Log.e(Tag, e.getStackTrace().toString());
        }
    }
}
