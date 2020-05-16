package com.example.noteapp.presentation.core;

import android.app.Application;
import android.content.Context;

public class NoteApplication extends Application {
    //     3lshan a5ad el context w a3raf ast5dmha fi kol 7ata

    // Mtancash t3adl fi el manifest 3la  <application  // android:name=".presentation.core.NoteApplication"

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {

        return context;

    }


}
