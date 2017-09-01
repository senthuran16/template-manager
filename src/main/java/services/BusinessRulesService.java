package services;

import core.BusinessRule;
import core.Property;
import core.RuleTemplate;
import core.TemplateGroup;

import java.util.Collection;
import java.util.Map;

public interface BusinessRulesService {

    /**
     * Creates a Business Rule instance from the specifications of the given Business Rule
     * and Deploys the Templates belonging to the Business Rule
     *
     * @param businessRule
     */
    void createBusinessRuleFromTemplate(BusinessRule businessRule);

    /**
     * Overwrites the Business Rule which has the given UUID, with the given Business Rule
     * and Updates the deployed Templates belonging to the Business Rule
     *
     * @param uuid UUID of the saved Business Rule definition
     * @param businessRule
     */
    void editBusinessRule(String uuid, BusinessRule businessRule);

    /**
     * Deletes the Business Rule that has the given UUID
     * and Undeploys the Templates belonging to the Business Rule
     *
     * @param uuid UUID of the saved Business Rule definition
     */
    void deleteBusinessRule(String uuid);

    /**
     * Deploys the Templates belonging to the given BusinessRule, that is denoted by the given UUID
     *
     * @param businessRule
     */
    void deployTemplates(BusinessRule businessRule);
}
