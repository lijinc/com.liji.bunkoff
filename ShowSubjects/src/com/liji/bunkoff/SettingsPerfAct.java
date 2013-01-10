package com.liji.bunkoff;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.app.Activity;
import android.view.Menu;

public class SettingsPerfAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_perf);
		getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefSettingsFragment()).commit();
	}

	public static class PrefSettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settingspref);
        }
    }

}
