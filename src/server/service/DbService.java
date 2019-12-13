package server.service;

import server.entity.Mark;
import server.entity.Record;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DbService {
    @WebMethod
    public String addRecord(Record record);

    @WebMethod
    void addMarkToRecord(String recordId, String[] markIds);

    @WebMethod
    public void addFileToRecord(String recordId, String fileName, byte[] file);

    @WebMethod
    String addMarkToSystem(String name);

    @WebMethod
    public byte[] getFile(String recordId, String fileName);

    @WebMethod
    public void removeMark(String markId);

    @WebMethod
    public Mark[] getAllMarks();

    @WebMethod
    public Record[] getAllRecords();

    @WebMethod
    public Record[] getRecordsByMark(String markId);
}
