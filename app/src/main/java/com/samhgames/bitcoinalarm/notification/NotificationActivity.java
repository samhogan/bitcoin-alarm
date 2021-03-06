package com.samhgames.bitcoinalarm.notification;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.samhgames.bitcoinalarm.AlarmSetter;
import com.samhgames.bitcoinalarm.CoinUtils;
import com.samhgames.bitcoinalarm.R;
import com.samhgames.bitcoinalarm.data.AlarmInfo;
import com.samhgames.bitcoinalarm.data.DbAccessor;

import java.io.IOException;
import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity
{

    //mediaplayer
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        AlarmSetter.setManager(this);

        long id = getIntent().getLongExtra("ID", -1);

       // Log.d("Idkwhatthismeans", "" + getIntent().getIntExtra("bobby", 4));
        //Log.d("Idkwhatthismeansy", "g" + getIntent().getIntExtra("bob", 4));

        AlarmInfo info = (AlarmInfo)getIntent().getSerializableExtra("Info");

        //Log.d("tagyoureit3", "" + (info==null));



        ///getSupportActionBar().hide();
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00FFFFFF"));
        }*/
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        //idk if i need this...
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


       // Log.d("notif", "actually this should be obvious");

        //start the alarm
        Uri uri = Uri.parse(info.getSoundUri());
        //Ringtone ringtone = RingtoneManager.getRingtone(this, uri);//idunno about this...
        //ringtone.play();

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_ALARM);
        player.setLooping(true);
        try {
            player.setDataSource(this,uri);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();


        //shut off after 3 seconds
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                player.stop();
            }
        }.start();


       // Calendar c = Calendar.getInstance();


        //Set click listeners for the two best buttons in the world

        final Context context = this;
        final AlarmInfo coolerInfo = info;

        Button snoozeBtn = findViewById(R.id.snoozeBtn);
        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                player.stop();
                AlarmSetter.snoozeAlarm(context, coolerInfo);
                finish();
            }
        });

        Button dismissBtn = findViewById(R.id.dismissBtn);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                player.stop();
                finish();
            }
        });


        TextView priceText = findViewById(R.id.price_text);
        String price = "$" + CoinUtils.getSavedPrice(this);
        priceText.setText(price);


        //if this alarm is not repeating, and its not a snooze, disable it (just for the visual switch)
        //if(info.isEnabled())// && info.getDaysInt()==0)
        //{
            info.setEnabled(false);
            new DbAccessor(this).saveAlarm(info, false);

       // }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        player.stop();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        player.stop();
        //finish();
    }



}
