package com.example.moshaikhon.isitcracked.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moshaikhon.isitcracked.DetailedGameActivity;
import com.example.moshaikhon.isitcracked.GameUtils;
import com.example.moshaikhon.isitcracked.MainActivity;
import com.example.moshaikhon.isitcracked.R;
import com.example.moshaikhon.isitcracked.model.Games;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MoShaikhon on 01-Jan-18.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {

    private Games[] games;
    private List<Games> gamesList;
    private List<Games> originalGameList;

    final private GameClickListener mOnClickListener;

    public interface GameClickListener {
        void onClick();
    }

    public GamesAdapter(Games[] games, GameClickListener onClickListener) {

        gamesList = new ArrayList<>(Arrays.asList(games));
        originalGameList=new ArrayList<>(Arrays.asList(games));
        mOnClickListener = onClickListener;
    }

    public void filter(String text) {
        gamesList.clear();
        if (text.isEmpty()) {
            gamesList.addAll(originalGameList);
        } else {
            text = text.toLowerCase();
            for (Games game : originalGameList) {
                if (game.getTitle().toLowerCase().contains(text)) {
                    gamesList.add(game);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_list_item, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.gameName.setText(gamesList.get(position).getTitle());
        String date = "Cracked since " + GameUtils.setCrackStatus(gamesList.get(position).getCrackDate());
        holder.crackedStatus.setText(date);
        GameUtils.changeStatusAndIcon(holder.itemView.getContext(), holder.crackedIcon, holder.crackedStatus);
        loadImage(gamesList.get(position).getImage(), holder.itemView.getContext(), holder.gameImage);


    }

    private void loadImage(String imageURL, Context context, RoundedImageView imageView) {
        Picasso.with(context)
                .load(imageURL)
            
                .into(imageView);

    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView gameName;
        TextView crackedStatus;
        RoundedImageView gameImage;
        ImageView crackedIcon;

        public MyViewHolder(View view) {
            super(view);
            gameName = view.findViewById(R.id.gameNameTextView);
            crackedStatus = view.findViewById(R.id.crackStatusTextView);
            gameImage = view.findViewById(R.id.gameCoverImageView);
            crackedIcon = view.findViewById(R.id.statusIconImageView);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            sendToDetailActivity(gamesList.get(adapterPosition), v.getContext());
            mOnClickListener.onClick();


        }

        public void sendToDetailActivity(Games game, Context context) {

            String releaseDate = game.getReleaseDate();
            String crackdate = game.getCrackDate();
            Intent intent = new Intent(context, DetailedGameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(context.getString(R.string.isAAA), game.getIsAAA());
            bundle.putString(context.getString(R.string.imageCover), game.getImageCover());
            bundle.putString(context.getString(R.string.releaseDate), releaseDate);
            bundle.putString(context.getString(R.string.crackDate), crackdate);
            bundle.putString(context.getString(R.string.originalPrice), game.getOriginalPrice());
            bundle.putString(context.getString(R.string.drm), game.getDrm1());
            bundle.putString(context.getString(R.string.gameImage), game.getImage());
            bundle.putString(context.getString(R.string.crackCount), game.getNfosCount());
            bundle.putString(context.getString(R.string.rating), game.getRatings());
            bundle.putString(context.getString(R.string.sceneGroup), game.getSceneGroup1());
            bundle.putString(context.getString(R.string.alterativePrice), game.getAlternativePrice());
            bundle.putString(context.getString(R.string.origin), game.getOrigin());
            bundle.putString(context.getString(R.string.platform), game.getPlatform());

            Bundle bundle1= ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                    gameImage,context.getString(R.string.imgCoverTransition)).toBundle();

            intent.putExtras(bundle);
            context.startActivity(intent,bundle1);


        }


    }
}
