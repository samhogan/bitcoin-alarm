package com.samhgames.bitcoinalarm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samhgames.bitcoinalarm.data.DbHelper;

/**
 * Created by samho on 1/27/2018.
 */

//enables getting and setting data from the database
public class DbAccessor
{
    //the database containing a table of all alarms
    private SQLiteDatabase mDb;


    public DbAccessor(Context context)
    {

        //apparently the right way to do this is to create a new instance of dbHelper...
        //dbhelper instance
        DbHelper dbHelper = new DbHelper(context);
        //get writable database
        mDb = dbHelper.getWritableDatabase();
    }

    //returns all the information about an alarm
    public AlarmInfo getAlarm(long id)
    {
        Cursor cursor = mDb.query(DataContract.DataEntry.TABLE_NAME,
                null,
                DataContract.DataEntry._ID + " = " + id,
                null,
                null,
                null,
                DataContract.DataEntry.COLUMN_TIME);

        // Log.d("idk", "blah " + cursor.getCount());
        //get the time in minutes
        cursor.moveToPosition(0);
        int time = cursor.getInt(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_TIME));
        cursor.close();

        return new AlarmInfo(time, id);

    }

    //saves the alarm data in the database, or adds a new one
    public void saveAlarm(AlarmInfo info, boolean newAlarm)
    {
        //time = time
        //encode all the data to be saved
        int readNum = 1;//readSwitch.isChecked() ? 1:0;
        int repeatNum = 5;

        ContentValues cv = new ContentValues();
        cv.put(DataContract.DataEntry.COLUMN_TIME, info.getTime());
        cv.put(DataContract.DataEntry.COLUMN_READ_PRICE, readNum);
        cv.put(DataContract.DataEntry.COLUMN_REPEAT, repeatNum);

        if(newAlarm)//insert it into the table
            mDb.insert(DataContract.DataEntry.TABLE_NAME, null, cv);
        else//update it
        {
            String strFilter = DataContract.DataEntry._ID + " = " + info.getId();
            mDb.update(DataContract.DataEntry.TABLE_NAME, cv, strFilter, null);
        }


    }

}