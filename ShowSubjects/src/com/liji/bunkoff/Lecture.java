package com.liji.bunkoff;

import android.os.Parcel;
import android.os.Parcelable;

public class Lecture implements Parcelable {
	private int uniqueid;
	private int id;
	private String day;
	private int hour;
	private int minute;
	private boolean selected;

	
	public Lecture(int id,String day,int hour,int minute){
		this.id=id;
		this.day=day;
		this.hour=hour;
		this.minute=minute;
		
	}
	
	public Lecture(){
		
	}
	
	public int getUniqueId(){
		return uniqueid;
	}
	
	public void setUniqueidId(int uniqueid){
		this.uniqueid=uniqueid;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public String getDay(){
		return day;
	}
	
	public void setDay(String day){
		this.day=day;
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
		dest.writeString(day);
		dest.writeInt(hour);
		dest.writeInt(minute);
	}
	
	public Lecture(Parcel source){
		id=source.readInt();
		day=source.readString();
		hour=source.readInt();
		minute=source.readInt();
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		   
	        @Override
	        public Lecture createFromParcel(Parcel source) {
	            // TODO Auto-generated method stub
	            return new Lecture(source);
	        }
	 
	        @Override
	        public Lecture[] newArray(int size) {
	            // TODO Auto-generated method stub
	            return new Lecture[size];
	        }
	    };

}
