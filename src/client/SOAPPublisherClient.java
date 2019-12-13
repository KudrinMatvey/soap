package client;

import server.entity.Record;
import server.service.DbService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class SOAPPublisherClient {
    public static void main(String[] args) throws MalformedURLException {
//        URL wsdlURL = new URL("http://localhost:8888/ws/student?wsdl");
//        //check above URL in browser, you should see WSDL file
//        //creating QName using targetNamespace and name
//        QName qname = new QName("http://service.server/", "StudentServiceImplService");
//        Service service = Service.create(wsdlURL, qname);
//        //We need to pass interface and model beans to client
//        StudentService studentService = service.getPort(StudentService.class);
//        Student p1 = new Student(); p1.setName("Jacob"); p1.setId(1); p1.setAge(30);
//        Student p2 = new Student(); p2.setName("Anna"); p2.setId(2); p2.setAge(25);
//        //add person
//        System.out.println("Add Person Status=" + studentService.addStudent(p1));
//        System.out.println("Add Person Status=" + studentService.addStudent(p2));
//        //get person
//        System.out.println(studentService.getStudent(1));
//        //get all persons
//        System.out.println(Arrays.asList(studentService.getAllStudents()));
//
//

        URL testServiceURL = new URL("http://localhost:8888/db");
        QName name = new QName("http://service.server/", "DbServiceImplService");
        Service testConnectService = Service.create(testServiceURL, name);
        DbService dbService = testConnectService.getPort(DbService.class);
        Record record = new Record("TEST");

        System.out.println("Record add: " + dbService.addRecord(record));

        Record[] list = dbService.getAllRecords();
        for (Record t : list) {
            System.out.println(t.toString());
        }

    }
}
