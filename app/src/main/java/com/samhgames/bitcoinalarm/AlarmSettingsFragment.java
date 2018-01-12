package com.samhgames.bitcoinalarm;

import android.os.Bundle;
import android.preference.PreferenceFragment;


/**
 * Created by samho on 1/6/2018.
 */

public class AlarmSettingsFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.alarm_preferences);


    }

}
