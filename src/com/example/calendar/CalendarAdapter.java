package com.example.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.customViews.CalendarFragment;


public class CalendarAdapter extends FragmentStatePagerAdapter{
	MyCalendar clndr = new MyCalendar();
	String month = clndr.months[0].shortName;
	int day = 1;
	
	
	public CalendarAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 365;
    }

    @Override
    public Fragment getItem(int position) {
    	setMonthDay(position + 1);
    	System.out.println("getItem pos: " + position);
        Fragment fm = CalendarFragment.newInstance(position, month, day);
        return fm;
    }
    
    private void setMonthDay(int position) {
    	int temp = position / 31;
    	System.out.println("temp is: " + temp + " pos: " + position);
    	int a1 = clndr.sumHolder[temp];
    	if(temp == 0) {
    		month = clndr.months[temp].shortName;
    		day = position;
    	}
    	else { 
    		int a2 = clndr.sumHolder[temp - 1];
    		if(position == a2) {
    			month =  clndr.months[temp - 1].shortName;
    			day = clndr.months[temp - 1].numberOfDay;
    		}
    		else if (position <= a1) {
    			month =  clndr.months[temp].shortName;
    			day = position - a2;
    		}
    		else {
    			month =  clndr.months[temp + 1].shortName;
    			day = position - a1;
    		}
    	}
    		
    }
}
