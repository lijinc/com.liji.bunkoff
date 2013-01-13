package com.liji.bunkoff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DeleteDialog extends SherlockDialogFragment {
	 static DeleteDialog newInstance(String msg,String id) {
		  
		  String title = "Delete Dialog";
		  
		  		DeleteDialog f = new DeleteDialog();
		        Bundle args = new Bundle();
		        args.putString("title", title);
		        args.putString("msg", msg);
		        args.putString("id", id);
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
		        	if(id.equals("1000")){
				         ((ShowSubjectInfo)getActivity()).okClicked();
		        	}
		        	if(id.equals("1001")){
				         ((ShowSubjects)getActivity()).okClicked();
		        	}
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
