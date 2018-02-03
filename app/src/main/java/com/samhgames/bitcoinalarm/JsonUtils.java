package com.samhgames.bitcoinalarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samho on 2/2/2018.
 */

public class JsonUtils
{


    public static double extractPrice(String rawJson) throws JSONException
    {
        JSONArray reader = new JSONArray(rawJson);

        double price = reader.getJSONObject(0).getDouble("price_usd");
        return price;



    }

}
