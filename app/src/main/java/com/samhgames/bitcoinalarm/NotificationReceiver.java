package com.samhgames.bitcoinalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
