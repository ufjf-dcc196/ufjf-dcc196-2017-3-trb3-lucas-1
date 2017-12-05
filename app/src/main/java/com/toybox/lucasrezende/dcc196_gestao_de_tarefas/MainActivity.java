package com.toybox.lucasrezende.dcc196_gestao_de_tarefas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Banco.TaskDbHelper;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Forms.NewTask;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Helper.TagHelper;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Helper.TaskHelper;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.View.TaskView;

public class MainActivity extends AppCompatActivity {

    private static final String Tag = "Main";
    private Button btnNovaTarefa;
    private Spinner spnFiltro;
    private ListView lstTarefas;
    private Button drop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovaTarefa = (Button) findViewById(R.id.btnNovaTarefa);
        spnFiltro = (Spinner) findViewById(R.id.spnFiltroTags);
        lstTarefas = (ListView) findViewById(R.id.lstListaDeTarefas);
        drop = (Button) findViewById(R.id.btndrop);

        TaskHelper.getInstance().initTaskAdapter(getBaseContext());
        TagHelper.getInstance().initTagAdapter(getBaseContext());
        lstTarefas.setAdapter(TaskHelper.getInstance().getTaskAdapter());
        spnFiltro.setAdapter(TagHelper.getInstance().getTagAdapter());
        TaskHelper.getInstance().PushTask();
        TagHelper.getInstance().PushTags();


        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = TaskDbHelper.getInstance(getBaseContext()).getReadableDatabase();
                TaskDbHelper.getInstance(getBaseContext()).DropAll(db);
                TaskHelper.getInstance().PushTask();
                TagHelper.getInstance().PushTags();
                TagHelper.getInstance().getTags().clear();
            }
        });

        spnFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTask.class);
                startActivity(intent);
            }
        });

        lstTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TaskView.class);
                intent.putExtra("id_Task",String.valueOf(l));
                startActivity(intent);
            }
        });


    }
}
