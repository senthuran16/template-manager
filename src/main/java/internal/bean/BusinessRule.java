package internal.bean;

import java.util.Map;

/**
 * Represents of a Business Rule
 */
public class BusinessRule {
    private String uuid;
    private String name;
    private String templateGroupName;
    private String ruleTemplateName;
    private String type; // "template" or "fromScratch"
    private Map<String, String> properties;

    public BusinessRule(String uuid, String name, String templateGroupName, String ruleTemplateName, String type, Map<String, String> properties) {
        this.uuid = uuid;
        this.name = name;
        this.templateGroupName = templateGroupName;
        this.ruleTemplateName = ruleTemplateName;
        this.type = type;
        this.properties = properties;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateGroupName() {
        return templateGroupName;
    }

    public void setTemplateGroupName(String templateGroupName) {
        this.templateGroupName = templateGroupName;
    }

    public String getRuleTemplateName() {
        return ruleTemplateName;
    }

    public void setRuleTemplateName(String ruleTemplateName) {
        this.ruleTemplateName = ruleTemplateName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "BusinessRule{" +
                "\nuuid='" + uuid + '\'' +
                ",\n name='" + name + '\'' +
                ",\n templateGroupName='" + templateGroupName + '\'' +
                ",\n ruleTemplateName='" + ruleTemplateName + '\'' +
                ",\n type='" + type + '\'' +
                ",\n properties=" + properties +
                "\n}";
    }
}
