package com.samhgames.bitcoinalarm.data;

/**
 * Created by samho on 1/27/2018.
 */

//holds data temporarily for a single alarm while it is being edited
public class AlarmInfo
{
    private int time, minutes, hours;
    private boolean enabled;

    private long id;

    public AlarmInfo(int _time, long _id, boolean _enabled)
    {
        setTime(_time);
        id = _id;
        enabled = _enabled;
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

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
}
