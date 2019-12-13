package client.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Record;

import javax.swing.*;

import static client.ui.FormUtils.*;

public class FormInterface {
    private Stage stage;
    private double width = 550;
    private double height = 350;

    private Record[] records = new Record[0];

    private JPanel panel;

    public FormInterface(Stage stage) {
        this.stage = stage;
        start();
    }

    public void setWindowSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void start() {
        registration();
    }

    public void registration() {
        setWindowSize(400, 500);
        stage.setTitle("Testing system");

        GridPane grid = createGrid();

        Text sceneTitle = createText("Welcome");
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Role:");
        grid.add(userName, 0, 1);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Teacher", "Student");
        grid.add(comboBox, 1, 1);

        Button btn = createButtonWithTitle("Sign in");
        btn.setOnAction(event -> {
            if (comboBox.getSelectionModel().getSelectedItem().equals("Teacher")) {
                new TeacherInterface(stage);
            } else {
                new StudentInterface(stage);
            }
        });

        grid.add(createHboxAndAppendButtons(btn), 1, 4);

        Scene scene = new Scene(grid, width, height);
        stage.setScene(scene);
        stage.show();
    }
}
