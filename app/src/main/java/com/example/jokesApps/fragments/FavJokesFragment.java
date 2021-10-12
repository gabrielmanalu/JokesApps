package com.example.jokesApps.fragments;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokesApps.R;
import com.example.jokesApps.controller.FavJokesListAdapter;
import com.example.jokesApps.model.Joke;
import com.example.jokesApps.model.JokeManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class FavJokesFragment extends Fragment {

    RecyclerView mRecyclerView;
    FavJokesListAdapter mFavJokesAdapter;
    JokeManager mJokeManager;
    private Joke deletedJoke;
    private List<Joke> mJokeList = new ArrayList<>();

    public FavJokesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FavJokesFragment newInstance() {
        FavJokesFragment fragment = new FavJokesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public void onAttach(@NonNull @org.jetbrains.annotations.NotNull Context context) {
        super.onAttach(context);

        mJokeManager = new JokeManager(context);
        mJokeList.clear();
        if(mJokeManager.retrievedJokes().size()>0){
            for (Joke joke : mJokeManager.retrievedJokes()){
                mJokeList.add(joke);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fav_jokes, container, false);
        if (view != null){
            mRecyclerView = view.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFavJokesAdapter = new FavJokesListAdapter(mJokeList, getContext());
            mRecyclerView.setAdapter(mFavJokesAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);

        }

        return view;

    }

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                case ItemTouchHelper.RIGHT:

                    deletedJoke = mJokeList.get(position);
                    mJokeManager.deleteJoke(deletedJoke);
                    mJokeList.remove(position);
                    mFavJokesAdapter.notifyItemRemoved(position);
                    mFavJokesAdapter.notifyDataSetChanged();

                    Snackbar.make(mRecyclerView, "Joke is \" Removed\" ", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mJokeList.add(position, deletedJoke);
                                    mJokeManager.saveJoke(deletedJoke);
                                    mFavJokesAdapter.notifyItemInserted(position);
                                }
                            }).show();
            break;
            }
        }
    };


}