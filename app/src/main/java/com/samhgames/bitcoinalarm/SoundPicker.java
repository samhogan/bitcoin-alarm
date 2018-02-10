package com.samhgames.bitcoinalarm;

import android.database.Cursor;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SoundPicker extends AppCompatActivity
{

    String[] ringtonesStrings, ringtonesUris;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_picker);

        radioGroup = findViewById(R.id.radio_sounds);

        getRingtones();
        setRadioButtons();


    }


    public void setRadioButtons()
    {
        //RadioButton button;
        for(int i = 0; i < ringtonesStrings.length; i++)
        {
            RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_sound, null);
            radioButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            radioButton.setText("  " + ringtonesStrings[i]);
            radioButton.setId(i+100);
           // button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            radioGroup.addView(radioButton);

            //View divider = (View)getLayoutInflater().inflate(R.layout.view_divider, null);
           // radioGroup.addView(divider);
        }

    }

    //gets all ringtone names and uris
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
