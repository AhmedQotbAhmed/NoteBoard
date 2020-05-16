package com.example.noteapp.usecase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.example.noteapp.presentation.NoteDescription;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NotesAdaptor extends RecyclerView.Adapter<NotesAdaptor.NoteHolder> {
    private Context context;
    private List<Note> list;

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

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.item_layout) {

                    Intent intent = new Intent(context, NoteDescription.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("note", current);
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("position", position);


                    ((Activity) context).startActivityForResult(intent, 1);

                }

            }
        });


    }


    public Note onActivityResult(int requestCode, int resultCode, Intent data) {
        Note note = null;
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getBundleExtra("bundle");
                int position = data.getIntExtra("position", -1);
                Toast.makeText(context.getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                note = (Note) bundle.getSerializable("updatedNote");

                update(note, position);

            }
        }
        return note;

    }


    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Note item, int position) {
        list.add(position, item);

        notifyItemInserted(position);
    }

    public void update(Note item, int position) {
        list.set(position, item);
        notifyItemChanged(position, item);
    }

    public List<Note> getData() {
        return list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView textTask;
        TextView textDescription;
        LinearLayout item_layout;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textTask = itemView.findViewById(R.id.task_item);
            textDescription = itemView.findViewById(R.id.desc_item);
            item_layout = itemView.findViewById(R.id.item_layout);

        }
    }
}
