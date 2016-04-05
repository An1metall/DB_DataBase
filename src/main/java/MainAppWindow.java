import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.*;

public class MainAppWindow extends JFrame {

    private static QueryTableModel tableModel = new QueryTableModel();
    private static SearchType searchType = SearchType.FNAME;
    private static int getRowsFromSite = 0;

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
                final JDialog frame = new JDialog(MainClass.mainAppWindow, "Добавить данные в БД", true);
                frame.setSize(400, 60);
                frame.setLocationRelativeTo(MainClass.mainAppWindow);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                frame.setAlwaysOnTop(true);
                frame.setUndecorated(true);

                JPanel jp = new JPanel(new FlowLayout());
                frame.add(jp);
                JLabel label1 = new JLabel("Спасибо порталу GeekBrains.ru за обучение!");
                jp.add(label1, BorderLayout.CENTER);
                JLabel label2 = new JLabel("Спасибо порталу randus.ru за генератор случайных данных!");
                jp.add(label2, BorderLayout.CENTER);
                jp.setBackground(Color.LIGHT_GRAY);

                frame.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        frame.dispose();
                    }
                });

                frame.setVisible(true);
            }
        });

        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        menuItemGetData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                final JDialog frame = new JDialog(MainClass.mainAppWindow, "Добавить данные в БД", true);
                JPanel panel = new JPanel(new BorderLayout());
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setSize(200, 100);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(MainClass.mainAppWindow);
                JLabel label = new JLabel("Добавить записей: ");
                final JFormattedTextField field = new JFormattedTextField(createFormatter("##"));
                field.setText("20");
                JButton jb = new JButton("OK");
                frame.add(label, BorderLayout.WEST);
                frame.add(field);
                frame.add(jb, BorderLayout.EAST);

                jb.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            getRowsFromSite = Integer.parseInt(field.getText());
                        } catch (NumberFormatException ev){}
                        frame.dispose();
                    }
                });

                frame.setVisible(true);

                if (getRowsFromSite > 0){
                    for (int i = 0; i < getRowsFromSite; i++) {
                        JSONObject json = DataFromSite.getDataFromSite();
                        DataBase.setInsertQuery(json);
                    }
                    updateTableModel();
                }
            }
        });

        menuItemDeleteData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DataBase.setDeleteQuery();
                updateTableModel();
            }
        });

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                if (comboBox.getSelectedIndex() == 0) {
                    searchType = SearchType.FNAME;
                }
                if (comboBox.getSelectedIndex() == 1) {
                    searchType = SearchType.LNAME;
                }
                if (comboBox.getSelectedIndex() == 2) {
                    searchType = SearchType.MNAME;
                }
                if (comboBox.getSelectedIndex() == 3) {
                    searchType = SearchType.PHONE;
                }
            }
        });

        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                doSeatch(textField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                doSeatch(textField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                doSeatch(textField.getText());
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

    private void doSeatch(String search){
        tableModel.setCache(DataBase.setSelectQuery(searchType, search));
        tableModel.fireTableDataChanged();
    }

    private void updateTableModel() {
        tableModel.setCache(DataBase.getTableData());
        tableModel.fireTableDataChanged();
    }

    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
        }
        return formatter;
    }
}
