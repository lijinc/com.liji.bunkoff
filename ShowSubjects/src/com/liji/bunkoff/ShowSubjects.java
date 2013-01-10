package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ShowSubjects extends SherlockFragmentActivity {
	ActionBar actionBar;
    ActionMode mMode;
    int longClickFlag=0;
	private DatabaseHandlerForSubject dbs;
	private DatabaseHandlerForLecture dbl;
	private DatabaseHandlerForAttendence dba;
	private ListView subjectListView;
	private SubjectArrayAdapter subAdapter ;
	Context context;
	List<Subject> subjectList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.activity_show_subjects);
		actionBar = getSupportActionBar();
		//if subcount== 0 show help
		setTitle("Bunk-Off!!!");
		subjectListView = (ListView) findViewById(R.id.showAllSubjectList);
		dbs=new DatabaseHandlerForSubject(this);
		subjectList =new ArrayList<Subject>();
		subjectList=dbs.getAllSubject();
		dbs.close();
		subAdapter =new SubjectArrayAdapter(this,subjectList);
		subjectListView.setAdapter(subAdapter);
		subjectListView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	        	if(longClickFlag==0){
		        	Subject sub = (Subject) parent.getItemAtPosition(position);
		        	Intent i = new Intent(getApplicationContext(), ShowSubjectInfo.class);
		        	i.putExtra("subject", sub.getId());
			      	startActivity(i);
	        	}
	        	else{
	                Subject sub=subjectList.get(position);
	                if(sub.isSelected()){
		                sub.setSelected(false);
		                subjectList.set(position, sub);
		                view.setBackgroundColor(Color.TRANSPARENT);

	                }
	                else{	                	
		                sub.setSelected(true);
		                subjectList.set(position, sub);
		                view.setBackgroundColor(Color.parseColor("#7733B5E5"));
	                }

	        	}
	        }
	      });
		subjectListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		subjectListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                // TODO Auto-generated method stub
                arg1.setBackgroundColor(Color.parseColor("#7733B5E5"));
                longClickFlag=1;
                Subject sub=subjectList.get(pos);
                sub.setSelected(true);
                subjectList.set(pos, sub);
                mMode = startActionMode(new ActionModeDelete());
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
	    case R.id.itemSettings:
	    	Intent i = new Intent(this, SettingsPerfAct.class);
		    startActivity(i);
		    break;

	    default:
	    	break;
	    }

	    return true;
	  }
	
	
	  public final class ActionModeDelete implements ActionMode.Callback {
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub
			int pos=0;
			for(Subject sub:subjectList){
				 sub.setSelected(false);
	             subjectList.set(pos, sub);
	             pos++;
			}
			subjectListView.setAdapter(null);
			subjectListView.setAdapter(subAdapter);
			longClickFlag=0;

		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	         MenuInflater inflater = mode.getMenuInflater();
	         inflater.inflate(R.menu.longclicksubmenu, menu);
	         return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			  switch (item.getItemId()) {
	            case R.id.itemDelete:
	            	DeleteDialog delDialogFragment = DeleteDialog.newInstance("Are you sure you want to delete the selected Subjects?","1001");
			        delDialogFragment.show(getSupportFragmentManager(), "Delete1");
	            	return true;
	            default:
	                mode.finish();
	                return false;
	       }			
		}
	}
	  
		public void okClicked() {
			dbs=new DatabaseHandlerForSubject(context); 
			dbl=new DatabaseHandlerForLecture(context);
			dba=new DatabaseHandlerForAttendence(context);
        	for(Subject sub:subjectList){
        		if(sub.isSelected()){
    				dbs.deleteSubject(sub);
    				dbl.deleteLectureGivenId(sub.getId());
    				dba.deleteBunkGivenId(sub.getId());
        		}
			}
	    	Intent intent = new Intent(context, ShowSubjects.class);
		    startActivity(intent);
		}  
	  
}
