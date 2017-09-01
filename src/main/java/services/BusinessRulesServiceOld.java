package services;

import core.BusinessRule;
import core.RuleTemplate;
import core.TemplateManagerException;

import java.util.Collection;
import java.util.Map;


/**
 * Consists of back end methods to handle Business Rules
 */
public interface BusinessRulesServiceOld {

    /**
     * Saves the given Business Rule to the directory as a JSON file, and saves its SiddhiApps //todo: rather than deploying only siddhi apps, also save the BR as JSON?
     *
     * @param businessRule Given Business Rule Object
     * @param fileName     Given File Name
     */
    public void addBusinessRule(BusinessRule businessRule, String fileName) throws TemplateManagerException;

    /**
     * Saves each SiddhiApp from a given Business Rule, to the directory as *.siddhi files
     *
     * @param businessRule Given Business Rule Object
     */
    public void addSiddhiApps(BusinessRule businessRule) throws TemplateManagerException;

    /**
     * Deletes each SiddhiApp from a given BusinessRule, from the directory
     *
     * @param businessRule Given Business Rule Object
     * @return Collection of undeleted siddhiApp names if any. Otherwise null
     * @throws TemplateManagerException
     */
    public Collection<String> deleteSiddhiApps(BusinessRule businessRule) throws TemplateManagerException;

    /**
     * Deletes the Business Rule with the given name, from the directory
     *
     * @param businessRuleName Given Name of Business Rule
     * @return Deleted Business Rule object
     */
    public BusinessRule deleteBusinessRule(String businessRuleName) throws TemplateManagerException;

    /**
     * Overwrites the existing Business Rule, that has the same name as the given Business Rule
     *
     * @param businessRule Given Business Rule Object
     */
    public void editBusinessRule(BusinessRule businessRule) throws TemplateManagerException;

    /**
     * Returns a list of available Business Rules, from the directory
     *
     * @return List of Business Rule names, and denoting BusinessRule objects
     */
    public Map<String, BusinessRule> listBusinessRules();

    /**
     * Returns a List of available RuleTemplates, denoted by their TemplateGroup names
     *
     * @return TemplateGroup names, denoting RuleTemplates available under that
     */
    public Map<String, Collection<RuleTemplate>> listTemplates();

}
