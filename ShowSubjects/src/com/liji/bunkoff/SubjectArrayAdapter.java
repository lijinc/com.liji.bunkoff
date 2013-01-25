package com.liji.bunkoff;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubjectArrayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Subject> list;
    private DatabaseHandlerForAttendence dba;
    private Context context;
    public SubjectArrayAdapter(Context context, List<Subject> list) {
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
            convertView = mInflater.inflate(android.R.layout.two_line_list_item, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        TextView subTitle = (TextView) convertView.findViewById(android.R.id.text2); 
        Subject sub = list.get(position);
        int noBunk=sub.getTotalClass()*(100-sub.getMinAttendence())/100;
        title.setText(" "+sub.getSubjectName());
        dba=new DatabaseHandlerForAttendence(context);
        int bunks=dba.getBunkCount(sub.getId());
        subTitle.setText(" Bunks : "+String.valueOf(bunks)+"("+noBunk+")");
        dba.close();
        return convertView;
	}

}
