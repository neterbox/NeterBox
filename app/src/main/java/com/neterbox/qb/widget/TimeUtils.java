package com.neterbox.qb.widget;

import android.util.Log;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private TimeUtils() {
    }

    public static String getTime(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getDate(long milliseconds) throws ParseException {
        String strFinalDate = null;
        Log.e("time_utils",":"+new Gson().toJson(milliseconds));
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        String strdate=df.format(new Date(milliseconds));

        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedcurrentDate = df.format(c.getTime());

        Date date=df.parse(strdate);
        Date currentDate=df.parse(formattedcurrentDate);

        if(currentDate.compareTo(date)==0)
        {
            strFinalDate="Today";
        }
//        else if(date.compareTo(currentDate)<0)
//        {
//            strFinalDate="Yesterday";
//        }
        else /*if(date.compareTo(currentDate)>0)*/
        {
            strFinalDate=strdate;
        }


        return strFinalDate;
    }

    public static long getDateAsHeaderId(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return Long.parseLong(dateFormat.format(new Date(milliseconds)));
    }
}
