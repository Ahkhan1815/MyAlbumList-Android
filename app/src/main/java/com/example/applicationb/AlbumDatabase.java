package com.example.applicationb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Album.class}, version = 1)
public abstract class AlbumDatabase extends RoomDatabase {

    public abstract AlbumDao albumDao();

    private static volatile AlbumDatabase INSTANCE;

    public static AlbumDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (AlbumDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AlbumDatabase.class, "album_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
