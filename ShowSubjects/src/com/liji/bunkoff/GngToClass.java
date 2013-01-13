package com.liji.bunkoff;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;

public class GngToClass extends SherlockFragmentActivity {
	private DatabaseHandlerForAttendence dba;
	private Context context;
	private Bunk bunk;
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
		if(setPreference.getBoolean("checkBoxVibratePref", true)){
			Vibrator vibrator;
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 500, 300 };  
			vibrator.vibrate(pattern,2);
		}
		LectureDialog lecDialogFragment = LectureDialog.newInstance("Are you attending "+sub.getSubjectName()+" hour?","1002");
        lecDialogFragment.show(getSupportFragmentManager(), "Going");

	}


	void okClicked(){

	}
	
	void cancelClicked(){
		Log.d("hii", "i bunked");
		dba = new DatabaseHandlerForAttendence(context);
		dba.addBunk(bunk);
		finish();
	}
}
