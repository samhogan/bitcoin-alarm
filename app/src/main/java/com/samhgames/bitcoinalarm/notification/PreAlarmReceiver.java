package com.samhgames.bitcoinalarm.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.samhgames.bitcoinalarm.DownloadCoinData;

public class PreAlarmReceiver extends BroadcastReceiver
{

    //this reciever is activated a minute or two before the alarm to download necessary data

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent getDataIntent = new Intent(context, DownloadCoinData.class);
        context.startService(getDataIntent);
    }
}
