package com.liji.bunkoff;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class DataDeleteActivity extends SherlockFragmentActivity {
	private DatabaseHandlerForSubject dbs;
	private DatabaseHandlerForLecture dbl;
	Context context=this;
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

}
