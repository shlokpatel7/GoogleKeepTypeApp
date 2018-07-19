package com.example.shlokpatel.mylistwithroom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Word.class},version = 2,exportSchema = false)
public abstract class WordDatabase extends RoomDatabase{
    public abstract WordDao getWordDao();
}
