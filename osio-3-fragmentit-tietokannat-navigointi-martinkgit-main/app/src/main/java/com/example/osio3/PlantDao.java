package com.example.osio3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PlantDao {

    @Query("SELECT * FROM plant WHERE name LIKE :name LIMIT 1")
    Plant findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Plant... plants);

    @Query("SELECT * FROM plant")
    Plant[] getAllPlants();

    @Query("DELETE FROM plant")
    void deleteAll();

}
