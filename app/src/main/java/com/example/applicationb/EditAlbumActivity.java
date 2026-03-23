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

public class EditAlbumActivity extends AppCompatActivity {

    public static final String ALBUM_ID = "extraAlbumId";
    public static final String ALBUM_TITLE = "extraAlbumTitle";
    public static final String ALBUM_ARTIST = "extraAlbumArtist";

    private EditAlbumViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_album);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editAlbumRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText titleEditText = findViewById(R.id.editAlbumTitleEditText);
        EditText artistEditText = findViewById(R.id.editAlbumArtistEditText);
        Button updateButton = findViewById(R.id.updateAlbumButton);

        viewModel = new ViewModelProvider(this).get(EditAlbumViewModel.class);

        String albumId = getIntent().getStringExtra(ALBUM_ID);
        titleEditText.setText(getIntent().getStringExtra(ALBUM_TITLE));
        artistEditText.setText(getIntent().getStringExtra(ALBUM_ARTIST));

        updateButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String artist = artistEditText.getText().toString().trim();

            if (albumId != null) {
                Album updatedAlbum = new Album(albumId, title, artist);
                viewModel.updateAlbum(updatedAlbum);
                finish();
            }
        });
    }
}
