package com.samhgames.bitcoinalarm.data;


import java.io.Serializable;

/**
 * Created by samho on 1/27/2018.
 */

//holds data temporarily for a single alarm while it is being edited
public class AlarmInfo implements Serializable
{
    //time is the time in minutes since 12 am, minutes and hours can be derived from it
    private int time, minutes, hours;
    private boolean enabled;

    private String soundName, soundUri;

    //the id for the database, NOT for the alarmManager, that is the time
    private long id;

    private boolean[] daysArray;

    //milliseconds since the epoch, for one time alarms, acts as the date in case the phone is rebooted, and the alarm time has already passed
    private long millis;

    public AlarmInfo(int _time, long _id, boolean _enabled, String _soundName, String _soundUri, int days, long _millis)
    {
        setTime(_time);
        id = _id;
        enabled = _enabled;
        soundName = _soundName;
        soundUri = _soundUri;
        setDaysArray(days);
        millis = _millis;

    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
        minutes = time%60;
        hours = time/60;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public int getMinutes(){return minutes;}



    public int getHours() {return hours;}

    public int getDaysInt()
    {
        int num = 0;
        for(int i=0; i<7; i++)
        {
            num = num+ (daysArray[i]?1:0)*(1<<i);
        }

        return num;
    }

    public void setDaysArray(int days)
    {
        daysArray = new boolean[7];

//        daysArray[0] = (days&1)!=0;//sunday
//        daysArray[1] = (days&2)!=0;//MONDAY
//        daysArray[2] = (days&4)!=0;
//        daysArray[3] = (days&8)!=0;
//        daysArray[4] = (days&16)!=0;
//        daysArray[5] = (days&32)!=0;
//        daysArray[6] = (days&64)!=0;

        for(int i=0; i<7; i++)
        {
            daysArray[i] = (days&(1<<i))!=0;
        }

    }

    public boolean[] getDaysArray()
    {
        return daysArray;
    }

    public void setDaysArray(boolean[] daysArray)
    {
        this.daysArray = daysArray;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getSoundName()
    {
        return soundName;
    }

    public void setSoundName(String soundName)
    {
        this.soundName = soundName;
    }

    public String getSoundUri()
    {
        return soundUri;
    }

    public void setSoundUri(String soundUri)
    {
        this.soundUri = soundUri;
    }

    public long getMillis()
    {
        return millis;
    }

    public void setMillis(long millis)
    {
        this.millis = millis;
    }

    public boolean isRepeating()
    {
        return getDaysInt()>0;
    }
}
