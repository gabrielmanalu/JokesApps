package com.example.jokesApps.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesApps.R;
import com.example.jokesApps.model.Joke;
import com.example.jokesApps.model.JokeManager;
import com.example.jokesApps.view.FavJokeViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavJokesListAdapter extends RecyclerView.Adapter<FavJokeViewHolder> {

    private List<Joke> mJokesList;
    private Context mContext;


    public Context getContext(){
        return mContext;
    }

    public FavJokesListAdapter(List jokesList, Context context){
        mJokesList = jokesList;
        mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public FavJokeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_jokes_list, parent, false);
        return new FavJokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavJokeViewHolder holder, int position) {

        String jokeText = mJokesList.get(position).getJoke();
        holder.getFavJokes().setText(jokeText);
        holder.getShareFavButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String shareBody =jokeText;
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mama Jokes");
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(intent, "Sharing Via"));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mJokesList.size();
    }
}
