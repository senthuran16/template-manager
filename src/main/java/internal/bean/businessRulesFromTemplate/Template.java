package internal.bean.businessRulesFromTemplate;

/**
 * Represents a Templated item
 * i.e : SiddhiApp (Not considering Gadget & Dashboard for now)
 */
public class Template {
    private String type;
    private String content;
    private String exposedStreamDefinition;

    public Template(String type, String content, String exposedStreamDefinition) {
        this.type = type;
        this.content = content;
        this.exposedStreamDefinition = exposedStreamDefinition;
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

    public String getExposedStreamDefinition() {
        return exposedStreamDefinition;
    }

    public void setExposedStreamDefinition(String exposedStreamDefinition) {
        this.exposedStreamDefinition = exposedStreamDefinition;
    }

    @Override
    public String toString() {
        return "Template{" +
                "\ntype='" + type + '\'' +
                ",\n content='" + content + '\'' +
                ",\n exposedStreamDefinition='" + exposedStreamDefinition + '\'' +
                "\n}";
    }
}
