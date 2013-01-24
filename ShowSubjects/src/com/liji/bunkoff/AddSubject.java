package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class AddSubject extends SherlockFragmentActivity {
	
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
			if(!subList.get(0).getSubjectName().equals("NULLTEXT")){
				nameText.setText(subList.get(0).getSubjectName());
			}
			if(!(subList.get(0).getTotalClass()==21001)){
				totalText.setText(String.valueOf(subList.get(0).getTotalClass()));
			}
			if(!(subList.get(0).getMinAttendence()==21001)){
				minText.setText(String.valueOf(subList.get(0).getMinAttendence()));
			}
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
			nameText=(EditText) findViewById(R.id.nameText);
			totalText=(EditText) findViewById(R.id.totalClassText);
			minText=(EditText) findViewById(R.id.minAttendenceText);
	    	if(nameText.getText().toString().trim().equals("")){
		    	AddDialog addDialogFragment = AddDialog.newInstance("Subject Name cannot be empty.!!");
		        addDialogFragment.show(getSupportFragmentManager(), "Error1");	    	
		        }
	    	else if(totalText.getText().toString().trim().equals("")){
		    	AddDialog addDialogFragment = AddDialog.newInstance("Number of classes cannot be empty.!!");
		        addDialogFragment.show(getSupportFragmentManager(), "Error2");	
	    	}
	    	else if(minText.getText().toString().trim().equals("")){
		    	AddDialog addDialogFragment = AddDialog.newInstance("Minimum attendence cannot be empty.!!");
		        addDialogFragment.show(getSupportFragmentManager(), "Error3");		    	}
	    	else{
	    		addSubject();	    
	    		}
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
		String tempName;
		int tempTotalClass;
		int tempMinAttendence;
		if(tempNameText.getText().toString().trim().equals("")){
			tempName="NULLTEXT";
		}
		else
		{
			tempName=tempNameText.getText().toString();
		}
		if(tempTotalText.getText().toString().trim().equals("")){
			tempTotalClass=21001;
		}
		else{
			tempTotalClass=Integer.parseInt(tempTotalText.getText().toString());
		}
		if(tempMinText.getText().toString().trim().equals("")){
			tempMinAttendence=21001;
		}
		else{
			tempMinAttendence=Integer.parseInt(tempMinText.getText().toString());
		}
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