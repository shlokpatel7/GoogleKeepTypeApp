package com.example.shlokpatel.mylistwithroom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {
    String task;

    @PrimaryKey(autoGenerate = true)
    int id;

    public Word(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
