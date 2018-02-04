package com.samhgames.bitcoinalarm;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.samhgames.bitcoinalarm.data.AlarmInfo;
import com.samhgames.bitcoinalarm.data.DataContract;
import com.samhgames.bitcoinalarm.data.DbAccessor;

public class AlarmSettingsActivity extends AppCompatActivity
{

    private DbAccessor db;

    private AlarmInfo info;

    //is a new alarm being created (as opposed to a current alarm being edited)
    private boolean newAlarm;

    private TimePickerDialog timePicker;

    private long id;

    private Switch readSwitch;
    private TextView timeTextView;

    Context context;

    //int time;
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
        context = this;

        newAlarm = false;

        //get the id passed into the intent so the data can be retrieved from the database
        if(getIntent().hasExtra("ID"))
            id = getIntent().getLongExtra("ID", -1);
        else
            newAlarm = true;


        readSwitch = (Switch)findViewById(R.id.read_switch);
        timeTextView = (TextView)findViewById(R.id.tv_time);

        setClickListeners();



        db = new DbAccessor(this);

        //create new info if a new alarm, else get it from the database
        if(newAlarm)
        {
            info = new AlarmInfo(420, -999, true);

        }
        else
        {
            info = db.getAlarm(id);

        }


        setTimeText(info.getHours(), info.getMinutes());

        //set up the time picker
        timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                info.setTime(selectedHour*60 + selectedMinute);
                setTimeText(selectedHour, selectedMinute);
            }
        }, info.getHours(), info.getMinutes(), false);

        //show the timePicker immediately if it is a new alarm
        if(newAlarm)
            timePicker.show();

    }

    //set the top time text
    void setTimeText(int hours, int minutes)
    {
        timeTextView.setText(TimeConverter.getTimeText(hours, minutes));
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
                db.saveAlarm(info, newAlarm);
                //save alarm also sets the id in info
                AlarmSetter.setAlarm(info, context);
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


    String[] ringtonesStrings, ringtonesUris;
    public void getRingtones( )
    {
        RingtoneManager ringtoneMgr = new RingtoneManager(this);
        ringtoneMgr.setType(RingtoneManager.TYPE_ALL);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        int alarmsCount = alarmsCursor.getCount();
        if (alarmsCount == 0 && !alarmsCursor.moveToFirst())
        {
        }
        else
        {
            ringtonesStrings = new String[alarmsCount];
            ringtonesUris = new String[alarmsCount];
            while(!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
                int currentPosition = alarmsCursor.getPosition();
                ringtonesStrings[currentPosition] =  ringtoneMgr.getRingtone(currentPosition).getTitle(getApplicationContext());
                ringtonesUris[currentPosition] =   ringtoneMgr.getRingtoneUri(currentPosition).toString();

            }
//alarmsCursor.close();

        }

    }

}
