package internal.services;

import internal.bean.BusinessRule;
import internal.exceptions.TemplateManagerException;
import internal.services.businessRulesFromScratch.BusinessRulesFromScratch;
import internal.services.businessRulesFromTemplate.BusinessRulesFromTemplate;

public interface BusinessRulesService extends BusinessRulesFromScratch, BusinessRulesFromTemplate {
    /**
     * Gives the Business Rule from Template instance that has the given UUID
     *
     * @param businessRuleUUID
     * @return
     * @throws TemplateManagerException
     */
    BusinessRule findBusinessRuleFromTemplate(String businessRuleUUID) throws TemplateManagerException;

    /**
     * Deletes the Business Rule that has the given UUID
     * and Undeploys the Templates belonging to the Business Rule
     *
     * @param uuid UUID of the saved Business Rule definition
     */
    void deleteBusinessRule(String uuid);
}
