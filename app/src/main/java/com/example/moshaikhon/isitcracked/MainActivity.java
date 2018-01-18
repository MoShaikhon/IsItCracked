package com.example.moshaikhon.isitcracked;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.support.v7.widget.SearchView;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.moshaikhon.isitcracked.adapter.GamesAdapter;
import com.example.moshaikhon.isitcracked.model.Games;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.*;


public class MainActivity extends AppCompatActivity implements GamesAdapter.GameClickListener {
    RecyclerView recyclerView;
    public Games[] games;

    GamesAdapter gamesAdapter;
    LottieAnimationView progressAnimationView;
    final String BASE_URL = "https://crackwatch.com/api/games";
    boolean isConnected;
    NetworkInfo activeNetwork;
    ConnectivityManager cm;
    @BindView(R.id.main_activity_container_land)
    ViewGroup landScapeLayout;
    @BindView(R.id.main_activity_container)
    ViewGroup portraitLayout;
    LayoutManager staggeredLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.games_recycler_view);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        progressAnimationView = findViewById(R.id.progressAnimationView);

        //if in landscape mode, view 2 items in the same row
        if (findViewById(R.id.main_activity_container_land) != null)
            staggeredLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        else if (findViewById(R.id.main_activity_container) != null)
            staggeredLayoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredLayoutManager);
        cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        handleConnectivityStateChanges();


    }


    void run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressAnimationView();
                        showConnectionErrorViews();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
                    hideProgressAnimationView();
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
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
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
                if(query!=null&&gamesAdapter!=null)
                gamesAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null&&gamesAdapter!=null)
                    gamesAdapter.filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void showConnectionErrorViews() {
        findViewById(R.id.connectionStatusContainer).setVisibility(View.VISIBLE);
    }

    public void hideConnectionErrorViews() {
        findViewById(R.id.connectionStatusContainer).setVisibility(View.GONE);

    }

    public void showProgressAnimationView() {
        progressAnimationView.setVisibility(View.VISIBLE);

    }

    public void hideProgressAnimationView() {
        progressAnimationView.setVisibility(View.GONE);

    }

    public void handleConnectivityStateChanges() {

        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            hideConnectionErrorViews();
            showProgressAnimationView();
            try {
                run(BASE_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            hideProgressAnimationView();
            showConnectionErrorViews();
        }
    }

    public void clickToRetry(View v) {


        handleConnectivityStateChanges();


    }
}