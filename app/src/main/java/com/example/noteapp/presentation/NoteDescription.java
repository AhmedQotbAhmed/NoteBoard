package com.example.noteapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDescription extends AppCompatActivity implements View.OnClickListener {
    private String title;
    private String Desc;
    private EditText titleNote;
    private EditText textArea;
    private ViewModel viewModel;
    private FloatingActionButton saveNote;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        Intent intent;
        intent = getIntent();

        titleNote = findViewById(R.id.title_note);
        textArea = findViewById(R.id.textArea_note);
        saveNote = findViewById(R.id.save_note);


        Bundle bundle = intent.getExtras();
        note = (Note) bundle.getSerializable("note");
        title = note.getTask();
        Desc = note.getDesc();

        titleNote.setText(title);
        textArea.setText(Desc);
        saveNote.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_note:
                title = titleNote.getText().toString();
                Desc = textArea.getText().toString();
                note.setDesc(Desc);
                note.setTask(title);
                viewModel = ViewModelProviders.of(NoteDescription.this).get(ViewModel.class);
                viewModel.update(note).observe(NoteDescription.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                    }
                });


                break;
        }

    }
}
