package com.samhgames.bitcoinalarm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.samhgames.bitcoinalarm.data.AlarmInfo;
import com.samhgames.bitcoinalarm.data.DbAccessor;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RestartAlarmsService extends IntentService
{
    public RestartAlarmsService()
    {
        super("RestartAlarmsService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        //restart all alarms, or disable them in the db if they are passed
        DbAccessor db = new DbAccessor(this);
        ArrayList<AlarmInfo> alarms = db.getAllAlarms();

        AlarmSetter.setManager(this);

        //get the current unix time milliseconds
        long currentMilis = Calendar.getInstance().getTimeInMillis();

        for(AlarmInfo alarm : alarms)
        {
            if(alarm.isEnabled())
            {
                //non-repeating alarm
                if(alarm.getDaysInt()==0)
                {
                    //if the time for this alarm hasn't passed
                    if(alarm.getMillis()>currentMilis)
                    {
                        AlarmSetter.setAlarm(alarm, this);
                        //Log.d("AlarmRestarter", "Alarm reset");
                    }
                    else//time passed
                    {
                        alarm.setEnabled(false);
                        db.saveAlarm(alarm, false);
                        //Log.d("AlarmRestarter", "Alarm canceled");

                    }

                }
                else
                {
                    AlarmSetter.setAlarm(alarm, this);
                }

            }

        }
    }
}
