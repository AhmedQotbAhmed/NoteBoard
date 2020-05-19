package com.example.noteapp.usecase.repository;

import com.example.noteapp.entity.Note;
import com.example.noteapp.presentation.core.NoteApplication;
import com.example.noteapp.usecase.database.NoteDao;
import com.example.noteapp.usecase.database.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private NoteDatabase noteDatabase;

    public NoteRepository() {
        this.noteDatabase = NoteDatabase.getDatabase(NoteApplication.getAppContext());
        this.noteDao = noteDatabase.noteDao();

    }

    public void insert(Note note) {

        noteDao.insert(note);
    }

    public List<Note> getAll() {
        return noteDao.getAll();

    }

    public void delete(Note note) {
        noteDao.delete(note);

    }


    public void update(Note note) {
        noteDao.update(note);

    }

}
