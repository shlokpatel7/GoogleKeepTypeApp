package com.example.shlokpatel.mylistwithroom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public class MyAndroidViewModel extends AndroidViewModel {
    WordDatabase wordDatabase;
    LiveData<List<Word>> liveListData;
    public MyAndroidViewModel(@NonNull Application application) {
        super(application);

        Context context = application.getApplicationContext();
        wordDatabase = Room.databaseBuilder(
                context,
                WordDatabase.class,
                Constants.database_name)
                .allowMainThreadQueries()
                .build();
    }
    LiveData<List<Word>> getTasksFromDb() {
        if (liveListData == null) {
            liveListData = (LiveData<List<Word>>) wordDatabase.getWordDao().getAllWords();
        }
        return liveListData;
    }
}
