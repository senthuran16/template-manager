package internal.services;

import internal.bean.businessRulesFromTemplate.BusinessRuleFromTemplate;
import internal.bean.businessRulesFromScratch.BusinessRuleFromScratch;

public interface BusinessRulesService extends BusinessRulesFromScratch,BusinessRulesFromTemplate{
    /**
     * Deletes the Business Rule that has the given UUID
     * and Undeploys the Templates belonging to the Business Rule
     *
     * @param uuid UUID of the saved Business Rule definition
     */
    void deleteBusinessRule(String uuid);
}
