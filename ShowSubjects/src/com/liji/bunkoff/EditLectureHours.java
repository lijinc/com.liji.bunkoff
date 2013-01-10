package com.liji.bunkoff;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class EditLectureHours extends SherlockActivity {
	public DatabaseHandlerForLecture dbl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lecture_hours);
	}

}


/*case R.id.deleteLecMenuItem:
	
	break;*/