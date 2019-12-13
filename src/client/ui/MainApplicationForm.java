package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainApplicationForm {
    private JButton selectButton;
    private JComboBox comboBox;
    private JPanel rootPanel;

    public MainApplicationForm() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(MainApplicationForm.this.rootPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1100, 1000);
                frame.setResizable(false);
                frame.pack();
                frame.setVisible(true);
                createUIComponents();
            }
        });
    }

    public static void main(String[] args) {
        MainApplicationForm form = new MainApplicationForm();
    }

    private void createUIComponents() {
        comboBox = new JComboBox();
        comboBox.addItem("Teacher");
        comboBox.addItem("Student");

        selectButton = new JButton("Select");
        selectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(comboBox.getSelectedItem().equals("Teacher")) {
                    drawTeacherPanel();
                }
            }
        });
    }

    private void drawTeacherPanel() {
        rootPanel.removeAll();
        rootPanel.revalidate();
        rootPanel.repaint();
        LayoutManager manager = new GridLayout();
        TextArea testName = new TextArea();
        JButton createButton = new JButton("Create test");
        rootPanel.add(testName);
        rootPanel.add(createButton);
    }
}
