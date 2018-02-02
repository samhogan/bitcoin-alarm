package com.samhgames.bitcoinalarm;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samho on 2/2/2018.
 */

public class JsonUtils
{


    public static double extractPrice(String rawJson) throws JSONException
    {
        JSONObject reader = new JSONObject(rawJson);

        double price = reader.getDouble("price_usd");
        return price;



    }

}
