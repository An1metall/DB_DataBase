package com.an1metall.gb_a_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, Manifest.DATABASE_FILE_NAME, null, Manifest.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(XmlHandler.parseToQueryString(context.getResources().openRawResource(R.raw.database)));
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Manifest.DATABASE_TABLE_NAME);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = getWritableDatabase();
    }

    public void close() throws SQLException {
        db.close();
    }

    public Cursor getAllData() {
        return db.query(Manifest.DATABASE_TABLE_NAME, Manifest.DATABASE_ALL_COLUMNS, null, null, null, null, null);
    }

    public Cursor getDataById(int id) {
        return db.query(Manifest.DATABASE_TABLE_NAME, Manifest.DATABASE_ALL_COLUMNS, Manifest.DATABASE_KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public boolean insertEntry(String description, String cost, boolean done) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Manifest.DATABASE_KEY_DESCRIPTION, description);
        contentValues.put(Manifest.DATABASE_KEY_COST, cost);
        contentValues.put(Manifest.DATABASE_KEY_DONE, done);
        long result = db.insert(Manifest.DATABASE_TABLE_NAME, null, contentValues);
        if (result > -1) return true;
        return false;
    }

    public boolean deleteEntry(int id) {
        int result = db.delete(Manifest.DATABASE_TABLE_NAME, Manifest.DATABASE_KEY_ID + " = ?" , new String[]{String.valueOf(id)});
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
