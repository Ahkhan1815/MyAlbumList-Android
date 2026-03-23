package com.example.applicationb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AddAlbumViewModel extends AndroidViewModel {

    private final AlbumRepository repository;
    private final MutableLiveData<List<Album>> serverAlbums = new MutableLiveData<>();

    public AddAlbumViewModel(Application application) {
        super(application);
        repository = AlbumRepository.getInstance(application);
    }

    
    public LiveData<List<Album>> getServerAlbums() {
        return serverAlbums;
    }

    public void loadAlbums() {
        repository.getAlbums(serverAlbums::postValue);
    }

    public void searchAlbums(String search) {
        repository.searchAlbums(search, serverAlbums::postValue);
    }

    public void addAlbum(Album album) {
        repository.insert(album);
    }
}
