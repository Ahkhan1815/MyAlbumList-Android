package com.example.applicationb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddAlbumActivity extends AppCompatActivity {

    private AddAlbumViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_album);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addAlbumRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        

        
        RecyclerView recyclerView = findViewById(R.id.searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ServerAlbumAdapter adapter = new ServerAlbumAdapter(this::onAddAlbum);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AddAlbumViewModel.class);
        viewModel.getServerAlbums().observe(this, adapter::setAlbums);
        viewModel.loadAlbums();

        EditText searchEditText = findViewById(R.id.albumSearchEditText);
        Button searchButton = findViewById(R.id.albumSearchButton);

        searchButton.setOnClickListener(v -> {
            String search = searchEditText.getText().toString().trim();
            if (search.isEmpty()) {
                viewModel.loadAlbums();
            } else {
                viewModel.searchAlbums(search);
            }
        });
    }

    private void onAddAlbum(Album album) {
        viewModel.addAlbum(album);
        finish();
    }
}
