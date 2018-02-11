package com.samhgames.bitcoinalarm.data;

import java.io.Serializable;

/**
 * Created by samho on 1/27/2018.
 */

//holds data temporarily for a single alarm while it is being edited
public class AlarmInfo implements Serializable
{
    private int time, minutes, hours;
    private boolean enabled;

    private String soundName, soundUri;

    private long id;

    public AlarmInfo(int _time, long _id, boolean _enabled, String _soundName, String _soundUri)
    {
        setTime(_time);
        id = _id;
        enabled = _enabled;
        soundName = _soundName;
        soundUri = _soundUri;

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
