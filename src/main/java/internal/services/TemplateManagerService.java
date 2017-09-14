package internal.services;

import internal.bean.BusinessRuleFromTemplate;
import internal.bean.RuleTemplateProperty;
import internal.bean.RuleTemplate;
import internal.bean.Template;
import internal.bean.TemplateGroup;
import internal.exceptions.TemplateManagerException;
import internal.util.TemplateManagerConstants;
import internal.util.TemplateManagerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateManagerService implements BusinessRulesService {
    private static final Log log = LogFactory.getLog(TemplateManagerService.class);
    // Available Template Groups from the directory
    private Map<String, TemplateGroup> availableTemplateGroups;
    private Map<String, BusinessRuleFromTemplate> availableBusinessRules;

    public TemplateManagerService() {
        // Load & store available Template Groups & Business Rules at the time of instantiation
        this.availableTemplateGroups = loadTemplateGroups();
        this.availableBusinessRules = loadBusinessRules();
    }

    public static void main(String[] args) {

    }

    public void createBusinessRuleFromTemplate(BusinessRuleFromTemplate businessRuleFromTemplate) { // todo: verify next lower level
        Map<String, Template> derivedTemplates = deriveTemplates(businessRuleFromTemplate);
        String businessRuleUUID = businessRuleFromTemplate.getUuid();
        try {
            saveBusinessRuleDefinition(businessRuleUUID, businessRuleFromTemplate);
            // Deploy templates, only if saving Business Rule definition is successful todo: (Q) is this ok?
            for (String templateUUID : derivedTemplates.keySet()) {
                deployTemplate(templateUUID, derivedTemplates.get(templateUUID));
            }
        } catch (TemplateManagerException e) {
            // Saving definition / deployment is unsuccessful
            log.error(e.getMessage()); //todo: (Q) is this ok?
        }
    }

    public void editBusinessRule(String uuid, BusinessRuleFromTemplate businessRuleFromTemplate) { // todo: verify next lower level
        Map<String, Template> derivedTemplates = deriveTemplates(businessRuleFromTemplate);

        try {
            overwriteBusinessRuleDefinition(uuid, businessRuleFromTemplate);
            // Load all available Business Rules again
            this.availableBusinessRules = loadBusinessRules();
            // Update Deploy templates, only if overwriting Business Rule Definition is successful todo: (Q) is this ok?
            for (String templateUUID : derivedTemplates.keySet()) {
                updateDeployTemplate(templateUUID, derivedTemplates.get(templateUUID));
            }
        } catch (TemplateManagerException e) {
            // Overwriting definition / Update Deploy unsuccessful
            log.error(e.getMessage()); // todo: (Q) is this ok?
        }
    }

    public void deleteBusinessRule(String uuid) { // todo: verify next lower level
        BusinessRuleFromTemplate foundBusinessRule;
        try {
            foundBusinessRule = findBusinessRule(uuid);
        } catch (TemplateManagerException e) {
            // No Business Rule Found
            log.error(e); // todo: (Q) is this ok?
            // No point of further execution
            return;
        }
        Collection<String[]> templateTypesAndUUIDs = getTemplateTypesAndUUIDs(foundBusinessRule);

        // Business Rule completely un-deployed status
        boolean isCompletelyUndeployed = true; // todo: think about having noOfDeployedTemplates field in the db

        for (String[] templateTypeAndUUID : templateTypesAndUUIDs) {
            try {
                undeployTemplate(templateTypeAndUUID[0], templateTypeAndUUID[1]);
            } catch (TemplateManagerException e) {
                isCompletelyUndeployed = false;
                // todo: (Q) what about previously undeployed partially? now the undeployed ones will cause this to be false [noOfDeployedTemplates] might be a solution
                log.error("Failed to un-deploy " + templateTypeAndUUID[0] + " : " + templateTypeAndUUID[1]); // todo: (Q) is this ok?
            }
        }
        // If all Templates are undeployed
        if (isCompletelyUndeployed) {
            try {
                removeBusinessRuleDefinition(uuid);
            } catch (TemplateManagerException e) {
                log.error("Failed to delete Business Rule definition of : " + uuid, e); // todo: (Q) is this ok?
            }
        } else {
            log.error("Failed to un-deploy all the templates. Unable to delete the Business Rule definition of : " + uuid); // todo: (Q) is this ok?
        }
    }

    public void deployTemplates(BusinessRuleFromTemplate businessRuleFromTemplate) {
        Map<String, Template> derivedTemplates = deriveTemplates(businessRuleFromTemplate);
        for (String templateUUID : derivedTemplates.keySet()) {
            try {
                deployTemplate(templateUUID, derivedTemplates.get(templateUUID));
            } catch (TemplateManagerException e) {
                log.error("Failed to deploy " + derivedTemplates.get(templateUUID).getType() + " : " + templateUUID, e);
            }
        }
    }

    /**
     * Loads and returns available Template Groups from the directory
     *
     * @return
     */
    public Map<String, TemplateGroup> loadTemplateGroups() {
        File directory = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY);
        // To store UUID and Template Group object
        Map<String, TemplateGroup> templateGroups = new HashMap();

        // Files from the directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (final File fileEntry : files) {
                // If file is a valid json file
                if (fileEntry.isFile() && fileEntry.getName().endsWith("json")) {
                    // convert and store
                    TemplateGroup templateGroup = TemplateManagerHelper.jsonToTemplateGroup(TemplateManagerHelper.fileToJson(fileEntry));
                    if (templateGroup != null) {
                        try {
                            TemplateManagerHelper.validateTemplateGroup(templateGroup);
                        } catch (TemplateManagerException e) {
                            // Invalid Template Configuration file is found
                            // Abort loading the current file and continue with the remaining
                            log.error("Invalid Template Group configuration file found: " + fileEntry.getName(), e);
                        }
                        // Put to map, as denotable by UUID
                        templateGroups.put(templateGroup.getName(), templateGroup);
                    } else {
                        log.error("Invalid Template Group configuration file found: " + fileEntry.getName());
                    }

                }
            }
        }

        return templateGroups;
    }

    /**
     * Loads and returns available Business Rules from the database
     *
     * @return
     */
    public Map<String, BusinessRuleFromTemplate> loadBusinessRules() {
        return null; //todo: implement
    }


    /**
     * Returns available Template Group objects, denoted by UUIDs
     *
     * @return
     */
    public Map<String, TemplateGroup> getTemplateGroups() {
        return this.availableTemplateGroups;
    }

    /**
     * Returns available Business Rule objects, denoted by UUIDs
     *
     * @return
     */
    public Map<String, BusinessRuleFromTemplate> getBusinessRules() {
        return this.availableBusinessRules;
    }

    /**
     * Returns RuleTemplate objects belonging to the given Template Group, denoted by UUIDs
     *
     * @param templateGroupName
     * @return
     */
    public Map<String, RuleTemplate> getRuleTemplates(String templateGroupName) throws TemplateManagerException {
        Map<String, RuleTemplate> ruleTemplates = new HashMap();
        for (String availableTemplateGroupName : availableTemplateGroups.keySet()) {
            // If matching UUID found
            if (availableTemplateGroupName.equals(templateGroupName)) {
                TemplateGroup foundTemplateGroup = availableTemplateGroups.get(availableTemplateGroupName);
                Collection<RuleTemplate> foundRuleTemplates = foundTemplateGroup.getRuleTemplates();

                // Put all the found Rule Templates denoted by their UUIDs, for returning
                for (RuleTemplate foundRuleTemplate : foundRuleTemplates) {
                    ruleTemplates.put(foundRuleTemplate.getName(), foundRuleTemplate);
                }

                return ruleTemplates;
            }
        }

        throw new TemplateManagerException("No Template Group found with the given Name");
    }

    /**
     * Derives and returns templates from the given BusinessRuleFromTemplate.
     * As specified in the given Business Rule,
     * RuleTemplate is found and its templated properties are replaced with the properties map
     *
     * @param businessRuleFromTemplate
     * @return Templates with replaced properties in the content, denoted by their UUIDs
     */
    public Map<String, Template> deriveTemplates(BusinessRuleFromTemplate businessRuleFromTemplate) {
        Map<String, Template> derivedTemplates = new HashMap(); // To store derived Template types and Templates
        // Get available Templates under the Rule Template, which is specified in the Business Rule
        Collection<Template> templatesToBeUsed = getTemplates(businessRuleFromTemplate);
        // Get properties, provided in the Business Rule
        Map<String, String> givenProperties = businessRuleFromTemplate.getProperties();

        for (Template template : templatesToBeUsed) {
            // If Template is a SiddhiApp
            if (template.getType().equals(TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE)) {
                Template derivedSiddhiApp = deriveSiddhiApp(template, givenProperties);
                try {
                    derivedTemplates.put(TemplateManagerHelper.getSiddhiAppName(derivedSiddhiApp), derivedSiddhiApp);
                } catch (TemplateManagerException e) {
                    log.error("Error in deriving SiddhiApp", e); // todo: (Q) Is this ok?
                }
            }
            // Other template types are not concerned for now
        }

        return derivedTemplates;
    }

    /**
     * Gives the list of Templates, that should be used by the given BusinessRuleFromTemplate
     *
     * @param businessRuleFromTemplate Given Business Rule
     * @return
     */
    public Collection<Template> getTemplates(BusinessRuleFromTemplate businessRuleFromTemplate) {
        RuleTemplate foundRuleTemplate = getRuleTemplate(businessRuleFromTemplate);
        // Get Templates from the found Rule Template
        Collection<Template> templates = foundRuleTemplate.getTemplates();

        return templates;
    }

    /**
     * Derives a SiddhiApp by replacing given SiddhiApp template's templated properties with the given values
     *
     * @param siddhiAppTemplate Templated SiddhiApp
     * @param givenValues       Values for replacing templated elements
     * @return
     */
    public Template deriveSiddhiApp(Template siddhiAppTemplate, Map<String, String> givenValues) {
        // SiddhiApp content, that contains templated elements
        String templatedSiddhiApp = siddhiAppTemplate.getContent();

        // To replace templated elements with given values
        StringBuffer derivedSiddhiAppBuffer = new StringBuffer();
        // Find all templated elements from the SiddhiApp
        Pattern templatedElementPattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_REGEX_PATTERN);
        Matcher templatedElementMatcher = templatedElementPattern.matcher(templatedSiddhiApp);

        // When a templated element is found
        while (templatedElementMatcher.find()) {
            // Templated Element (inclusive of template pattern)
            String templatedElement = templatedElementMatcher.group(1);
            // Find Templated Element's name
            Pattern templatedElementNamePattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_NAME_REGEX_PATTERN);
            Matcher templatedElementNameMatcher = templatedElementNamePattern.matcher(templatedElement);

            // When the templated element's name is found
            if (templatedElementNameMatcher.find()) {
                String templatedElementName = templatedElementNameMatcher.group(1);
                String elementReplacement = givenValues.get(templatedElementName);
                // Replace templated element with provided value
                templatedElementMatcher.appendReplacement(derivedSiddhiAppBuffer, elementReplacement);
            }
        }
        templatedElementMatcher.appendTail(derivedSiddhiAppBuffer);
        Template derivedSiddhiApp = new Template(TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE, derivedSiddhiAppBuffer.toString());

        return derivedSiddhiApp;
    }

    /**
     * Gets the Rule Template, that is specified in the given Business Rule
     *
     * @param businessRuleFromTemplate
     * @return
     */
    public RuleTemplate getRuleTemplate(BusinessRuleFromTemplate businessRuleFromTemplate) {
        String templateGroupName = businessRuleFromTemplate.getTemplateGroupName();
        String ruleTemplateName = businessRuleFromTemplate.getRuleTemplateName();

        TemplateGroup foundTemplateGroup = this.availableTemplateGroups.get(templateGroupName);
        RuleTemplate foundRuleTemplate = null;

        // A Template Group has been found with the given name
        if (foundTemplateGroup != null) {
            for (RuleTemplate ruleTemplate : foundTemplateGroup.getRuleTemplates()) {
                if (ruleTemplate.getName().equals(ruleTemplateName)) {
                    foundRuleTemplate = ruleTemplate;
                    break;
                }
            }
        }

        return foundRuleTemplate; // todo: throw exception or log? which is good?
    }

    /**
     * Saves JSON definition of the given Business Rule, to the database
     *
     * @param businessRuleFromTemplate
     * @throws TemplateManagerException
     */
    public void saveBusinessRuleDefinition(String uuid, BusinessRuleFromTemplate businessRuleFromTemplate) throws TemplateManagerException {
        //todo: implement
    }

    /**
     * Deploys the given Template
     *
     * @param template
     * @throws TemplateManagerException
     */
    public void deployTemplate(String uuid, Template template) throws TemplateManagerException {
        if (template.getType().equals(TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE)) {
            deploySiddhiApp(uuid, template);
        }
        // Other template types are not considered for now
    }

    /**
     * Deploys the given Template, of type SiddhiApp
     *
     * @param siddhiAppName
     * @param siddhiApp
     * @throws TemplateManagerException
     */
    public void deploySiddhiApp(String siddhiAppName, Template siddhiApp) throws TemplateManagerException {
        System.out.println("Successfully Deployed SiddhiApp : " + siddhiAppName);
        // todo: implement
    }

    /**
     * Gets properties that are specified in the BusinessRuleFromTemplate, with entered values as default values
     *
     * @param businessRuleFromTemplate
     * @return
     */
    public Collection<RuleTemplateProperty> getProperties(BusinessRuleFromTemplate businessRuleFromTemplate) {
        return null; //todo: implement
    }

    /**
     * Overwrites JSON definition of the Business Rule that has the given id,
     * with the given Business Rule
     *
     * @param uuid
     * @param businessRuleFromTemplate
     * @throws TemplateManagerException
     */
    public void overwriteBusinessRuleDefinition(String uuid, BusinessRuleFromTemplate businessRuleFromTemplate) throws TemplateManagerException {
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
     * Gets types and UUIDs of the Templates, that belong to the given BusinessRuleFromTemplate
     *
     * @param businessRuleFromTemplate
     * @return Collection of String array entries, of which elements are as following : [0]-TemplateType & [1]-TemplateUUID
     */
    public Collection<String[]> getTemplateTypesAndUUIDs(BusinessRuleFromTemplate businessRuleFromTemplate) {
        // To store found Template UUIDs and types
        // Each entry's [0]-TemplateType [1]-TemplateUUID
        Collection<String[]> templateTypesAndUUIDs = new ArrayList();

        // UUIDs and denoted derived Templates
        Map<String, Template> derivedTemplates = deriveTemplates(businessRuleFromTemplate);
        for (Template derivedTemplate : derivedTemplates.values()) {
            // If Template is a SiddhiApp
            if (derivedTemplate.getType().equals(TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE)) {
                try {
                    String siddhiAppName = TemplateManagerHelper.getSiddhiAppName(derivedTemplate);
                    // Add type and name of template
                    templateTypesAndUUIDs.add(new String[]{TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE, siddhiAppName});
                } catch (TemplateManagerException e) {
                    log.error(e.getMessage()); // todo: (Q) Is this ok?
                }
            }
            // Other template types are not considered for now
        }

        return templateTypesAndUUIDs;
    }

    /**
     * Undeploys the Template with the given UUID, according to the given template type
     *
     * @param templateType
     * @param uuid
     * @throws TemplateManagerException
     */
    public void undeployTemplate(String templateType, String uuid) throws TemplateManagerException {
        // If Template is a SiddhiApp
        if (templateType.equals(TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE)) {
            undeploySiddhiApp(uuid);
        }
        // other Template types are not considered for now
    }

    /**
     * Undeploys the Template of type SiddhiApp, with the given UUID
     *
     * @param uuid
     * @throws TemplateManagerException
     */
    public void undeploySiddhiApp(String uuid) throws TemplateManagerException {
        // todo: implement
    }

    /**
     * Deletes the JSON definition of the Business Rule, that has the given UUID
     *
     * @param uuid
     * @throws TemplateManagerException
     */
    public void removeBusinessRuleDefinition(String uuid) throws TemplateManagerException {
        // todo: implement
    }

    //////////// insert anything on top of this //////////
    
    /**
     * Finds the Template Group with the given name
     *
     * @param templateGroupName
     * @return
     * @throws TemplateManagerException
     */
    public TemplateGroup findTemplateGroup(String templateGroupName) throws TemplateManagerException {
        for (String availableTemplateGroupName : availableTemplateGroups.keySet()) {
            if(availableTemplateGroupName.equals(templateGroupName)){
                return availableTemplateGroups.get(availableTemplateGroupName);
            }
        }

        throw new TemplateManagerException("No Template Group found with the name : "+templateGroupName);
    }

    /**
     * Gives the Business Rule with the given UUID
     *
     * @param businessRuleUUID
     * @return
     * @throws TemplateManagerException
     */
    public BusinessRuleFromTemplate findBusinessRule(String businessRuleUUID) throws TemplateManagerException {
        for (String availableBusinessRuleUUID : availableBusinessRules.keySet()) {
            if (availableBusinessRuleUUID.equals(businessRuleUUID)) {
                return availableBusinessRules.get(availableBusinessRuleUUID);
            }
        }

        throw new TemplateManagerException("No Business Rule found with the UUID : " + businessRuleUUID);
    }
}
