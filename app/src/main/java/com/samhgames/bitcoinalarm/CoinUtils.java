package com.samhgames.bitcoinalarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by samho on 2/3/2018.
 */

public class CoinUtils
{


    public static String getSavedPrice(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        float price = prefs.getFloat("last_bitcoin_price", 0);

        return String.format("%.2f", price);


    }
}
