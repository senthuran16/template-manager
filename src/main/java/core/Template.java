package core;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a Templated item
 * Eg: SiddhiApp, //todo: Gadget & Dashboard
 */
public class Template {
    private String type;
    private String content;

    public Template(String type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "\t\tTemplate{" +
                "\t\t\ntype='" + type + '\'' +
                ", \t\t\ncontent='" + content + '\'' +
                "\t\t\n}";
    }
}
