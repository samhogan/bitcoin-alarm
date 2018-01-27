package com.samhgames.bitcoinalarm.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.samhgames.bitcoinalarm.notification.NotificationActivity;

public class NotificationReceiver extends BroadcastReceiver
{

    /*
    Recieves the broadcast from the alarmmanager at whatever time the alarm is set for and starts the NotifcationActivity
     */

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent activityIntent = new Intent(context, NotificationActivity.class);
        context.startActivity(activityIntent);
    }
}
