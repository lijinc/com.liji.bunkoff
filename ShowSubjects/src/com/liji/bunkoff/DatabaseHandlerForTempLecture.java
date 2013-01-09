package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerForTempLecture extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "lectureManager";
	private static final String TABLE_TEMP_LECTURES = "templectures";
	private static final String UNIQUEID = "uniqueid";
	private static final String ID = "id";
	private static final String DAY = "day";
	private static final String HOUR ="hour";
	private static final String MINUTE="minute";
	public static String CREATE_LECTURES_TEMP_TABLE = "CREATE TABLE " + TABLE_TEMP_LECTURES + "("
			+ UNIQUEID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ID + " INTEGER NOT NULL," + DAY + " TEXT NOT NULL,"
            + HOUR + " INTEGER NOT NULL," + MINUTE + " INTEGER NOT NULL" + ")";
    private String[] allColumns = {ID,DAY,HOUR,MINUTE };
	public DatabaseHandlerForTempLecture(Context context) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_LECTURES);
		onCreate(db);
	}
	
	public long addLecture(Lecture lecture){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ID,lecture.getId());
		values.put(DAY, lecture.getDay());
		values.put(HOUR, lecture.getHour());
		values.put(MINUTE, lecture.getMinute());
		long i=db.insert(TABLE_TEMP_LECTURES, null, values);
		db.close();
		return i;
	}	
	
	public List<Lecture> getAllLecures(int id){
		List<Lecture> lectureList =new ArrayList<Lecture>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_TEMP_LECTURES,
		        		allColumns, ID+" = "+String.valueOf(id), null, null, null, null);
		if (cursor != null){
			cursor.moveToFirst();
		}
		while(!cursor.isAfterLast()){
			Lecture lecture=new Lecture();
			lecture.setId(Integer.parseInt(cursor.getString(0)));
			lecture.setDay(cursor.getString(1));
			lecture.setHour(Integer.parseInt(cursor.getString(2)));
			lecture.setMinute(Integer.parseInt(cursor.getString(3)));
			lectureList.add(lecture);
			cursor.moveToNext();
		}
		return lectureList;
	}
	

	
	public void deleteAllLecture(){
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_LECTURES);
		db.execSQL(DatabaseHandlerForTempLecture.CREATE_LECTURES_TEMP_TABLE);
	}
	
}
