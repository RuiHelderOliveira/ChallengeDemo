package me.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rui Oliveira on 11/01/18.
 */

public class Util {

    public static final String USER = "user";

    private static final SimpleDateFormat sdfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdfAfter = new SimpleDateFormat("yyyy/MM/dd");


    public static String convertDateFormat(String date) throws ParseException {

        Date dt = sdfBefore.parse(date);

        return sdfAfter.format(dt);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
