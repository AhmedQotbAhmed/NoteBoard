package com.example.noteapp.presentation;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.noteapp.entity.Note;
import com.example.noteapp.usecase.noteusecase.NoteUseCase;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ViewModel extends androidx.lifecycle.ViewModel {
    public MutableLiveData<Boolean> isInserted;
    public MutableLiveData<List<Note>> Inserted;
    public MutableLiveData<Boolean> isDelete;
    public MutableLiveData<Boolean> isUpdate;

    public NoteUseCase useCase;


    public ViewModel() {
        this.isInserted = new MutableLiveData();
        this.isDelete = new MutableLiveData();
        this.isUpdate = new MutableLiveData();
        useCase = new NoteUseCase();
    }

    public LiveData<Boolean> insertData(Note note) {

        Single.fromCallable((Callable<Object>) () -> {
            useCase.insert(note);
            return true;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(value -> {
                    Log.e("success", "success");
                    isInserted.postValue(true);
                }, error -> {
                    Log.e("error", error.getMessage());
                    isInserted.postValue(false);
                });
        return isInserted;
    }

    public LiveData<List<Note>> getData() {

        if (Inserted == null) {
            Inserted = new MutableLiveData<List<Note>>();
            LoadData();
        }
        return Inserted;
    }


    public LiveData<Boolean> delete(Note note) {

        Single.fromCallable((Callable<Object>) () -> {
            useCase.delete(note);
            return true;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(value -> {
                    Log.e(" delete data success", "success");
                    isDelete.postValue(true);
                }, error -> {
                    Log.e("error", error.getMessage());
                    isDelete.postValue(false);
                });
        return isDelete;

    }


    public LiveData<Boolean> update(Note note) {

        Single.fromCallable((Callable<Object>) () -> {
            useCase.update(note);
            return true;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(value -> {
                    Log.e(" adaptorNotify success", "success");
                    isUpdate.postValue(true);
                }, error -> {
                    Log.e("error", error.getMessage());
                    isUpdate.postValue(false);
                });
        return isUpdate;
    }


    private void LoadData() {

        Single.fromCallable(() -> useCase.getAll()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(list_Notes -> {
                    Inserted.postValue(list_Notes);
                }, error -> Log.e("error", error.getMessage()));

    }


}
