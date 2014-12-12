package com.example.hw3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


//import com.viewpagerindicator.CirclePageIndicator;

public class SplashActivity extends ActionBarActivity {
	boolean opened = false;
	ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		ViewPager pager = (ViewPager)findViewById(R.id.splash_pager);
		pager.setAdapter(new SplashAdapter(getSupportFragmentManager()));
		
//		CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.splash_indicators);
//		titleIndicator.setViewPager(pager);
		
		automaticStartNextActivity();
	}
	
	public void startButtonOnClick(View view) {
		startNextActivity();
		opened = true;
	}
	public void startNextActivity() {
		Intent splashMainIntent = new Intent(this, MainActivity.class);
		startActivity(splashMainIntent);
	}
	public void automaticStartNextActivity() {
		Runnable task = new Runnable() {
		    public void run() {
		    	if(!opened)
		    		startNextActivity();
		    }
		  };
		  worker.schedule(task, 3, TimeUnit.SECONDS);
	}
}
