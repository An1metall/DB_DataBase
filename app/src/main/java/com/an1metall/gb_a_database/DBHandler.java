package com.an1metall.gb_a_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    Context context;

    public DBHandler(Context context) {
        super(context, Manifest.DATABASE_NAME, null, Manifest.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            List<String> tables = XmlHandler.parseToQuery(context.getResources().openRawResource(R.raw.database));
            for (String table: tables) {
                db.execSQL(table);
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS *");
        onCreate(db);
    }
}
