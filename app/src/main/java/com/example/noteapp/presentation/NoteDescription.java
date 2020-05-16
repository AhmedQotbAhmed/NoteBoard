package com.example.noteapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDescription extends AppCompatActivity implements View.OnClickListener {
    private String title;
    private String Desc;
    private EditText titleNote;
    private EditText textArea;

    private FloatingActionButton saveNote;
    private Note note;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        Intent intent;
        intent = getIntent();

        titleNote = findViewById(R.id.title_note);
        textArea = findViewById(R.id.textArea_note);
        saveNote = findViewById(R.id.save_note);


        Bundle bundle = intent.getBundleExtra("bundle");
        position = intent.getIntExtra("position", -1);


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
                titleNote.requestFocus();
                textArea.requestFocus();
                title = titleNote.getText().toString();
                Desc = textArea.getText().toString();
                note.setDesc(Desc);
                note.setTask(title);

                Intent intent = new Intent();

                Bundle bundle = new Bundle();
                bundle.putSerializable("updatedNote", note);

                intent.putExtra("bundle", bundle);
                intent.putExtra("position", position);

                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
}
