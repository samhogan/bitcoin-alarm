package com.samhgames.bitcoinalarm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

/**
 * Created by samho on 1/8/2018.
 */

public class DbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "alarms.db";

    private static final int DATABASE_VERSION = 4;

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //the query that creates the table
        final String SQL_CREATE_ALARM_TABLE = "CREATE TABLE " + DataContract.DataEntry.TABLE_NAME + " (" +
                DataContract.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataContract.DataEntry.COLUMN_TIME + " INTEGER NOT NULL," +
                DataContract.DataEntry.COLUMN_REPEAT + " INTEGER NOT NULL," +
                DataContract.DataEntry.COLUMN_ACTIVE + " INTEGER NOT NULL," +
                DataContract.DataEntry.COLUMN_READ_PRICE + " INTEGER NOT NULL, " +
                DataContract.DataEntry.COLUMN_SOUND_NAME + " TEXT NOT NULL, " +
                DataContract.DataEntry.COLUMN_SOUND_URI + " TEXT NOT NULL, " +
                DataContract.DataEntry.COLUMN_DAYS + " INTEGER NOT NULL " +
                ");";

        //execute the query
        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        //implement if ever changing version number
        //append extra column to current table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.DataEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
