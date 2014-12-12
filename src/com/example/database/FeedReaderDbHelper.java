package com.example.database;

import com.example.database.FeedReaderContract.FeedEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
	private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

	
	private static final String SQL_CREATE_ENTRIES =
    	    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
    	    FeedEntry._ID + " INTEGER PRIMARY KEY," +
    	    FeedEntry.COLUMN_NAME_MONTH + TEXT_TYPE + COMMA_SEP +
    	    FeedEntry.COLUMN_NAME_DAY + TEXT_TYPE + COMMA_SEP +
    	    FeedEntry.COLUMN_NAME_COST + TEXT_TYPE + COMMA_SEP +
    	    FeedEntry.COLUMN_NAME_INCOME + TEXT_TYPE +
    	    " );";


	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

	
	
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}