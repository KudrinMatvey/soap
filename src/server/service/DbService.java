package server.service;

import server.entity.Option;
import server.entity.Question;
import server.entity.Record;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DbService {
    @WebMethod
    public String addRecord(Record record);

    @WebMethod
    public Record[] getAllTests();

    @WebMethod
    public Record getTest(String id);

    @WebMethod
    public Record getTestByName(String name);

    @WebMethod
    public void addOptions(String testId, String questionId, Option [] options);

    @WebMethod
    public void updateTest (Record record);

    @WebMethod
    public Question[] getQuestions(String testName);

    @WebMethod
    public void addQuestions(Question[] questions, String testName);

    @WebMethod
    public void addQuestion(Question question, String testName);

    @WebMethod
    public void addOption(Question question, Option option);

    @WebMethod
    public Option[] retrieveAllOptions(Question question);
}
