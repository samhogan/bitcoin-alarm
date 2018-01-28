package com.samhgames.bitcoinalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.samhgames.bitcoinalarm.data.AlarmInfo;
import com.samhgames.bitcoinalarm.notification.NotificationReceiver;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by samho on 1/27/2018.
 */

public class AlarmSetter
{
    private static AlarmManager alarmManager;


    public static void setManager(Context context)
    {
        alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);


    }


    public static void setAlarm(AlarmInfo info, Context context)
    {

        assert info.getId() != -999;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, info.getHours());
        calendar.set(Calendar.MINUTE, info.getMinutes());
        Intent myIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)info.getId(), myIntent, 0);

        //setexact
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


    }

}