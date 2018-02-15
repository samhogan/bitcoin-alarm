package com.samhgames.bitcoinalarm;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.samhgames.bitcoinalarm.data.AlarmInfo;
import com.samhgames.bitcoinalarm.data.DataContract;
import com.samhgames.bitcoinalarm.data.DbAccessor;

import java.util.ArrayList;
import java.util.Arrays;

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

    private TextView soundTextView;

    Context context;



    //temporarily stores the days the user is picking, (reverted back if cancelled)
    boolean[] tempDays;

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
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
            info = new AlarmInfo(420, -999, true, ringtone.getTitle(this), uri.toString(), 0);

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

        soundTextView = findViewById(R.id.sound_text);
        soundTextView.setText(info.getSoundName());


       // createRepeatDialog();


        boolean[] a1 = {false, false, false};
        boolean[] a2 = a1.clone();
        boolean[] a3 = Arrays.copyOf(a1, 3);

        a1[0] = true;

        Log.d("arraytest", "a1[0] == " + a1[0]);
        Log.d("arraytest", "a2[0] == " + a2[0]);
        Log.d("arraytest", "a3[0] == " + a3[0]);


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

        //open the sound picker
        findViewById(R.id.sound_card).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent i = new Intent(getBaseContext(), SoundPicker.class);
                //startActivityForResult(i, 1);
                //startActivity(i);

                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(info.getSoundUri()));
                startActivityForResult(intent, 5);
            }
        });

        findViewById(R.id.repeat_card).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
               //repeatDialog.show();
                createRepeatDialog();
            }
        });


    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("wow", "oh ok i see " + resultCode);


//        if (requestCode == 1) {
//            if(resultCode == RESULT_OK) {
//                //Log.d("wow", "oh ok i seesfd " + resultCode);
//
//                info.setSoundName(data.getStringExtra("soundName"));
//                info.setSoundUri(data.getStringExtra("soundUri"));
//                soundTextView.setText(info.getSoundName());
//            }
//        }

        if (resultCode == RESULT_OK && requestCode == 5)
        {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
            {
                info.setSoundUri(uri.toString());
                info.setSoundName(RingtoneManager.getRingtone(this, uri).getTitle(this));


            }
            else
            {
                //this.chosenRingtone = null;
                info.setSoundName("None");
                info.setSoundUri("null");
            }

            soundTextView.setText(info.getSoundName());

        }
    }





    void createRepeatDialog()
    {

        tempDays = Arrays.copyOf(info.getDaysArray(), 7);

        //final ArrayList<Integer> selected = new ArrayList<Integer>();

        Log.d("test", "sunday is checked: " + info.getDaysArray()[0]);
        Log.d("test", "are arrays equal" + (tempDays == info.getDaysArray()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Repeat")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.days, tempDays,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {

                                tempDays[which] = isChecked;
                                Log.d("test", "sunday is checked2: " + info.getDaysArray()[0]);

                            }
                        })
                // Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                        info.setDaysArray(tempDays);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
//                        tempDays = info.getDaysArray().clone();
//
//                        for(int i=0; i<7; i++)
//                        {
//                            repeatDialog.getListView().setItemChecked(i, tempDays[i]);
//                        }
                    }
                });

        builder.create().show();
    }



}
