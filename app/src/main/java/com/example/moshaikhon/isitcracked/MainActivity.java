package com.example.moshaikhon.isitcracked;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements GamesAdapter.GameClickListener {
    RecyclerView recyclerView;
    public Games[] games;
    public List<Games> gamesFiltered;

    GamesAdapter gamesAdapter;
    ProgressBar progressBar;
    final String BASE_URL = "https://crackwatch.com/api/games";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.games_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Log.d("searchResult", "starbo".substring(0, "starbo".length() - 1));
        Log.d("searchResult", "starbo".substring(0, 2));


        try {
            run(BASE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("server response", "a7a");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //    Log.i("server response", response.body().string());
                handleJsonResponse(response.body().string());

            }
        });


    }

    public void handleJsonResponse(String content) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(JsonParser.Feature.ALLOW_TRAILING_COMMA);
            mapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

            content = content.replaceAll("(?<!\\s)'(?!,)", "\\\\'");


            Log.i("content:", content);
            games = mapper.readValue(content, Games[].class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    gamesAdapter = new GamesAdapter(games, MainActivity.this);
                    recyclerView.setAdapter(gamesAdapter);

                }
            });

            Log.i("first game: ", games[0].getTitle());


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onClick() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                gamesFiltered = new ArrayList<>();
                int index = 0;
                if (progressBar.getVisibility() == View.GONE) {

                    for (Games game : games) {
                        if (game.getTitle().length() >= query.length())
                            if (game.getTitle().substring(0, query.length()).equalsIgnoreCase(query) && query.length() >= 1) {
                                ++index;
                                //  Log.d("searchResult", game.getTitle());
                                gamesFiltered.add(game);

                                //   Toast.makeText(MainActivity.this, game.getTitle(), Toast.LENGTH_LONG).show();
                            }
                    }

                }
                for (Games game : gamesFiltered) {
                    Log.d("searchResult", game.getTitle());

                }
                searchView.clearFocus();
                gamesAdapter = new GamesAdapter(gamesFiltered.toArray(new Games[gamesFiltered.size()])
                        , MainActivity.this);
                gamesAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(gamesAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  Toast.makeText(MainActivity.this,newText,Toast.LENGTH_LONG).show();
             /*   if(progressBar.getVisibility()==View.GONE){

                    for (Games game:games) {
                        if(game.getTitle().substring(0,newText.length()-1).contains(newText)&&newText.length()>=1)

                        Toast.makeText(MainActivity.this,game.getTitle(),Toast.LENGTH_LONG).show();


                    }

                }*/
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
//@TODO: 06-Jan-18  design & implement detailed activity
//@TODO: 06-Jan-18  polish toolbar and navigation, and search functionality
//@TODO: appBar title filter (latest cracks& all games)
//@TODO: hot games slider