package internal.bean.businessRulesFromTemplate;

/**
 * Represents a Templated item
 * i.e : SiddhiApp (Not considering Gadget & Dashboard for now)
 */
public class Template {
    private String type;
    private String content;

    public Template(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Template{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
