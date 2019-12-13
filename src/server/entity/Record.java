package server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//  todo add marks does not work
// add file npe on not selected
public class Record implements Serializable, Printable {
    private String id;
    private String name;
    private Map<String, byte[]> filesMap = new HashMap<>();
    private List<Mark> marks = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Record() {

    }

    public Record(String name) {
        this.name = name;
    }
    public Record(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, byte[]> getFilesMap() {
        return filesMap;
    }

    public void setFilesMap(Map<String, byte[]> filesMap) {
        this.filesMap = filesMap;
    }

    @Override
    public String getText() {
        return name;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getTrimmedText() {
        return name;
    }

    public void addMarks(Mark[] mark) {
        for (int i = 0; i < mark.length; i++) {
            marks.add(mark[i]);
            // todo does it serilize
//            throw new RuntimeException();
        }
    }

    public boolean containsMark(String id) {
        return marks.stream().anyMatch(m -> m.getId().equals(id));
    }

    public void removeMark(String markId) {
        marks = marks.stream().filter(mark -> !mark.getId().equals(markId)).collect(Collectors.toList());
    }
}
