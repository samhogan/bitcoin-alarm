package com.samhgames.bitcoinalarm;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


public class DownloadCoinData extends IntentService
{

    public static String DOWNLOAD_DATA = "com.samhgames.bitcoinalarm.action.DOWNLOAD_DATA";

    public DownloadCoinData()
    {
        super("DownloadCoinData");
    }



    @Override
    protected void onHandleIntent(Intent intent)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        URL btcUrl = NetworkUtils.buildUrl();
        String rawJson = "you have failed";

        try {
            rawJson = NetworkUtils.getResponseFromHttpUrl(btcUrl);
        } catch (IOException e)
        {
            e.printStackTrace();
        }



        //parse json for price

        double price = 0;
        try {
            price = JsonUtils.extractPrice(rawJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }


            //set sharedpreferences




        //broadcast that new data has been downloaded
        Intent updateIntent = new Intent(DOWNLOAD_DATA);
        // You can also include some extra data.
        updateIntent.putExtra("price", price); // msg for textview if needed
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateIntent);

        Log.d("coindata", "data downloaded!");

        //sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key),
          //      getResources().getBoolean(R.bool.pref_show_bass_default)));
    }

}
