package com.an1metall.gb_a_database;

class Manifest {

    // Debug section
    public static final String LOG_TAG = "debugLog";

    // Database section
    public static final String DATABASE_FILE_NAME = "purchases.db";
    public static final String DATABASE_TABLE_NAME = "purchases";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_KEY_ID = "id";
    public static final String DATABASE_KEY_DESCRIPTION = "description";
    public static final String DATABASE_KEY_COST = "cost";
    public static final String DATABASE_KEY_DONE = "done";
    public static final String[] DATABASE_ALL_COLUMNS = new String[]{DATABASE_KEY_ID, DATABASE_KEY_DESCRIPTION, DATABASE_KEY_COST, DATABASE_KEY_DONE};

    // XML Parse section
    public static final String XML_ENTITY = "table";
    public static final String XML_ENTITY_NAME = "table_name";
    public static final String XML_ENTITY_ATTRIBUTE = "attribute";
    public static final String XML_ENTITY_ATTRIBUTE_NAME = "name";
    public static final String XML_ENTITY_ATTRIBUTE_TYPE = "type";
    public static final String XML_ENTITY_ATTRIBUTE_PARAMETER = "parameter";
}
