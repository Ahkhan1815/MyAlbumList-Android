package com.example.applicationb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private LocalAlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.albumRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new LocalAlbumAdapter(this::onRemoveAlbum, this::onModifyAlbum);

        recyclerView.setAdapter(adapter);
        viewModel.getLocalAlbums().observe(this, adapter::setAlbums);

        Button addAlbumButton = findViewById(R.id.addAlbumButton);
        addAlbumButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAlbumActivity.class);
            startActivity(intent);
        });

    }

    private void onRemoveAlbum(Album album) {
        viewModel.deleteAlbum(album);
        adapter.removeAlbum(album);
    }

    private void onModifyAlbum(Album album) {
        Intent intent = new Intent(MainActivity.this, EditAlbumActivity.class);
        intent.putExtra(EditAlbumActivity.ALBUM_ID, album.get_id());
        intent.putExtra(EditAlbumActivity.ALBUM_TITLE, album.getTitle());
        intent.putExtra(EditAlbumActivity.ALBUM_ARTIST, album.getArtist());
        startActivity(intent);
    }
}