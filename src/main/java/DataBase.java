import org.json.simple.JSONObject;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class DataBase {

    private static Connection conn;
    private static Statement statement;
    private static ResultSet resultSet;
    private static ResultSetMetaData metaData;
    private static int columnsCount;
    private static int rowsCount;
    private static final String defQuery = "SELECT lname, fname, mname, phone FROM CPHONES";

    public static boolean connect(String dataBaseUrl) {
        conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
        }

        try {
            conn = DriverManager.getConnection(dataBaseUrl);
            statement = conn.createStatement();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "SQL Lite Error", JOptionPane.ERROR_MESSAGE);
        }

        if (conn != null) {
            return true;
        }
        return false;
    }

    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "SQL Lite Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Vector setSelectQuery(SearchType type, String search) {
        String query = "";
        switch (type){
            case FNAME:
                query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE fname LIKE '%" + search + "%'";
                break;
            case LNAME:
                query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE lname LIKE '%" + search + "%'";
                break;
            case MNAME:
                query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE mname LIKE '%" + search + "%'";
                break;
            case PHONE:
                query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE phone LIKE '%" + search + "%'";
                break;
        }
        return getTableData(query);
    }

    public static void setDeleteQuery() {
        String query = "DELETE FROM CPHONES";
        try {
            statement.execute(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "SQL Lite Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setInsertQuery(JSONObject json) {
        try {
            String query = "INSERT INTO CPHONES (lname, fname, mname, phone) VALUES ('" + json.get("lname") + "', '" + json.get("fname") + "', '" + json.get("patronymic") + "', '" + json.get("phone") + "')";
            statement.execute(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "SQL Lite Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Vector getTableData(String query) {
        Vector cache = new Vector();
        try {
            resultSet = statement.executeQuery(query);
            metaData = resultSet.getMetaData();
            columnsCount = metaData.getColumnCount();
            rowsCount = 0;
            while (resultSet.next()) {
                String[] record = new String[metaData.getColumnCount()];
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    record[i] = resultSet.getString(i + 1);
                }
                rowsCount++;
                cache.addElement(record);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "SQL Lite Error", JOptionPane.ERROR_MESSAGE);
        }
        return cache;
    }

    public static Vector getTableData() {
        return getTableData(defQuery);
    }

    // GETTERS

    public static int getColumnsCount() {
        return columnsCount;
    }

    public static int getRowsCount() {
        return rowsCount;
    }

    // GETTERS END
}
