package com.liji.bunkoff;
//in reboot,everyday 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


public class InitializeAlarmService extends Service {
	private String TAG = "intializealarmservice";
	private NotificationManager manager;
	private DatabaseHandlerForLecture dbl;
	private DatabaseHandlerForSubject dbs;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override	
	public void onDestroy() {	
		Log.d(TAG, "Distroyed");
		super.onDestroy();
	}	

	@Override
	public int onStartCommand (Intent intent, int flags, int startId){
		Calendar realTime = Calendar.getInstance();
		Calendar alarmTime = Calendar.getInstance();
		SharedPreferences setPreference=PreferenceManager.getDefaultSharedPreferences(this);
		if(setPreference.getBoolean("checkBoxEnablePref", true)==false){
			Log.d("stop", " sr");
			stopSelf();
		}
		else{
			int realDay = realTime.get(Calendar.DAY_OF_WEEK)-1;
			int realHour = realTime.get(Calendar.HOUR_OF_DAY);
			int realMinute = realTime.get(Calendar.MINUTE);
			int totalMinutes = realHour*60 + realMinute;
			String[] daysOfWeek = getResources().getStringArray(R.array.daysOfWeek);
			List<Lecture> lectureList = new ArrayList<Lecture>();
			dbl = new DatabaseHandlerForLecture(this);
			if(realDay==0){
				lectureList = dbl.getLectureTimesForDay(daysOfWeek[6]);
			}
			else{
				lectureList = dbl.getLectureTimesForDay(daysOfWeek[(realDay-1)]);
			}
			Log.d("hii",daysOfWeek[(realDay)%6]);
			Collections.sort(lectureList, new ComparebleLecture());
			List<Lecture> temp=filterAlarms(lectureList, totalMinutes);
			if(lectureList.isEmpty()){
				alarmTime.set(Calendar.HOUR_OF_DAY, 0);
				alarmTime.set(Calendar.MINUTE, 0);
				alarmTime.add(Calendar.DAY_OF_WEEK, 1);
				if(setPreference.getBoolean("checkBoxMemPref", false)){
					NotificationCompat.Builder builder =  
				            new NotificationCompat.Builder(this)  
				            .setSmallIcon(R.drawable.ic_launcher)  
				            .setContentTitle("Bunk-Off")  
				            .setContentText("No Lectures for today.!!");
				    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
				    manager.notify(10013, builder.build());  
				}
				Log.d("Daaay", String.valueOf(alarmTime.getTime()));
				Intent i = new Intent(this, NextDayInitializeReceiver.class);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, i, PendingIntent.FLAG_CANCEL_CURRENT);
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(), pendingIntent);
				Log.d(null, "alarmsetaaa");
				}
			else{
				dbs = new DatabaseHandlerForSubject(this);
				Subject sub = dbs.getSubject(lectureList.get(0).getId());
				Log.d("sup", sub.getSubjectName());
				String time;
		        if(lectureList.get(0).getHour()<10){
		            time=" 0"+lectureList.get(0).getHour()+":"+lectureList.get(0).getMinute()+" AM";
		        }
		        else if(lectureList.get(0).getHour()>=10&&lectureList.get(0).getHour()<12){
		        	time=" "+lectureList.get(0).getHour()+":"+lectureList.get(0).getMinute()+" AM";
		        }
		        else{
		        	time=" "+(lectureList.get(0).getHour()-12)+":"+lectureList.get(0).getMinute()+" PM";
		        }
				if(setPreference.getBoolean("checkBoxMemPref", false)){
					NotificationCompat.Builder builder =  
				            new NotificationCompat.Builder(this)  
				            .setSmallIcon(R.drawable.ic_launcher)  
				            .setContentTitle("Bunk-Off")  
				            .setContentText("Next class on "+sub.getSubjectName()+"at"+time);
				    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
				    manager.notify(10013, builder.build()); 
				}
				  
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
			}	
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
