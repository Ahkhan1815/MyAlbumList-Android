package com.example.applicationb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ServerAlbumAdapter extends RecyclerView.Adapter<ServerAlbumAdapter.ServerAlbumViewHolder> {

    private final List<Album> albumList = new ArrayList<>();
    private final Consumer<Album> onAdd;

    public ServerAlbumAdapter(Consumer<Album> onAdd) {
        this.onAdd = onAdd;
    }

    @NonNull
    @Override
    public ServerAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item_server, parent, false);
        return new ServerAlbumViewHolder(view);
    }

    
    @Override
    public void onBindViewHolder(@NonNull ServerAlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.artist.setText(album.getArtist());
        holder.addButton.setOnClickListener(v -> onAdd.accept(album));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void setAlbums(List<Album> albums) {
        albumList.clear();
        if (albums != null) {
            albumList.addAll(albums);
        }
        notifyDataSetChanged();
    }

    public static class ServerAlbumViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView artist;
        Button addButton;

        public ServerAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.albumTitleTextView);
            artist = itemView.findViewById(R.id.albumArtistTextView);
            addButton = itemView.findViewById(R.id.addAlbumButton);
        }
    }
}
