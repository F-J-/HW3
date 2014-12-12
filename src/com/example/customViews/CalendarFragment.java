package com.example.customViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calendar.CostIncomeEditDialog;
import com.example.database.BusinessAcount;
import com.example.hw3.MainActivity;
import com.example.hw3.R;

public class CalendarFragment extends Fragment {
	private final static String MONTH = "month";
	private final static String DAY = "day";
	private String month;
	private int day;
	private int position;

	public static CalendarFragment newInstance(int i, String month, int day) {
		CalendarFragment cf = new CalendarFragment();
		Bundle args = new Bundle();
		args.putInt("position", i);
		args.putString(MONTH, month);
		args.putInt(DAY, day);
		cf.setArguments(args);
		return cf;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			this.position = getArguments().getInt("position");
			this.month = getArguments().getString(MONTH);
			this.day = getArguments().getInt(DAY);
		} else
			this.position = 0;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.calendar_fargment, container, false);
		setHandler(v);

		View tv = v.findViewById(R.id.text);
		if (day == 1)
        	((TextView)tv).setText(month + " " + day + "st");
        else if (day == 2)
        	((TextView)tv).setText(month + " " + day + "nd");
        else if (day == 3)
        	((TextView)tv).setText(month + " " + day + "rd");
        else
        	((TextView)tv).setText(month + " " + day + "th");

		ButtonLoopView blv = (ButtonLoopView) v.findViewById(R.id.myButton);
		BusinessAcount ba = MainActivity.db.findDailyInfo(day, month);
		if(ba != null && ba.cost != 0 && ba.income != 0)
			blv.setValue(ba.cost*100 / ba.income);
		else
			blv.setValue(0);
		
		final ChartView chart = (ChartView) v.findViewById(R.id.drawer_content);
		ChartHandler.initializeLabels(month);
		ChartHandler.initializeValues(month);
		ChartHandler.initializeChart(chart, ChartHandler.ChartState.week);
		Button zoomInButton = (Button) v.findViewById(R.id.zoomInButton);
		zoomInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ChartHandler.zoomInChart(chart);
				chart.invalidate();
				chart.requestLayout();
			}
		});
		Button zoomOutButton = (Button) v.findViewById(R.id.zoomOutButton);
		zoomOutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ChartHandler.zoomOutChart(chart);
				chart.invalidate();
				chart.requestLayout();
			}
		});

		return v;
	}

	private void showEditDialog(String title, String month, int day) {
		FragmentManager fm = getChildFragmentManager();
		CostIncomeEditDialog costIncomeEditDialog = new CostIncomeEditDialog(
				title, month, day);
		costIncomeEditDialog.setTargetFragment(this, 0);
		costIncomeEditDialog.show(fm, title);
	}

	private void setHandler(View view) {
		System.err.println("in seteventHandler");
		final ButtonLoopView blv = (ButtonLoopView) view
				.findViewById(R.id.myButton);
		MyEventHandler[] handler = new MyEventHandler[2];
		handler[0] = new MyEventHandler() {
			@Override
			public void handleEvent() {
				showEditDialog("Cost", month, day);
				BusinessAcount b = MainActivity.db.findDailyInfo(day, month);
				//System.err.println("testing SQL" + b.cost + b.income);
				float cost = 0;
				float income = 0;

				if (b != null) {
					cost = b.cost;
					income = b.income;
				}
				if (income != 0)
					blv.setValue(cost * 100 / income);
				else
					blv.setValue(100);
			}
		};
		handler[1] = new MyEventHandler() {
			@Override
			public void handleEvent() {
				showEditDialog("Income", month, day);
				BusinessAcount b = MainActivity.db.findDailyInfo(day, month);
				float cost = 0;
				float income = 0;

				if (b != null) {
					cost = b.cost;
					income = b.income;
				}
				if (income != 0)
					blv.setValue(cost * 100 / income);
				else
					blv.setValue(100);
			}
		};
		blv.setHandler(handler);
	}
	
	
	public void refreshFragment() {
	    ButtonLoopView blv = (ButtonLoopView) this.getView().findViewById(R.id.myButton);
	    BusinessAcount ba = MainActivity.db.findDailyInfo(day, month);
	    if(ba != null && ba.cost != 0 && ba.income != 0) {
	    	blv.setValue(ba.cost*100 / ba.income);
	    	
	    }
		else
			blv.setValue(0);
	    
	}
	
	
}
