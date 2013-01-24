package com.liji.bunkoff;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerForTempSubject extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "lectureManager";
	private static final String TABLE_TEMP_SUBJECTS = "tempsubjects";
    private static final String ID = "id";
    private static final String SUBJECT_NAME = "subject_name";
    private static final String TOTAL_CLASS = "total_class";
    private static final String MIN_ATTENDENCE = "min_attendence";
    public static String CREATE_SUBJECTS_TEMP_TABLE = "CREATE TABLE " + TABLE_TEMP_SUBJECTS + "("
            + ID + " INTEGER PRIMARY KEY," + SUBJECT_NAME + " TEXT,"
            + TOTAL_CLASS + " INTEGER," + MIN_ATTENDENCE + " INTEGER" + ")";
    private String[] allColumns = { ID,SUBJECT_NAME,TOTAL_CLASS,MIN_ATTENDENCE };
    public DatabaseHandlerForTempSubject(Context context) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_SUBJECTS);
		onCreate(db);
	}
	
	
	public long addSubject(Subject subject){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ID, subject.getId());
		values.put(SUBJECT_NAME, subject.getSubjectName());
		values.put(TOTAL_CLASS, subject.getTotalClass());
		values.put(MIN_ATTENDENCE, subject.getMinAttendence());
		long i=db.insert(TABLE_TEMP_SUBJECTS, null, values);
		db.close();
		return i;
	}	
	
	public List<Subject> getAllSubject(int id){
		List<Subject> subjectList =new ArrayList<Subject>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_TEMP_SUBJECTS,
		        		allColumns, ID+" = "+String.valueOf(id), null, null, null, null);
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
	
	public void deleteTempSubject(){
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_SUBJECTS);
	}
	
	public void deleteAllSubject(){
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_SUBJECTS);
		db.execSQL(DatabaseHandlerForTempSubject.CREATE_SUBJECTS_TEMP_TABLE);
	}
}
