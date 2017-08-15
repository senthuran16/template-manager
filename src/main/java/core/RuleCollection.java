package core;

import java.util.Collection;

/**
 * Represents a Rule Collection, which consists of one or more RuleTemplates
 */
public class RuleCollection {
    public String name;
    public Collection<RuleTemplate> ruleTemplates;

    public RuleCollection(String name, Collection<RuleTemplate> ruleTemplates) {
        this.name = name;
        this.ruleTemplates = ruleTemplates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<RuleTemplate> getRuleTemplates() {
        return ruleTemplates;
    }

    public void setRuleTemplates(Collection<RuleTemplate> ruleTemplates) {
        this.ruleTemplates = ruleTemplates;
    }

    @Override
    public String toString() {
        return "RuleCollection{" +
                "name='" + name + '\'' +
                ", ruleTemplates=" + ruleTemplates +
                '}';
    }
}
