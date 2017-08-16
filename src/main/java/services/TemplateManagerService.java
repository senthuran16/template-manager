package services;

import core.BusinessRule;
import core.RuleCollection;
import core.RuleTemplate;
import core.Template;
import core.TemplateManagerConstants;
import core.TemplateManagerHelper;
import core.TemplateManagerInstance;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateManagerService implements BusinessRulesService {

    public static void main(String[] args) {
        TemplateManagerService templateManagerService = TemplateManagerInstance.getInstance();

        File templateFile = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY+"SensorDataAnalysis.json");
        File businessRuleFile = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY+"myBusinessRule.json");

        RuleCollection rc = TemplateManagerHelper.jsonToRuleCollection(TemplateManagerHelper.fileToJson(templateFile));

        System.out.println(rc);

        templateManagerService.createbusinessRuleFromTemplate(TemplateManagerHelper.jsonToBusinessRule(TemplateManagerHelper.fileToJson(businessRuleFile)));
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
            deployPropertiesMap(properties);

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
     * Returns BusinessRules, that have RuleCollection as the given one
     *
     * @param ruleCollection Given RuleCollection object
     * @return BusinessRules belonging to the given RuleCollection
     */
    public Collection<BusinessRule> getBusinessRules(RuleCollection ruleCollection) {
        String ruleCollectionName = ruleCollection.getName();
        Collection<BusinessRule> businessRulesUnderRuleCollection = new ArrayList();

        Collection<BusinessRule> availableBusinessRules = listBusinessRules();

        for (BusinessRule businessRule : availableBusinessRules) {
            // Only Business Rules from templates can be listed under a specific RuleCollection
            if (businessRule.getType().equals("template")) { //todo: [template, scratch] or [fromTemplate, fromScratch] or anything else?
                // If RuleCollection name of BusinessRule matches
                if (businessRule.getRuleTemplateName().split("/")[0].equals(ruleCollectionName)) {
                    businessRulesUnderRuleCollection.add(businessRule);
                }
            }

        }

        // If at least one configured Business Rule exists under this category
        if (businessRulesUnderRuleCollection.size() > 0) {
            return businessRulesUnderRuleCollection;
        }

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
            deployPropertiesMap(properties);

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
     * Finds RuleTemplate which is specified in the given BusinessRule
     * Returns templates, that belong to the found RuleTemplate
     *
     * @param businessRule Given BusinessRule
     * @return Templates that belong to the found RuleTemplate. null, if RuleTemplate name is invalid //todo: what about name invalid validation?
     */
    public Collection<Template> getTemplates(BusinessRule businessRule) {
        System.out.println("BUSINESS RLE : "+businessRule.toString()); //todo: BusinessRule doesn't map properly
        // Get RuleTemplateName mentioned in the BusinessRule
        String ruleCollectionRuleTemplateName = businessRule.getRuleTemplateName();
        String ruleCollectionName = ruleCollectionRuleTemplateName.split("/")[0];
        String ruleTemplateName = ruleCollectionRuleTemplateName.split("/")[1];

        File ruleCollectionFile = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY + ruleCollectionName + ".json");
        RuleCollection ruleCollection = TemplateManagerHelper.jsonToRuleCollection(TemplateManagerHelper.fileToJson(ruleCollectionFile));
        // Get RuleTemplates belonging to RuleCollection
        System.out.println("RULETEMPLATE : "+ruleCollection.toString());
        Collection<RuleTemplate> ruleTemplates = ruleCollection.getRuleTemplates();
        for (RuleTemplate ruleTemplate : ruleTemplates) {
            // If RuleTemplate name matches with given name
            if (ruleTemplate.getName().equals(ruleTemplateName)) {
                return ruleTemplate.getTemplates();
            }
        }

        return null;
    }

    /**
     * Derives a Template of SiddhiApp by mapping given values to templated elements
     *
     * @return
     */
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
     * Deploys the given SiddhiApp template's content as a *.siddhi file
     *
     * @param siddhiAppTemplate Siddhi App as a template element
     */
    public void deploySiddhiApp(Template siddhiAppTemplate) {
        // todo: get content of siddhiAppTemplate. Deploy it as *.siddhi
        // For test
        System.out.println("[DEPLOYED] ");
        System.out.println(siddhiAppTemplate);
    }

    /**
     * Deploys the given properties map
     *
     * @param properties
     */
    public void deployPropertiesMap(Map<String, String> properties) {
        // todo: implement deployPropertiesMap. Concern about overwriting
    }

    /**
     * Derives a Template by mapping the given properties to the templated elements of the given Template
     *
     * @param template   Given Template object
     * @param properties Properties for templated elements of the Templates
     * @return Derived Template
     */
    public Template deriveTemplate(Template template, Map<String, String> properties) {
        String templateType = template.getType();
        if (templateType.equals("siddhiApp")) {
            return deriveSiddhiApp(template, properties);
        } else {
            // todo: implement for other template types
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
}
