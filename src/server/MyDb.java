package server;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService()
public class MyDb {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  @WebMethod
  public String sayHelloTo (@WebParam(name="text") String text) {
      System.out.println("SayHelloTo invoked " + text);
      return text;
  }
  public static void main(String[] argv) {
    Object implementor = new MyDb();
    String address = "http://localhost:9000/MyDb";
    Endpoint.publish(address, implementor);
  }
}
