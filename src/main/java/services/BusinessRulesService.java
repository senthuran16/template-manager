package services;

import root.*;

import java.util.Collection;


/**
 * Consists of back end methods to handle Business Rules
 */
public interface BusinessRulesService {

    /**
     * Saves the given Business Rule to the directory, as a Siddhi App
     * @param businessRule Given Business Rule
     */
    public void addBusinessRule(BusinessRule businessRule);

    /**
     * Deletes the Business Rule with the given name, from the directory
     * @param businessRuleName Given Name of Business Rule
     * @return Deleted Business Rule object
     */
    public BusinessRule deleteBusinessRule(String businessRuleName);

    /**
     * Overwrites the existing Business Rule, that has the same name as the given Business Rule
     * @param businessRule Given Business Rule
     */
    public void editBusinessRule(BusinessRule businessRule);

    /**
     * Returns a list of available Business Rules, from the directory
     * @return
     */
    public Collection<BusinessRule> listBusinessRules();
}
