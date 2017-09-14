package internal.bean.businessRulesFromTemplate;

import java.util.ArrayList;

/**
 * Represents a Property, that is a templated element of any template,
 * which belongs to a Rule Template
 * (Eg: 'fieldName' in a SiddhiApp template)
 */
public class RuleTemplateProperty {
    private String name; //todo:
    private String description; // Optional
    private String defaultValue;
    private String type; //todo: what are the types
    private ArrayList<String> options; // Only for type 'Options'

    public RuleTemplateProperty(String name, String description, String defaultValue, String type, ArrayList<String> options) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        this.type = type;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "RuleTemplateProperty{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", type='" + type + '\'' +
                ", options=" + options +
                '}';
    }
}
