package com.example.noteapp.usecase.noteusecase;

import android.text.TextUtils;

import com.example.noteapp.entity.Note;
import com.example.noteapp.usecase.repository.NoteRepository;

import java.util.List;

public class NoteUseCase {

    private NoteRepository noteRepository;

    public NoteUseCase() {

        this.noteRepository = new NoteRepository();
    }

    public void insert(Note note) {
        noteRepository.insert(note);

    }

    public List<Note> getAll() {
        return noteRepository.getAll();

    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }


    public void update(Note note) {
        noteRepository.update(note);

    }

    public boolean validateName(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return true;
    }
}

