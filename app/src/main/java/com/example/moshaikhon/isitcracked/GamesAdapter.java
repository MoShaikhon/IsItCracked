package com.example.moshaikhon.isitcracked;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by MoShaikhon on 01-Jan-18.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {

    private Games[] games;

    public GamesAdapter(Games[] games) {
        this.games = games;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_list_item, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.gameName.setText(games[position].getTitle());
        String date = ImageAndColorUtils.trimCrackDate(games[position].getCrackDate());
        holder.crackedStatus.setText(date);
        ImageAndColorUtils.changeStatusAndIcon(holder.itemView.getContext(), holder.crackedIcon, holder.crackedStatus);
        loadImage(games[position].getImage(), holder.itemView.getContext(), holder.gameImage);

    }

    private void loadImage(String imageURL, Context context, RoundedImageView imageView) {
        Picasso.with(context).load(imageURL).into(imageView);

    }

    @Override
    public int getItemCount() {
        return games.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView gameName;
        TextView crackedStatus;
        RoundedImageView gameImage;
        ImageView crackedIcon;

        public MyViewHolder(View view) {
            super(view);
            gameName = (TextView) view.findViewById(R.id.gameNameTextView);
            crackedStatus = (TextView) view.findViewById(R.id.crackStatusTextView);
            gameImage = (RoundedImageView) view.findViewById(R.id.gameCoverImageView);
            crackedIcon = (ImageView) view.findViewById(R.id.statusIconImageView);

        }
    }
}
