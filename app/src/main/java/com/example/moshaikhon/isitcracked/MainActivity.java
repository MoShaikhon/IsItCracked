package com.example.moshaikhon.isitcracked;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public Games[] games;
    ProgressBar progressBar;
    final String BASE_URL = "https://crackwatch.com/api/games";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.games_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


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
                    recyclerView.setAdapter(new GamesAdapter(games));

                }
            });

            Log.i("first game: ", games[0].getTitle());


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

