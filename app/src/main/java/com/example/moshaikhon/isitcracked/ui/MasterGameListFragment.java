package com.example.moshaikhon.isitcracked.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moshaikhon.isitcracked.R;
import com.example.moshaikhon.isitcracked.adapter.GamesAdapter;
import com.example.moshaikhon.isitcracked.model.Games;

/**
 * Created by mohamed on 31-Jan-18.
 */

public class MasterGameListFragment extends Fragment implements GamesAdapter.GameClickListener {
    RecyclerView.LayoutManager staggeredLayoutManager;
    RecyclerView recyclerView;
    GamesAdapter gamesAdapter;
    Games[] games;
    OnGameClickListener mCallback;
    private int numberOfColumns;

    public MasterGameListFragment() {

    }

    @Override
    public void onClick(int position,Games game) {
        mCallback.onGameClicked(position,game);

    }


    public interface OnGameClickListener {
        void onGameClicked(int position,Games game);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View roootView = inflater.inflate(R.layout.game_list, container, false);
        staggeredLayoutManager = new StaggeredGridLayoutManager(numberOfColumns
                , StaggeredGridLayoutManager.VERTICAL);
        recyclerView = roootView.findViewById(R.id.games_recycler_view);
        recyclerView.setLayoutManager(staggeredLayoutManager);
        if (games != null) {
            gamesAdapter = new GamesAdapter(games, this);
            recyclerView.setAdapter(gamesAdapter);
        }

        return roootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnGameClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnGameClickListener");
        }

    }


    public void setGamesAdapter(GamesAdapter gamesAdapter) {
        this.gamesAdapter = gamesAdapter;
    }

    public GamesAdapter getGamesAdapter() {
        return gamesAdapter;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public void setGames(Games[] games) {
        this.games = games;
    }
}
