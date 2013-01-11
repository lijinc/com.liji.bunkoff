package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class BootService extends Service {
	public BootService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	@Override	
	public void onDestroy() {	
		Log.d("boot", "Distroyed");
		super.onDestroy();
	}	
	public int onStartCommand (Intent intent, int flags, int startId){
		SharedPreferences setPreference=PreferenceManager.getDefaultSharedPreferences(this);
		if(setPreference.getBoolean("checkBoxRebootPref", true)){
			Intent i = new Intent(this,InitializeAlarmService.class);
			Log.d("boot", "on");
			startService(i);
			stopSelf();
		}
		else{
			Log.d("boot", "off");
			stopSelf();
		}
	
		return Service.START_NOT_STICKY;	
	}
}
