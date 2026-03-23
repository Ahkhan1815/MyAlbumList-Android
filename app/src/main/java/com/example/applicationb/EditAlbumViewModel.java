package com.example.applicationb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class EditAlbumViewModel extends AndroidViewModel {

    private final AlbumRepository repository;

    
    public EditAlbumViewModel(Application application) {
        super(application);
        repository = AlbumRepository.getInstance(application);
    }


    public void updateAlbum(Album album) {
        repository.update(album);
        repository.updateAlbum(album);
    }
}
