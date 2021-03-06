package com.liji.bunkoff;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class CheckBoxBunkArrayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    public static List<Bunk> BUNKLIST;
    public CheckBoxBunkArrayAdapter(Context context, List<Bunk> blist) {
    	BUNKLIST=blist;
    	mInflater = LayoutInflater.from(context);
    	BUNKLIST = blist;
        this.context=context;
    }
	@Override
	public int getCount() {
		return BUNKLIST.size();
	}
	
	@Override
	public Object getItem(int position) {
		return BUNKLIST.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.two_line_list_item, parent, false);
        }
        TextView day = (TextView) convertView.findViewById(android.R.id.text1);
        TextView timelbl = (TextView) convertView.findViewById(android.R.id.text2);
        Bunk bunk = BUNKLIST.get(position);
        day.setText(" "+bunk.getDayOfMonth()+"/"+bunk.getMonth()+"/"+bunk.getYear());
        String time;
        if(bunk.getHour()<10){
            time=" 0"+bunk.getHour()+":"+bunk.getMinute()+" AM";
        }
        else if(bunk.getHour()>=10&&bunk.getHour()<12){
        	time=" "+bunk.getHour()+":"+bunk.getMinute()+" AM";
        }
        else{
        	time=" "+(bunk.getHour()-12)+":"+bunk.getMinute()+" PM";
        }
        timelbl.setText(time);

        return convertView;
	}
}
