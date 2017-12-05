package com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Forms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Banco.TaskContract;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Helper.TagHelper;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Helper.TaskHelper;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.MainActivity;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Model.Task;
import com.toybox.lucasrezende.dcc196_gestao_de_tarefas.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewTask extends AppCompatActivity {

    //Variaveis
    private Task newTask;
    private ArrayList<Task> tarefas = new ArrayList<>();
    private String array[];
    private long id_task;
    private long id_Tag;


    //Widgets
    private TextView tituloTarefa;
    private TextView descricaoTarefa;
    private Spinner status;
    private Spinner dificuldade;
    private Button cancelar;
    private Button confirmar;
    private TextView tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        tituloTarefa = (TextView) findViewById(R.id.txtTituloNewTask);
        descricaoTarefa = (TextView) findViewById(R.id.txtDescricaoTarefa);
        status = (Spinner) findViewById(R.id.spnStatus);
        dificuldade = (Spinner) findViewById(R.id.spnDificuldade);
        cancelar = (Button) findViewById(R.id.btnCancelar);
        confirmar = (Button) findViewById(R.id.btnConfirmar);
        tags = (TextView) findViewById(R.id.txtTagsAdcionar);

        ArrayAdapter<String> dificuldadesPossiveis = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TaskHelper.getInstance().getDificuldadeList());
        ArrayAdapter<String> statusPossiveis = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TaskHelper.getInstance().getStatusList());
        status.setAdapter(statusPossiveis);
        dificuldade.setAdapter(dificuldadesPossiveis);


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Adiçao Cancelada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewTask.this, MainActivity.class);
                startActivity(intent);
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adiciona a tarefa
                newTask = new Task();
                newTask.setTitulo(tituloTarefa.getText().toString());
                newTask.setDescricao(descricaoTarefa.getText().toString());
                newTask.setDificuldade(Integer.parseInt(dificuldade.getSelectedItem().toString()) - 1);
                newTask.setStatus(status.getSelectedItem().toString());
                tarefas.add(newTask);
                id_task = TaskHelper.getInstance().AddNewTask(newTask);

                //adiciona novas tags
                array = tags.getText().toString().split(",");
                adicionaNovasTags();

                //vincula as tag ao objeto
                vinculoTarefaTag();

                //feedback
                Toast.makeText(getApplicationContext(), "Tarefa Adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewTask.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void adicionaNovasTags() {
       // TagHelper.getInstance().AddNewTag("isto entra");
        for(int i = 0 ; i < array.length ; i++){
            if(TagHelper.getInstance().verificaTag(array[i])) {
                TagHelper.getInstance().AddNewTag(array[i]);
            }
        }
    }

    public void vinculoTarefaTag(){
        for(int i = 0; i < array.length; i++) {
            id_Tag = TagHelper.getInstance().SearchTag(array[i]);
            if(id_Tag != -1) {
                TaskHelper.getInstance().CriaComposicaoDeTags(id_task, id_Tag);
            }
        }
    }
}

