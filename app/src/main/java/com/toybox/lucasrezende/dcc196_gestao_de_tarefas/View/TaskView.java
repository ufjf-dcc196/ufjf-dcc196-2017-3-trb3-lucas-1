package com.toybox.lucasrezende.dcc196_gestao_de_tarefas.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Helper.TaskHelper;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.MainActivity;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Model.Task;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.R;

public class TaskView extends AppCompatActivity {

    //variaveis
    private Task myTask;

    //widgets
    private TextView titulo;
    private TextView descricao;
    private Spinner status;
    private Spinner dificuldade;
    private Button apagar;
    private Button editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        titulo = (TextView)findViewById(R.id.txtTituloTarefaView);
        descricao = (TextView)findViewById(R.id.txtDescricaoTarefa);
        status = (Spinner) findViewById(R.id.spnStatusView);
        dificuldade = (Spinner) findViewById(R.id.spnDificuldadeView);
        apagar = (Button)findViewById(R.id.btnApagarTarefa);
        editar = (Button)findViewById(R.id.btnEditarTarefa);
        ArrayAdapter<String> dificuldadesPossiveis = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,TaskHelper.getInstance().getDificuldadeList());
        ArrayAdapter<String> statusPossiveis = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,TaskHelper.getInstance().getStatusList());



        myTask = TaskHelper.getInstance().SearchForTask(Integer.parseInt(getIntent().getStringExtra("id_Task")));

        titulo.setText(myTask.getTitulo().toString());
        descricao.setText(myTask.getDescricao().toString());
        status.setAdapter(statusPossiveis);
        dificuldade.setAdapter(dificuldadesPossiveis);
        dificuldade.setSelection(myTask.getDificuldade());
        //status.setSelection(myTask.getStatus());

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskHelper.getInstance().DeleteTask(myTask);
                TaskHelper.getInstance().PushTask();
                Toast.makeText(getApplicationContext(), "Tarefa Deletada!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TaskView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //efetiva alteraçoes
                myTask.setTitulo(titulo.getText().toString());
                myTask.setDescricao(descricao.getText().toString());
                myTask.setDificuldade(Integer.parseInt((dificuldade.getSelectedItem()).toString())-1);
                //myTask.setStatus();
                //envia alteraçoes
                TaskHelper.getInstance().UpdateTask(myTask);
                //TaskHelper.getInstance().PushTask();
                Toast.makeText(getApplicationContext(), "Tarefa Atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TaskView.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
