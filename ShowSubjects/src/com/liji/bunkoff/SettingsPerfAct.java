package com.liji.bunkoff;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;

public class SettingsPerfAct extends Activity {
	private Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_perf);
		getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefSettingsFragment()).commit();
	}

	public class PrefSettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settingspref);
            Preference customPref = (Preference) findPreference("factResetPref");
			customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				 public boolean onPreferenceClick(Preference preference) {
					Intent in =new Intent(context,DataDeleteActivity.class); 
					context.startActivity(in);
					return true;
				 }
			});
        }
    }

}
