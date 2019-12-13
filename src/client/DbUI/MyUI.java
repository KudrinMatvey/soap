package client.DbUI;

import server.entity.Mark;
import server.entity.Record;
import server.service.DbService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

public class MyUI extends JFrame {
    URL testServiceURL;
    QName name;
    Service testConnectService;
    DbService dbService;
    Record record;
    DefaultListModel listModel;
    DefaultListModel markListModel;
    Record selectedRecord;
    Mark[] selectedMarks;
    private JList recordsList;
    private JTextField addRecordTextField;
    private JButton getAllButton;
    private JPanel panel;
    private JButton findByMarkButton;
    private JButton addRecordButton;
    private JList marksList;
    private JButton getAllMarksButton;
    private JButton addSelectedMarksToButton;
    private JButton addMarkButton;
    private JButton getFileButton;
    private JButton removeMarkButton;
    private JButton selectFileButton;
    private JButton addFileButton;
    JFileChooser fileChooser;
    File selectedFile;

    MyUI() throws MalformedURLException {
        testServiceURL = new URL("http://localhost:8888/db");
        name = new QName("http://service.server/", "DbServiceImplService");
        testConnectService = Service.create(testServiceURL, name);
        dbService = testConnectService.getPort(DbService.class);
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

//        record = new Record("TEST");

//        DefaultListModel listModel = new DefaultListModel();
//        listModel.addElement("hello");
//        recordsList = new JList(listModel);
//        lis
//        recordsList.setModel(listModel);
        this.getContentPane().add(panel);
        setSize(1100, 1000);
        getAllButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Record[] r = dbService.getAllRecords();
                listModel = new DefaultListModel();
                for (int i = 0; i < r.length; i++) {
                    listModel.addElement(r[i]);
                }
                recordsList.setModel(listModel);
            }
        });
        addRecordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dbService.addRecord(new Record(addRecordTextField.getText()));
            }
        });
        recordsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRecord = (Record) listModel.get(e.getFirstIndex());
            }
        });
        getAllMarksButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] r = dbService.getAllMarks();
                markListModel = new DefaultListModel();
                for (int i = 0; i < r.length; i++) {
                    markListModel.addElement(r[i]);
                }
                marksList.setModel(markListModel);
            }
        });
        marksList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedMarks = new Mark[e.getFirstIndex() - e.getLastIndex() + 1];
                for (int i = 0; i < e.getFirstIndex() - e.getLastIndex() + 1; i++) {
                    selectedMarks[i] = (Mark) markListModel.get(i);
                }
            }
        });
        addSelectedMarksToButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedRecord != null && selectedMarks != null) {
                    String[] arr = new String[selectedMarks.length];
                    for (int i = 0; i < selectedMarks.length; i++) {
                        arr[i] = selectedMarks[i].getId();
                    }
                    dbService.addMarkToRecord(selectedRecord.getId(), arr);
                }
            }
        });
        addMarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dbService.addMarkToSystem(addRecordTextField.getText());
            }
        });
        getFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedRecord != null) {
                    dbService.getFile(selectedRecord.getId(), addRecordTextField.getText());
                }
            }
        });
        removeMarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedMarks != null) {
                    for (int i = 0; i < selectedMarks.length; i++) {
                        dbService.removeMark(selectedMarks[i].getId());
                    }
                }
            }
        });
        findByMarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedMarks != null && selectedMarks[0] != null) {
                    dbService.getRecordsByMark(selectedMarks[0].getId());
                }
            }
        });
        selectFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = fileChooser.showOpenDialog(panel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    selectFileButton.setText("File is selected");
                }
            }
        });
        addFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(selectedFile != null) {
                    try {
                        byte[] a = Files.readAllBytes(selectedFile.toPath());
                        dbService.addFileToRecord(selectedRecord.getId(), addRecordTextField.getText(), a);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                selectFileButton.setText("Select file");
                selectedFile = null;
            }
        });
    }

    public static void main(String[] args) {
        MyUI myUI = null;
        try {
            myUI = new MyUI();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        myUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        myUI.setResizable(false);
        myUI.pack();
        myUI.setVisible(true);
    }
}
