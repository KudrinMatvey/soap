package client.ui;

import client.TestFacade;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Record;

import static client.ui.FormUtils.*;
import static client.ui.FormUtils.createHboxAndAppendButtons;

public class TeacherInterface {
    private Stage stage;
    private double width = 550;
    private double height = 550;

    public TeacherInterface(Stage stage) {
        this.stage = stage;
        showTeacherPanel();
    }

    public GridPane showCreateTestGrid(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop) {
        GridPane createGrid = createGrid();

        Text createTitle = createText("Create test");
        createGrid.add(createTitle, 0, 0, 2, 1);

        Label testName = new Label("Record name:");
        createGrid.add(testName, 0, 1);

        TextField testTextField = new TextField();
        testTextField.setText("Record 1");
        createGrid.add(testTextField, 1, 1);

        Button okBtn = createButtonWithTitle("Create");
        okBtn.setOnMouseClicked(userConfirm -> {
            TestFacade.createAndAddTest(testTextField.getText());
            go(previousPane, previousTop, previousGrid, previousBottom);
            listView.setItems(FXCollections.observableArrayList(TestFacade.retrieveAllTestNames()));
        });

        Button leaveBtn = createButtonWithTitle("Leave");
        leaveBtn.setOnMouseClicked(event -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
        });

        createGrid.add(createHboxAndAppendButtons(okBtn, leaveBtn), 1, 5);

        return createGrid;
    }

    public GridPane showAddQuestion(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop, Record selectedRecord) {
        GridPane createGrid = createGrid();
        int gridRowIndex = 0;

        Text createTitle = createText("Create question for " + selectedRecord.getName());
        createGrid.add(createTitle, 0, gridRowIndex++, 2, 1);

        Label testName = new Label("Question name:");
        createGrid.add(testName, 0, gridRowIndex++);

        TextField questionNameField = new TextField();
        questionNameField.setText("Question 1");
        createGrid.add(questionNameField, 0, gridRowIndex++);

        Label createdQuestionsLabel = new Label("Created questions:");
        createGrid.add(createdQuestionsLabel, 0, gridRowIndex++);

        ListView<String> list = createListAndFill(selectedRecord.getAllQuestions());
        createGrid.add(list, 0, gridRowIndex++);


        Button okBtn = createButtonWithTitle("Create");
        okBtn.setOnMouseClicked(userConfirm -> {
            TestFacade.addQuestion(selectedRecord, new Question(questionNameField.getText(), selectedRecord.getName()));
            fillList(list, TestFacade.getQuestions(selectedRecord));
        });

        Button backBtn = createButtonWithTitle("Back");
        backBtn.setOnMouseClicked(event -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
        });

        createGrid.add(createHboxAndAppendButtons(okBtn, backBtn), 1, gridRowIndex);
        return createGrid;
    }

    public GridPane showPreviewTest(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, ListView<String> listView, HBox previousTop, Record selectedRecord) {
        GridPane createGrid = createGrid();
        int gridRowIndex = 0;

        Text createTitle = createText("Record info: " + selectedRecord.getName());
        createGrid.add(createTitle, 0, gridRowIndex++, 2, 1);


        Label createdQuestionsLabel = new Label("Created questions:");
        createGrid.add(createdQuestionsLabel, 0, gridRowIndex++);

        ListView<String> list = createListAndFill(selectedRecord.getAllQuestions());
        createGrid.add(list, 0, gridRowIndex);


        Button modifyQuestionButton = createButtonWithTitle("Modify Question");
        createGrid.add(modifyQuestionButton, 1, gridRowIndex++);


        Button backBtn = createButtonWithTitle("Back");
        backBtn.setOnMouseClicked(event -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
        });

        HBox bottom = createHboxAndAppendButtons(backBtn);

        modifyQuestionButton.setOnMouseClicked(onSelect -> {
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex >= 0) {
                Question selectedQuestion = selectedRecord.getAllQuestions()[selectedIndex];
                go(previousPane, new HBox(), showModifyQuestionPanel(createGrid, previousPane, bottom, new HBox(), selectedQuestion),new HBox());
            }
        });

        createGrid.add(bottom, 1, gridRowIndex);
        return createGrid;
    }

    public void showTeacherPanel() {
        GridPane joinGrid = createGrid();

        Text testsTitle = createText("Existing tests");
        joinGrid.add(testsTitle, 0, 0, 2, 1);

        Button exit = createButtonWithTitle("exit");
        Button modifyTestBtn = createButtonWithTitle("Modify test");
        Button createBtn = createButtonWithTitle("Create test");
        Button previewTestBtn = createButtonWithTitle("Preview Record");

        HBox hBox = createHboxAndAppendButtons(modifyTestBtn, createBtn, previewTestBtn);
        HBox hBoxBtn = createHboxAndAppendButtons(exit);

        BorderPane borderPane = new BorderPane();
        go(borderPane, hBox, joinGrid, hBoxBtn);

        ListView<String> list = createListAndFill(TestFacade.getAllTests());
        joinGrid.add(list, 0, 1);

        exit.setOnMouseClicked(event1 -> stage.close());

        createBtn.setOnMouseClicked(event -> {
            go(borderPane, new HBox(), showCreateTestGrid(joinGrid, borderPane, hBoxBtn, list, hBox), new HBox());
        });

        modifyTestBtn.setOnMouseClicked(event2 -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Record record = TestFacade.getTestByName(selectedTest);
                go(borderPane, new HBox(), showAddQuestion(joinGrid, borderPane, hBoxBtn, list, hBox, record), new HBox());
            }
        });

        previewTestBtn.setOnMouseClicked(event -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Record record = TestFacade.getTestByName(selectedTest);
                go(borderPane, new HBox(), showPreviewTest(joinGrid, borderPane, hBoxBtn, list, hBox, record), new HBox());
            }
        });

        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }

    public GridPane showModifyQuestionPanel(GridPane previousGrid, BorderPane previousPane, HBox previousBottom, HBox previousTop, Question selectedQuestion) {
        GridPane createGrid = createGrid();
        int gridRowIndex = 0;

        Text createTitle = createText("Question: " + selectedQuestion.getQuestionText());
        createGrid.add(createTitle, 0, gridRowIndex++, 2, 1);

        Text name = createText("Name");
        createGrid.add(name, 0, gridRowIndex, 1, 1);
        Text addPoints = createText("Add");
        createGrid.add(addPoints, 1, gridRowIndex, 1, 1);
        Text removePoints = createText("Remove");
        createGrid.add(removePoints, 2, gridRowIndex++, 1, 1);

        TextArea nameArea = new TextArea("Name");
        createGrid.add(nameArea, 0, gridRowIndex, 1, 1);
        TextArea addPointsArea = new TextArea("1");
        createGrid.add(addPointsArea, 1, gridRowIndex, 1, 1);
        TextArea removePointsArea = new TextArea("2");
        createGrid.add(removePointsArea, 2, gridRowIndex, 1, 1);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Correct", "Incorrect");
        createGrid.add(comboBox, 3, gridRowIndex++);

        Label createdOptions = new Label("Created options:");
        createGrid.add(createdOptions, 0, gridRowIndex++, 2, 1);

        Button addButton = createButtonWithTitle("Add");
        createGrid.add(addButton, 3, gridRowIndex, 1,1);

        ListView<String> list = createListAndFill(TestFacade.retrieveAllOptions(selectedQuestion));
        createGrid.add(list, 0, gridRowIndex++, 2, 1);

        addButton.setOnMouseClicked(event -> {
            boolean isCorrect = false;
            if(comboBox.getSelectionModel().getSelectedIndex() >= 0) {
                isCorrect = comboBox.getSelectionModel().getSelectedItem().equals("Correct");
            }
            Option newOption = new Option(nameArea.getText(), isCorrect,
                    Integer.valueOf(addPointsArea.getText()), Integer.valueOf(removePointsArea.getText()));
            TestFacade.addOption(selectedQuestion, newOption);
            fillList(list, TestFacade.retrieveAllOptions(selectedQuestion));
        });


        Button backBtn = createButtonWithTitle("Back");
        backBtn.setOnMouseClicked(event -> {
            go(previousPane, previousTop, previousGrid, previousBottom);
        });
        createGrid.add(createHboxAndAppendButtons(backBtn), 1, gridRowIndex);
        return createGrid;
    }
}
