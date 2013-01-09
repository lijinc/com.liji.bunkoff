package com.liji.bunkoff;
//in reboot,everyday 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;


public class InitializeAlarmService extends Service {
	private String TAG = "intializealarmservice";
	private DatabaseHandlerForLecture dbl;
	private DatabaseHandlerForSubject dbs;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override	
	public void onDestroy() {	
		Log.d(TAG, "Distroyed");
	}	

	@Override
	public int onStartCommand (Intent intent, int flags, int startId){
		Calendar realTime = Calendar.getInstance();
		Calendar alarmTime = Calendar.getInstance();
		int realDay = realTime.get(Calendar.DAY_OF_WEEK)-1;
		int realHour = realTime.get(Calendar.HOUR_OF_DAY);
		int realMinute = realTime.get(Calendar.MINUTE);
		int totalMinutes = realHour*60 + realMinute;
		String[] daysOfWeek = getResources().getStringArray(R.array.daysOfWeek);
		List<Lecture> lectureList = new ArrayList<Lecture>();
		dbl = new DatabaseHandlerForLecture(this);
		lectureList = dbl.getLectureTimesForDay(daysOfWeek[realDay-1]);
		Log.d("hii", "get here");
		Collections.sort(lectureList, new ComparebleLecture());
		Log.d("hii", "sorted");
		List<Lecture> temp=filterAlarms(lectureList, totalMinutes);
		if(lectureList.isEmpty()){
			Log.d("hii", "im empty");
			alarmTime.set(Calendar.HOUR_OF_DAY, 0);
			alarmTime.set(Calendar.MINUTE, 1);
			alarmTime.set(Calendar.DAY_OF_WEEK,realDay+2 );
			Intent i = new Intent(this, NextDayInitializeReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, i, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(), pendingIntent);
			Log.d(null, "alarmset");
			Toast.makeText(this, "Alarm set in " +String.valueOf(alarmTime.getTime())+ " seconds",
			     Toast.LENGTH_LONG).show();		}
		else{
			dbs = new DatabaseHandlerForSubject(this);
			Subject sub = dbs.getSubject(lectureList.get(0).getId());
			Log.d("sup", sub.getSubjectName());
			alarmTime.set(Calendar.HOUR_OF_DAY, lectureList.get(0).getHour());
			alarmTime.set(Calendar.MINUTE, lectureList.get(0).getMinute());
			Intent i = new Intent(this, StartAlarmReceiver.class);
			Bundle b = new Bundle();
			b.putParcelable("bunk", new Bunk(lectureList.get(0),realTime.get(Calendar.DAY_OF_MONTH),realTime.get(Calendar.MONTH),realTime.get(Calendar.YEAR)));
			b.putParcelable("subject", sub);
			i.putExtras(b);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, i, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(), pendingIntent);
			Log.d(null, "alarmset "+String.valueOf(alarmTime.getTime()));
			Toast.makeText(this, "Alarm set in " +String.valueOf(alarmTime.getTime())+ " seconds",
			     Toast.LENGTH_LONG).show();
		}		
		return Service.START_NOT_STICKY;	
	}
	
	public List<Lecture> filterAlarms(List<Lecture> lList,int realMin){
		if(lList.isEmpty()){
			Log.d("hii", "nully");
			return lList;
		}
		else{
			Lecture l=lList.get(0);
			if((l.getHour()*60+l.getMinute())>realMin){
			return lList;
			}
			else{
				lList.remove(0);
				filterAlarms(lList,realMin);
			}
		}
		return lList;
	}
}
