package com.samhgames.bitcoinalarm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SoundPicker extends AppCompatActivity
{

    String[] ringtonesStrings, ringtonesUris;

    RadioGroup radioGroup;

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_picker);

        final Context context = this;



        radioGroup = findViewById(R.id.radio_sounds);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                stopPlayer();
                Uri uri = Uri.parse(ringtonesUris[checkedId-100]);
                player = MediaPlayer.create(context, uri);
                player.setLooping(true);
                player.start();



            }
        });

        getRingtones();
        setRadioButtons();


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        stopPlayer();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        stopPlayer();
    }

    void stopPlayer()
    {
        if(player!=null && player.isPlaying())
        {
            player.stop();
            player.reset();
        }
    }

    public void setRadioButtons()
    {
        RadioButton radioButton;
        for(int i = 0; i < ringtonesStrings.length; i++)
        {
            radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_sound, null);
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
                Log.d("wow", "yeah this is here yo");

            }
//alarmsCursor.close();

        }

    }




    public void onBackPressed()
    {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if(checkedId>0)
        {
            Intent intent = new Intent();
            intent.putExtra("soundName", ringtonesStrings[checkedId - 100]);
            intent.putExtra("soundUri", ringtonesUris[checkedId - 100]);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return true;
    }

}
