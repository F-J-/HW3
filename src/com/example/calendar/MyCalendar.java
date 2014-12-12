package com.example.calendar;




public class MyCalendar {
	public Month[] months = new Month[12];
	public int[] sumHolder = new int[12];
	
	public MyCalendar() {
		
		for(int i = 0; i < 12; i++) {
			months[i] = new Month();
		}
		
		months[0].init("Jan", 31);
		months[1].init("Feb", 28);
		months[2].init("Mar", 31);
		months[3].init("Apr", 30);
		months[4].init("May", 31);
		months[5].init("Jun", 30);
		months[6].init("Jul", 31);
		months[7].init("Aug", 31);
		months[8].init("Sep", 30);
		months[9].init("Oct", 31);
		months[10].init("Nov", 30);
		months[11].init("Dec", 31);
		
		sumHolder[0] = months[0].numberOfDay;
		for(int i = 1; i < 12; i++) {
			sumHolder[i] = sumHolder[i - 1] + months[i].numberOfDay ; 
		}
	}
	
	
}