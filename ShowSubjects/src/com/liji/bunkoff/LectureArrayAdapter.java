package com.liji.bunkoff;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LectureArrayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Lecture> list;
    private Context context;
    public LectureArrayAdapter(Context context, List<Lecture> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context=context;
    }
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lecture_row_view, parent, false);
        }
        TextView day = (TextView) convertView.findViewById(R.id.textDay);
        TextView time = (TextView) convertView.findViewById(R.id.textTime); 
        Lecture lec = list.get(position);
        day.setText(" "+lec.getDay());
        if(lec.getHour()<10){
            time.setText(" 0"+lec.getHour()+":"+lec.getMinute()+" AM");
        }
        else if(lec.getHour()>=10&&lec.getHour()<12){
        	time.setText(" "+lec.getHour()+":"+lec.getMinute()+" AM");
        }
        else{
        	time.setText(" "+lec.getHour()+":"+lec.getMinute()+" PM");
        }
        
        return convertView;
	}
}
