package core;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a RuleTemplate, which consists of one or more Templates
 */
public class RuleTemplate {
    private String name;
    private String type;
    private String instanceCount; // "one" or "many"
    private String script; // Optional
    private String description; // Optional
    private Collection<Template> templates;
    private Map<String, Property> properties; // Name, Property object

    public RuleTemplate(String name, String type, String instanceCount, String script, String description, Collection<Template> templates, Map<String, Property> properties) {
        this.name = name;
        this.type = type;
        this.instanceCount = instanceCount;
        this.script = script;
        this.description = description;
        this.templates = templates;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "\tRuleTemplate{" +
                "\t\nname='" + name + '\'' +
                ", \t\ntype='" + type + '\'' +
                ", \t\ninstanceCount='" + instanceCount + '\'' +
                ", \t\nscript='" + script + '\'' +
                ", \t\ndescription='" + description + '\'' +
                ", \t\ntemplates=" + templates +
                ", \t\nproperties=" + properties +
                "\t\n}";
    }
}
