package com.example.noteapp.usecase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.example.noteapp.presentation.NoteDetails;

import java.util.ArrayList;
import java.util.List;

public class NotesAdaptor extends RecyclerView.Adapter<NotesAdaptor.NoteHolder> implements Filterable {
    private Context context;
    private List<Note> list;
    private List<Note> filterList;


    //    Constructor

    public NotesAdaptor(List<Note> list) {
        this.list = list;
        filterList = list;

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        NoteHolder hold = new NoteHolder(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note current = filterList.get(position);
        holder.textTask.setText(current.getTitle());
        holder.item_layout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.content_transition_animation));

        holder.dateTimeDisplay.setText(current.getFinishAt());

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.item_layout) {


                    Intent intent = new Intent(context, NoteDetails.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("note", current);
                    intent.putExtras(bundle);
                    intent.putExtra("position", position);

//
                    ((Activity) context).startActivityForResult(intent, 1);

                }

            }
        });


    }


    public void removeItem(int position) {
        filterList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Note item, int position) {
        filterList.add(position, item);

        notifyItemInserted(position);
    }

    public void adaptorNotify(Note item, int position) {
        filterList.set(position, item);
        notifyItemChanged(position, item);

    }

    public List<Note> getData() {
        return filterList;
    }

    public List<Note> getFilterList() {
        return filterList;
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filterList = (list);
                } else {
                    List<Note> filteredList = new ArrayList<>();
                    for (Note note : list) {

                        if (note.getTitle().contains(charString.toLowerCase())) {

                            filteredList.add(note);

                        }
                    }
                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filterList = (List<Note>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


    class NoteHolder extends RecyclerView.ViewHolder {

        TextView textTask;
        LinearLayout item_layout;
        private TextView dateTimeDisplay;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textTask = itemView.findViewById(R.id.task_item);
            item_layout = itemView.findViewById(R.id.item_layout);
            dateTimeDisplay = itemView.findViewById(R.id.date_item);

        }
    }
}
