package com.example.noteapp.usecase.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapp.entity.Note;

import java.util.List;
@Dao
public interface NoteDao {
    //Data access object '@Dao'

    @Query("SELECT * FROM NoteEntity")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note task);

}