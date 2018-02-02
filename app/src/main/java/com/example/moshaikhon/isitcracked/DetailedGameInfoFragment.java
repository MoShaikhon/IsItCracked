package com.example.moshaikhon.isitcracked;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moshaikhon.isitcracked.model.Games;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 30-Jan-18.
 */


public class DetailedGameInfoFragment extends Fragment {

    private Bundle gameBundle;

    @BindView(R.id.crackDateTextView)
    TextView crackDateTextView;

    @BindView(R.id.detailedGameCoverImageView)
    RoundedImageView detailedGameCoverImageView;

    @BindView(R.id.priceTextView)
    TextView priceTextView;

    @BindView(R.id.isAAATextView)
    TextView isAAATextView;

    @BindView(R.id.ratingTextView)
    TextView ratingTextView;

    @BindView(R.id.nfoTextView)
    TextView nfoTextView;

    @BindView(R.id.sceneGroupTextView)
    TextView sceneGroupTextView;

    @BindView(R.id.drmTextView)
    TextView drmTextView;

    @BindView(R.id.releaseDateTextView)
    TextView releaseDateTextView;

    @BindView(R.id.platformIconImageView)
    ImageView platformIconImageView;

    @BindView(R.id.crackStatusImageView)
    ImageView crackStatusImageView;

    public DetailedGameInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detailed_layout_content, container, false);
        ButterKnife.bind(this,rootView);
        if (gameBundle != null)
            bindData(gameBundle);

        return rootView;

    }

    public void bindData(Bundle bundle) {
        if (nfoTextView != null) {


            nfoTextView.setText(GameUtils.getNfoString(bundle.getString(getString(R.string.crackCount))));
            ratingTextView.setText(GameUtils.getRatingString(bundle.getString(getString(R.string.rating))));
            priceTextView.setText(GameUtils.getPriceString(bundle.getString(getString(R.string.originalPrice))
                    , bundle.getString(getString(R.string.alterativePrice))));
            releaseDateTextView.setText(GameUtils.getReleaseDate(bundle.getString(getString(R.string.releaseDate))));
            crackDateTextView.setText(GameUtils.getCrackDate(bundle.getString(getString(R.string.crackDate))));
            isAAATextView.setText(GameUtils.GetAAA(bundle.getString(getString(R.string.isAAA))));
            sceneGroupTextView.setText(GameUtils.getSceneGroup(bundle.getString(getString(R.string.sceneGroup))));
            drmTextView.setText(GameUtils.getDRMProtection(bundle.getString(getString(R.string.drm))));
            GameUtils.getPlatformIcon(platformIconImageView
                    , bundle.getString(getString(R.string.platform))
                    , bundle.getString(getString(R.string.origin)));
            setCrackStatusIcon(bundle.getString(getString(R.string.crackDate)));

            loadThumbnail(bundle.getString(getString(R.string.gameImage)));
            detailedGameCoverImageView.post(new Runnable() {
                @Override
                public void run() {
                    if (getActivity().findViewById(R.id.detailed_activity_fragment)!=null)
                    AnimationUtils.circularReveal(getActivity().findViewById(R.id.detailed_activity_fragment));

                }
            });

        }

    }

    private void loadThumbnail(String imgURL) {
       // ActivityCompat.postponeEnterTransition(getActivity());

        Picasso.with(this.getContext())
                .load(imgURL)
                .noFade()
                .fit()
                .into(detailedGameCoverImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                       // ActivityCompat.startPostponedEnterTransition(DetailedGameInfoFragment.this.getActivity());
                    }

                    @Override
                    public void onError() {
                     //   ActivityCompat.startPostponedEnterTransition(DetailedGameInfoFragment.this.getActivity());
                    }
                });
    }


    private void setCrackStatusIcon(String crackDate) {

        boolean isCracked = !crackDate.equalsIgnoreCase("undefined");
        GameUtils.changeCrackIcon(isCracked, crackStatusImageView);
    }


    public void setGameBundle(Bundle gameBundle) {
        this.gameBundle = gameBundle;
    }

}
