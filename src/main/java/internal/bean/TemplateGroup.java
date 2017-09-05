package internal.bean;

import java.util.Collection;

/**
 * Represents a Template Group, which consists of one or more RuleTemplates
 */
public class TemplateGroup {
    private String name;
    private String description;
    private Collection<RuleTemplate> ruleTemplates;

    public TemplateGroup(String name, String description, Collection<RuleTemplate> ruleTemplates) {
        this.name = name;
        this.description = description;
        this.ruleTemplates = ruleTemplates;
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

    public Collection<RuleTemplate> getRuleTemplates() {
        return ruleTemplates;
    }

    public void setRuleTemplates(Collection<RuleTemplate> ruleTemplates) {
        this.ruleTemplates = ruleTemplates;
    }

    @Override
    public String toString() {
        return "TemplateGroup{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ruleTemplates=" + ruleTemplates +
                '}';
    }
}
