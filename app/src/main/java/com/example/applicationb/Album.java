package com.example.applicationb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "albumTable")
public class Album {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String _id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "artist")
    private String artist;

    public Album(@NonNull String _id, @NonNull String title, @NonNull String artist){
        this._id = _id;
        this.title = title;
        this.artist = artist;
    }

    public String get_id(){
        return this._id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public void setArtist(@NonNull String artist) {
        this.artist = artist;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }
}
