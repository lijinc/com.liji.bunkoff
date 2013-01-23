package com.liji.bunkoff;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;

public class GngToClass extends SherlockFragmentActivity {
	private DatabaseHandlerForAttendence dba;
	private Context context;
	public static Bunk bunk;
	private NotificationManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gng_to_class_alert);
		Log.d("dfff", "im alert");
		context=this;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
		           WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
		           WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
		           WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		Intent i=getIntent();
		Bundle b=i.getExtras();
		Subject sub=b.getParcelable("subject");
		bunk=b.getParcelable("bunk");
		Intent is =new Intent(this,InitializeAlarmService.class);
		startService(is);
		SharedPreferences setPreference=PreferenceManager.getDefaultSharedPreferences(this);
		Uri alarmSound=Uri.parse(setPreference.getString("ringtonePref", "DEFAULT_SOUND"));
	    NotificationCompat.Builder builder =  
	            new NotificationCompat.Builder(this)  
	            .setSmallIcon(R.drawable.ic_launcher)  
	            .setContentTitle("Bunk-Off")  
	            .setContentText("Class on "+sub.getSubjectName());
	    if(setPreference.getBoolean("checkBoxSilentPref", false)==false){
            builder.setSound(alarmSound);
	    }
	    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
	    manager.notify(10013, builder.build());  
		if(setPreference.getBoolean("checkBoxVibratePref", true)){
			Vibrator vibrator;
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 500, 300 };  
			vibrator.vibrate(pattern,1);
		}
		LectureDialog lecDialogFragment = LectureDialog.newInstance("Are you attending "+sub.getSubjectName()+" hour?","1002");
        lecDialogFragment.show(getSupportFragmentManager(), "Going");
	}


	void okClicked(){
		manager.cancel(10013);
		finish();
	}
	
	void cancelClicked(){
		Log.d("hii", "i bunked");
		dba = new DatabaseHandlerForAttendence(context);
		dba.addBunk(bunk);
		manager.cancel(10013);
		finish();
	}
}