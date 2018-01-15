package com.samhgames.bitcoinalarm;

/**
 * Created by samho on 1/14/2018.
 */

public class TimeConverter
{

    public static String getTimeText(int totalMinutes)
    {
        return getTimeText(totalMinutes/60, totalMinutes%60);
    }

    public static String getTimeText(int hours, int minutes)
    {
        //add a check for 24 hour clock
        String postfix = "AM";
        int realHours = hours;
        if(hours==12)
            postfix = "PM";
        else if(hours>12)
        {
            postfix = "PM";
            realHours-=12;
        }
        else if(hours==0)
            realHours = 12;


        return realHours + ":" + String.format("%02d ", minutes) + postfix;
    }
}
