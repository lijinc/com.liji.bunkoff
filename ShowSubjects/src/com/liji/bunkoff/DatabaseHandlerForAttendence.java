package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerForAttendence extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "lectureManager";
	private static final String TABLE_ATTENDENCE = "attendence";
	private static final String UNIQUEID = "uniqueid";
	private static final String ID = "id";
	private static final String HOUR ="hour";
	private static final String MINUTE="minute";
	private static final String DAYOFMONTH = "dayofmonth";
	private static final String MONTH ="month";
	private static final String YEAR="year";
	public static String CREATE_ATTENDENCE_TABLE = "CREATE TABLE " + TABLE_ATTENDENCE + "("
			+ UNIQUEID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ID + " INTEGER NOT NULL," 
            + HOUR + " INTEGER NOT NULL," + MINUTE + " INTEGER NOT NULL,"
            + DAYOFMONTH +" INTEGER NOT NULL,"+ MONTH +" INTEGER NOT NULL,"
            + YEAR +" INTEGER NOT NULL"+")";
    private String[] allColumns = {UNIQUEID,ID,HOUR,MINUTE,DAYOFMONTH,MONTH,YEAR };
	public DatabaseHandlerForAttendence(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseHandlerForSubject.CREATE_SUBJECTS_TABLE);
		db.execSQL(DatabaseHandlerForLecture.CREATE_LECTURES_TABLE);
		db.execSQL(DatabaseHandlerForAttendence.CREATE_ATTENDENCE_TABLE);
		db.execSQL(DatabaseHandlerForTempSubject.CREATE_SUBJECTS_TEMP_TABLE);
		db.execSQL(DatabaseHandlerForTempLecture.CREATE_LECTURES_TEMP_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDENCE);
		onCreate(db);
	}
	public long addBunk(Bunk bunk){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ID,bunk.getId());
		values.put(HOUR, bunk.getHour());
		values.put(MINUTE, bunk.getMinute());
		values.put(DAYOFMONTH, bunk.getDayOfMonth());
		values.put(MONTH, bunk.getMonth());
		values.put(YEAR, bunk.getYear());
		long i=db.insert(TABLE_ATTENDENCE, null, values);
		db.close();
		return i;
	}	
	
	public List<Bunk> getAllBunks(int id){
		List<Bunk> bunkList =new ArrayList<Bunk>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_ATTENDENCE,
		        		allColumns, ID+" = "+String.valueOf(id), null, null, null, null);
		if (cursor != null){
			cursor.moveToFirst();
		}
		while(!cursor.isAfterLast()){
			Bunk bunk=new Bunk();
			bunk.setUniqueidId(Integer.parseInt(cursor.getString(0)));
			bunk.setId(Integer.parseInt(cursor.getString(1)));
			bunk.setHour(Integer.parseInt(cursor.getString(2)));
			bunk.setMinute(Integer.parseInt(cursor.getString(3)));
			bunk.setDayOfMonth(Integer.parseInt(cursor.getString(4)));
			bunk.setMonth(Integer.parseInt(cursor.getString(5)));
			bunk.setYear(Integer.parseInt(cursor.getString(6)));
			bunkList.add(bunk);
			cursor.moveToNext();
		}
		return bunkList;
	}

	public int getBunkCount(int id){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_ATTENDENCE,
		        		allColumns, ID+" = "+String.valueOf(id), null, null, null, null);
		return cursor.getCount();
	}

	
	
	public int deleteBunk(Bunk bunk){
		SQLiteDatabase db = this.getWritableDatabase();
	    int i=db.delete(TABLE_ATTENDENCE, UNIQUEID + " = ?",
	          new String[] { String.valueOf(bunk.getUniqueId()) });
	    db.close();
	    return i;
	}
	
	public int deleteBunkGivenId(int id){
		SQLiteDatabase db = this.getWritableDatabase();
	    int i=db.delete(TABLE_ATTENDENCE, ID + " = ?",
	          new String[] { String.valueOf(id) });
	    db.close();
	    return i;
	}
	
	public void deleteAllBunks(){
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDENCE);
	}
}
