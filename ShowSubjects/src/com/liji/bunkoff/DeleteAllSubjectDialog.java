package com.liji.bunkoff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DeleteAllSubjectDialog extends SherlockDialogFragment {
	static DeleteAllSubjectDialog newInstance(String msg,String id) {
		  
		  String title = "Delete all subjects";
		  
		  		DeleteAllSubjectDialog f = new DeleteAllSubjectDialog();
		        Bundle args = new Bundle();
		        args.putString("title", title);
		        args.putString("msg", msg);
		        f.setArguments(args);
		        return f;
	}

		 @Override
		 public Dialog onCreateDialog(Bundle savedInstanceState) {
		  String title = getArguments().getString("title");
		  String msg = getArguments().getString("msg");
		  final String id=getArguments().getString("id");
		  Dialog myDialog = new AlertDialog.Builder(getActivity())
		     .setIcon(R.drawable.ic_launcher)
		     .setTitle(title)
		     .setMessage(msg)
		     .setPositiveButton("Yes", 
		       new DialogInterface.OnClickListener() {
		        
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
			         ((DataDeleteActivity)getActivity()).okClicked();
		        }
		       })
		     .setNegativeButton("No", 
		       new DialogInterface.OnClickListener() {
		        
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        }
		       })
		     .create();

		  return myDialog;
		 }
}
