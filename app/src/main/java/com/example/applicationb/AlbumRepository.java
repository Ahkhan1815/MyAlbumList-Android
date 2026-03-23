package com.example.applicationb;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import android.util.Log;

import android.app.Application;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {
    private static volatile AlbumRepository instance;

    private AlbumDao albumDao;
    private AlbumAPI api;
    private LiveData<List<Album>> localAlbums;
    private final MutableLiveData<List<Album>> serverAlbums = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static AlbumRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (AlbumRepository.class) {
                if (instance == null) {
                    instance = new AlbumRepository(application);
                }
            }
        }
        return instance;
    }

    private AlbumRepository(Application application) {
        AlbumDatabase db = AlbumDatabase.getInstance(application);
        albumDao = db.albumDao();
        localAlbums = albumDao.getAlbums();
        api = RetrofitClient.getRetrofitInstance().create(AlbumAPI.class);
    }

    // Local DB
    public LiveData<List<Album>> getLocalAlbums() {
        return localAlbums;
    }

    public LiveData<List<Album>> getServerAlbums() {
        return serverAlbums;
    }

    public LiveData<Album> getAlbumByID(String id) {
        return albumDao.getAlbumByID(id);
    }

    public void deleteByID(String id) {
        executor.execute(() -> albumDao.deleteByID(id));
    }

    public void insert(Album album) {
        executor.execute(() -> albumDao.insert(album));
    }

    public void delete(Album album) {
        executor.execute(() -> albumDao.delete(album));
    }

    public void update(Album album) {
        executor.execute(() -> albumDao.update(album));
    }


    // Retrofit
    
    public void updateAlbum(Album album){
        api.updateAlbum(album).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    return;
                }
                Log.e("Retrofit", "Server error: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Retrofit", t.getMessage());
            }
        });
    }




    public void getAlbums(Consumer<List<Album>> callback) {
        api.getAlbums().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.accept(response.body());
                    Log.i("Retrofit", "Response: " + response.code());
                } else {
                    Log.e("Retrofit", "Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.e("Retrofit", t.getMessage());
            }
        });

    }


    public void searchAlbums(String search, Consumer<List<Album>> callback) {
        api.searchAlbums(search).enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.accept(response.body());
                    Log.i("Retrofit", "Response: " + response.code());
                } else {
                    Log.e("Retrofit", "Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.e("Retrofit", t.getMessage());
            }
        });
    }

    public void searchServerAlbums(String search) {
        searchAlbums(search, serverAlbums::postValue);
    }

}
