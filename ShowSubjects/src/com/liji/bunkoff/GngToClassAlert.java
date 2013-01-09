package com.liji.bunkoff;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;

public class GngToClassAlert extends Activity {
	private DatabaseHandlerForAttendence dba;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dba = new DatabaseHandlerForAttendence(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gng_to_class_alert);
		Log.d("dfff", "im alert");
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
		           WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
		           WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
		           WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		Intent i=getIntent();
		Bundle b=i.getExtras();
		Subject sub=b.getParcelable("subject");
		final Bunk bunk=b.getParcelable("bunk");
		Intent is =new Intent(this,InitializeAlarmService.class);
		startService(is);
		AlertDialog.Builder gngToClassDialogBuilder = new AlertDialog.Builder(this);
		gngToClassDialogBuilder.setTitle("Class!!");
		gngToClassDialogBuilder.setMessage("Are You Attending The "+sub.getSubjectName()+" Class??");
		gngToClassDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Log.d("hii", "iam gng to class");
				dba.close();
				finish();
			}
		  });
		gngToClassDialogBuilder.setCancelable(false);
		gngToClassDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Log.d("hii", "i bunked");
				dba.addBunk(bunk);
				finish();
			}
		});
		AlertDialog gngToClassDialog = gngToClassDialogBuilder.create();
		gngToClassDialog.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_gng_to_class_alert, menu);
		return true;
	}

}
