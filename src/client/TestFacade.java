//package client;
//
//import server.entity.Record;
//import server.service.DbService;
//
//import javax.xml.namespace.QName;
//import javax.xml.ws.Service;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.stream.Stream;
//
//public final class TestFacade {
//
//    private static DbService service;
//
//    static {
//        try {
//            URL testServiceURL = new URL("http://localhost:8888/test?wsdl");
//            QName name = new QName("http://service.server/", "TestServiceImplService");
//            Service testConnectService = Service.create(testServiceURL, name);
//            service = testConnectService.getPort(DbService.class);
//        } catch (Throwable e) {
//            System.out.println("Error when trying to connect server" + e.getMessage());
//        }
//    }
//
//    public static void addTest(Record record) {
//        service.addRecord(record);
//    }
//
//    public static void createAndAddTest(String name) {
//        Record record = new Record(name);
//        service.addRecord(record);
//    }
//
//    public static Record[] getAllTests() {
//        return service.getAllRecords();
//    }
//
//    public static ArrayList<String> retrieveAllTestNames() {
//        ArrayList<String> result = new ArrayList<>();
//        Stream<Record> stream = Arrays.stream(service.getAllRecords());
//        stream.forEach(test -> result.add(test.getName()));
//        return result;
//    }
//
//    public static Record getTestByName(String name) {
//        return service.getTestByName(name);
//    }
//
//    public static void updateTest(Record record) {
//        service.updateTest(record);
//    }
//
//    public static Question[] getQuestions(Record record) {
//        return service.getQuestions(record.getName());
//    }
//
//    public static void addQuestions(Record record, Question[] questions) {
//        service.addQuestions(questions, record.getName());
//    }
//
//    public static void addQuestion(Record record, Question question) {
//        service.addQuestion(question, record.getName());
//    }
//
//    public static void addOption(Question question, Option option) {
//        service.addOption(question, option);
//    }
//
//    public static Option[] retrieveAllOptions(Question question) {
//        return service.retrieveAllOptions(question);
//    }
//}
