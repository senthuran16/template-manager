package services;

import core.BusinessRule;
import core.Property;
import core.RuleTemplate;
import core.Template;
import core.TemplateGroup;
import core.TemplateManagerException;
import core.TemplateManagerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Map;

public class TemplateManagerService implements BusinessRulesService {
    private static final Log log = LogFactory.getLog(TemplateManagerService.class);
    // Available Template Groups from the directory
    private Map<String, TemplateGroup> availableTemplateGroups;
    private Map<String, BusinessRule> availableBusinessRules;

    public TemplateManagerService() {
        // Load & store available Template Groups & Business Rules at the time of instantiation
        this.availableTemplateGroups = loadTemplateGroups();
        this.availableBusinessRules = loadBusinessRules();
    }

    public void createBusinessRuleFromTemplate(BusinessRule businessRule) {
        Map<String, Template> derivedTemplates = deriveTemplates(businessRule);
        String businessRuleUUID = TemplateManagerHelper.generateUUID(businessRule);
        try {
            saveBusinessRuleDefinition(businessRuleUUID, businessRule);
            // No deployment if saving is failure
            // Deploy each Template with UUID
            for (String templateUUID : derivedTemplates.keySet()) {
                deployTemplate(templateUUID, derivedTemplates.get(templateUUID));
            }
        } catch (TemplateManagerException e) {
            // Problems in saving
            e.printStackTrace();
        }
    }

    public void editBusinessRule(String uuid, BusinessRule businessRule) {
        Map<String, Template> derivedTemplates = deriveTemplates(businessRule);
        try {
            overwriteBusinessRuleDefinition(uuid, businessRule);
            // No deployment if overwriting is failure todo: is this ok?
            for (String templateUUID : derivedTemplates.keySet()) {
                //todo: [UPDATE] UUID. NOT THAT EASY!
            }
        } catch (TemplateManagerException e) {
            // todo: when no uuid is found, SAVE rather than OVERWRITING
            e.printStackTrace();
        }
    }

    public void deleteBusinessRule(String uuid) {
        BusinessRule foundBusinessRule = findBusinessRule(uuid);
        Collection<String> templateUUIDs = getTemplateUUIDs(foundBusinessRule);

        for (String templateUUID : templateUUIDs) {
            try {
                undeployTemplate(templateUUID);
                removeBusinessRuleDefinition(uuid);
            } catch (TemplateManagerException e) {
                e.printStackTrace(); //todo: handle
            }
        }
    }

    public void deployTemplates(BusinessRule businessRule) {
        Map<String,Template> derivedTemplates = deriveTemplates(businessRule);
        for (String templateUUID : derivedTemplates.keySet()) {
            try {
                deployTemplate(templateUUID, derivedTemplates.get(templateUUID));
            } catch (TemplateManagerException e) {
                e.printStackTrace(); //todo: handle
            }
        }
    }

    /**
     * Loads and returns available Template Groups from the directory
     *
     * @return
     */
    public Map<String, TemplateGroup> loadTemplateGroups() {
        return null; //todo: implement
    }

    /**
     * Loads and returns available Business Rules from the database
     *
     * @return
     */
    public Map<String, BusinessRule> loadBusinessRules() {
        return null; //todo: implement
    }


    /**
     * Returns available Template Group objects, denoted by UUIDs
     *
     * @return
     */
    public Map<String, TemplateGroup> getTemplateGroups() {
        return null; //todo: implement
    }

    /**
     * Returns available Business Rule objects, denoted by UUIDs
     *
     * @return
     */
    public Map<String, BusinessRule> getBusinessRules() {
        return null; //todo: implement
    }

    /**
     * Returns RuleTemplate objects belonging to the given Template Group, denoted by UUIDs
     *
     * @param templateGroupName Template Group name
     * @return
     */
    public Map<String, RuleTemplate> getRuleTemplates(String templateGroupName) {
        return null; //todo: implement
    }

    /**
     * Returns Template objects from the given Rule Template, denoted by UUIDs
     *
     * @param ruleTemplateName
     * @return
     */
    public Map<String, Property> getProperties(String ruleTemplateName) {
        return null; //todo: implement
    }

    /**
     * Derives and returns templates from the given BusinessRule
     * RuleTemplate is found, and the templated properties are replaced with the given properties map
     * as specified in the Business Rule object
     *
     * @param businessRule
     * @return
     */
    public Map<String, Template> deriveTemplates(BusinessRule businessRule) {
        return null; //todo: implement
    }

    /**
     * Gets the Rule Template, that is specified in the given Business Rule
     *
     * @param businessRule
     * @return
     */
    public RuleTemplate getRuleTemplate(BusinessRule businessRule) {
        return null; //todo: implement
    }

    /**
     * Saves JSON definition of the given Business Rule, to the database
     *
     * @param businessRule
     * @throws TemplateManagerException
     */
    public void saveBusinessRuleDefinition(String uuid, BusinessRule businessRule) throws TemplateManagerException {
        //todo: implement
    }

    /**
     * Deploys the given Template
     *
     * @param template
     * @throws TemplateManagerException
     */
    public void deployTemplate(String uuid, Template template) throws TemplateManagerException {
        // todo: implement
    }

    /**
     * Deploys the given Template, of type SiddhiApp
     *
     * @param siddhiApp
     * @throws TemplateManagerException
     */
    public void deploySiddhiApp(String uuid, Template siddhiApp) throws TemplateManagerException {
        // todo: implement
    }

    /**
     * Gets properties that are specified in the BusinessRule, with entered values as default values
     *
     * @param businessRule
     * @return
     */
    public Collection<Property> getProperties(BusinessRule businessRule) {
        return null; //todo: implement
    }

    /**
     * Overwrites JSON definition of the Business Rule that has the given id,
     * with the given Business Rule
     *
     * @param uuid
     * @param businessRule
     * @throws TemplateManagerException
     */
    public void overwriteBusinessRuleDefinition(String uuid, BusinessRule businessRule) throws TemplateManagerException {
        //todo: implement
    }

    /**
     * Updates the deployment of the given Template
     *
     * @param template
     * @throws TemplateManagerException
     */
    public void updateDeployTemplate(String uuid, Template template) throws TemplateManagerException {
        // todo: implement
    }

    /**
     * Updates the deployment of the given Template, of type SiddhiApp
     *
     * @param siddhiApp
     * @throws TemplateManagerException
     */
    public void updateDeploySiddhiApp(String uuid, Template siddhiApp) throws TemplateManagerException {
        // todo: implement
    }

    /**
     * Gets UUIDs of the Templates, that belong to the given BusinessRule
     *
     * @param businessRule
     * @return
     */
    public Collection<String> getTemplateUUIDs(BusinessRule businessRule) {
        return null; //todo: implement
    }

    /**
     * Undeploys the Template with the given UUID
     *
     * @param uuid
     * @throws TemplateManagerException
     */
    public void undeployTemplate(String uuid) throws TemplateManagerException {

    }

    /**
     * Undeploys the Template of type SiddhiApp, with the given UUID
     *
     * @param uuid
     * @throws TemplateManagerException
     */
    public void undeploySiddhiApp(String uuid) throws TemplateManagerException {

    }

    /**
     * Deletes the JSON definition of the Business Rule, that has the given UUID
     *
     * @param uuid
     * @throws TemplateManagerException
     */
    public void removeBusinessRuleDefinition(String uuid) throws TemplateManagerException {

    }

    //////////// insert anything on top of this //////////

    /**
     * Finds the Template Group with the given name
     *
     * @param templateGroupUUID
     * @return
     */
    public TemplateGroup findTemplateGroup(String templateGroupUUID) {
        return null; //todo: implement
    }

    /**
     * Finds the Business Rule with the given name
     *
     * @param businessRuleUUID
     * @return
     */
    public BusinessRule findBusinessRule(String businessRuleUUID) {
        return null; //todo: implement
    }
}
