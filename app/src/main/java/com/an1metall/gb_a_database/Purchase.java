package com.an1metall.gb_a_database;

import android.database.Cursor;

public class Purchase {

    private int _id, _cost;
    private String _description;
    int _done = 0;

    private Purchase(int _id, String _description, int _cost, int _done) {
        this._id = _id;
        this._description = _description;
        this._cost = _cost;
        this._done = _done;
    }

    public static Purchase formCursor(Cursor cursor){
        if (cursor != null) {
            int id = cursor.getInt(cursor.getColumnIndex(Manifest.DATABASE_KEY_ID));
            int cost = cursor.getInt(cursor.getColumnIndex(Manifest.DATABASE_KEY_COST));
            String description = cursor.getString(cursor.getColumnIndex(Manifest.DATABASE_KEY_DESCRIPTION));
            int done = cursor.getInt(cursor.getColumnIndex(Manifest.DATABASE_KEY_COST));
            return new Purchase(id, description, cost, done);
        }
        return null;
    }

    // GETTERS and SETTERS

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_cost() {
        return _cost;
    }

    public void set_cost(int _cost) {
        this._cost = _cost;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_done() {
        return _done;
    }

    public void set_done(int _done) {
        this._done = _done;
    }
}
