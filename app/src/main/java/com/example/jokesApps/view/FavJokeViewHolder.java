package com.example.jokesApps.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesApps.R;

import org.jetbrains.annotations.NotNull;

public class FavJokeViewHolder extends RecyclerView.ViewHolder {

    private ImageButton shareFavButton;
    private TextView favJokes;
    public FavJokeViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        shareFavButton = itemView.findViewById(R.id.shareFavJoke);
        favJokes = itemView.findViewById(R.id.txtFavJoke);

    }

    public ImageButton getShareFavButton() {
        return shareFavButton;
    }

    public void setShareFavButton(ImageButton shareFavButton) {
        this.shareFavButton = shareFavButton;
    }

    public TextView getFavJokes() {
        return favJokes;
    }

    public void setFavJokes(TextView favJokes) {
        this.favJokes = favJokes;
    }
}
