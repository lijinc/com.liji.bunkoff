package com.liji.bunkoff;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class DataDeleteActivity extends SherlockFragmentActivity {
	private DatabaseHandlerForSubject dbs;
	private DatabaseHandlerForLecture dbl;
	private DatabaseHandlerForAttendence dba;
	private DatabaseHandlerForTempSubject dbts;
	private DatabaseHandlerForTempLecture dbtl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_delete);
		DeleteAllSubjectDialog delDialogFragment = DeleteAllSubjectDialog.newInstance("Are you sure you want to delete the selected Subjects?","1001");
        delDialogFragment.show(getSupportFragmentManager(), "Delete");
	}
	
	public void okClicked(){
		Log.d("hii", "msg");
		stopService(InitializeAlarmService.class);
		dbs=new DatabaseHandlerForSubject(this);
		dbl=new DatabaseHandlerForLecture(this);
		dba=new DatabaseHandlerForAttendence(this);
		dbts=new DatabaseHandlerForTempSubject(this);
		dbtl=new DatabaseHandlerForTempLecture(this);
		dbl.deleteAllLecture();
		dba.deleteAllBunks();
		dbs.deleteAllSubject();
		dbts.deleteTempSubject();
		dbtl.deleteTempLecture();
		dbs.createTable();
		Intent in=new Intent(this,SettingsPerfAct.class);
		startActivity(in);
	}

	private void stopService(Class<InitializeAlarmService> class1) {
		// TODO Auto-generated method stub
		
	}

}
