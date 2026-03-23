package com.example.applicationb;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONObject;

public class ApplicationB extends Application {

    private io.socket.client.Socket socket;
    private AlbumRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = AlbumRepository.getInstance(this);
        connectSocket();
    }

    private void connectSocket() {
        try {
            socket = io.socket.client.IO.socket(BuildConfig.SERVER_URL);
            socket.on("albumsUpdated", args -> {
                JSONObject data = (JSONObject) args[0];
                String action = data.optString("action");
                String id = data.optString("_id");
                String message = "DB Changed: " + action + " (" + id + ")";
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show());

                
                if (action.equals("deleted")) {
                    repository.deleteByID(id);
                    Log.i("Socket", "Deleted: " + id);
                }
                else if (action.equals("updated")) {
                    String title = data.optString("title");
                    String artist = data.optString("artist");
                    Album updated = new Album(id, title, artist);
                    
                    repository.update(updated);
                    Log.i("Socket", "Updated: " + id);
                }


            });
            socket.connect();
        } catch (Exception e) {
            Handler handler = new Handler(Looper.getMainLooper());
            Log.e("Socket", e.getMessage());
        }
    }
}
