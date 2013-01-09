package com.liji.bunkoff;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class ShowAllBunks extends SherlockActivity {
	private DatabaseHandlerForAttendence dba;
	private DatabaseHandlerForSubject dbs;
	private ListView bunkListView;
	private Subject sub=new Subject();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_all_bunks);
		bunkListView= (ListView)findViewById(R.id.showAllBunkList);
		Intent i = getIntent();
		String subject = i.getStringExtra("subject");
		dba=new DatabaseHandlerForAttendence(this);
		dbs=new DatabaseHandlerForSubject(this);
		sub=dbs.getSubject(subject);
		Log.d("hii", "im here "+String.valueOf(sub.getId()));
		final List<Bunk> bunkList =dba.getAllBunks(sub.getId());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_list_item_1, android.R.id.text1);
		for(Bunk bunk:bunkList){
			adapter.add(String.valueOf(bunk.getHour())+":"+String.valueOf(bunk.getMinute())+"Day:"+String.valueOf(bunk.getDayOfMonth()));
		}
		bunkListView.setAdapter(adapter);
	}

}
