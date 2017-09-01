package services;

import core.BusinessRule;
import core.Property;
import core.RuleTemplate;
import core.Template;
import core.TemplateGroup;
import core.TemplateManagerConstants;
import core.TemplateManagerHelper;
import core.TemplateManagerInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateManagerService implements BusinessRulesService {
    private static final Log log = LogFactory.getLog(TemplateManagerServiceOldOld.class);
    // Available Template Groups from the directory
    private Collection<TemplateGroup> availableTemplateGroups;
    private Collection<BusinessRule> availableBusinessRules;

    // Load & store available Template Groups & Business Rules at the time of instantiation
    public TemplateManagerService() {
        this.availableTemplateGroups = loadTemplateGroups();
        this.availableBusinessRules = loadBusinessRules();
    }

    public static void main(String[] args) {
        TemplateManagerService templateManagerService = TemplateManagerInstance.getInstance();

        File businessRuleFile = new File(TemplateManagerConstants.BUSINESS_RULES_DIRECTORY + "myBusinessRule.json");
        templateManagerService.createbusinessRuleFromTemplate(TemplateManagerHelper.jsonToBusinessRule(TemplateManagerHelper.fileToJson(businessRuleFile)));
        System.out.println("\nFound TemplateGroups from the directory : ");
        System.out.println(templateManagerService.listTemplateGroups());
    }

    /**
     * Loads available Template Groups from the directory
     *
     * @return Available Template Groups
     */
    public Collection<TemplateGroup> loadTemplateGroups() {
        // todo: implement
        return null;
    }

    /**
     * Loads available Business Rules from the database
     *
     * @return Available Business Rules
     */
    public Collection<BusinessRule> loadBusinessRules() {
        // todo: implement
        return null;
    }

    /**
     * Lists available Template Groups
     *
     * @return available Template Groups
     */
    public Collection<TemplateGroup> listTemplateGroups() {
        return this.availableTemplateGroups;
    }

    /**
     * Lists available Rule Templates, that belong to the given Template Group
     *
     * @param templateGroupName Given Template Group name
     * @return Collection of Rule Templates
     */
    public Collection<RuleTemplate> listRuleTemplates(String templateGroupName) {
        return null;
    }

    /**
     * Lists Properties of a given Rule Template
     *
     * @param ruleTemplateName Given Rule Template name
     * @return Properties of the given Rule Template
     */
    public Collection<Property> listProperties(String ruleTemplateName) {
        return null;
    }

    /**
     * Returns templates, that belong to the given RuleTemplate
     *
     * @param ruleTemplate Given RuleTemplate
     * @return Templates of the given Rule Template
     */
    public Collection<Template> listTemplates(RuleTemplate ruleTemplate) {
        // todo: implement
        return null;
    }

    /**
     * Finds the specified RuleTemplate
     * Derives Templates by replacing templated elements with given values
     * Deploys Templates in corresponding formats
     * Saves provided values map to the database
     *
     * @param businessRule Given BusinessRule object, which has RuleTemplate name and provided values
     */
    public void createbusinessRuleFromTemplate(BusinessRule businessRule) {
        Collection<Template> templates = getTemplates(businessRule);
        Map<String, String> properties = businessRule.getProperties();

        // Derive all templates & deploy
        for (Template template : templates) {
            //deployPropertiesMap(properties);

            // Derive & deploy SiddhiApp
            if (template.getType().equals("siddhiApp")) {
                deploySiddhiApp(deriveSiddhiApp(template, properties));
            }
            // todo: Other template types (i.e: gadgets etc.)
        }
    }

    /**
     * Returns available BusinessRules
     *
     * @return Available Business Rules
     */
    public Collection<BusinessRule> listBusinessRules() {
        // todo: implement listAllBusinessRules. Should read from database
        return null;
    }

    /**
     * Finds the specified RuleTemplate
     * Derive Templates by replacing templated elements with newly given values
     * Deploys Templates in corresponding formats
     * Updates existing values map in the database, with the new one
     *
     * @param businessRule Given BusinessRule object, which has RuleTemplate name and newly provided values
     */
    public void editBusinessRule(BusinessRule businessRule) {
        // Get required templates from the business rule
        Collection<Template> templates = getTemplates(businessRule);
        // Get provided values for properties
        Map<String, String> properties = businessRule.getProperties();

        Collection<Template> derivedTemplates = new ArrayList();

        // Derive all templates & deploy
        for (Template template : templates) {
            //deployPropertiesMap(properties);

            // Deploy SiddhiApp
            if (template.getType().equals("siddhiApp")) {
                deploySiddhiApp(deriveSiddhiApp(template, properties));
            }
            // todo: Other template types (i.e: gadgets etc.)
        }
    }

    /**
     * Deletes the given values map from the database
     * Undeploy the templates
     *
     * @param businessRule
     */
    public void deleteBusinessRule(BusinessRule businessRule) {
        // todo: implement deleteBusinessRule. Also, undeployBusinessRule
    }

    /**
     * Derives Business Rule from a given Rule Template and entered values
     *
     * @param ruleTemplate  RuleTemplate
     * @param enteredValues Values given for the templated properties
     * @return Derived BusinessRule object
     */
    public BusinessRule deriveBusinessRule(RuleTemplate ruleTemplate, Map<String, String> enteredValues) {
        // todo: implement
        return null;
    }

    /**
     * Derives a Template by mapping the given properties to the templated elements of the given Template
     *
     * @param templates     Given Template objects
     * @param enteredValues Values given for templated Properties
     * @return
     */
    public Collection<Template> deriveTemplates(Collection<Template> templates, Map<String, String> enteredValues) {
        // todo: implement
        return null;
    }

    /**
     * Derives a Template of SiddhiApp by mapping given values to templated elements
     *
     * @param siddhiAppTemplate SiddhiApp with templated elements
     * @param properties        Given values for templated elements
     * @return Derived SiddhiApp, as Template object
     */
    public Template deriveSiddhiApp(Template siddhiAppTemplate, Map<String, String> properties) {
        String templatedSiddhiApp = siddhiAppTemplate.getContent();

        // To replace Templated Elements with given values
        StringBuffer derivedSiddhiAppBuffer = new StringBuffer();
        // Find all templated elements from the siddhiApp
        Pattern templatedElementPattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_REGEX_PATTERN);
        Matcher templatedElementMatcher = templatedElementPattern.matcher(templatedSiddhiApp);

        // When each templated element is found
        while (templatedElementMatcher.find()) {
            // Templated Element (inclusive of template pattern)
            String templatedElement = templatedElementMatcher.group(1);
            // Find Templated Element's Name
            Pattern templatedElementNamePattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_NAME_REGEX_PATTERN);
            Matcher templatedElementNameMatcher = templatedElementNamePattern.matcher(templatedElement);

            // When the Templated Element's Name is found
            if (templatedElementNameMatcher.find()) {
                // Templated Element's Name
                String templatedElementName = templatedElementNameMatcher.group(1);

                String elementReplacement = properties.get(templatedElementName);
                templatedElementMatcher.appendReplacement(derivedSiddhiAppBuffer, elementReplacement);
            }
        }
        templatedElementMatcher.appendTail(derivedSiddhiAppBuffer);

        Template derivedSiddhiApp = new Template("siddhiApp", derivedSiddhiAppBuffer.toString());

        return derivedSiddhiApp;
    }

    /**
     * Deploys the given Template
     *
     * @param template Given Template
     */
    public void deployTemplate(Template template) {
        // todo: implement
    }

    /**
     * Deploys the given SiddhiApp template's content as a *.siddhi file
     *
     * @param siddhiAppTemplate Siddhi App as a template element
     */
    public void deploySiddhiApp(Template siddhiAppTemplate) {
        // todo: get content of siddhiAppTemplate. Deploy it as *.siddhi
        // For test
        System.out.println("[DEPLOYED]  " + siddhiAppTemplate);
    }


    /**
     * Saves the JSON definition of the given Business Rule to the database
     *
     * @param businessRule
     */
    public void SaveBusinessRule(BusinessRule businessRule) {
        // todo: implement
    }

    /**
     * Gives Filled Properties of a given Business Rule
     *
     * @param businessRuleName Given Business Rule name
     * @return
     */
    public Collection<Property> getBusinessRuleProperties(String businessRuleName) {
        // todo: implement
        // todo: return currently filled values as defaultValues. That'll be displayed in front end.
        return null;
    }

    /**
     * Update Deploys the given Template
     *
     * @param template Given Template
     */
    public void updateDeployTemplate(Template template) {
        // todo: implement
    }

    /**
     * Update Deploys the given SiddhiApp template's content as a *.siddhi file
     *
     * @param siddhiAppTemplate Siddhi App as a template element
     */
    public void updateDeploySiddhiApp(Template siddhiAppTemplate) {
        // todo: get content of siddhiAppTemplate. Deploy it as *.siddhi
        // For test
        System.out.println("[DEPLOYED]  " + siddhiAppTemplate);
    }


    /**
     * Returns templates, that belong to the RuleTemplate mentioned in the given BusinessRule
     *
     * @param businessRule Given BusinessRule
     * @return Templates that belong to the found RuleTemplate. null, if RuleTemplate name is invalid //todo: what about name invalid validation?
     */
    public Collection<Template> getTemplates(BusinessRule businessRule) {
        // Get RuleTemplateName mentioned in the BusinessRule
        String templateGroupName = businessRule.getTemplateGroupName();
        String ruleTemplateName = businessRule.getRuleTemplateName();
        TemplateGroup templateGroup = null;

        // Get specified TemplateGroup
        for (TemplateGroup availableTemplateGroup : this.availableTemplateGroups) {
            if (availableTemplateGroup.getName().equals(templateGroupName)) {
                templateGroup = availableTemplateGroup;
                break;
            }
        }

        // If TemplateGroup is found
        if (templateGroup != null) {
            // Get RuleTemplates belonging to TemplateGroup
            Collection<RuleTemplate> ruleTemplates = templateGroup.getRuleTemplates();
            for (RuleTemplate ruleTemplate : ruleTemplates) {
                // If RuleTemplate name matches with given name
                if (ruleTemplate.getName().equals(ruleTemplateName)) {
                    return ruleTemplate.getTemplates();
                }
            }
        }

        return null;
    }

    /**
     * Returns all the available BusinessRules
     *
     * @return
     */
    public Collection<BusinessRule> getBusinessRules() {
        // todo: implement getAllBusinessRules. Check whether how to do it, from DB
        return null;
    }

    /**
     * Undeploys the Template with the given name
     *
     * @param templateName
     */
    public void undeployTemplate(String templateName) {
        // todo: implement
    }

    /**
     * Undeploys the SiddhiApp, with the given name
     *
     * @param siddhiAppName
     */
    public void undeploySiddhiApp(String siddhiAppName) {
        // todo: implement
    }

    /**
     * Gets the Template Group object, that has the given name
     *
     * @param templateGroupName Given name for Template Group
     * @return Found Template Group object
     */
    public TemplateGroup getTemplateGroup(String templateGroupName) {
        // todo: implement
        return null;
    }

    /**
     * Gets the Rule Template object, that has the given name
     *
     * @param ruleTemplateName Given name for Rule Template
     * @return Found Rule Template object
     */
    public RuleTemplate getRuleTemplate(String ruleTemplateName) {
        // todo: implement
        return null;
    }


}
