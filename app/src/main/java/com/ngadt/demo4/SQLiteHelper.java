package com.ngadt.demo4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ngadt.demo4.model.Time;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME = "TimeDB";
    public static final int VERSION = 3;
    public static final String KEY_ID = "id";

    public static final String TABLE_NAME = "TimeTable";

    public static final String KEY_Name = "name";

    public static final String KEY_Hour = "hour";

    public static final String KEY_Minutes = "minutes";
    public static final String KEY_Check = "checked";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String query =
//                "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                        + KEY_Hour + " TEXT, " + KEY_Minutes + " TEXT, " + KEY_Name + " TEXT, " + KEY_Check + " TEXT)";
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_Hour + " TEXT,"
                + KEY_Minutes + " TEXT,"
                + KEY_Name + " TEXT,"
                + KEY_Check + " INTEGER)";
        // String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_Hour + " VARCHAR, " + KEY_Minutes + " VARCHAR, " + KEY_Name + " VARCHAR)";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public boolean addNewTime(Time time) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        Log.d("test", "addNewTime: " + time.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(KEY_Name, time.getName());
        values.put(KEY_Hour, time.getHour());
        values.put(KEY_Minutes, time.getMinutes());
        values.put(KEY_Check, 1);
//        values.put(KEY_Name, "3");
//        values.put(KEY_Hour, "4");
//        values.put(KEY_Minutes, "5");

        // after adding all values we are passing
        // content values to our table.
        long insert = db.insert(TABLE_NAME, null, values);
        //   Log.d("test", "addNewTime: " + insert);
        // at last we are closing our
        // database after adding database.
        db.close();
        if (insert == 1)
            return false;
        else {
            return true;
        }
    }

    // we have created a new method for reading all the courses.
    public ArrayList<Time> readAll() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<Time> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new Time(cursorCourses.getInt(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getInt(4)));

            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

    // below is the method for updating our courses
    public void updateTime(Time time) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(KEY_Name, time.getName());
        values.put(KEY_Hour, time.getHour());
        values.put(KEY_Minutes, time.getMinutes());
        values.put(KEY_Check, time.getChecked());

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(time.getId())});
        db.close();
    }

    // below is the method for deleting our course.
    public void deleteCourse(String id) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "id=?", new String[]{id});
        db.close();
    }

}
