package server;

import server.service.StudentServiceImpl;
import server.service.DbServiceImpl;

import javax.xml.ws.Endpoint;

public class SOAPPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/ws/student", new StudentServiceImpl());
        Endpoint.publish("http://localhost:8888/test", new DbServiceImpl());
    }
}
