package server.service;

import server.entity.Mark;
import server.entity.Record;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@WebService(endpointInterface = "server.service.DbService")
public class DbServiceImpl implements DbService {
    private static Map<String, Record> map = new ConcurrentHashMap<>();
    private static Map<String, Mark> markMap = new ConcurrentHashMap<>();
    private static int count = 0;

    public DbServiceImpl() {
//        for (int i = 0; i < 10; i++) {
//            map.put(String.valueOf(i), new Record(String.valueOf(i), String.valueOf(i)));
//            markMap.put(String.valueOf(i), new Mark(String.valueOf(i), String.valueOf(i)));
//            Mark[] m = new Mark[1];
//            m[0] = markMap.get(String.valueOf(i));
//            map.get(String.valueOf(i)).addMarks(m);
//        }
    }

    @Override
    public String addRecord(Record record) {
        String currentIndex = String.valueOf(count++);
        record.setId(currentIndex);
        map.put(currentIndex, record);
        return currentIndex;
    }

    @Override
    public void addMarkToRecord(String recordId, String[] markIds) {
        Record r = map.get(recordId);
        List<Mark> mark = markMap.values().stream()
                .filter(stringMarkEntry -> stringMarkEntry.includedIn(markIds))
                .collect(Collectors.toList());
        if (r != null && !mark.isEmpty()) {
            r.addMarks(mark.toArray(new Mark[0]));
        }
    }

    @Override
    public void addFileToRecord(String recordId, String fileName, byte[] file) {
        Record r = map.get(recordId);
        if (r != null) {
            r.getFilesMap().put(fileName, file);
        } else throw new UnsupportedOperationException();
    }

    @Override
    public String addMarkToSystem(String name) {
        markMap.put(String.valueOf(count++), new Mark(String.valueOf(count), name));
        return String.valueOf(count++);
    }

    @Override
    public byte[] getFile(String recordId, String fileName) {
        Record r = map.get(recordId);
        if (r != null) {
            return r.getFilesMap().containsKey(fileName) ? r.getFilesMap().get(fileName) : new byte[0];
        }
        return new byte[0];
    }

    @Override
    public void removeMark(String markId) {
        if (markMap.remove(markId) != null)
            map.values().forEach(record -> record.removeMark(markId));
    }

    @Override
    public Mark[] getAllMarks() {
        int n = markMap.size();
        Mark[] m = new Mark[n];
        int i = 0;
        for (Mark value : markMap.values()) {
            m[i++] = value;
        }
        return m;
    }

    @Override
    public Record[] getAllRecords() {
        Record[] result = new Record[map.size()];
        int i = 0;
        for (Record value : map.values()) {
            result[i++] = new Record(value.getId(), value.getName());
        }
        return result;
    }

    @Override
    public Record[] getRecordsByMark(String markId) {
        Map<String, Record> m = map.entrySet().stream()
                .filter(stringRecordEntry -> stringRecordEntry.getValue().containsMark(markId))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
        Record[] result = new Record[m.size()];
        int i = 0;
        for (Record value : m.values()) {
            result[i++] = value;
        }
        return result;
    }
}
