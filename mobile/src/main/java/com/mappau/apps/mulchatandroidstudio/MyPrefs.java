package com.mappau.apps.mulchatandroidstudio;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by guru on 23.04.2015.
 */
@SharedPref
public interface MyPrefs {

    @DefaultString("andre")
    String name();

}
