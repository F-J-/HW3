package com.example.hw3;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.calendar.CalendarAdapter;
import com.example.database.DBHandler;

public class MainActivity extends ActionBarActivity {
	public static DBHandler db;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db = new DBHandler(getBaseContext());
		
		ViewPager pager = (ViewPager)findViewById(R.id.pager);
		pager.setAdapter(new CalendarAdapter(getSupportFragmentManager()));
		
		
		//Bind the title indicator to the adapter
//		TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
//		titleIndicator.setViewPager(pager);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}