package com.samhgames.bitcoinalarm.data;

import java.io.Serializable;

/**
 * Created by samho on 1/27/2018.
 */

//holds data temporarily for a single alarm while it is being edited
public class AlarmInfo implements Serializable
{
    private int time, minutes, hours, days;
    private boolean enabled;

    private String soundName, soundUri;

    private long id;

    private boolean[] daysArray;

    public AlarmInfo(int _time, long _id, boolean _enabled, String _soundName, String _soundUri, int _days)
    {
        setTime(_time);
        id = _id;
        enabled = _enabled;
        soundName = _soundName;
        soundUri = _soundUri;
        days = _days;

        daysArray = new boolean[7];

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

    public int getDays()
    {
        return days;
    }

    public void setDays(int days)
    {
        this.days = days;
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
}
