package com.samhgames.bitcoinalarm.data;

/**
 * Created by samho on 1/27/2018.
 */

//holds data temporarily for a single alarm while it is being edited
public class AlarmInfo
{
    private int time;
    private long id;

    public AlarmInfo(int _time, long _id)
    {
        time = _time;
        id = _id;

    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public long getId()
    {
        return id;
    }

}
