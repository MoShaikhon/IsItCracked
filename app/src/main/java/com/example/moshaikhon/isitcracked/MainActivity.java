package com.example.moshaikhon.isitcracked;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.view.ViewAnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.example.moshaikhon.isitcracked.adapter.GamesAdapter;
import com.example.moshaikhon.isitcracked.model.Games;
import com.example.moshaikhon.isitcracked.ui.MasterGameListFragment;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements MasterGameListFragment.OnGameClickListener {
    RecyclerView recyclerView;
    public Games[] games;
    SearchView searchView;

    DetailedGameInfoFragment detailedGameInfoFragment;
    FragmentManager fragmentManager;
    GamesAdapter gamesAdapter;
    LottieAnimationView progressAnimationView;
    final String BASE_URL = "https://crackwatch.com/api/games";
    boolean isConnected;
    NetworkInfo activeNetwork;
    MasterGameListFragment masterGameListFragment;
    ConnectivityManager cm;
    boolean mTwoPaneMode;
    int numberOfColumnsInRecyclerViewGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        progressAnimationView = findViewById(R.id.progressAnimationView);
        fragmentManager = getSupportFragmentManager();
        cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        handleConnectivityStateChanges();
        mTwoPaneMode = false;
        numberOfColumnsInRecyclerViewGrid = numberOfColumnsInRecyclerViewGrid();


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
                    createAndPopulateMasterGameListFragment(games);
                    animateActivityEntry();
                    if (mTwoPaneMode) {
                        createOrReplaceDetailedGameFragment(createGameBundle(games[0]));
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mTwoPaneMode) {
                    if (hasFocus) {
                        AnimationUtils.circularHide(findViewById(R.id.detailed_fragment_container));
                        fragmentManager.beginTransaction().hide(detailedGameInfoFragment).commitNowAllowingStateLoss();
                    }
                }
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.circularReveal(findViewById(R.id.detailed_fragment_container));
                fragmentManager.beginTransaction().show(detailedGameInfoFragment).commitNowAllowingStateLoss();
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPaneMode) {
                    AnimationUtils.circularHide(findViewById(R.id.detailed_fragment_container));
                    fragmentManager.beginTransaction().hide(detailedGameInfoFragment).commitNowAllowingStateLoss();

                }
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (mTwoPaneMode) {
                    AnimationUtils.circularReveal(findViewById(R.id.detailed_fragment_container));
                    fragmentManager.beginTransaction().show(detailedGameInfoFragment).commitNowAllowingStateLoss();
                }
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && masterGameListFragment.getGamesAdapter() != null)
                    masterGameListFragment.getGamesAdapter().filter(query);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && masterGameListFragment.getGamesAdapter() != null) {
                    masterGameListFragment.getGamesAdapter().filter(newText);
                    if (detailedGameInfoFragment != null) {
                        AnimationUtils.circularHide(findViewById(R.id.detailed_fragment_container));
                        fragmentManager.beginTransaction().hide(detailedGameInfoFragment).commit();
                    }
                }
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

    public void createAndPopulateMasterGameListFragment(Games[] games) {

        masterGameListFragment = new MasterGameListFragment();
        masterGameListFragment.setGames(games);
        masterGameListFragment.setNumberOfColumns(numberOfColumnsInRecyclerViewGrid);
        fragmentManager.beginTransaction()
                .add(R.id.master_game_list_fragment_container, masterGameListFragment)
                .commitNowAllowingStateLoss();

    }

    @Override
    public void onGameClicked(int position, Games game) {

        if (!mTwoPaneMode) {
            sendToDetailActivity(game, MainActivity.this);

        } else {

            // if user clicks an item while searchView has focus then clear focus
            if (searchView.hasFocus())
                searchView.clearFocus();

            createOrReplaceDetailedGameFragment(createGameBundle(game));
            fragmentManager.beginTransaction()
                    .show(detailedGameInfoFragment)
                    .commitNowAllowingStateLoss();
            AnimationUtils.circularReveal(findViewById(R.id.detailed_fragment_container));


        }
    }

    public void sendToDetailActivity(Games game, Context context) {

        Intent intent = new Intent(context, DetailedGameActivity.class);
        Bundle detailedGameBundle = createGameBundle(game);
        Bundle transitionBundle = createTransitionBundle();
        intent.putExtras(detailedGameBundle);
        startActivity(intent);


    }

    public void createOrReplaceDetailedGameFragment(Bundle bundle) {

        if (detailedGameInfoFragment == null) {
            detailedGameInfoFragment = new DetailedGameInfoFragment();
            detailedGameInfoFragment.setGameBundle(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.detailed_fragment_container, detailedGameInfoFragment)
                    .commit();
            AnimationUtils.circularReveal(findViewById(R.id.detailed_fragment_container));
        } else {
            detailedGameInfoFragment = new DetailedGameInfoFragment();
            detailedGameInfoFragment.setGameBundle(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.detailed_fragment_container, detailedGameInfoFragment)
                    .commit();
        }


    }

    public Bundle createGameBundle(Games game) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.isAAA), game.getIsAAA());
        bundle.putString(getString(R.string.imageCover), game.getImageCover());
        bundle.putString(getString(R.string.releaseDate), game.getReleaseDate());
        bundle.putString(getString(R.string.crackDate), game.getCrackDate());
        bundle.putString(getString(R.string.originalPrice), game.getOriginalPrice());
        bundle.putString(getString(R.string.drm), game.getDrm1());
        bundle.putString(getString(R.string.gameImage), game.getImage());
        bundle.putString(getString(R.string.crackCount), game.getNfosCount());
        bundle.putString(getString(R.string.rating), game.getRatings());
        bundle.putString(getString(R.string.sceneGroup), game.getSceneGroup1());
        bundle.putString(getString(R.string.alterativePrice), game.getAlternativePrice());
        bundle.putString(getString(R.string.origin), game.getOrigin());
        bundle.putString(getString(R.string.platform), game.getPlatform());
        return bundle;

    }

    public Bundle createTransitionBundle() {
        RoundedImageView gameImage = findViewById(R.id.gameCoverImageView);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                gameImage, getString(R.string.imgCoverTransition)).toBundle();
        return bundle;
    }

    private int numberOfColumnsInRecyclerViewGrid() {
        int columnNumber = 1;


        if (findViewById(R.id.main_activity_600dp_land_container) != null) {
            columnNumber = 1;
            mTwoPaneMode = true;
            Log.i("which layout", "600dp-land " + mTwoPaneMode);
        } else if (findViewById(R.id.main_activity_600dp_container) != null)
            columnNumber = 2;


        else if (findViewById(R.id.main_activity_container) != null) {
            columnNumber = 1;
            Log.i("which layout", "normal container");

        } else if (findViewById(R.id.main_activity_container_land) != null)
            columnNumber = 3;


        return columnNumber;

    }


    @Override
    protected void onResume() {
        super.onResume();
        animateActivityEntry();

    }
    private void animateActivityEntry(){
        final View activity_main_layout;

        if (findViewById(R.id.main_activity_container) != null) {
            activity_main_layout = findViewById(R.id.main_activity_container);
            activity_main_layout.post(new Runnable() {
                @Override
                public void run() {
                    AnimationUtils.circularReveal(activity_main_layout);
                }
            });

        } else if (findViewById(R.id.main_activity_container_land) != null) {
            activity_main_layout = findViewById(R.id.main_activity_container_land);
            activity_main_layout.post(new Runnable() {
                @Override
                public void run() {

                    AnimationUtils.circularReveal(activity_main_layout);
                }
            });

        } else if (findViewById(R.id.main_activity_600dp_container) != null) {
            activity_main_layout = findViewById(R.id.main_activity_600dp_container);
            activity_main_layout.post(new Runnable() {
                @Override
                public void run() {

                    AnimationUtils.circularReveal(activity_main_layout);

                }
            });
        }}


}