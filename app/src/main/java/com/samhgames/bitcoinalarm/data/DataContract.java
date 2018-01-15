package com.samhgames.bitcoinalarm.data;

import android.provider.BaseColumns;

/**
 * Created by samho on 1/8/2018.
 */

public class DataContract
{

    public static final class DataEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "alarms";

        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_READ_PRICE = "readPrice";
        public static final String COLUMN_REPEAT = "repeat";
        public static final String COLUMN_ACTIVE = "active";




    }
}
