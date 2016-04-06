import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class QueryTableModel extends AbstractTableModel {

    private Vector cache;

    public String getColumnName(int i) {
        switch (i){
            case 0: return "Фамилия";
            case 1: return "Имя";
            case 2: return "Отчество";
            case 3: return "Телефон";
            default: return "";
        }
    }

    public int getColumnCount() {
        return DataBase.getColumnsCount();
    }

    public int getRowCount() {
        return DataBase.getRowsCount(); // cache.size();
    }

    public Object getValueAt(int row, int col) {
        return ((String[])cache.elementAt(row))[col];
    }

    // SETTERS

    public void setCache(Vector cache) {
        this.cache = cache;
    }

    // SETTERS END
}
