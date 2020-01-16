package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdaptor extends RecyclerView.Adapter<NotesAdaptor.NoteHolder> {
    Context context;
   private List<Note>list;
    //    Constructor
    public NotesAdaptor(List<Note> list) {
        this.list=list;

    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();

        View view= LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_layout,parent,false);

        NoteHolder hold=new NoteHolder(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note current= list.get(position);
        holder.textTask.setText(current.getTask());
        holder.textDescription.setText(current.getDesc());
        holder.textFinishBy.setText(current.getFinishBy());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView textTask;
        TextView textDescription;
        TextView textFinishBy;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textTask = itemView.findViewById(R.id.task_item);
            textDescription = itemView.findViewById(R.id.desc_item);
            textFinishBy = itemView.findViewById(R.id.finish_item);
        }
    }
}
