package server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record implements Serializable, Printable {
    private String name;
    private Map<String, byte[]> filesMap = new HashMap<>();
    private Question[] questionTransport = new Question[5];
    private transient int questionsCount = 0;

    public Record() {

    }

    public Record(String name) {
        this.name = name;
    }

    public String addQuestion (Question question) {
        if(questionsCount == questionTransport.length) {
            repack();
        }
        questionTransport[questionsCount++] = question;
        filesMap.put(question.getQuestionText(), question);
        return question.getQuestionText();
    }

    public boolean addQuestions (List<Question> questions) {
        for (Question question : questions) {
            if(questionsCount == this.questionTransport.length) {
                repack();
            }
            this.questionTransport[questionsCount++] = question;
            this.filesMap.put(question.getQuestionText(), question);
        }
        return true;
    }

    private void repack() {
        Question[] newQuestions = new Question[questionTransport.length * 2];
        for (int i = 0; i < questionTransport.length; i++) {
            questionTransport[i] = questionTransport[i];
        }
        questionTransport = newQuestions;
    }

    public String getName() {
        return name;
    }

    public Question getQuestion(String id) {
        return filesMap.get(id);
    }

    public Question[] getAllQuestions() {
        return questionTransport;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Question> getFilesMap() {
        return filesMap;
    }

    public void setFilesMap(Map<String, Question> filesMap) {
        this.filesMap = filesMap;
    }

    public Question[] getQuestionTransport() {
        return questionTransport;
    }

    public void setQuestionTransport(Question[] questionTransport) {
        this.questionTransport = questionTransport;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    @Override
    public String getText() {
        return name;
    }

    @Override
    public String toString() {
        return "Record: " + name + " filesMap: " + questionsCount;
    }

    @Override
    public String getTrimmedText() {
        return name;
    }
}
