package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
// Room it's OFFLINE DB                                                     "SQLite"

//    Table  "named" --> Entity
    // annotation --> "@"
    // 1. make a jodo Class using a setter and getter
    // @Entity "to make a room knowing your class
    //@ColumnInfo ("name=.....'same of your var-name-id' ")   " to make a room creating and knowing your 'varColumn' "

    Button  btn_save_;
    Button btn_get_;
    EditText et_task;
    EditText et_desc;
    EditText et_fin;

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
        btn_get_=findViewById(R.id.bt_get);
        btn_save_=findViewById(R.id.bt_save);
        et_task=findViewById(R.id.edt_task);
        et_desc=findViewById(R.id.edt_desc);
        et_fin=findViewById(R.id.edt_finish);
//


        btn_save_.setOnClickListener(view->insertData());
       btn_get_.setOnClickListener(view->startActivity(new Intent(this,Notes.class)));




    }
    //

    void insertData() {
     String   sTask = et_task.getText().toString().trim();
     String sDesc = et_desc.getText().toString().trim();
     String   sFinishBy = et_fin.getText().toString().trim();
        Note note = new Note();
        note.setTask(sTask);
        note.setDesc(sDesc);
        note.setFinishBy(sFinishBy);
        note.setFinished(false);


        NoteDatabase database = NoteDatabase.getDatabase(getApplicationContext());
        NoteDao dao = database.noteDao();

        // java8  to make a single how it used bdal el onclick
        Single.fromCallable(()->{dao.insert(note);
            return true;}).
                subscribeOn(Schedulers.io()).// هنا فاهم انه مش هيتنفز علي المين
                observeOn(AndroidSchedulers.mainThread()).// لما تخلصي هتتنقلي علي المين سريد
                subscribe(value -> Toast.makeText(MainActivity.this,"Task inserted successfully",Toast.LENGTH_LONG).show(),
                error -> Log.e("error", error.getMessage()));
                // هنا معنلي الحاجه تديت success ','                    و التانيه في حالة error


    }







}
