package services;

import core.*;

import java.util.Collection;

/**
 * Exposed root.Template Manager service, that handles Templates and Business Rules
 */
public class TemplateManagerService implements TemplateManager, BusinessRulesService{
    // todo: implement all methods

    /**
     * Saves the given Business Rule to the directory, as a Siddhi App
     *
     * @param businessRule Given Business Rule
     */
    public void addBusinessRule(BusinessRule businessRule) {

    }

    /**
     * Deletes the Business Rule with the given name, from the directory
     *
     * @param businessRuleName Given Name of Business Rule
     * @return Deleted Business Rule object
     */
    public BusinessRule deleteBusinessRule(String businessRuleName) {
        return null;
    }

    /**
     * Overwrites the existing Business Rule, that has the same name as the given Business Rule
     *
     * @param businessRule Given Business Rule
     */
    public void editBusinessRule(BusinessRule businessRule) {

    }

    /**
     * Returns a list of available Business Rules, from the directory
     *
     * @return
     */
    public Collection<BusinessRule> listBusinessRules() {
        return null;
    }

    /**
     * Saves the given root.Template to the directory
     *
     * @param template Given root.Template
     */
    public void addTemplate(Template template) {

    }

    /**
     * Deletes the root.Template with the given name, from the directory
     *
     * @param templateName Given Name of root.Template
     * @return Deleted root.Template object
     */
    public Template deleteTemplate(String templateName) {
        return null;
    }

    /**
     * Returns available Templates, from the directory
     *
     * @return
     */
    public Collection<Template> listTemplates() {
        return null;
    }
}
