package com.liji.bunkoff;

import java.util.Calendar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.liji.bunkoff.AddBunk.SelectTimeFragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;

public class AddLectureHour extends SherlockFragmentActivity {
	 private Spinner spinDay;
	 private String day;
	 private EditText timeText;
	 private int minute;
	 private int hour;
	 private int id;
	 private DatabaseHandlerForLecture dbl;
	 private Intent in;
	 final Calendar c=Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_lecture_hour);
		in=getIntent();
		spinDay=(Spinner) findViewById(R.id.daySpinner);
		spinDay.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			 public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
						day= parent.getItemAtPosition(pos).toString();
				  }
				 
				  @Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
		}
				);
		populateSetTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));    
	}
	
	public void selectTime(View view) {
		DialogFragment newFragment = new SelectTimeFragment();
		newFragment.show(getSupportFragmentManager(), "TimePicker");
	}

	public void addLectureHour(View view){
		Intent i=getIntent();
		if(i.hasExtra("fromAddSubid")){
			Intent intent = new Intent(this, AddSubject.class);
			Lecture lec = new Lecture(id,day,hour,minute);
			Bundle b=new Bundle();
			b.putParcelable("lectureparcel", lec);
			intent.putExtras(b);
			startActivity(intent);
		}
		else{
			id=i.getIntExtra("fromSubInfoId", 0);
			Intent intent = new Intent(this, ShowSubjectInfo.class);
        	intent.putExtra("subject", id);
			Lecture lec = new Lecture(id,day,hour,minute);
			dbl=new DatabaseHandlerForLecture(this);
			dbl.addLecture(lec);
			dbl.close();
			startActivity(intent);
		}
	}
	
	public void populateSetTime(int hour, int min) {
		this.hour=hour;
		this.minute=min;
		timeText = (EditText)findViewById(R.id.editTextTime);
		if(hour<10){
			timeText.setText("0"+hour+":"+min+" AM");
		}
		else if(hour>=10&&hour<12){
			timeText.setText(hour+":"+min+" AM");
		}
		else{
			timeText.setText((hour-12)+":"+min+" PM");
		}
	}
	
	public class SelectTimeFragment extends SherlockDialogFragment implements TimePickerDialog.OnTimeSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int hh = calendar.get(Calendar.HOUR_OF_DAY);
		int mm = calendar.get(Calendar.MINUTE);
		return new TimePickerDialog(getActivity(), this, hh, mm, false);
		}

		@Override
		public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
			populateSetTime(arg1,arg2);
		}
		 
		}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if(in.hasExtra("fromAddSubid")){
				Intent intent = new Intent(this, AddSubject.class);
				startActivity(intent);
	    	}
	    	else{
				Intent intent = new Intent(this, ShowSubjectInfo.class);
				startActivity(intent);
	    	}
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	 

}
