import java.io.IOException;

public class MainClass {

    private static final String dataBase = "db\\data.db";
    private static final String JDBC = "jdbc:sqlite";
    private static final String dataBaseUrl = JDBC + ":" + dataBase;
    public static MainAppWindow mainAppWindow;

    public static void main(String[] args) throws IOException {
        if (DataBase.connect(dataBaseUrl)) {
            DataBase.getTableData();
            mainAppWindow = new MainAppWindow();
        }
    }
}

