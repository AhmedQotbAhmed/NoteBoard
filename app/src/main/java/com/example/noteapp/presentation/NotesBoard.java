package com.example.noteapp.presentation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.example.noteapp.usecase.NotesAdaptor;
import com.example.noteapp.usecase.SwipeToDeleteCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesBoard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private NotesAdaptor adaptor;
    private ConstraintLayout constraintLayout;
    private boolean undo = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        constraintLayout = findViewById(R.id.conLayout);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView=findViewById(R.id.recycler_Note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // RecyclerView Adaptor
                adaptor = new NotesAdaptor(notes);
                recyclerView.setAdapter(adaptor);

            }
        });


        enableSwipeToDeleteAndUndo();


//        Intent intent;
//        intent = getIntent();
//
//        Bundle bundle = intent.getExtras();
//        Note  note = (Note) bundle.getSerializable("updatedNote");
//
//
//        if(note!=null ){
//
//            update( note);
//
//
//
//        }
//        onActivityResult();




    }


    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getLayoutPosition();
//                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();

                final Note item = adaptor.getData().get(position);


                adaptor.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);

                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adaptor.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                        undo = false;
                    }
                });


                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

                if (undo) {

                    viewModel.delete(item).observe(NotesBoard.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {


                        }
                    });

                }

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);


    }

    public void update(Note note) {


        viewModel.update(note).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

//                adaptor.restoreItem(note, position);
//                recyclerView.scrollToPosition(position);


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Note note = adaptor.onActivityResult(requestCode, resultCode, data);

        viewModel.update(note).observe(NotesBoard.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                viewModel.getData().observe(NotesBoard.this, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        // RecyclerView Adaptor
                        adaptor = new NotesAdaptor(notes);
                        recyclerView.setAdapter(adaptor);

                    }
                });


            }
        });

    }


}



