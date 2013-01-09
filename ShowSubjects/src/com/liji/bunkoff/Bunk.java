package com.liji.bunkoff;

import android.os.Parcel;
import android.os.Parcelable;

public class Bunk implements Parcelable {
	private int uniqueid;
	private int id;
	private int hour;
	private int minute;
	private int dayofmonth;
	private int month;
	private int year;
	private boolean selected;
	
	public Bunk(int id,int hour,int minute,int dayofmonth,int month,int year){
		this.id=id;
		this.hour=hour;
		this.minute=minute;
		this.dayofmonth=dayofmonth;
		this.month=month;
		this.year=year;
	}
	
	public Bunk(Lecture lecture,int dayofmonth,int month,int year){
		this.id=lecture.getId();
		this.hour=lecture.getHour();
		this.minute=lecture.getMinute();
		this.dayofmonth=dayofmonth;
		this.month=month;
		this.year=year;
	}
	
	public int getUniqueId(){
		return uniqueid;
	}
	
	public void setUniqueidId(int uniqueid){
		this.uniqueid=uniqueid;
	}
	
	public Bunk(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
		
	public int getHour(){
		return hour;
	}
	
	public void setHour(int hour){
		this.hour=hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public void setMinute(int minute){
		this.minute=minute;
	}
	
	public int getDayOfMonth(){
		return dayofmonth;
	}
	
	public void setDayOfMonth(int dayofmonth){
		this.dayofmonth=dayofmonth;
	}
	
	public int getMonth(){
		return month;
	}
	
	public void setMonth(int month){
		this.month=month;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setYear(int year){
		this.year=year;
	}
	
	public boolean isSelected() {
	    return selected;
	}
    public void setSelected(boolean selected){
	    this.selected = selected;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(hour);
		dest.writeInt(minute);
		dest.writeInt(dayofmonth);
		dest.writeInt(month);
		dest.writeInt(year);
	}
	
	public Bunk(Parcel source){
		id=source.readInt();
		hour=source.readInt();
		minute=source.readInt();
		dayofmonth=source.readInt();
		month=source.readInt();
		year=source.readInt();
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		   
        @Override
        public Bunk createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Bunk(source);
        }
 
        @Override
        public Bunk[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Bunk[size];
        }
    };

}
