package com.example.moshaikhon.isitcracked;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailedGameActivity extends AppCompatActivity {

    @BindView(R.id.activity_detailed_toolBar)
    Toolbar mToolbar;

    @BindView(R.id.crackDateTextView)
    TextView crackDateTextView;

    @BindView(R.id.gameCoverImageView)
    RoundedImageView imageCover;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_game);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        Picasso.with(this)
                .load(bundle.getString(getString(R.string.imageCover)))
                .into(imageCover);

    }

}
