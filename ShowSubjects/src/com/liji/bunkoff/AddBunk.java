package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TimePicker.OnTimeChangedListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;

public class AddBunk extends SherlockFragmentActivity {
	private EditText dateText;
	private EditText timeText;
	private int minute;
	private int hour;
	private int subId;
	private int day;
	private int month;
	private int year;
	private DatabaseHandlerForAttendence dba;

	final Calendar c=Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bunk);
		Intent i=getIntent();
		subId=i.getIntExtra("fromSubInfoId", 0);
		populateSetDate( c.get(Calendar.YEAR),c.get(Calendar.DAY_OF_MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
		populateSetTime(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
	}
	
	public void addBunk(View view){
		Intent intent = new Intent(this, ShowSubjectInfo.class);
		intent.putExtra("subject", subId);
		dba=new DatabaseHandlerForAttendence(this);
		Bunk bunk=new Bunk();
		bunk.setId(subId);
		bunk.setDayOfMonth(day);
		bunk.setHour(hour);
		bunk.setMinute(minute);
		bunk.setMonth(month);
		bunk.setYear(year);
		dba.addBunk(bunk);
		startActivity(intent);
	}
	
	public void selectDate(View view) {
		DialogFragment newFragment = new SelectDateFragment();
		newFragment.show(getSupportFragmentManager(), "DatePicker");
	}
	
	public void selectTime(View view) {
		DialogFragment newFragment = new SelectTimeFragment();
		newFragment.show(getSupportFragmentManager(), "TimePicker");
	}
	
	public void populateSetTime(int hour, int min) {
		this.hour=hour;
		this.minute=min;
		timeText = (EditText)findViewById(R.id.timeText1);
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
	
	public void populateSetDate(int year, int month, int day) {
		dateText = (EditText)findViewById(R.id.dateText1);
		dateText.setText(day+"/"+month+"/"+year);
		this.day=day;
		this.month=month;
		this.year=year;
	}

	public class SelectDateFragment extends SherlockDialogFragment implements DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int yy = calendar.get(Calendar.YEAR);
		int mm = calendar.get(Calendar.MONTH);
		int dd = calendar.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, yy, mm, dd);
		}
		 
		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
		populateSetDate(yy, mm+1, dd);
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
	
}
