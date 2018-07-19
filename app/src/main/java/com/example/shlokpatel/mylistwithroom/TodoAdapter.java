package com.example.shlokpatel.mylistwithroom;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyHolder> {
    List<Word> arrayList;
    WordDao dao;
    Context context;
    Activity activity;

    public TodoAdapter(List<Word> arrayList, WordDao dao, Context context, Activity activity) {
        this.arrayList = arrayList;
        this.dao = dao;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (arrayList != null) {
            Word current = arrayList.get(position);
            holder.textView.setText(current.getTask());
        } else {
            // Covers the case of data not being ready yet.
            Toast.makeText(context, "No Word", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList != null)
            return arrayList.size();
        else return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                int counter = 0;

                @Override
                public void onClick(View v) {

                    final FloatingActionButton deleteBtn = activity.findViewById(R.id.fab2);
                    final FloatingActionButton addBtn = activity.findViewById(R.id.fab);
                    if (counter == 0) {
                        addBtn.setVisibility(View.GONE);
                        deleteBtn.setVisibility(View.VISIBLE);
                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dao.deleteWord(arrayList.get(getAdapterPosition()));
                                arrayList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                                notifyItemRangeChanged(getAdapterPosition(), arrayList.size());
                                notifyDataSetChanged();
                                deleteBtn.setVisibility(View.GONE);
                                addBtn.setVisibility(View.VISIBLE);
                            }
                        });
                        counter = 1;
                    } else {
                        deleteBtn.setVisibility(View.GONE);
                        addBtn.setVisibility(View.VISIBLE);
                        counter=0;
                    }

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("task", arrayList.get(getAdapterPosition()).getTask());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT).show();

                    return true;
                }
            });

        }
    }

    void setWords(List<Word> words) {
        arrayList = words;
        notifyDataSetChanged();
    }
}
