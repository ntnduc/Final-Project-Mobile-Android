package com.example.todolist;

public class DataTask {
    private String id;
    private String value;
    private Boolean check;
    private Boolean mark;

    DataTask(String id, String value, Boolean check, Boolean mark){
        super();
        this.id = id;
        this.value = value;
        this.check = check;
        this.mark = mark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "DataTask{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", check=" + check +
                ", mark=" + mark +
                '}';
    }
}
