package services;

import core.BusinessRule;
import core.Property;
import core.RuleTemplate;
import core.TemplateGroup;

import java.util.Collection;

public interface BusinessRulesService {
    /**
     * Lists available Template Groups, from the directory
     *
     * @return available Template Groups
     */
    Collection<TemplateGroup> listTemplateGroups();

    /**
     * Lists available Rule Templates, that belong to the given Template Group
     *
     * @param templateGroupName Given Template Group name
     * @return Collection of Rule Templates
     */
    Collection<RuleTemplate> listRuleTemplates(String templateGroupName);

    /**
     * Lists Properties of a given Rule Template
     *
     * @param ruleTemplateName Given Rule Template name
     * @return Properties of the given Rule Template
     */
    Collection<Property> listProperties(String ruleTemplateName);

    /**
     * Finds the specified RuleTemplate
     * Derives Templates by replacing templated elements with given values
     * Deploys Templates in corresponding
     * Saves provided values map to the database
     *
     * @param businessRule Given BusinessRule object, which has RuleTemplate name and provided values
     */
    void createbusinessRuleFromTemplate(BusinessRule businessRule);

    /**
     * Returns available BusinessRules
     *
     * @return Available Business Rules
     */
    Collection<BusinessRule> listBusinessRules();

    /**
     * Finds the specified RuleTemplate
     * Derive Templates by replacing templated elements with newly given values
     * Deploys Templates in corresponding formats
     * Updates existing values map in the database, with the new one
     *
     * @param businessRule Given BusinessRule object, which has RuleTemplate name and newly provided values
     */
    void editBusinessRule(BusinessRule businessRule);


    /**
     * Deletes the given values map from the database
     * Undeploy the templates
     *
     * @param businessRule
     */
    void deleteBusinessRule(BusinessRule businessRule);
}
