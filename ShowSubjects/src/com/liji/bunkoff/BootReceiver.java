package com.liji.bunkoff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("hello", "i rebooted");

		Intent i = new Intent(context,BootService.class);
		context.startService(i);
	}
}
