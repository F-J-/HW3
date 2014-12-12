package com.example.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.customViews.ButtonLoopView;
import com.example.customViews.CalendarFragment;
import com.example.database.BusinessAcount;
import com.example.hw3.MainActivity;
import com.example.hw3.R;

public class CostIncomeEditDialog extends DialogFragment{
	public final static String COST = "cost";
	public final static String INCOME = "income";
	public final static String MY_PREFRENCES = "prefrences";
	
	private final static String TITLE = "title";
	private String title;
	private String month;
	private int day;
	private EditText mEditText;

    public CostIncomeEditDialog(String title, String month, int day) {
    	super();
    	this.title = title;
    	this.month = month;
    	this.day = day;
	}
    
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	

        AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity());
        LayoutInflater i = getActivity().getLayoutInflater();
        final View v = i.inflate(R.layout.cost_income_dialog_fragment,null);
        b.setView(v)
        .setTitle(title)
        .setPositiveButton("Save",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	EditText edt = (EditText) v.findViewById(R.id.cost_income);
                	
                	
                	float a = Float.parseFloat(edt.getText().toString());
                	if(title == "Cost")
                		MainActivity.db.editEntity1(a, month, day);
                	else 
                		MainActivity.db.editEntity2(a, month, day);
                	
                	((CalendarFragment)getTargetFragment()).refreshFragment();
                }
            }
        )
        .setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    
                }
            }
        );

        return b.create();
    }
    
    
    public String getCostOrIncome() {
    	return mEditText.toString();
    }
    
    
}
