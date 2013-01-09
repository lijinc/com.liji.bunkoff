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

public class CheckBoxLectureArrayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public static List<Lecture> LECTLIST;
    private Context context;
    public CheckBoxLectureArrayAdapter(Context context, List<Lecture> list) {
    	LECTLIST=list;
    	mInflater = LayoutInflater.from(context);
        this.context=context;
    }
	@Override
	public int getCount() {
		return LECTLIST.size();
	}

	@Override
	public Object getItem(int position) {
		return LECTLIST.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.textcheckbox, parent, false);
        }
        TextView day = (TextView) convertView.findViewById(R.id.datelabel);
        TextView timelbl = (TextView) convertView.findViewById(R.id.timelabel);
        final CheckBox chkLec = (CheckBox) convertView.findViewById(R.id.check);
        Lecture lec = LECTLIST.get(position);
        day.setText(" "+lec.getDay());
        String time;
        if(lec.getHour()<10){
            time=" 0"+lec.getHour()+":"+lec.getMinute()+" AM";
        }
        else if(lec.getHour()>=10&&lec.getHour()<12){
        	time=" "+lec.getHour()+":"+lec.getMinute()+" AM";
        }
        else{
        	time=" "+(lec.getHour()-12)+":"+lec.getMinute()+" PM";
        }
        timelbl.setText(time);
        chkLec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
        		Lecture l=LECTLIST.get(position);
            	l.setSelected(isChecked);
            	LECTLIST.set(position, l);
            }
        });

        return convertView;
	}
}
