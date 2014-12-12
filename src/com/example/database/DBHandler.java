package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.database.FeedReaderContract.FeedEntry;

public class DBHandler {
	FeedReaderDbHelper dbHandler;
	SQLiteDatabase db;

	
	public DBHandler(Context context) {
		dbHandler = new FeedReaderDbHelper(context);
		db = dbHandler.getWritableDatabase();;
	}
	
	public void addEntity(float cost, float income, String month, int day) {
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COLUMN_NAME_MONTH, month);
		values.put(FeedEntry.COLUMN_NAME_DAY, day);
		values.put(FeedEntry.COLUMN_NAME_COST, cost);
		values.put(FeedEntry.COLUMN_NAME_INCOME, income);
		
		long newRowId;
		newRowId = db.insert(
		         FeedEntry.TABLE_NAME,
		         "null",
		         values);
		System.err.println("newRowId" + newRowId);
		BusinessAcount b = findDailyInfo(day, month);
		System.out.println("######" + b.cost);
		//getList();
	}
	
	
//	public List<BusinessAcount> getList() {
//		String[] a = {FeedEntry.COLUMN_NAME_COST, FeedEntry.COLUMN_NAME_DAY, FeedEntry.COLUMN_NAME_INCOME, FeedEntry.COLUMN_NAME_MONTH};
//		List<BusinessAcount> businessList = new ArrayList();
//		
//		        Cursor cursor = db.query(FeedEntry.TABLE_NAME, a, null, null, null,null, null);
//		        	
//			        cursor.moveToFirst();
//			        while (!cursor.isAfterLast()) {
//			        	BusinessAcount ba = new BusinessAcount(cursor.getInt(1), cursor.getString(3), cursor.getFloat(2), cursor.getFloat(0));
//			        	businessList.add(ba);
//			            System.out.println("%%%%%%" + cursor.getString(0) + " " + cursor.getInt(1));
//			            
//			            cursor.moveToNext();
//			        }
//			 
//			        cursor.close();
//		return businessList;
//	}

	
	public void editEntity1(float cost, String month, int day) {
		BusinessAcount ba = findDailyInfo(day, month);
		if(ba != null) {
			ContentValues values = new ContentValues();
			values.put(FeedEntry.COLUMN_NAME_COST, cost);
			String selection = FeedEntry.COLUMN_NAME_DAY + "=?" + " AND " + FeedEntry.COLUMN_NAME_MONTH + "=?";
			String[] selectionArgs = {day+"", month};
			int count = db.update(
				    FeedEntry.TABLE_NAME,
				    values,
				    selection,
				    selectionArgs);
			//getList();
		}
		else {
			System.err.println("here");
			addEntity(cost, 0, month, day);
		}
	}
	
	public void editEntity2(float income, String month, int day) {
		BusinessAcount ba = findDailyInfo(day, month);
		if(ba != null) {
			ContentValues values = new ContentValues();
			values.put(FeedEntry.COLUMN_NAME_INCOME, income);
			String selection = FeedEntry.COLUMN_NAME_DAY + "=?" + " AND " + FeedEntry.COLUMN_NAME_MONTH + "=?";
			String[] selectionArgs = {day+"", month};
			int count = db.update(
				    FeedEntry.TABLE_NAME,
				    values,
				    selection,
				    selectionArgs);
			//getList();
		}
		else {
			System.err.println("here");
			addEntity(0, income, month, day);
		}
		
	}
	
	public BusinessAcount findDailyInfo(int day, String month) {
		String[] projection = {
			    FeedEntry.COLUMN_NAME_COST,
			    FeedEntry.COLUMN_NAME_INCOME,
			    };
		
		String selection = FeedEntry.COLUMN_NAME_DAY + "=?" + " AND " + FeedEntry.COLUMN_NAME_MONTH + "=?";
		String[] selectionArgs = {day+"", month};
		
		Cursor c = db.query(
			    FeedEntry.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    null                                // The sort order
			    );
				
		c.moveToFirst();
		float cost = 0;
		float income = 0;
		BusinessAcount ba = null;
		if(c.getCount() > 0 ) {
			cost = c.getFloat(0);
			income = c.getFloat(1);
			ba = new BusinessAcount(day, month, income, cost);
		}
		c.close();
		
		return ba;		
	}
	
	
	public List<BusinessAcount> findMonthlyInfo(String month) {
		List<BusinessAcount> businessList = new ArrayList<BusinessAcount>();
		
		String[] projection = {
			    FeedEntry.COLUMN_NAME_COST,
			    FeedEntry.COLUMN_NAME_INCOME,
			    FeedEntry.COLUMN_NAME_DAY
			    };
		
		String selection = FeedEntry.COLUMN_NAME_MONTH + "=?";
		String[] selectionArgs = {month};
		
		Cursor cursor = db.query(
			    FeedEntry.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    selection,                                // The columns for the WHERE clause
			    selectionArgs,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    null                                // The sort order
			    );
				
		cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	BusinessAcount ba = new BusinessAcount(cursor.getInt(2), month, cursor.getFloat(1), cursor.getFloat(0));
        	businessList.add(ba);
            System.out.println("%%%%%%" + cursor.getString(0) + " " + cursor.getInt(1));
            
            cursor.moveToNext();
        }
 
        cursor.close();
		
		return businessList;
	}
	
	public List<BusinessAcount> findWeeklyInfo(int week, String month) {
		return null;
	}
}
