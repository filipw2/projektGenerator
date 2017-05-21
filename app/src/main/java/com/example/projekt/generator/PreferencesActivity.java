package com.example.projekt.generator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Filip on 2017-05-19.
 */

public class PreferencesActivity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);

        getFragmentManager().beginTransaction()
                .replace(R.id.preferences_container, new PreferencesFragment())
                .commit();

    }
}
