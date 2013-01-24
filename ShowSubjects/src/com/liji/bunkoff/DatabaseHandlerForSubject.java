package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerForSubject extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "lectureManager";
	private static final String TABLE_SUBJECTS = "subjects";
    private static final String ID = "id";
    private static final String SUBJECT_NAME = "subject_name";
    private static final String TOTAL_CLASS = "total_class";
    private static final String MIN_ATTENDENCE = "min_attendence";
    public static String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SUBJECT_NAME + " TEXT UNIQUE NOT NULL,"
            + TOTAL_CLASS + " INTEGER NOT NULL," + MIN_ATTENDENCE + " INTEGER NOT NULL" + ")";
    private String[] allColumns = { ID,SUBJECT_NAME,TOTAL_CLASS,MIN_ATTENDENCE };
    public DatabaseHandlerForSubject(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }   

	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL(DatabaseHandlerForSubject.CREATE_SUBJECTS_TABLE);
		db.execSQL(DatabaseHandlerForLecture.CREATE_LECTURES_TABLE);
		db.execSQL(DatabaseHandlerForAttendence.CREATE_ATTENDENCE_TABLE);
		db.execSQL(DatabaseHandlerForTempSubject.CREATE_SUBJECTS_TEMP_TABLE);
		db.execSQL(DatabaseHandlerForTempLecture.CREATE_LECTURES_TEMP_TABLE);;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
		onCreate(db);
	}
	
	
	public long addSubject(Subject subject){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SUBJECT_NAME, subject.getSubjectName());
		values.put(TOTAL_CLASS, subject.getTotalClass());
		values.put(MIN_ATTENDENCE, subject.getMinAttendence());
		long i=db.insert(TABLE_SUBJECTS, null, values);
		db.close();
		return i;
	}
	public Subject getSubject(String subName){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_SUBJECTS, new String[] { ID,
		            	SUBJECT_NAME, TOTAL_CLASS, MIN_ATTENDENCE }, SUBJECT_NAME + "=?",
		            	new String[] { subName }, null, null, null, null);
		if (cursor != null){
			cursor.moveToFirst();
		}
		
		Subject subject = new Subject(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));
		return subject;
	}

	public Subject getSubject(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_SUBJECTS, allColumns, ID+" = "+String.valueOf(id), null, null, null, null);
		if (cursor != null){
			cursor.moveToFirst();
		}
		
		Subject subject = new Subject(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));
		return subject;
	}
	
	public List<Subject> getAllSubject(){
		List<Subject> subjectList =new ArrayList<Subject>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_SUBJECTS,
		        		allColumns, null, null, null, null, null);
		if (cursor != null){
			cursor.moveToFirst();
		}
		while(!cursor.isAfterLast()){
			Subject subject=new Subject();
			subject.setId(Integer.parseInt(cursor.getString(0)));
			subject.setSubjectName(cursor.getString(1));
			subject.setTotalClass(Integer.parseInt(cursor.getString(2)));
			subject.setMinAttendence(Integer.parseInt(cursor.getString(3)));
			subjectList.add(subject);
			cursor.moveToNext();
		}
		return subjectList;
	}
	
	public int getSubjectCount(){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_SUBJECTS,
		        		allColumns, null, null, null, null, null);
		int count=cursor.getCount();
		db.close();
		return count;
	}
	
	public int updatueSubject(Subject subject){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(SUBJECT_NAME, subject.getSubjectName());
	    values.put(TOTAL_CLASS,subject.getTotalClass());
	    values.put(MIN_ATTENDENCE, subject.getMinAttendence());
	    int i= db.update(TABLE_SUBJECTS, values,ID+ " = ?", new String[] { String.valueOf(subject.getId()) });
	    db.close();
	    return i;
	}
	
	public int deleteSubject(Subject subject){
		SQLiteDatabase db = this.getWritableDatabase();
	    int i=db.delete(TABLE_SUBJECTS, ID + " = ?",
	            new String[] { String.valueOf(subject.getId()) });
	    db.close();
	    return i;
	}
	
	public void deleteAllSubject(){
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
	}
	
	public void createTable(){
		SQLiteDatabase db=this.getWritableDatabase();
		onCreate(db);
	}
}
