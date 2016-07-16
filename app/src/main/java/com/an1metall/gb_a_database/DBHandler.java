package com.an1metall.gb_a_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase db;

    private static int lastID = 0;

    public DBHandler(final Context context) {
        super(context, Manifest.DATABASE_FILE_NAME, null, Manifest.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sqlCmd = XmlHandler.parseToQueryString(context.getResources().openRawResource(R.raw.database));
            db.execSQL(sqlCmd);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Manifest.DATABASE_TABLE_NAME);
        onCreate(db);
    }

    public void open(){
        db = getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public ObservableList<Purchase> getAllData() {
        ObservableList<Purchase> result = new ObservableArrayList<>();
        Cursor cursor = db.query(Manifest.DATABASE_TABLE_NAME, Manifest.DATABASE_ALL_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            Purchase item = Purchase.formCursor(cursor);
            if (item.get_id() > lastID) lastID = item.get_id();
            result.add(item);
        }
        return result;
    }

    public ObservableList<Purchase> wipeAllData(){
        db.delete(Manifest.DATABASE_TABLE_NAME, null, null);
        return getAllData();
    }

    public Purchase getDataById(int _id){
        Cursor cursor = db.query(Manifest.DATABASE_TABLE_NAME, Manifest.DATABASE_ALL_COLUMNS, Manifest.DATABASE_KEY_ID + " = ?", new String[]{String.valueOf(_id)}, null, null, null );
        cursor.moveToFirst();
        Purchase result = Purchase.formCursor(cursor);
        return result;
    }

    public Purchase getInsertedData(){
        Cursor cursor  = db.query(Manifest.DATABASE_TABLE_NAME, Manifest.DATABASE_ALL_COLUMNS, Manifest.DATABASE_KEY_ID + " > ?", new String[]{String.valueOf(lastID)}, null, null, null );
        cursor.moveToFirst();
        Purchase result = Purchase.formCursor(cursor);
        lastID = result.get_id();
        return result;
    }

    public boolean insertEntry(String description, int cost, int done) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Manifest.DATABASE_KEY_DESCRIPTION, description);
        contentValues.put(Manifest.DATABASE_KEY_COST, cost);
        contentValues.put(Manifest.DATABASE_KEY_DONE, done);
        long result = db.insert(Manifest.DATABASE_TABLE_NAME, null, contentValues);
        if (result > -1) return true;
        return false;
    }

    public boolean deleteEntry(int id) {
        int result = db.delete(Manifest.DATABASE_TABLE_NAME, Manifest.DATABASE_KEY_ID + " = ?", new String[]{String.valueOf(id)});
        if (result > 0) return true;
        return false;
    }

    public boolean updateEntry(Purchase item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Manifest.DATABASE_KEY_ID, item.get_id());
        contentValues.put(Manifest.DATABASE_KEY_DESCRIPTION, item.get_description());
        contentValues.put(Manifest.DATABASE_KEY_COST, item.get_cost());
        contentValues.put(Manifest.DATABASE_KEY_DONE, item.get_id());
        int result = db.update(Manifest.DATABASE_TABLE_NAME, contentValues, Manifest.DATABASE_KEY_ID + " = ?", new String[]{String.valueOf(item.get_id())});
        if (result > 0) return true;
        return false;
    }
}
