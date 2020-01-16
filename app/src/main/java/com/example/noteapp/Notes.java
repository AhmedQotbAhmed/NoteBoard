package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Notes extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        recyclerView=findViewById(R.id.recycler_Note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }


    private void getData(){
        NoteDatabase database = NoteDatabase.getDatabase(getApplicationContext());
        final NoteDao dao = database.noteDao();
        Single.fromCallable(() -> dao.getAll()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(list_Notes -> {
                    NotesAdaptor adaptor=new NotesAdaptor(list_Notes);
                    // RecyclerView Adaptor
                    recyclerView.setAdapter(adaptor);
                }, error -> Log.e("error", error.getMessage()));
    }
}
