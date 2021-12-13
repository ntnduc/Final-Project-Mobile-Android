package com.example.todolist;

public class DataTask {
    private int id;
    private String title;
    private Boolean done;
    private Boolean important;

    public DataTask(int id,String title, Boolean done, Boolean important){
        super();
        this.id = id;
        this.title = title;
        this.done = done;
        this.important = important;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id){
        this.id = id;
    }

    public Boolean getDone() {
        return done;
    }

    public Boolean getImportant() {
        return important;
    }

    public String getTitle() {
        return title;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "DataTask{" +
                "title='" + title + '\'' +
                ", done=" + done +
                ", important=" + important +
                '}';
    }
}
