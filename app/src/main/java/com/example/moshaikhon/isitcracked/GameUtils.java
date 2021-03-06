package com.example.moshaikhon.isitcracked;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MoShaikhon on 04-Jan-18.
 */

public class GameUtils {

    public static void changeStatusAndIcon(Context context, ImageView imageView, TextView statusView) {
        if (statusView.getText().equals("undefined")) {
            changeCrackIcon(false,imageView);
            statusView.setTextColor(ContextCompat.getColor(context, R.color.secondaryColorAccent));

        } else {
            statusView.setTextColor(ContextCompat.getColor(context, R.color.primaryColorAccent));
            changeCrackIcon(true,imageView);

        }

    }

    public static void changeCrackIcon(boolean cracked,ImageView imageView) {
        if (!cracked)
            imageView.setImageResource(R.drawable.ic_slider_cross);
        else
            imageView.setImageResource(R.drawable.ic_slider_check);


    }


    public static String setCrackStatus(String crackStatus) {
        if (!crackStatus.equals("undefined")) {
            crackStatus = crackStatus.substring(0, crackStatus.indexOf(":") - 3);
            return crackStatus;
        } else
            return "Not cracked yet";
    }

    public static String getCrackDate(String crackDate) {
        return "Crack date: " + setCrackStatus(crackDate);

    }

    public static String getReleaseDate(String releaseDate) {
        if (!releaseDate.equals("undefined")) {

            releaseDate = releaseDate.substring(0, releaseDate.indexOf(":") - 3);
            return "Release date: " + releaseDate;
        } else
            return "Unknown";
    }

    public static void getPlatformIcon(ImageView imageView, String platform, String isOrigin) {

        platform = fixPlatform(platform, isOrigin);

        switch (platform) {
            case "steam":
                imageView.setImageResource(R.drawable.steam_logo);
                break;
            case "origin":
                imageView.setImageResource(R.drawable.ic_origins);
                break;
            default:
                imageView.setVisibility(View.GONE);
                break;
        }
    }

    public static String GetAAA(String isAAA) {
        if (isAAA.equals("true"))
            return "AAA";
        else
            return "Not AAA";
    }

    public static String getPriceString(String originalPrice, String altPrice) {
        originalPrice = fixPrice(originalPrice, altPrice);
        return "Price: $" + originalPrice;
    }

    public static String getRatingString(String rating) {
        rating = fixRating(rating);
        return "Rating: " + rating ;
    }

    public static String getNfoString(String nfos) {
        return "Cracks: " + nfos;
    }

    public static String getSceneGroup(String sceneGroup) {
        return "Scene Group: " + sceneGroup;
    }

    public static String getDRMProtection(String drm) {

        switch (drm) {
            case "steam":
                drm = "Steam";
                break;
            case "origin":
                drm = "Origin";
                break;
            case "denuvo":
                drm = "Denuvo";
                break;

        }
        return drm = "DRM Protection: " + drm;
    }

    public static String fixRating(String rating) {
        if (rating.equals("undefined") || Integer.parseInt(rating) < 1)
            return "Undefined";
        return rating+ "%";
    }

    public static String fixPlatform(String platform, String originPlatform) {
        if (originPlatform.equals("undefined"))
            return platform;
        else
            return "origin";
    }

    public static String fixPrice(String originalPrice, String altPrice) {

        if (!originalPrice.equals("undefined")) {

            if (Double.parseDouble(originalPrice) < 1
                    && !altPrice.equals("undefined")
                    && Double.parseDouble(altPrice) >= 1)
                return altPrice;

            else if (Double.parseDouble(originalPrice) >= 1)
                return originalPrice;
            else
                return "Undefined";
        }
        return "Undefined";

    }
}
