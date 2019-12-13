package server.entity;

public class Mark {
    private String id;
    private String Text;

    public Mark(String id, String text) {
        this.id = id;
        Text = text;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id='" + id + '\'' +
                ", Text='" + Text + '\'' +
                '}';
    }

    public Mark() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public boolean includedIn(String[] markIds) {
        for (int i = 0; i < markIds.length; i++) {
            if (markIds[i] == id) return true;
        }
        return false;
    }
}
