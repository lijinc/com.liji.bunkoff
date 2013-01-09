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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ShowSubjects extends SherlockActivity {
	ActionBar actionBar;
	private DatabaseHandlerForSubject dbs;
	private ListView subjectListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_subjects);
		actionBar = getSupportActionBar();
		//if subcount== 0 show help
		setTitle("Bunk-Off!!!");
		subjectListView = (ListView) findViewById(R.id.showAllSubjectList);
		dbs=new DatabaseHandlerForSubject(this);
		List<Subject> subjectList =new ArrayList<Subject>();
		subjectList=dbs.getAllSubject();
		dbs.close();
		SubjectArrayAdapter subAdapter =new SubjectArrayAdapter(this,subjectList);
		subjectListView.setAdapter(subAdapter);
		subjectListView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	        	Subject sub = (Subject) parent.getItemAtPosition(position);
	        	Intent i = new Intent(getApplicationContext(), ShowSubjectInfo.class);
	        	i.putExtra("subject", sub.getId());
		      	startActivity(i);
	        }
	      });
		subjectListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                // TODO Auto-generated method stub
                Log.v("long clicked","pos"+" "+pos);

                return true;
            }
        }); 
	}

	 @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.add_menu, menu);
	    return true;
	  }
	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.addSubjectItem:
	    	Intent intent = new Intent(this, AddSubject.class);
		    startActivity(intent);
		    break;

	    default:
	    	break;
	    }

	    return true;
	  }
	
	
	  
}
