package com.samhgames.bitcoinalarm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samhgames.bitcoinalarm.data.DbHelper;

import java.util.ArrayList;
import java.util.List;

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
        AlarmInfo info = readInfo(cursor);

        cursor.close();

        return info;

    }

    //saves the alarm data in the database, or adds a new one
    public void saveAlarm(AlarmInfo info, boolean newAlarm)
    {
        //time = time
        //encode all the data to be saved
        int readNum = 1;//readSwitch.isChecked() ? 1:0;
        //int repeatNum = 5;

        ContentValues cv = new ContentValues();
        cv.put(DataContract.DataEntry.COLUMN_TIME, info.getTime());
        cv.put(DataContract.DataEntry.COLUMN_READ_PRICE, readNum);
//        cv.put(DataContract.DataEntry.COLUMN_REPEAT, repeatNum);
        cv.put(DataContract.DataEntry.COLUMN_ACTIVE, info.isEnabled()?1:0);
        cv.put(DataContract.DataEntry.COLUMN_SOUND_NAME, info.getSoundName());
        cv.put(DataContract.DataEntry.COLUMN_SOUND_URI, info.getSoundUri());
        cv.put(DataContract.DataEntry.COLUMN_DAYS, info.getDaysInt());
        cv.put(DataContract.DataEntry.COLUMN_DATE, info.getMillis());

        if(newAlarm)//insert it into the table
        {
            //returns the id
            long id = mDb.insert(DataContract.DataEntry.TABLE_NAME, null, cv);
           // info.setId(id);
        }
        else//update it
        {
            String strFilter = DataContract.DataEntry._ID + " = " + info.getId();
            mDb.update(DataContract.DataEntry.TABLE_NAME, cv, strFilter, null);
        }

       // Log.d("yo", "oh... no");
    }

    //returns an arraylist of all alarms, ordered by time
    public ArrayList<AlarmInfo> getAllAlarms()
    {
        Cursor cursor = mDb.query(DataContract.DataEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DataContract.DataEntry.COLUMN_TIME);

        ArrayList<AlarmInfo> alarmList = new ArrayList<AlarmInfo>();

        for(int i=0; i<cursor.getCount(); i++)
        {
            cursor.moveToPosition(i);

            alarmList.add(readInfo(cursor));

        }

        cursor.close();
        return alarmList;

    }

    //returns the alarminfo form the current position of the cursor
    private AlarmInfo readInfo(Cursor cursor)
    {
        long id = cursor.getLong(cursor.getColumnIndex(DataContract.DataEntry._ID));
        int time = cursor.getInt(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_TIME));
        boolean enabled = cursor.getInt(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_ACTIVE)) == 1;
        String soundName = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_SOUND_NAME));
        String soundUri = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_SOUND_URI));

        int days = cursor.getInt(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_DAYS));
        long millis = cursor.getLong(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_DATE));


        return new AlarmInfo(time, id, enabled, soundName, soundUri, days, millis);

    }

}
