package com.samhgames.bitcoinalarm;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by samho on 1/6/2018.
 */

public class TimePreference extends DialogPreference
{
    private int totalMinutes;//500 == 8:20 for example
    private int hours = 0;
    private int minutes = 0;
    private TimePicker picker = null;

    public int getTime()
    {
        return totalMinutes;
    }
    public void setTime(int time)
    {
        totalMinutes = time;
        // Save to Shared Preferences
        if(callChangeListener(time))
            persistInt(time);

    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    @Override
    protected View onCreateDialogView() {
        picker=new TimePicker(getContext());

        return(picker);
    }

    @Override
    protected void onBindDialogView(View v)
    {
        super.onBindDialogView(v);

        picker.setCurrentHour(hours);
        picker.setCurrentMinute(minutes);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult)
        {
            hours = picker.getCurrentHour();
            minutes = picker.getCurrentMinute();

            setTime(hours*60+minutes);
            //notifyChanged();//dunno what this does but it seems important
            updateSummary();
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return(a.getInt(index, 0));
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        // Read the value. Use the default value if it is not possible.
        setTime(restorePersistedValue ?  getPersistedInt(totalMinutes) : (int) defaultValue);

        //set the hour and minute
        hours = totalMinutes / 60;
        minutes = totalMinutes % 60;

        updateSummary();

    }

    //updates the summary text of the preference
    protected void updateSummary()
    {
        //add a check for 24 hour clock
        String postfix = "AM";
        int realHours = hours;
        if(hours>12)
        {
            postfix = "PM";
            realHours-=12;
        }

        String summary = realHours + ":" + minutes + " " + postfix;

        setSummary(summary);

    }
}
