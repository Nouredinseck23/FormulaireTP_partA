package com.example.formulairetp;

import android.content.Context;

import androidx.room.Room;

public class DbProvider {

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase get(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    ctx.getApplicationContext(),
                    AppDatabase.class,
                    "app.db"
            ).build();
        }
        return INSTANCE;
    }
}
