package com.example.moshaikhon.isitcracked;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.MenuItem;
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

    FragmentManager fragmentManager;
    DetailedGameInfoFragment detailedGameInfoFragment;
    @BindView(R.id.activity_detailed_toolBar)
    Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_game);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        detailedGameInfoFragment = new DetailedGameInfoFragment();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            detailedGameInfoFragment.setGameBundle(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.detailed_activity_fragment, detailedGameInfoFragment)
                   // .addSharedElement(findViewById(R.id.detailedGameCoverImageView),getString(R.string.imgCoverTransition))
                    .commit();



        }


    }



        //loadThumbnail(bundle.getString(getString(R.string.gameImage)));







    /**
     * Load the item's full-size image into our ImageView
     */
    /*
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
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
