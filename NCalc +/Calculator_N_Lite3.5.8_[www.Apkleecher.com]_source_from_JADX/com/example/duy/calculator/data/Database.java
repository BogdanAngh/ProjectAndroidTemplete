package com.example.duy.calculator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.duy.calculator.define.VariableEntry;
import com.example.duy.calculator.history.ItemHistory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Database extends SQLiteOpenHelper implements Serializable {
    public static final String CREATE_TABLE_HISTORY = "create table tbl_history(time LONG PRIMARY KEY, math TEXT, result TEXT, color INTEGER,colorINTEGER)";
    public static final String CREATE_TABLE_VARIABLE = "CREATE TABLE tbl_variable(name TEXT PRIMARY KEY, value TEXT)";
    private static final String DATABASE_NAME = "history_manager";
    private static final int DATABASE_VERSION = 4;
    private static final String KEY_COLOR = "color";
    private static final String KEY_ID = "time";
    private static final String KEY_MATH = "math";
    private static final String KEY_RESULT = "result";
    private static final String KEY_TYPE = "color";
    private static final String KEY_VAR_NAME = "name";
    private static final String KEY_VAR_VALUE = "value";
    private static final String TABLE_HISTORY = "tbl_history";
    private static final String TABLE_VARIABLE = "tbl_variable";
    static final long serialVersionUID = 2;
    private String TAG;

    public Database(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.TAG = Database.class.getName();
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.TAG = Database.class.getName();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HISTORY);
        db.execSQL(CREATE_TABLE_VARIABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_history");
        db.execSQL("DROP TABLE IF EXISTS tbl_variable");
        onCreate(db);
    }

    public long saveHistory(ItemHistory itemHistory) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, Long.valueOf(itemHistory.getTime()));
        contentValues.put(KEY_MATH, itemHistory.getMath());
        contentValues.put(KEY_RESULT, itemHistory.getResult());
        contentValues.put(KEY_TYPE, Integer.valueOf(itemHistory.getColor()));
        contentValues.put(KEY_TYPE, Integer.valueOf(itemHistory.getType()));
        return sqLiteDatabase.insert(TABLE_HISTORY, null, contentValues);
    }

    public ArrayList<ItemHistory> getAllItemHistory() {
        ArrayList<ItemHistory> itemHistories = new ArrayList();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM tbl_history", null);
        if (cursor.moveToFirst()) {
            do {
                long time = cursor.getLong(cursor.getColumnIndex(KEY_ID));
                String math = cursor.getString(cursor.getColumnIndex(KEY_MATH));
                String result = cursor.getString(cursor.getColumnIndex(KEY_RESULT));
                itemHistories.add(new ItemHistory(math, result, cursor.getInt(cursor.getColumnIndex(KEY_TYPE)), time, cursor.getInt(cursor.getColumnIndex(KEY_TYPE))));
                Log.d(this.TAG, "getAllItemHistory: " + time + " " + math + " " + result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemHistories;
    }

    public long removeItemHistory(ItemHistory itemHistory) {
        long id = itemHistory.getTime();
        try {
            return (long) getWritableDatabase().delete(TABLE_HISTORY, "time=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long removeItemHistory(long id) {
        try {
            return (long) getWritableDatabase().delete(TABLE_HISTORY, "time=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long clearHistory() {
        return (long) getWritableDatabase().delete(TABLE_HISTORY, null, null);
    }

    public long addVariable(String name, String value) {
        Log.d(this.TAG, "addVariable: " + name + " =" + value);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VAR_NAME, name);
        contentValues.put(KEY_VAR_VALUE, value);
        return sqLiteDatabase.insert(TABLE_VARIABLE, null, contentValues);
    }

    public long removeVariable(String name) {
        try {
            return (long) getWritableDatabase().delete(TABLE_VARIABLE, "name=?", new String[]{String.valueOf(name)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateValueVariable(String name, String newValue) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VAR_NAME, name);
        contentValues.put(KEY_VAR_VALUE, newValue);
        Log.d(this.TAG, "updateValueVariable: " + name + " " + newValue);
        Log.d(this.TAG, "updateValueVariable: " + ((long) sqLiteDatabase.update(TABLE_VARIABLE, contentValues, "name=?", new String[]{name})));
    }

    public ArrayList<VariableEntry> getAllVariable() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM tbl_variable", null);
        ArrayList<VariableEntry> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                list.add(new VariableEntry(cursor.getString(cursor.getColumnIndex(KEY_VAR_NAME)), cursor.getString(cursor.getColumnIndex(KEY_VAR_VALUE))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void saveHistory(ArrayList<ItemHistory> histories) {
        Iterator it = histories.iterator();
        while (it.hasNext()) {
            saveHistory((ItemHistory) it.next());
        }
        Log.d(this.TAG, "saveHistory: ok");
    }
}
