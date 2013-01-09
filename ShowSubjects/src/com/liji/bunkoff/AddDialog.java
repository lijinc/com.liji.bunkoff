package com.liji.bunkoff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class AddDialog extends SherlockDialogFragment {
	 static AddDialog newInstance(String msg) {
		  
		  String title = "Error!!";
		  
		  		AddDialog f = new AddDialog();
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
		  Dialog myDialog = new AlertDialog.Builder(getActivity())
		     .setIcon(R.drawable.ic_launcher)
		     .setTitle(title)
		     .setMessage(msg)
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
