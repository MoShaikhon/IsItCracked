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
            imageView.setImageResource(R.drawable.ic_slider_cross);
            statusView.setTextColor(ContextCompat.getColor(context, R.color.secondaryColorAccent));

        } else {
            imageView.setImageResource(R.drawable.ic_slider_check);
            statusView.setTextColor(ContextCompat.getColor(context, R.color.primaryColorAccent));

        }

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

    public static void getPlatformIcon( ImageView imageView, String platform,String isOrigin) {

        platform=fixPlatform(platform,isOrigin);

        switch (platform) {
            case "steam":
                imageView.setImageResource(R.drawable.ic_steam_icon_logo);
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
        return "Rating: " + rating + "%";
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
        if (Integer.parseInt(rating) < 1)
            return "Unknown";
        return rating;
    }

    public static String fixPlatform(String platform, String originPlatform) {
        if (originPlatform.equals("undefined"))
            return platform;
        else
            return "Origin";
    }

    public static String fixPrice(String originalPrice, String altPrice) {
        if (Double.parseDouble(originalPrice) < 1 && Double.parseDouble(altPrice) >= 1)
            return altPrice;
        else if (Double.parseDouble(originalPrice) >= 1)
            return originalPrice;
        else
            return "Unknown";


    }
}
