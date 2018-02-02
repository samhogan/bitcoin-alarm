package com.samhgames.bitcoinalarm;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.net.URL;


public class DownloadCoinData extends IntentService
{

    @Override
    protected void onHandleIntent(Intent intent)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        URL btcUrl = NetworkUtils.buildUrl();
        String rawJson = "you have failed";

        try {
            rawJson = NetworkUtils.getResponseFromHttpUrl(btcUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //broadcast that new data has been downloaded
        Intent updateIntent = new Intent("myBroadcast");
        // You can also include some extra data.
        intent.putExtra("message", "bobby"); // msg for textview if needed
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateIntent);

        //sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key),
          //      getResources().getBoolean(R.bool.pref_show_bass_default)));
    }

}
