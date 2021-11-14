package com.example.showtracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.showtracker.domain.Show;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "showsdb.db";
    private static final String TABLE_SHOWS = "shows";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PRODUCER = "producer";
    private static final String KEY_FIRSTYEAR = "firstYear";
    private static final String KEY_LASTYEAR = "lastYear";
    private static final String KEY_NUMBEROFSEASONS = "numberOfSeasons";
    private static final String KEY_RATING = "rating";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOWS);
        String sql = "CREATE TABLE " + TABLE_SHOWS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," +
                KEY_PRODUCER + " TEXT," + KEY_FIRSTYEAR + " INTEGER," + KEY_LASTYEAR + " INTEGER, " +
                KEY_NUMBEROFSEASONS + " INTEGER, " +
                KEY_RATING + " REAL" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void addShow(Show show) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, show.getTitle());
        values.put(KEY_PRODUCER, show.getProducer());
        values.put(KEY_FIRSTYEAR, show.getFirstYear());
        values.put(KEY_LASTYEAR, show.getLastYear());
        values.put(KEY_NUMBEROFSEASONS, show.getNumberOfSeasons());
        values.put(KEY_RATING, show.getRating());

        // Inserting Row
        db.beginTransaction();
        db.insert(TABLE_SHOWS, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }

    public void deleteShow(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.delete(TABLE_SHOWS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public int updateShow(Show show) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, show.getTitle());
        values.put(KEY_PRODUCER, show.getProducer());
        values.put(KEY_FIRSTYEAR, show.getFirstYear());
        values.put(KEY_LASTYEAR, show.getLastYear());
        values.put(KEY_NUMBEROFSEASONS, show.getNumberOfSeasons());
        values.put(KEY_RATING, show.getRating());

        // updating row
        db.beginTransaction();
        int result = db.update(TABLE_SHOWS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(show.getId()) });
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return result;
    }

    public List<Show> getAllShows() {
        List<Show> shows = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SHOWS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Show show = new Show(cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Double.parseDouble(cursor.getString(6)));
                show.setId(Integer.parseInt(cursor.getString(0)));
                // Adding show to list
                shows.add(show);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // return shows list
        return shows;
    }

    public Show getShow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHOWS, new String[] { KEY_ID,
                        KEY_TITLE, KEY_PRODUCER, KEY_FIRSTYEAR,KEY_LASTYEAR,KEY_NUMBEROFSEASONS, KEY_RATING }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Show show = new Show(cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Double.parseDouble(cursor.getString(6)));
        show.setId(Integer.parseInt(cursor.getString(0)));
        cursor.close();
        // return show
        return show;
    }
}
