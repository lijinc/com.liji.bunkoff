package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.liji.bunkoff.ShowSubjects.ActionModeDelete;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ViewPagerAdapter extends PagerAdapter
{
        private static String[] titles = new String[] { "Bunks", "Lectures"};
        private final Context context;
        private int[] scrollPosition = new int[titles.length];
        private int sId;
        private DatabaseHandlerForLecture dbl;
        private DatabaseHandlerForAttendence dba;
        private ListView lecListView;
        private ListView bunkListView;
    	ActionBar actionBar;
        ActionMode mMode;
        public static ActionMode mmd;
        public ViewPagerAdapter( Context context,int sId )
        {		
        		this.sId=sId;
                this.context = context;
                for ( int i = 0; i < titles.length; i++ )
                {
                        scrollPosition[i] = 0;
                }
        }

        @Override
        public String getPageTitle( int position )
        {
                return titles[position];
        }

        @Override
        public int getCount()
        {
                return titles.length;
        }

        @Override
        public Object instantiateItem( View pager, final int position )
        {		
        	dbl=new DatabaseHandlerForLecture(context);
        	dba=new DatabaseHandlerForAttendence(context);
        	List<Lecture> lecList=dbl.getAllLecures(sId);
        	List<Bunk> bunkList=dba.getAllBunks(sId);
        	lecListView = new ListView( context );
        	bunkListView = new ListView( context );
        	
        	if(position==1){
        		if(lecList.isEmpty()){
                    lecListView.setAdapter( null);
        		}
        		else{
        			CheckBoxLectureArrayAdapter chkLecAdapter =new CheckBoxLectureArrayAdapter(context,lecList);
                    lecListView.setAdapter( chkLecAdapter );
        		}
                lecListView.setSelection( scrollPosition[ position ] );
                lecListView.setOnItemClickListener(new OnItemClickListener() {
        	        public void onItemClick(AdapterView<?> parent, View view,
        		            int position, long id) {
        		        	
        		                Lecture lec=CheckBoxLectureArrayAdapter.LECTLIST.get(position);
        		                if(lec.isSelected()){
        			                lec.setSelected(false);
        			                CheckBoxLectureArrayAdapter.LECTLIST.set(position, lec);
        			                view.setBackgroundColor(Color.TRANSPARENT);

        		                }
        		                else{	                	
        			                lec.setSelected(true);
        			                CheckBoxLectureArrayAdapter.LECTLIST.set(position, lec);
        			                view.setBackgroundColor(Color.parseColor("#7733B5E5"));
        		                }

        		        }
        		      });
                lecListView.setOnItemLongClickListener(new OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                            int pos, long id) {
                        arg1.setBackgroundColor(Color.parseColor("#7733B5E5"));
                        Lecture lec=CheckBoxLectureArrayAdapter.LECTLIST.get(pos);
                        lec.setSelected(true);
                        CheckBoxLectureArrayAdapter.LECTLIST.set(pos, lec);
                        mMode = ((SherlockFragmentActivity) context).startActionMode(new ActionModeDelete());
						return true;
                    }
                });
                lecListView.setOnScrollListener( new OnScrollListener(){
                          
                            @Override
                            public void onScrollStateChanged( AbsListView view, int scrollState )
                            {
                            }
                            
                            @Override
                            public void onScroll( AbsListView view, int firstVisibleItem,
                                            int visibleItemCount, int totalItemCount )
                            {                            	
                                scrollPosition[ position ] = firstVisibleItem;
                            }
                    } );
                ((ViewPager)pager ).addView(lecListView, 0 );
                return lecListView;
                
        	}
        	else{  
        		if(bunkList.isEmpty()){
                    bunkListView.setAdapter(null);
        		}
        		else{
                	CheckBoxBunkArrayAdapter chkBunkAdapter =new CheckBoxBunkArrayAdapter(context,bunkList);
                    bunkListView.setAdapter(chkBunkAdapter);
        		}
                bunkListView.setSelection( scrollPosition[ position ] );
                bunkListView.setOnItemClickListener(new OnItemClickListener() {
        	        public void onItemClick(AdapterView<?> parent, View view,
        		            int position, long id) {
        		        	
        		                Bunk bun=CheckBoxBunkArrayAdapter.BUNKLIST.get(position);
        		                if(bun.isSelected()){
        			                bun.setSelected(false);
        			                CheckBoxBunkArrayAdapter.BUNKLIST.set(position, bun);
        			                view.setBackgroundColor(Color.TRANSPARENT);

        		                }
        		                else{	                	
        			                bun.setSelected(true);
        			                CheckBoxBunkArrayAdapter.BUNKLIST.set(position, bun);
        			                view.setBackgroundColor(Color.parseColor("#7733B5E5"));
        		                }

        		        }
        		      });
                bunkListView.setOnItemLongClickListener(new OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                            int pos, long id) {
                        arg1.setBackgroundColor(Color.parseColor("#7733B5E5"));
                        Bunk bun=CheckBoxBunkArrayAdapter.BUNKLIST.get(pos);
                        bun.setSelected(true);
                        CheckBoxBunkArrayAdapter.BUNKLIST.set(pos, bun);
                        mMode = ((SherlockFragmentActivity) context).startActionMode(new ActionModeDelete());
						return true;
                    }
                });
                bunkListView.setOnScrollListener( new OnScrollListener(){
                            
                            @Override
                            public void onScrollStateChanged( AbsListView view, int scrollState )
                            {
                            }
                            
                            @Override
                            public void onScroll( AbsListView view, int firstVisibleItem,
                                            int visibleItemCount, int totalItemCount )
                            {
                                    scrollPosition[ position ] = firstVisibleItem;
                            }
                    } );
                ((ViewPager)pager ).addView(bunkListView, 0 );
                return bunkListView;
        	}
        	
        	

        }

        @Override
        public void destroyItem( View pager, int position, Object view )
        {
                ( (ViewPager) pager ).removeView( (ListView) view );
        }

        @Override
        public boolean isViewFromObject( View view, Object object )
        {
                return view.equals( object );
        }

        @Override
        public void finishUpdate( View view )
        {
        }

        @Override
        public void restoreState( Parcelable p, ClassLoader c )
        {
                if ( p instanceof ScrollState )
                {
                        scrollPosition = ( (ScrollState) p ).getScrollPos();
                }
        }

        @Override
        public Parcelable saveState()
        {
                return new ScrollState( scrollPosition );
        }

        @Override
        public void startUpdate( View view )
        {
        }
        
        public final class ActionModeDelete implements ActionMode.Callback {
    		@Override
    		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    			// TODO Auto-generated method stub
    			return false;
    		}
    		
    		@Override
    		public void onDestroyActionMode(ActionMode mode) {
    			int pos=0;
    			for(Lecture lec:CheckBoxLectureArrayAdapter.LECTLIST){
    				 lec.setSelected(false);
    				 CheckBoxLectureArrayAdapter.LECTLIST.set(pos, lec);
    	             pos++;
    			}
    			pos=0;
    			for(Bunk bun:CheckBoxBunkArrayAdapter.BUNKLIST){
    				bun.setSelected(false);
    				CheckBoxBunkArrayAdapter.BUNKLIST.set(pos, bun);
    				pos++;
    			}
    			ShowSubjectInfo.setView();
    		}
			
    		@Override
    		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    	         MenuInflater inflater = mode.getMenuInflater();
    	         inflater.inflate(R.menu.longclicksubmenu, menu);
    	         return true;
    		}
    		
    		@Override
    		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    			mmd=mode;  
    			switch (item.getItemId()) {
    	            case R.id.itemDelete:
    	            	dbl=new DatabaseHandlerForLecture(context);
    	            	dba=new DatabaseHandlerForAttendence(context);
    	            	int count=0;
    	            	if(dbl.getLectureCount(sId)!=0){
    	              		for(Lecture lec:CheckBoxLectureArrayAdapter.LECTLIST){
    	            	    	if(lec.isSelected()==true){
    	            	    		count++;
    	            	    	}
    	            		}
    	            	}
    	            	if(dba.getBunkCount(sId)!=0){
    	            		for(Bunk bunk:CheckBoxBunkArrayAdapter.BUNKLIST){
    	                		if(bunk.isSelected()==true){
    	                			count++;
    	                		}
    	                	}
    	            	}
    	            	if(count==0){
    	                	CountDialog countDialogFragment = CountDialog.newInstance();
    	                    countDialogFragment.show(((SherlockFragmentActivity) context).getSupportFragmentManager(), "Error");
    	            	}
    	            	else{
    	                	DeleteDialog delDialogFragment = DeleteDialog.newInstance("Are you sure you want to delete the selected items?","1000");
    	                    delDialogFragment.show(((SherlockFragmentActivity) context).getSupportFragmentManager(), "Delete");    
    	            	}
    	            	
    	            	return true;
    	            default:
    	                mode.finish();
    	                return false;
    	       }			
    		}
    	}

}