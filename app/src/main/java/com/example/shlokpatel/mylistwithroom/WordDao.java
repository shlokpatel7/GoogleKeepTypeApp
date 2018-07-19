package com.example.shlokpatel.mylistwithroom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static com.example.shlokpatel.mylistwithroom.Constants.*;
@Dao
public interface WordDao {

    @Insert
    void insertWord(Word word);

    @Query("SELECT * FROM "+table_name)
    LiveData<List<Word>> getAllWords();

    @Delete
    void deleteWord(Word word);

    @Query("DELETE FROM "+table_name)
    void deleteTable();


}
