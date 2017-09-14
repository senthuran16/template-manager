package internal.services;

import internal.bean.BusinessRuleFromTemplate;

public interface BusinessRulesService {

    /**
     * Creates a Business Rule instance from the specifications of the given Business Rule
     * and Deploys the Templates belonging to the Business Rule
     *
     * @param businessRuleFromTemplate
     */
    void createBusinessRuleFromTemplate(BusinessRuleFromTemplate businessRuleFromTemplate);

    /**
     * Overwrites the Business Rule which has the given UUID, with the given Business Rule
     * and Updates the deployed Templates belonging to the Business Rule
     *
     * @param uuid UUID of the saved Business Rule definition
     * @param businessRuleFromTemplate
     */
    void editBusinessRule(String uuid, BusinessRuleFromTemplate businessRuleFromTemplate);

    /**
     * Deletes the Business Rule that has the given UUID
     * and Undeploys the Templates belonging to the Business Rule
     *
     * @param uuid UUID of the saved Business Rule definition
     */
    void deleteBusinessRule(String uuid);

    /**
     * Deploys the Templates belonging to the given BusinessRuleFromTemplate, that is denoted by the given UUID
     *
     * @param businessRule
     */
    void deployTemplates(BusinessRuleFromTemplate businessRule);
}
