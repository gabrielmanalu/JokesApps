package com.example.jokesApps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.arasthel.asyncjob.AsyncJob;
import com.example.jokesApps.controller.CardsDataAdapter;
import com.example.jokesApps.controller.JokeLikeListener;
import com.example.jokesApps.model.Joke;
import com.example.jokesApps.model.JokeManager;
import com.wenchao.cardstack.CardStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CardStack.CardEventListener, JokeLikeListener {

    CardStack mCardStack;
    CardsDataAdapter mCardAdapter;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private JokeManager mJokeManager;
    private List<Joke> allJokes = new ArrayList<>();
    private List<Joke> randomJokes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCardStack = findViewById(R.id.container);
        mJokeManager = new JokeManager(this);
        mCardStack.setContentResource(R.layout.jokes_card);
        mCardStack.setStackMargin(20);
        mCardAdapter = new CardsDataAdapter(this,0);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        mShakeDetector.setOnShakeListener(count -> handleShakeEvent());

        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(() -> {
//                         Do some background work
                    try {

                        JSONObject rootObject = new JSONObject(loadJSONFromAsset());

                        JSONArray fatJokes = rootObject.getJSONArray("fat");
                        addJokesToArrayList( fatJokes, allJokes );
                        JSONArray stupidJokes = rootObject.getJSONArray("stupid");
                        addJokesToArrayList( stupidJokes, allJokes );
                        JSONArray uglyJokes = rootObject.getJSONArray("ugly");
                        addJokesToArrayList( uglyJokes, allJokes );
                        JSONArray nastyJokes = rootObject.getJSONArray("nasty");
                        addJokesToArrayList( nastyJokes, allJokes );
                        JSONArray odorJokes = rootObject.getJSONArray("odor");
                        addJokesToArrayList( odorJokes, allJokes );
                        JSONArray hairyJokes = rootObject.getJSONArray("hairy");
                        addJokesToArrayList( hairyJokes, allJokes );
                        JSONArray baldJokes = rootObject.getJSONArray("bald");
                        addJokesToArrayList( baldJokes, allJokes );
                        JSONArray oldJokes = rootObject.getJSONArray("old");
                        addJokesToArrayList( oldJokes, allJokes );
                        JSONArray poorJokes = rootObject.getJSONArray("poor");
                        addJokesToArrayList( poorJokes, allJokes );
                        JSONArray shortJokes = rootObject.getJSONArray("short");
                        addJokesToArrayList( shortJokes, allJokes );
                        JSONArray skinnyJokes = rootObject.getJSONArray("skinny");
                        addJokesToArrayList( skinnyJokes, allJokes );
                        JSONArray tallJokes = rootObject.getJSONArray("tall");
                        addJokesToArrayList( tallJokes, allJokes );
                        JSONArray likeJokes = rootObject.getJSONArray("like");
                        addJokesToArrayList( likeJokes, allJokes );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                })
                .doWhenFinished((AsyncJob.AsyncResultAction<Boolean>) result -> {
                    Collections.shuffle(allJokes);
//
                    for (Joke joke : allJokes) {

                        mCardAdapter.add(joke.getJoke());
                    }
                }).create().start();


        mCardStack.setAdapter(mCardAdapter);

    }

    private void handleShakeEvent() {
        startActivity(new Intent(MainActivity.this, FavJokesActivity.class));
    }


    public void addJokesToArrayList(JSONArray jsonArray, List<Joke> arrayList){

        try {
            if (jsonArray != null){
                for (int i = 0; i < jsonArray.length(); i++){
                    arrayList.add(new Joke(jsonArray.getString(i), false));

                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("jokes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public boolean swipeEnd(int section, float distance) {
        return (distance>300)? true : false;
    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return true;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void discarded(int mIndex, int direction) {

    }

    @Override
    public void topCardTapped() {

    }

    @Override
    public void jokeIsLiked(Joke joke) {

        if (joke.jokeIsLiked()){
            mJokeManager.saveJoke(joke);
        } else {
            mJokeManager.deleteJoke(joke);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        startActivity(new Intent(MainActivity.this, FavJokesActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }
}

