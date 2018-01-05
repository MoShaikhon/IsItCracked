package com.example.moshaikhon.isitcracked;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MoShaikhon on 04-Jan-18.
 */

public class ImageAndColorUtils {

    public static void changeStatusAndIcon(Context context,ImageView imageView, TextView statusView) {
        if (statusView.getText().equals("undefined")) {
            imageView.setImageResource(R.drawable.ic_slider_cross);
            statusView.setTextColor(ContextCompat.getColor(context, R.color.redText));

        } else {
            imageView.setImageResource(R.drawable.ic_slider_check);
            statusView.setTextColor(ContextCompat.getColor(context, R.color.greenText));

        }

    }
    public static String trimCrackDate(String crackDate){
        if (!crackDate.equals("undefined")) {
            crackDate=crackDate.substring(0,crackDate.indexOf("+")-1);
            return crackDate;
        }
        else
            return crackDate;
}
}
