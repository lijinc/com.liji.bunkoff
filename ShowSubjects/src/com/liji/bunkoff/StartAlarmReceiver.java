package com.liji.bunkoff;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StartAlarmReceiver extends BroadcastReceiver {
	

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(null, "Alarm!!!!");
		Intent is =new Intent(context,InitializeAlarmService.class);
		context.stopService(is);
		Bundle b=intent.getExtras();
		Intent i = new Intent(context,GngToClassAlert.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.putExtras(b);
		context.startActivity(i);
	}
}
