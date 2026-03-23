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

public class LocalAlbumAdapter extends RecyclerView.Adapter<LocalAlbumAdapter.LocalAlbumViewHolder> {

    private final List<Album> albumList = new ArrayList<>();
    private final Consumer<Album> onRemove;
    private final Consumer<Album> onModify;

    public LocalAlbumAdapter(Consumer<Album> onRemove, Consumer<Album> onModify) {
        this.onRemove = onRemove;
        this.onModify = onModify;
    }

    @NonNull
    @Override
    public LocalAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new LocalAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalAlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.artist.setText(album.getArtist());
        holder.removeButton.setOnClickListener(v -> onRemove.accept(album));
        holder.modifyButton.setOnClickListener(v -> onModify.accept(album));
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

    public void removeAlbum(Album album) {
        int index = albumList.indexOf(album);
        if (index >= 0) {
            albumList.remove(index);
            notifyItemRemoved(index);
        }
    }

    public static class LocalAlbumViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView artist;
        Button removeButton;
        Button modifyButton;

        
        public LocalAlbumViewHolder(View item) {
            super(item);
            title = item.findViewById(R.id.albumTitleTextView);
            artist = item.findViewById(R.id.albumArtistTextView);
            removeButton = item.findViewById(R.id.removeAlbumButton);
            modifyButton = item.findViewById(R.id.modifyAlbumButton);
        }

    }
}
