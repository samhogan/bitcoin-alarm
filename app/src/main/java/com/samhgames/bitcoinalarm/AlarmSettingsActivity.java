package com.samhgames.bitcoinalarm;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.samhgames.bitcoinalarm.data.DataContract;
import com.samhgames.bitcoinalarm.data.DbHelper;

public class AlarmSettingsActivity extends AppCompatActivity
{
    //the database containing a table of all alarms
    private SQLiteDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //SharedPreferences settings = getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
        //settings.edit().clear().commit();
       /* getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AlarmSettingsFragment())
                .commit();*/

        setContentView(R.layout.activity_alarm_settings);
        getSupportActionBar().hide();


        //apparently the right way to do this is to create a new instance of dbHelper...
        //dbhelper instance
        DbHelper dbHelper = new DbHelper(this);
        //get writable database
        mDb = dbHelper.getWritableDatabase();


        Button saveBtn = (Button)findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                finish();
                // Code here executes on main thread after user presses button
            }
        });

        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                finish();
                // Code here executes on main thread after user presses button
            }
        });

    }


    //if the player saves a new alarm, it is added to the database
    private void addNewAlarm(int time)
    {
        ContentValues cv = new ContentValues();
        cv.put(DataContract.DataEntry.COLUMN_TIME, time);
        cv.put(DataContract.DataEntry.COLUMN_READ_PRICE, 1);
        cv.put(DataContract.DataEntry.COLUMN_REPEAT, 4);

        //insert it into the table
        mDb.insert(DataContract.DataEntry.TABLE_NAME, null, cv);
    }
}
