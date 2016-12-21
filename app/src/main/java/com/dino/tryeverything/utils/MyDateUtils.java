package com.dino.tryeverything.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dino on 12/21 0021.
 */

public class MyDateUtils {
    public static String formatDate(String date){
        if(StringUtils.isEmpty(date))return "";
        String formatDateStr = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                Date d = format.parse(date);
                formatDateStr =  DateUtils.getRelativeTimeSpanString(d.getTime()).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return formatDateStr;
    }
}
