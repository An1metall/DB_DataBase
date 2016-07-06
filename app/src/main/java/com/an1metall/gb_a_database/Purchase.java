package com.an1metall.gb_a_database;

public class Purchase {
    int _id, _cost;
    String _name;
    Boolean _done = false;

    public Purchase(int _id, String _name, int _cost) {
        this._id = _id;
        this._name = _name;
        this._cost = _cost;
    }

    public Purchase(String _name, int _cost) {
        this._cost = _cost;
        this._name = _name;
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

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Boolean get_done() {
        return _done;
    }

    public void set_done(Boolean _done) {
        this._done = _done;
    }
}
