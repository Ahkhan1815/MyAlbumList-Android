package com.example.applicationb;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.app.Application;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Album>> localAlbums;

    private AlbumRepository repository;

    public MainViewModel(Application application){
        super(application);
        repository = AlbumRepository.getInstance(application);
        localAlbums = repository.getLocalAlbums();
    }

    public LiveData<List<Album>> getLocalAlbums() {
        return localAlbums;
    }

    public void deleteAlbum(Album album){
        repository.delete(album);
    }
}
