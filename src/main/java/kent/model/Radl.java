package kent.model;

public class Radl {
    private String content;

    public Radl(String content) {
        this.content = content;
    }

    public Radl() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Radl{" +
                "content='" + content + '\'' +
                '}';
    }
}
