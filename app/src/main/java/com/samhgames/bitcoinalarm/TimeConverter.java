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


    static String[] dayAbs = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public static String getDayText(boolean[] days)
    {
        String dayString = "";
        int num = 0;
        for(int i=0; i<7; i++)
        {
            num = num + (days[i]?1:0)*(1<<i);

            if(days[i])
                dayString += dayAbs[i] + ", ";
        }
        if(dayString.length()>2)
            dayString = dayString.substring(0,dayString.length()-2);

        if(num==127)
            return "Daily";
        if(num==65)
            return "Weekends";
        if(num==127-65)
            return "Weekdays";

        if(num==0)
            return "Today or Tomorrow or sm";

        return dayString;

    }
}
