package com.example.moshaikhon.isitcracked;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

        goToMainActivityIfTabletChangedOrientationAtRunTime();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTheIntentAndItsBundleAndAddFragment();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMainActivityIfTabletChangedOrientationAtRunTime() {
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && findViewById(R.id.defaultGameCardView) != null)
            startActivity(new Intent(this, MainActivity.class));
    }

    private void getTheIntentAndItsBundleAndAddFragment() {
        fragmentManager = getSupportFragmentManager();
        detailedGameInfoFragment = new DetailedGameInfoFragment();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            detailedGameInfoFragment.setGameBundle(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.detailed_activity_fragment, detailedGameInfoFragment)
                    .commit();
        }
    }
}
