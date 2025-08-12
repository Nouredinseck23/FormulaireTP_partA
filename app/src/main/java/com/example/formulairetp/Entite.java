package com.example.formulairetp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Entite {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String email;
    public String phone;
    public long createdAt;
}

