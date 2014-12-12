package com.example.customViews;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.example.calendar.MyCalendar;
import com.example.database.BusinessAcount;
import com.example.database.DBHandler;
import com.example.hw3.MainActivity;


public class ChartHandler {

	public enum ChartState {
		day, week, month
	}

	public static String daysLabels[];
	public static String weeksLabels[];
	public static String monthsLabels[];
	public static float daysValues[][];
	public static float weeksValues[][];
	public static float monthsValues[][];
	public static ChartState chartState;

	public static void initializeLabels(String month) {
		MyCalendar cal = new MyCalendar();
		chartState = ChartState.month;
		
		monthsLabels = new String[12];
		int monthIndex = 0;
		for (int i = 0; i < monthsLabels.length; i++) {
			monthsLabels[i] = cal.months[i].shortName;
			if (month.equals(monthsLabels[i]))
				monthIndex = i;
		}
		daysLabels = new String[cal.months[monthIndex].numberOfDay];
		for (int i = 0; i < daysLabels.length; i++) {
			daysLabels[i] = Integer.toString(i + 1);
			/*if ((i + 1) == 1)
				daysLabels[i] += "st";
			else if ((i + 1) == 2)
				daysLabels[i] += "nd";
			else if ((i + 1) == 3)
				daysLabels[i] += "rd";
			else
				daysLabels[i] += "th";*/
		}
		int days = cal.months[monthIndex].numberOfDay;
		int weeks = (int) days / 7;
		if (days % 7 != 0)
			weeks++;
		weeksLabels = new String[weeks];
		for (int i = 0; i < weeksLabels.length; i++) {
			weeksLabels[i] = Integer.toString(i + 1);
			if ((i + 1) == 1)
				weeksLabels[i] += "st";
			else if ((i + 1) == 2)
				weeksLabels[i] += "nd";
			else if ((i + 1) == 3)
				weeksLabels[i] += "rd";
			else
				weeksLabels[i] += "th";
		}
	}
	
	public static void initializeValues(String month) {
		DBHandler db = MainActivity.db;
		
		daysValues = new float[daysLabels.length][2];
		for (int i = 0; i < daysValues.length; i++) {
			BusinessAcount dayResult = db.findDailyInfo(i + 1, month);
			if (dayResult == null) {
				daysValues[i][0] = 0;
				daysValues[i][1] = 0;
			} else {
				daysValues[i][0] = dayResult.cost;
				daysValues[i][1] = dayResult.income;
			}
		}
		weeksValues = new float[weeksLabels.length][2];
		for (int i = 0; i < weeksValues.length; i++) {
			float cost = 0;
			float income = 0;
			for (int j = 0; j < 7; j++) {
				BusinessAcount dayResult = db.findDailyInfo(j + i * 7 + 1, month);
				if (dayResult != null) {
					cost += dayResult.cost;
					income += dayResult.income;
				}
			}
			weeksValues[i][0] = cost;
			weeksValues[i][1] = income;
		}
		monthsValues = new float[monthsLabels.length][2];
		for (int i = 0; i < monthsValues.length; i++) {
			//List<BusinessAcount> monthResult = db.findMonthlyInfo(monthsLabels[i]);
			List<BusinessAcount> monthResult = new ArrayList<BusinessAcount>();
			for (int j = 0; j < monthsLabels.length; j++) {
				BusinessAcount dayResult = db.findDailyInfo(j + 1, monthsLabels[i]);
				if (dayResult != null)
					monthResult.add(dayResult);
			}
			float cost = 0;
			float income = 0;
			if (monthResult != null && monthResult.size() != 0) {
				for (int j = 0; j < monthResult.size(); j++)
					if (monthResult.get(j) != null) {
						cost += monthResult.get(j).cost;
						income += monthResult.get(j).income;
					}
			}
			monthsValues[i][0] = cost;
			monthsValues[i][1] = income;
		}
	}

	public static void initializeChart(ChartView chart, ChartState state) {
		chartState = state;
		chart.setCategoriesNumber(2);
		int categoriesColors[] = { Color.RED, Color.BLUE };
		chart.setCategoriesColors(categoriesColors);
		chart.setLabelsColor(Color.WHITE);
		if (chartState == ChartState.day) {
			chart.setColumnsNumber(daysLabels.length);
			chart.setLabels(daysLabels);
			chart.setValues(daysValues);
		} else if (chartState == ChartState.week) {
			chart.setColumnsNumber(weeksLabels.length);
			chart.setLabels(weeksLabels);
			chart.setValues(weeksValues);
		} else {
			chart.setColumnsNumber(monthsLabels.length);
			chart.setLabels(monthsLabels);
			chart.setValues(monthsValues);
		}
	}

	public static void zoomInChart(ChartView chart) {
		if (chartState == ChartState.day)
			return;
		if (chartState == ChartState.week)
			initializeChart(chart, ChartState.day);
		else
			initializeChart(chart, ChartState.week);
	}

	public static void zoomOutChart(ChartView chart) {
		if (chartState == ChartState.month)
			return;
		if (chartState == ChartState.week)
			initializeChart(chart, ChartState.month);
		else
			initializeChart(chart, ChartState.week);
	}
	
	public static void zoomChart(ChartView chart, boolean zoomInState) {
		if (zoomInState)
			zoomInChart(chart);
		else
			zoomOutChart(chart);
	}
}
