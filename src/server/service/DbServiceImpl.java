package server.service;

import server.entity.Option;
import server.entity.Question;
import server.entity.Record;

import javax.jws.WebService;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebService(endpointInterface = "server.service.DbService")
public class DbServiceImpl implements DbService {
    private static Map<String, Record> testMap = new ConcurrentHashMap<>();
    private static int count = 0;

    @Override
    public String addRecord(Record record) {
        String currentIndex = String.valueOf(count++);
        testMap.put(currentIndex, record);
        return currentIndex;
    }

    @Override
    public Record[] getAllTests() {
        Record[] result = new Record[testMap.size()];
        int i = 0;
        for (Record value : testMap.values()) {
            System.out.println(value);
            result[i++] = value;
        }
        System.out.println();
        return result;
    }

    @Override
    public Record getTest(String id) {
        return testMap.get(id);
    }

    @Override
    public Record getTestByName(String name) {
        for (Record record : testMap.values()) {
            if (record.getName().equals(name)) {
                return record;
            }
        }
        return null;
    }

    @Override
    public Question[] getQuestions(String testName) {
        Record existingRecord = getTestByName(testName);
        System.out.println("Fetched test " + existingRecord);
        Question[] returnQuestions =  getTestByName(testName).getAllQuestions();
        for (Question question : returnQuestions) {
            if(question != null) {
                System.out.println("Existing question for " + testName + " " + question.getQuestionText());
            }
        }
        System.out.println();
        return returnQuestions;
    }

    @Override
    public void updateTest(Record record) {
        getTestByName(record.getName()).addQuestions(Arrays.asList(record.getAllQuestions()));
    }

    @Override
    public void addOptions(String testId, String questionId, Option [] options) {
        testMap.get(testId).getQuestion(questionId).addOptions(options);
    }

    @Override
    public void addQuestions(Question[] questions, String testName) {
        getTestByName(testName).addQuestions(Arrays.asList(questions));
    }

    @Override
    public void addQuestion(Question question, String testName) {
        getTestByName(testName).addQuestion(question);
    }

    @Override
    public void addOption(Question question, Option option) {
        getTestByName(question.getTestId()).getQuestion(question.getQuestionText()).addOption(option);
    }

    @Override
    public Option[] retrieveAllOptions(Question question) {
        return getTestByName(question.getTestId()).getQuestion(question.getQuestionText()).getOptions();
    }
}
