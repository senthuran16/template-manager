package internal.bean.businessRulesFromTemplate;

import internal.bean.BusinessRule;

import java.util.Map;

/**
 * Represents a Business Rule, created from Template
 */
public class BusinessRuleFromTemplate extends BusinessRule {
    private String ruleTemplateName;
    private Map<String, String> properties;

    public BusinessRuleFromTemplate(String uuid, String name, String templateGroupName, String type, String ruleTemplateName, Map<String, String> properties) {
        super(uuid, name, templateGroupName, type);
        this.ruleTemplateName = ruleTemplateName;
        this.properties = properties;
    }

    public String getRuleTemplateName() {
        return ruleTemplateName;
    }

    public void setRuleTemplateName(String ruleTemplateName) {
        this.ruleTemplateName = ruleTemplateName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "BusinessRuleFromTemplate{" +
                "uuid='" + super.getUuid() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", templateGroupName='" + super.getTemplateGroupName() + '\'' +
                ", type='" + super.getType() + '\'' +
                "ruleTemplateName='" + ruleTemplateName + '\'' +
                ", properties=" + properties +
                '}';
    }
}
