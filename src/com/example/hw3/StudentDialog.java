package com.example.hw3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class StudentDialog extends DialogFragment{
	Context mContext;
	String name;
	String sid;
	int num;
	
    public StudentDialog(String name, String sid, int num) {
        
        this.name = name;
        this.sid = sid;
        this.num = num;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Member Group " + num);
        alertDialogBuilder.setMessage(name + ": " + sid);
        //null should be your on click listener
        
        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }
}
