package com.toybox.lucasrezende.dcc196_gestao_de_tarefas.Model;

/**
 * Created by LucasRezende on 28/11/2017.
 */




public class Task {

    private int id;
    private String titulo;
    private String descricao;
    private int dificuldade;
    private String status;
    //A_fazer //Em_execução //Bloqueada //Concluída,


    public Task() {}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
