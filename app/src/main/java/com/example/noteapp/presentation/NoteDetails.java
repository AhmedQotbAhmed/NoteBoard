package com.example.noteapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetails extends AppCompatActivity implements View.OnClickListener {
    private String title;
    private String Desc;
    private EditText titleNote;
    private EditText textArea;
    private int position;
    private FloatingActionButton saveNote;
    private Note note;
    private TextView dateView;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);


        getSupportActionBar().hide();

        dateView = findViewById(R.id.date_view);
        titleNote = findViewById(R.id.title_note);
        textArea = findViewById(R.id.textArea_note);
        saveNote = findViewById(R.id.save_note);


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#0000'>" + "Note" + "</font>"));
        Intent intent;
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = intent.getIntExtra("position", -1);
        note = (Note) bundle.getSerializable("note");
        title = note.getTitle();
        date = note.getFinishAt();
        Desc = note.getDesc();
        titleNote.setText(title);
        textArea.setText(Desc);
        dateView.setText(date);
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
                note.setTitle(title);
                note.setFinishAt(date);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("updatedNote", note);

                intent.putExtras(bundle);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
}
