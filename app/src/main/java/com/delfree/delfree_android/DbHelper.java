package com.delfree.delfree_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.delfree.delfree_android.Model.Tracking;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "com_batavree.db";
    public static final String GPSTRACKING_TABLE_NAME = "tracking";
    public static final String TRACKING_COLUMN_ID = "id";
    public static final String TRACKING_COLUMN_DATE = "date";
    public static final String TRACKING_COLUMN_LOCATION_LAT = "location_lat";
    public static final String TRACKING_COLUMN_LOCATION_LONG = "location_long";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + GPSTRACKING_TABLE_NAME + "(id integer primary key, date text, location_lat real, location_long real)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + GPSTRACKING_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTracking(String date, double location_lat, double location_long) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACKING_COLUMN_DATE, date);
        contentValues.put(TRACKING_COLUMN_LOCATION_LAT, location_lat);
        contentValues.put(TRACKING_COLUMN_LOCATION_LONG, location_long);
        Log.i("AMG", contentValues.toString());
        db.insert(GPSTRACKING_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteTracking(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GPSTRACKING_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Tracking getTracking(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from"+GPSTRACKING_TABLE_NAME+" where id="+id+"", null);
        Tracking retTracking = new Tracking(res.getInt(res.getColumnIndex(TRACKING_COLUMN_ID)),
                res.getString(res.getColumnIndex(TRACKING_COLUMN_DATE)),
                res.getDouble(res.getColumnIndex(TRACKING_COLUMN_LOCATION_LAT)),
                res.getDouble(res.getColumnIndex(TRACKING_COLUMN_LOCATION_LONG)));
        return retTracking;
    }
}