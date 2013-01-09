package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
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

}