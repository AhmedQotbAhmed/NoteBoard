package com.example.noteapp.usecase.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.entity.Note;
// annotation

@Database(entities = {Note.class}, version = 1, exportSchema = false)
//My Class ---->
//              |
//              |
//              |
//              V
public abstract class NoteDatabase extends RoomDatabase {

    private static volatile NoteDatabase INSTANCE; // عايز اخد اوبجيكت بمعني انسيستنس

    //builderPattern
    public static NoteDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) { /// synchronized --> block all tread after finish this operation first
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            NoteDatabase.class, "note_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    // volatile
    // thread 2
    //1. MainThread    2.garbage Collector
    //علشان البرنامج مش يضرب بنعمل حاجه اسمها  anther api thread
    // so you need to make a thread for all annotation '@'
    //   and handled synchronized ''

    public abstract NoteDao noteDao();


}
