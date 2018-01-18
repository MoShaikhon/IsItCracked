package com.example.moshaikhon.isitcracked;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moshaikhon.isitcracked.GameUtils;
import com.example.moshaikhon.isitcracked.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailedGameActivity extends AppCompatActivity {



    @BindView(R.id.activity_detailed_toolBar)
    Toolbar mToolbar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_game);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // ViewCompat.setTransitionName(detailedGameCoverImageView,"imgTrans");

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle;
            bundle = intent.getExtras();
            bindData(bundle);
        }


    }

    public void bindData(Bundle bundle) {
        crackDateTextView.setText(bundle.getString(getString(R.string.crackDate)));
        releaseDateTextView.setText(bundle.getString(getString(R.string.releaseDate)));
        sceneGroupTextView.setText(bundle.getString(getString(R.string.sceneGroup)));
        nfoTextView.setText(GameUtils.getNfoString(bundle.getString(getString(R.string.crackCount))));
        ratingTextView.setText(GameUtils.getRatingString(bundle.getString(getString(R.string.rating))));
        priceTextView.setText(GameUtils.getPriceString(bundle.getString(getString(R.string.originalPrice))
                ,bundle.getString(getString(R.string.alterativePrice))));
        releaseDateTextView.setText(GameUtils.getReleaseDate(bundle.getString(getString(R.string.releaseDate))));
        crackDateTextView.setText(GameUtils.getCrackDate(bundle.getString(getString(R.string.crackDate))));
        isAAATextView.setText(GameUtils.GetAAA(bundle.getString(getString(R.string.isAAA))));
        sceneGroupTextView.setText(GameUtils.getSceneGroup(bundle.getString(getString(R.string.sceneGroup))));
        drmTextView.setText(GameUtils.getDRMProtection(bundle.getString(getString(R.string.drm))));
        GameUtils.getPlatformIcon(platformIconImageView
                ,bundle.getString(getString(R.string.platform))
                ,bundle.getString(getString(R.string.origin)));
        setCrackStatusIcon(bundle.getString(getString(R.string.crackDate)));

loadThumbnail(bundle.getString(getString(R.string.gameImage)));


    }

    private void setCrackStatusIcon(String crackDate){

        boolean isCracked=!crackDate.equalsIgnoreCase("undefined");
        GameUtils.changeCrackIcon(isCracked,crackStatusImageView);
    }

    private void loadImage(Bundle game) {
        // Set the title TextView to the item's name and author
        loadThumbnail(game.getString(getString(R.string.gameImage)));
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                addTransitionListener(game.getString(getString(R.string.imageCover)))) {
            // If we're running on Lollipop and we have added a listener to the shared element
            // transition, load the thumbnail. The listener will load the full-size image when
            // the transition is complete.
            );*/
        /*} else {
            // If all other cases we should just load the full-size image now
            loadFullSizeImage(game.getString(getString(R.string.imageCover)));
        }*/
    }
    private void loadThumbnail(String imgURL) {
        supportPostponeEnterTransition();

        Picasso.with(this)
                .load(imgURL)
                .noFade()
                .fit()
                .into(detailedGameCoverImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });

    }

    /**
     * Load the item's full-size image into our ImageView
     */
    private void loadFullSizeImage(String imgURL) {

        Picasso.with(this)
                .load(imgURL)
                .noFade()
                .noPlaceholder()
                .into(detailedGameCoverImageView);
    }

    private boolean addTransitionListener(final String imgURL) {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage(imgURL);

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }

}
