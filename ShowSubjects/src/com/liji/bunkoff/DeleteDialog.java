package com.liji.bunkoff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DeleteDialog extends SherlockDialogFragment {
	 static DeleteDialog newInstance() {
		  
		  String title = "Delete Dialog";
		  
		  		DeleteDialog f = new DeleteDialog();
		        Bundle args = new Bundle();
		        args.putString("title", title);
		        f.setArguments(args);
		        return f;
		    }

		 @Override
		 public Dialog onCreateDialog(Bundle savedInstanceState) {
		  String title = getArguments().getString("title");
		  Dialog myDialog = new AlertDialog.Builder(getActivity())
		     .setIcon(R.drawable.ic_launcher)
		     .setTitle(title)
		     .setMessage("Are you sure you want to delete the selected?")
		     .setPositiveButton("Yes", 
		       new DialogInterface.OnClickListener() {
		        
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		         ((ShowSubjectInfo)getActivity()).okClicked();
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
