package kent.model;

public class Wsdl {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Wsdl(String content) {
        this.content = content;
    }

    public Wsdl() {
    }

    @Override
    public String toString() {
        return "Wsdl{" +
                "content='" + content + '\'' +
                '}';
    }
}
