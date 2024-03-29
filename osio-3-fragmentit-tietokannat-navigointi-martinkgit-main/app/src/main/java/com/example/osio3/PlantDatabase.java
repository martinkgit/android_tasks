package com.example.osio3;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Plant.class}, version = 1)
public abstract class PlantDatabase extends RoomDatabase {
    public abstract PlantDao plantDao();

}
