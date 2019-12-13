package client.ui;

import client.TestFacade;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.entity.Option;
import server.entity.Question;
import server.entity.Record;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static client.ui.FormUtils.*;

public class StudentInterface {
    private Stage stage;
    private double width = 550;
    private double height = 550;
    private int result = 0;

    public StudentInterface(Stage stage) {
        this.stage = stage;
        showStudentPanel(false);
    }

    public void showStudentPanel(boolean fromTest) {
        GridPane joinGrid = createGrid();

        Text testsTitle = createText("Existing tests");
        joinGrid.add(testsTitle, 0, 0, 2, 1);

        Button exit = createButtonWithTitle("exit");

        Button takeTestBtn = createButtonWithTitle("Take Record");

        HBox hBox = createHboxAndAppendButtons(takeTestBtn);
        HBox hBoxBtn = createHboxAndAppendButtons(exit);

        BorderPane borderPane = new BorderPane();
        go(borderPane, hBox, joinGrid, hBoxBtn);

        ListView<String> list = createListAndFill(TestFacade.getAllTests());

        if(fromTest) {
            Text resultText = createText("Your last result is : " + result);
            result = 0;
            joinGrid.add(resultText, 0, 1);
        }
        joinGrid.add(list, 0, 2);

        exit.setOnMouseClicked(event1 -> stage.close());


        takeTestBtn.setOnMouseClicked(event2 -> {
            String selectedTest = list.getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                Record record = TestFacade.getTestByName(selectedTest);
                Question[] questions = TestFacade.getQuestions(record);
                List<Question> questionList = new ArrayList<>();
                for (Question q : questions) {
                    if(q.getQuestionText() != null) {
                        questionList.add(q);
                    }
                }
                go(borderPane, new HBox(), startTest(borderPane ,joinGrid, record, questionList.iterator()), new HBox());
            }
        });

        Scene scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.show();
    }

    public GridPane startTest(BorderPane previousPane, GridPane previousGrid, Record selectedRecord, Iterator<Question> iterator) {
        GridPane grid = createGrid();
        int gridRowIndex = 0;

        Text testTitle = createText(selectedRecord.getName());
        grid.add(testTitle, 0, gridRowIndex++, 2, 1);

        Question currentQuestion = iterator.next();

        Text questionTitle = createText(currentQuestion.getQuestionText());
        grid.add(questionTitle, 0, gridRowIndex++, 2, 1);

        Option[] options = TestFacade.retrieveAllOptions(currentQuestion);

        ListView<String> list = createAndFillWithTrimmedValues(options);
        grid.add(list, 0, gridRowIndex++);

        if(!iterator.hasNext()) {
            Button finishBtn = createButtonWithTitle("Finish");
            finishBtn.setOnMouseClicked(onFinish -> {
                showStudentPanel(true);
            });
            grid.add(createHboxAndAppendButtons(finishBtn), 1, gridRowIndex);
        } else {
            Button okBtn = createButtonWithTitle("Answer");
            okBtn.setOnMouseClicked(userConfirm -> {
                int index = list.getSelectionModel().getSelectedIndex();
                if(index >= 0) {
                    for (int i = 0; i < options.length; i++) {
                        if(options[i].isCorrect()) {
                            if (i == index) {
                                result += options[i].getAddPoints();
                            } else {
                                result -= options[i].getRemovePoints();
                            }
                        } else {
                            if (i == index) {
                                result -= options[i].getRemovePoints();
                            } else {
                                result += options[i].getAddPoints();
                            }
                        }
                    }
                    previousPane.setCenter(startTest(previousPane, previousGrid, selectedRecord, iterator));
                }
            });
            grid.add(createHboxAndAppendButtons(okBtn), 1, gridRowIndex);
        }

        return grid;
    }

}
