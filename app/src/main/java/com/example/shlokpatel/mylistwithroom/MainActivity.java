package com.example.shlokpatel.mylistwithroom;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {
    List<Word> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference rootRef = firebaseDatabase.getReference();

        final DatabaseReference childRef = rootRef.child("abcd");
        FloatingActionButton floatingActionButton=findViewById(R.id.fab2);
        FloatingActionButton actionButton = findViewById(R.id.fab);
        final View view = LayoutInflater.from(this).inflate(R.layout.alert_layout, null, true);
        WordDatabase wordDatabase=Room.databaseBuilder(this,WordDatabase.class,Constants.database_name).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        final WordDao wordDao=wordDatabase.getWordDao();
        final RecyclerView recyclerView=findViewById(R.id.recyclerView);
        final LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        final TodoAdapter todoAdapter=new TodoAdapter(arrayList,wordDao,this,MainActivity.this);
        recyclerView.setAdapter(todoAdapter);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = view.findViewById(R.id.editText);
                String note = editText.getText().toString();
                if (note.equals(""))
                    Toast.makeText(MainActivity.this, "Enter sometihing", Toast.LENGTH_SHORT).show();
                else {
                    Word word=new Word(note);
                    wordDao.insertWord(word);

                    todoAdapter.notifyDataSetChanged();
                    childRef.push().setValue(word);
                    editText.setText("");
                }
            }
        }).create();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        LiveData listLiveData=wordDao.getAllWords();
        listLiveData.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                recyclerView.setLayoutManager(manager);
                TodoAdapter todoAdapter1=new TodoAdapter(words,wordDao,MainActivity.this,MainActivity.this);
                recyclerView.setAdapter(todoAdapter1);
            }
        });


    }
}
