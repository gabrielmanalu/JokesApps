package com.example.jokesApps.model;

public class Joke {
    private String joke;
    private boolean jokeIsLiked;

    public Joke (String joke, boolean jokeIsLiked){
        this.joke = joke;
        this.jokeIsLiked = jokeIsLiked;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public boolean jokeIsLiked() {
        return jokeIsLiked;
    }

    public void setJokeIsLiked(boolean jokeIsLiked) {
        this.jokeIsLiked = jokeIsLiked;
    }
}
