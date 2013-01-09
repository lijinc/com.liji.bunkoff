package com.liji.bunkoff;

import android.os.Parcel;
import android.os.Parcelable;

public class Subject implements Parcelable{
	private int id;
	private String subName;
	private int totalClass;
	private int minAttendence;
	
	public Subject(){
		
	}
	
	public Subject(String subName,int totalClass,int minAttendence){
		this.subName=subName;
		this.totalClass=totalClass;
		this.minAttendence=minAttendence;
	}
	
	public Subject(int id,String subName,int totalClass,int minAttendence){
		this.id=id;
		this.subName=subName;
		this.totalClass=totalClass;
		this.minAttendence=minAttendence;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public String getSubjectName(){
		return this.subName;
	}
	
	public void setSubjectName(String subName){
		this.subName=subName;
	}
	
	public int getTotalClass(){
		return this.totalClass;
	}
	
	public void setTotalClass(int totalClass){
		this.totalClass=totalClass;
	}
	
	public int getMinAttendence(){
		return this.minAttendence;
	}
	
	public void setMinAttendence(int minAttendence){
		this.minAttendence=minAttendence;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(subName);
		dest.writeInt(totalClass);
		dest.writeInt(minAttendence);		
	}
	
	public Subject(Parcel source){
		id=source.readInt();
		subName=source.readString();
		totalClass=source.readInt();
		minAttendence=source.readInt();
	}
	
	 public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		   
	        @Override
	        public Subject createFromParcel(Parcel source) {
	            // TODO Auto-generated method stub
	            return new Subject(source);
	        }
	 
	        @Override
	        public Subject[] newArray(int size) {
	            // TODO Auto-generated method stub
	            return new Subject[size];
	        }
	    };
}
