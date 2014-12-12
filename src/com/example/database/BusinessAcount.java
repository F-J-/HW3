package com.example.database;

import android.content.Context;
import android.text.NoCopySpan.Concrete;



public class BusinessAcount{
	public int day;
	public String month;
	public float cost;
	public float income;
	
	
	
	
	public BusinessAcount(int day, String month, float income, float cost) {
		this.day = day;
		this.month = month;
		this.cost = cost;
		this.income = income;
	}
	
	
}
