package com.liji.bunkoff;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
public class ShowSubjectInfo extends SherlockFragmentActivity {
	public static final String[] CONTENT = new String[] { "Bunks","Lectures" };
	private ViewPagerAdapter adapter;
    private List<Bunk> blist;
    private List<Lecture> llist;
	private DatabaseHandlerForLecture dbl;
	private DatabaseHandlerForAttendence dba;
	private static TabPageIndicator indicator;
	private static ViewPager pager;
	private static int sId;
	private static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//try to fix view with listAdapter
		context=this;
    	Intent in=new Intent(this,InitializeAlarmService.class);
    	startService(in);
		setContentView(R.layout.activity_show_subject_info);
		Intent i=getIntent();
		sId=i.getIntExtra("subject",0);
        pager =(ViewPager)findViewById( R.id.viewpager );
        indicator = (TabPageIndicator)findViewById( R.id.indicator );
        setView();

	}
	
	public void okClicked() {
		dba=new DatabaseHandlerForAttendence(this);
		dbl=new DatabaseHandlerForLecture(this);
    	if(dbl.getLectureCount(sId)!=0){
    		for(Lecture lec:CheckBoxLectureArrayAdapter.LECTLIST){
		    	if(lec.isSelected()==true){
		    		dbl.deleteLecture(lec);
		    	}
    		}
    	}
    	if(dba.getBunkCount(sId)!=0){
    		for(Bunk bunk:CheckBoxBunkArrayAdapter.BUNKLIST){
	    		if(bunk.isSelected()==true){
	    			dba.deleteBunk(bunk);
	    		}
	    	}
    	}
    	setView();
    	ViewPagerAdapter.mmd.finish();
    	Intent i=new Intent(this,InitializeAlarmService.class);
    	startService(i);
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
	    	if(DetailOnPageChangeListener.CURRENTPAGE==1){
				Intent intent = new Intent(this, AddLectureHour.class);
				intent.putExtra("fromSubInfoId", sId);
				Log.d("from sub info", String.valueOf(sId));
				DetailOnPageChangeListener.CURRENTPAGE=0;
			    startActivity(intent);
	    	}
	    	else{
	    		//change layout to add bunk from a sub
				Intent intent = new Intent(this, AddBunk.class);
				intent.putExtra("fromSubInfoId", sId);
			    startActivity(intent);
	    	}
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
	  public static void setView(){
		  ViewPagerAdapter adapter = new ViewPagerAdapter( context,sId );
		  pager.setAdapter( adapter );
	      indicator.setOnPageChangeListener(new DetailOnPageChangeListener());
	      indicator.setViewPager(pager, DetailOnPageChangeListener.CURRENTPAGE);
	  }

	
	
}
