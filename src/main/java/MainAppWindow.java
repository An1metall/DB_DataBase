import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainAppWindow extends JFrame {

    private static QueryTableModel tableModel = new QueryTableModel();
    private static int getRowsFromSite = 20;
    private static SearchType searchType = SearchType.FNAME;

    public MainAppWindow() throws HeadlessException {
        //Main Window
        setTitle("Телефонная книга");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Main Menu
        JMenuBar mainMenuBar = new JMenuBar();
        add(mainMenuBar, BorderLayout.NORTH);
        JMenu menuFile = new JMenu("Файл");
        mainMenuBar.add(menuFile);
        JMenuItem menuItemGetData = new JMenuItem("Заполнить БД");
        menuFile.add(menuItemGetData);
        JMenuItem menuItemDeleteData = new JMenuItem("Удалить данные из БД");
        menuFile.add(menuItemDeleteData);
        JMenuItem menuItemExit = new JMenuItem("Выход");
        menuFile.add(menuItemExit);
        JMenu menuHelp = new JMenu("Помощь");
        mainMenuBar.add(menuHelp);
        JMenuItem menuItemAbout = new JMenuItem("О программе");
        menuHelp.add(menuItemAbout);

        // Center Panel
        tableModel.setCache(DataBase.getTableData());
        JTable dataTable = new JTable(tableModel);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane);

        // Bottom Panel
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JPanel bottomRightPanel = new JPanel(new FlowLayout());
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
        JButton jb2 = new JButton("Найти");
        bottomRightPanel.add(jb2);
        JButton jb3 = new JButton("Очистить фильтр");
        bottomRightPanel.add(jb3);
        String[] comboboxItemsList = {"Имя", "Фамилия", "Отчество", "Телефон"};
        final JComboBox comboBox = new JComboBox(comboboxItemsList);
        comboBox.setSelectedIndex(0);
        bottomPanel.add(comboBox, BorderLayout.WEST);
        final JTextField textField = new JTextField();
        bottomPanel.add(textField, BorderLayout.CENTER);

        // Main window visibility
        setVisible(true);

        // LISTENERS

        menuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AboutWindow();
            }
        });

        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        menuItemGetData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (DataBase.getRowsCount() != 0) {
                    DataBase.setQuery("DELETE FROM CPHONES");
                }
                for (int i = 0; i < getRowsFromSite; i++) {
                    JSONObject json = DataFromSite.getDataFromSite();
                    String query = "INSERT INTO CPHONES (lname, fname, mname, phone) VALUES ('" + json.get("lname") + "', '" + json.get("fname") + "', '" + json.get("patronymic") + "', '" + json.get("phone") + "')";
                    DataBase.setQuery(query);
                }
                updateTableModel();
            }
        });

        menuItemDeleteData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DataBase.setQuery("DELETE FROM CPHONES");
                updateTableModel();
            }
        });

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedIndex() == 0) {searchType = SearchType.FNAME;}
                if (comboBox.getSelectedIndex() == 1) {searchType = SearchType.LNAME;}
                if (comboBox.getSelectedIndex() == 2) {searchType = SearchType.MNAME;}
                if (comboBox.getSelectedIndex() == 3) {searchType = SearchType.PHONE;}
            }
        });

        jb2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String query = "";
                switch (searchType){
                    case FNAME:
                        query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE fname LIKE '%" + textField.getText() + "%'";
                        break;
                    case LNAME:
                        query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE lname LIKE '%" + textField.getText() + "%'";
                        break;
                    case MNAME:
                        query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE mname LIKE '%" + textField.getText() + "%'";
                        break;
                    case PHONE:
                        query = "SELECT lname, fname, mname, phone FROM CPHONES WHERE phone LIKE '%" + textField.getText() + "%'";
                        break;
                }
                updateTableModel(query);
            }
        });

        jb3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateTableModel();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataBase.close();
                System.exit(0);
            }
        });

        // LISTENERS END
    }

    private void updateTableModel() {
        tableModel.setCache(DataBase.getTableData());
        tableModel.fireTableDataChanged();
    }

    private void updateTableModel(String query) {
        tableModel.setCache(DataBase.getTableData(query));
        tableModel.fireTableDataChanged();
    }
}
