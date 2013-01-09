package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowAllLectures extends SherlockActivity {
	private DatabaseHandlerForLecture dbl;
	private DatabaseHandlerForSubject dbs;
	private ListView lectureListView;
	private Subject sub=new Subject();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_all_lectures);
		lectureListView= (ListView)findViewById(R.id.showAllLectureList);
		Intent i = getIntent();
		String subject = i.getStringExtra("subject");
		dbl=new DatabaseHandlerForLecture(this);
		dbs=new DatabaseHandlerForSubject(this);
		sub=dbs.getSubject(subject);
		final List<Lecture> lectureList =dbl.getAllLecures(sub.getId());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_list_item_1, android.R.id.text1);		
		for(Lecture lec:lectureList){
			adapter.add(lec.getDay()+" "+String.valueOf(lec.getHour())+":"+String.valueOf(lec.getMinute()));
		}
		lectureListView.setAdapter(adapter);
		lectureListView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		        	Lecture lec=lectureList.get(position);
		        	Intent i = new Intent(getApplicationContext(), EditLectureHours.class);
		            i.putExtra("lectureid", lec.getId());
		            startActivity(i);
		        }
		      });
	}

	

}
