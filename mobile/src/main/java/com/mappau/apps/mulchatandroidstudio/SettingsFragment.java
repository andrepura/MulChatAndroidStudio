package com.mappau.apps.mulchatandroidstudio;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by guru on 23.04.2015.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
