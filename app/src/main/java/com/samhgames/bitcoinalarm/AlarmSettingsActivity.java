package com.samhgames.bitcoinalarm;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.samhgames.bitcoinalarm.data.DataContract;
import com.samhgames.bitcoinalarm.data.DbHelper;

public class AlarmSettingsActivity extends AppCompatActivity
{
    //the database containing a table of all alarms
    private SQLiteDatabase mDb;

    //is a new alarm being created (as opposed to a current alarm being edited)
    private boolean newAlarm;

    private TimePickerDialog timePicker;

    private long id;

    private Switch readSwitch;
    private TextView timeTextView;

    int time;
   // int hours;
    //int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //SharedPreferences settings = getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
        //settings.edit().clear().commit();
       /* getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AlarmSettingsFragment())
                .commit();*/

        setContentView(R.layout.activity_alarm_settings2);

        //hide the action bar (save and cancel buttons in its place
        getSupportActionBar().hide();

        newAlarm = false;

        //get the id passed into the intent so the data can be retrieved from the database
        if(getIntent().hasExtra("ID"))
            id = getIntent().getLongExtra("ID", -1);
        else
            newAlarm = true;


        readSwitch = (Switch)findViewById(R.id.read_switch);
        timeTextView = (TextView)findViewById(R.id.tv_time);

        setClickListeners();



        //apparently the right way to do this is to create a new instance of dbHelper...
        //dbhelper instance
        DbHelper dbHelper = new DbHelper(this);
        //get writable database
        mDb = dbHelper.getWritableDatabase();

        if(newAlarm)
        {
            time = 420;

        }
        else
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
            time = cursor.getInt(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_TIME));
            cursor.close();
        }

        //calculate the hours and minutes from the int time
        int hours = time/60;
        int minutes = time%60;

        setTimeText(hours, minutes);

        //set up the time picker
        timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                time = selectedHour*60 + selectedMinute;
                setTimeText(selectedHour, selectedMinute);
            }
        }, hours, minutes, false);

        //show the timePicker immediately if it is a new alarm
        if(newAlarm)
            timePicker.show();

    }

    //set the top time text
    void setTimeText(int hours, int minutes)
    {
        //add a check for 24 hour clock
        String postfix = "AM";
        int realHours = hours;
        if(hours>12)
        {
            postfix = "PM";
            realHours-=12;
        }

        String summary = realHours + ":" + minutes + " " + postfix;

        timeTextView.setText(summary);

    }

    //for some organization
    private void setClickListeners()
    {
        findViewById(R.id.time_card).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                timePicker.show();
            }
        });

        //read aloud switch
        findViewById(R.id.read_card).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                readSwitch.setChecked(!readSwitch.isChecked());
            }
        });

        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                saveAlarm();
                finish();
            }
        });

        //canceling saves none of the data
        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    private void saveAlarm()
    {
        //time = time
        //encode all the data to be saved
        int readNum = readSwitch.isChecked() ? 1:0;
        int repeatNum = 5;

        ContentValues cv = new ContentValues();
        cv.put(DataContract.DataEntry.COLUMN_TIME, time);
        cv.put(DataContract.DataEntry.COLUMN_READ_PRICE, readNum);
        cv.put(DataContract.DataEntry.COLUMN_REPEAT, repeatNum);

        if(newAlarm)//insert it into the table
            mDb.insert(DataContract.DataEntry.TABLE_NAME, null, cv);
        else//update it
        {
            String strFilter = DataContract.DataEntry._ID + " = " + id;
            mDb.update(DataContract.DataEntry.TABLE_NAME, cv, strFilter, null);
        }


    }

    //if the player saves a new alarm, it is added to the database
    private void addAlarm()
    {
        ContentValues cv = new ContentValues();
        cv.put(DataContract.DataEntry.COLUMN_TIME, time);
        cv.put(DataContract.DataEntry.COLUMN_READ_PRICE, 1);
        cv.put(DataContract.DataEntry.COLUMN_REPEAT, 4);

        //insert it into the table
        mDb.insert(DataContract.DataEntry.TABLE_NAME, null, cv);
    }
}
