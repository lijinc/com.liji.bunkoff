package com.liji.bunkoff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NextDayInitializeReceiver extends BroadcastReceiver {
	

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("hii", "iam next day");
		Intent i = new Intent(context,InitializeAlarmService.class);
		context.stopService(i);
		context.startService(i);
	}
}
