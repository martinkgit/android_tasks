package com.example.osio3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plant {

    @PrimaryKey
    public int platId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image_id")
    public int imageId;

    @ColumnInfo(name = "main_text")
    public String mainText;

}
