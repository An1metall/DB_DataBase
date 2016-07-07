package com.an1metall.gb_a_database;

public class Purchase {
    private int _id, _cost;
    private String _description;
    private Boolean _done = false;

    public Purchase(int _id, String _description, int _cost) {
        this._id = _id;
        this._description = _description;
        this._cost = _cost;
    }

    public Purchase(String _description, int _cost) {
        this._cost = _cost;
        this._description = _description;
    }

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

    public Boolean get_done() {
        return _done;
    }

    public void set_done(Boolean _done) {
        this._done = _done;
    }
}
