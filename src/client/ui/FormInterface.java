package client.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Test;

import javax.swing.*;

import static client.ui.FormUtils.*;

public class FormInterface {
    private Stage stage;
    private double width = 550;
    private double height = 350;

    private Test[] tests = new Test[0];

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

        Button btns = createButtonWithTitle("go as student");
        Button btnt = createButtonWithTitle("go as teacher");

        btnt.setOnAction(event -> {
                new TeacherInterface(stage);
        });
        btns.setOnAction(event -> {
                new StudentInterface(stage);
        });

        grid.add(createHboxAndAppendButtons( btnt), 0, 4);
        grid.add(createHboxAndAppendButtons(btns, btnt), 0, 5);

        Scene scene = new Scene(grid, width, height);
        stage.setScene(scene);
        stage.show();
    }
}
