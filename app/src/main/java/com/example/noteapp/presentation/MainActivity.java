package com.example.noteapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.noteapp.R;
import com.example.noteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
// Room it's OFFLINE DB                                                                 "SQLite"

//    Table  "named" --> Entity
    // annotation -- > "@"
    // 1. make a jodo Class using a setter and getter
    // @Entity "to make a room knowing your class
    //@ColumnInfo ("name=.....'same of your var-name-id' ")   " to make a room creating and knowing your 'varColumn' "

    private ViewModel viewModel;
    private FloatingActionButton btn_save_;
    private EditText title_edt;
    private EditText desc_edt;
    private TextView dateTv;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

//    = ------------------- ----------------------------  =
// Main Activity to eases The UI
    // worker thread any activist exception main
// AsyncTask  class have a three function 1. pre excite  2. doing    3. postExcite
    // هتمسح ففي حاجه شبهه اسمها ا
    //  |
    //  V
    // Reactive programming "--- RX-Java  -----"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save_ = findViewById(R.id.save_btn_notes);
        title_edt = findViewById(R.id.title_edt);
        desc_edt = findViewById(R.id.edt_desc);
        dateTv = findViewById(R.id.date_tv);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        date = dateFormat.format(calendar.getTime());
        dateTv.setText(date);


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#0000'>" + " New note" + "</font>"));


        btn_save_.setOnClickListener(view->insertData());


        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.isInserted.observe(this, aBoolean -> {

            if (aBoolean) {
                Intent newIntent = new Intent(this, NotesBoard.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
            }

        });

    }

    void insertData() {
        String sTask = title_edt.getText().toString().trim();
        String sDesc = desc_edt.getText().toString().trim();

        Note note = new Note();
        note.setTitle(sTask);
        note.setDesc(sDesc);
        note.setFinishAt(dateTv.getText().toString());
        note.setFinished(false);
        viewModel.insertData(note);


//        NoteDatabase database = NoteDatabase.getDatabase(getApplicationContext());
//        NoteDao dao = database.noteDao();

        // java8  to make a single how it used bdal el onclick
//        Single.fromCallable(()->{dao.insert(note);
//            return true;}).
//                subscribeOn(Schedulers.io()).// هنا فاهم انه مش هيتنفز علي المين
//                observeOn(AndroidSchedulers.mainThread()).// لما تخلصي هتتنقلي علي المين سريد
//                subscribe(value -> Toast.makeText(MainActivity.this,"Task inserted successfully",Toast.LENGTH_LONG).show(),
//                error -> Log.e("error", error.getMessage()));
                // هنا معنلي الحاجه تديت success ','                    و التانيه في حالة error

    }


}
