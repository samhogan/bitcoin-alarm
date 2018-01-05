package com.samhgames.bitcoinalarm;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by samho on 1/4/2018.
 */

public class SettingsFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}
