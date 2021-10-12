package com.example.jokesApps.controller;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.example.jokesApps.R;
import com.example.jokesApps.model.Joke;

public class CardsDataAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private JokeLikeListener mJokeLikeListener;
    private SharedPreferences mSharedPreferences;
    private Joke mJoke;
    private String shareBody;
    private boolean liked = true;

    public CardsDataAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        mContext = context;
        mJokeLikeListener = (JokeLikeListener) context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        //supply the layout for your card
        ImageButton likeButton = contentView.findViewById(R.id.likeButton);
        TextView v = (contentView.findViewById(R.id.content));
        v.setText(getItem(position));

        if(mSharedPreferences.contains(getItem(position))){
            likeButton.setImageResource(R.drawable.ic_baseline_thumb_up_24);
            liked = false;
        }else {
            liked = true;
        }


       likeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               Toast.makeText(mContext, "LIKED", Toast.LENGTH_SHORT).show();
               if (liked){
                   likeButton.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                   liked = false;

                   mJoke = new Joke(getItem(position), true);
                   mJokeLikeListener.jokeIsLiked(mJoke);

               }else {
                   likeButton.setImageResource(R.drawable.ic_outline_thumb_up_24);
                   liked = true;

                   mJoke = new Joke(getItem(position), false);
                   mJokeLikeListener.jokeIsLiked(mJoke);
               }

           }
       });


       ImageButton shareButton = contentView.findViewById(R.id.shareButton);
       shareButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_SEND);
               shareBody = v.toString();
               intent.setType("text/plain");
               intent.putExtra(Intent.EXTRA_SUBJECT, "Mama Jokes");
               intent.putExtra(Intent.EXTRA_TEXT, shareBody);
               v.getContext().startActivity(Intent.createChooser(intent, "Sharing Via"));
           }
       });

        return contentView;
    }

}