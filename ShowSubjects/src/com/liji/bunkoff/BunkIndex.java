package com.liji.bunkoff;

import com.actionbarsherlock.app.SherlockActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BunkIndex extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bunk_index);
		
	}
	
	public void addSubject(View view){
		Intent intent = new Intent(this, AddSubject.class);
	    startActivity(intent);
	    
	}
	
	public void removeSubject(View view){
		Intent intent = new Intent(this, RemoveSubject.class);
	    startActivity(intent);
	}
	public void showSubjects(View view){
		Intent intent = new Intent(this, ShowSubjects.class);
		intent.putExtra("bunk", false);
	    startActivity(intent);
	}
	
	public void enableAlarm(View view){
		Intent intent = new Intent(this,InitializeAlarmService.class);
		startService(intent);
	}
	
	public void showAllBunks(View view){
		Intent intent = new Intent(this, ShowSubjects.class);
		intent.putExtra("bunk", true);
	    startActivity(intent);
	}
	
	public void addBunk(View view){
		Intent intent = new Intent(this,AddBunk.class);
		startActivity(intent);
	}
	
	
}
