package com.liji.bunkoff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class CountDialog extends SherlockDialogFragment {
	 static CountDialog newInstance() {
		  
		  String title = "Error!!";
		  
		  		CountDialog f = new CountDialog();
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
		     .setMessage("Select atleast one item.!!")
		     .setCancelable(false)
		     .setPositiveButton("Ok", 
		       new DialogInterface.OnClickListener() {
		        
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        }
		       })
		     
		     .create();

		  return myDialog;
		 }
}
