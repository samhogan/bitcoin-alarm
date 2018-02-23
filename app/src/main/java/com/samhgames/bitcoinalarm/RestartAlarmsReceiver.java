package com.samhgames.bitcoinalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RestartAlarmsReceiver extends BroadcastReceiver
{

    //this receiver is activated when the device reboots so all the alarms can be reactivated
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent i = new Intent(context, RestartAlarmsService.class);
        context.startService(i);
    }
}
