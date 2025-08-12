package com.example.formulairetp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Entite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
