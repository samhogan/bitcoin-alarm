package com.samhgames.bitcoinalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.samhgames.bitcoinalarm.data.AlarmInfo;
import com.samhgames.bitcoinalarm.notification.NotificationReceiver;
import com.samhgames.bitcoinalarm.notification.PreAlarmReceiver;

import java.io.Serializable;
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


    public static void setAlarm(long time, AlarmInfo info, Context context)
    {

        //the id for the alarmmanager to distinguish this alarm
        int intentID = (int)info.getId()*2;

        Intent myIntent = new Intent(context, NotificationReceiver.class);

        //have to put the alarminfo into a bundle for some reason
        Bundle args = new Bundle();
        args.putSerializable("Info",(Serializable)info);
        myIntent.putExtra("InfoBundle",args);

        Log.d("tagyoureit", "" + (info==null));
        myIntent.putExtra("bob", 8);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intentID, myIntent, 0);


        //setexact
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        //Log.d("alarm is set", "alarm set" + info.getHours() + " " + info.getMinutes());


        //now set the download data receiver
        Intent preIntent = new Intent(context, PreAlarmReceiver.class);
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(context, intentID+1, preIntent, 0);

        //60 seconds earlier
        alarmManager.set(AlarmManager.RTC_WAKEUP, time-60000, prePendingIntent);


    }

    public static void setAlarm(AlarmInfo info, Context context)
    {

        assert info.getId() != -999;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, info.getHours());
        calendar.set(Calendar.MINUTE, info.getMinutes());
        setAlarm(calendar.getTimeInMillis(), info, context);

    }

    public static void snoozeAlarm(Context context, AlarmInfo info)
    {
        setAlarm(Calendar.getInstance().getTimeInMillis()+1000*60*1, info, context);
    }

    public static void cancelAlarm(AlarmInfo info, Context context)
    {
        Intent myIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)info.getId(), myIntent, 0);

        pendingIntent.cancel();

    }


}
