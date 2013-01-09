package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class AddSubject extends SherlockActivity {
	
	private EditText nameText;
	private EditText totalText;
	private EditText minText;
	private DatabaseHandlerForSubject dbs;
	private DatabaseHandlerForLecture dbl;
	private DatabaseHandlerForTempSubject dbsTemp;
	private DatabaseHandlerForTempLecture dblTemp;
	private List<Lecture> lectureList = new ArrayList<Lecture>();
	private List<Subject> subList = new ArrayList<Subject>();
	private ListView lectureListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_subject);
		Intent i=getIntent();
		if(i.hasExtra("lectureparcel")){
			dbs=new DatabaseHandlerForSubject(this);
			Bundle b=i.getExtras();
			Lecture lect=b.getParcelable("lectureparcel");
			lect.setId(dbs.getSubjectCount()+1);
			dblTemp=new DatabaseHandlerForTempLecture(this);
			dblTemp.addLecture(lect);
			dblTemp.close();
		}
		dbs = new DatabaseHandlerForSubject(this);
		dbsTemp=new DatabaseHandlerForTempSubject(this);
		dblTemp=new DatabaseHandlerForTempLecture(this);
		int sid=dbs.getSubjectCount()+1;
		dbs.close();
		subList=dbsTemp.getAllSubject(sid);
		dbsTemp.close();
		if(subList.size()!=0){
			nameText=(EditText) findViewById(R.id.nameText);
			totalText=(EditText) findViewById(R.id.totalClassText);
			minText=(EditText) findViewById(R.id.minAttendenceText);
			nameText.setText(subList.get(0).getSubjectName());
			totalText.setText(String.valueOf(subList.get(0).getTotalClass()));
			minText.setText(String.valueOf(subList.get(0).getMinAttendence()));
			lectureList=dblTemp.getAllLecures(sid);
			dblTemp.close();
		}
		lectureListView = (ListView) findViewById(R.id.lectureListView);
		LectureArrayAdapter lecAdapter =new LectureArrayAdapter(this,lectureList);
		lectureListView.setAdapter(lecAdapter);
	}
	
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.done_menu, menu);
	    return true;
	  }
	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.doneItem:
	    	addSubject();
		    break;

	    default:
	    	break;
	    }

	    return true;
	  }


	public void addLectureHour(View view){
		Intent intent = new Intent(this, AddLectureHour.class);
		EditText tempNameText=(EditText) findViewById(R.id.nameText);
		EditText tempTotalText=(EditText) findViewById(R.id.totalClassText);
		EditText tempMinText=(EditText) findViewById(R.id.minAttendenceText);
		String tempName=tempNameText.getText().toString();
		int tempTotalClass=Integer.parseInt(tempTotalText.getText().toString());
		int tempMinAttendence=Integer.parseInt(tempMinText.getText().toString());
		dbs = new DatabaseHandlerForSubject(this);
		dbsTemp=new DatabaseHandlerForTempSubject(this);
		dbsTemp.deleteAllSubject();
		int sid=dbs.getSubjectCount()+1;
		dbsTemp.addSubject(new Subject(sid,tempName,tempTotalClass,tempMinAttendence));
		intent.putExtra("fromAddSubid", sid);
	    startActivity(intent);
	}
	
	public void addSubject(){
		nameText=(EditText) findViewById(R.id.nameText);
		totalText=(EditText) findViewById(R.id.totalClassText);
		minText=(EditText) findViewById(R.id.minAttendenceText);
		String sName=nameText.getText().toString();
		int totalClass=Integer.parseInt(totalText.getText().toString());
		int minAttendence=Integer.parseInt(minText.getText().toString());
		dbs=new DatabaseHandlerForSubject(this);
		dbs.addSubject(new Subject(sName,totalClass,minAttendence));
		dbl=new DatabaseHandlerForLecture(this);
		for(Lecture l:lectureList){
			dbl.addLecture(l);	///check for emptyness
		}
		dbsTemp=new DatabaseHandlerForTempSubject(this);
		dblTemp=new DatabaseHandlerForTempLecture(this);
		dbsTemp.deleteAllSubject();
		dblTemp.deleteAllLecture();
		Intent intent = new Intent(this,ShowSubjects.class);
		startActivity(intent);
		
	}
}