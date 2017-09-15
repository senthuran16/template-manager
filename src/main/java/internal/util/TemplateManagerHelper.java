package internal.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import internal.bean.businessRulesFromTemplate.BusinessRuleFromTemplate;
import internal.exceptions.TemplateManagerException;
import internal.bean.businessRulesFromTemplate.RuleTemplateProperty;
import internal.bean.businessRulesFromTemplate.RuleTemplate;
import internal.bean.businessRulesFromTemplate.Template;
import internal.bean.businessRulesFromTemplate.TemplateGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Consists of methods for additional features for the exposed Template Manager service
 */
//TODO : Verify class names
public class TemplateManagerHelper {
    //private static final Log log = LogFactory.getLog(TemplateManagerHelper.class);

    /**
     * To avoid instantiation
     */
    private TemplateManagerHelper() {

    }

    // todo : hardcoded test. remove main()
    public static void main(String[] args) throws TemplateManagerException {

        File directory = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY);
        Collection<TemplateGroup> availableTemplateGroups = new ArrayList();

        // To store files from the directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File fileEntry : files) {
                // If file is a valid json file
                if (fileEntry.isFile() && fileEntry.getName().endsWith("json")) {
                    TemplateGroup templateGroup = null;
                    // Convert to TemplateGroup object
                    try {
                        templateGroup = TemplateManagerHelper.jsonToTemplateGroup(TemplateManagerHelper.fileToJson(fileEntry));
                    } catch (NullPointerException ne) {
                        System.out.println("Unable to convert TemplateGroup file : " + fileEntry.getName() + " " + ne);
                    }

                    // Validate contents of the object
                    if (templateGroup != null) {
                        try {
                            TemplateManagerHelper.validateTemplateGroup(templateGroup);
                            // Add only valid TemplateGroups to the template
                            availableTemplateGroups.add(templateGroup);
                        } catch (TemplateManagerException e) { //todo: implement properly
                            // Files with invalid content are not added.
                            System.out.println("Invalid Template Group configuration file found : " + fileEntry.getName() + e);
                        }
                    } else {
                        System.out.println("Invalid Template Group configuration file found : " + fileEntry.getName());
                    }

                }
            }
        }

        System.out.println(availableTemplateGroups.size() + " Templates Found");

    }

    /**
     * Converts given JSON File to a JSON object
     *
     * @param jsonFile Given JSON File
     * @return JSON object
     */
    public static JsonObject fileToJson(File jsonFile) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonObject jsonObject = null;

        try {
            Reader reader = new FileReader(jsonFile);
            jsonObject = gson.fromJson(reader, JsonObject.class);
        } catch (FileNotFoundException e) {
            //log.error("FileNotFound Exception occurred when converting JSON file to JSON Object", e); //todo: FileNotFound exception occured. error message?
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * Converts given JSON object to TemplateGroup object
     *
     * @param jsonObject Given JSON object
     * @return TemplateGroup object
     */
    public static TemplateGroup jsonToTemplateGroup(JsonObject jsonObject) {
        String templateGroupJsonString = jsonObject.get("templateGroup").toString();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        TemplateGroup templateGroup = gson.fromJson(templateGroupJsonString, TemplateGroup.class);

        return templateGroup;
    }

    /**
     * Converts given String JSON definition to TemplateGroup object
     *
     * @param jsonDefinition Given String JSON definition
     * @return TemplateGroup object
     */
    public static TemplateGroup jsonToTemplateGroup(String jsonDefinition) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        TemplateGroup templateGroup = gson.fromJson(jsonDefinition, TemplateGroup.class);

        return templateGroup;
    }

    /**
     * Converts given JSON object to BusinessRuleFromTemplate object
     *
     * @param jsonObject Given JSON object
     * @return BusinessRuleFromTemplate object
     */
    public static BusinessRuleFromTemplate jsonToBusinessRuleFromTemplate(JsonObject jsonObject) {
        String businessRuleJsonString = jsonObject.get("businessRuleFromTemplate").toString();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        BusinessRuleFromTemplate businessRuleFromTemplate = gson.fromJson(businessRuleJsonString, BusinessRuleFromTemplate.class);

        return businessRuleFromTemplate;
    }

    // todo : public static BusinessRuleFromScratch jsonToBusinessRuleFromScratch(JsonObject jsonObject)

    /**
     * Converts given String JSON definition to BusinessRuleFromTemplate object
     *
     * @param jsonDefinition Given String JSON definition
     * @return TemplateGroup object
     */
    public static BusinessRuleFromTemplate jsonToBusinessRuleFromTemplate(String jsonDefinition) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        BusinessRuleFromTemplate businessRuleFromTemplate = gson.fromJson(jsonDefinition, BusinessRuleFromTemplate.class);

        return businessRuleFromTemplate;
    }

    // todo: public static BusinessRuleFromScratch jsonToBusinessRuleFromScratch(String jsonDefinition)

    /**
     * Checks whether a given TemplateGroup object has valid content
     * Validation criteria : //todo: confirm
     * - name is available
     * - At least one ruleTemplate is available
     *
     * @param templateGroup
     * @throws TemplateManagerException
     */
    public static void validateTemplateGroup(TemplateGroup templateGroup) throws TemplateManagerException {
        try { // todo: remove this. This is just temporary
            if (templateGroup.getName() == null) {
                throw new TemplateManagerException("Invalid TemplateGroup configuration file found");
            }
            if (!(templateGroup.getRuleTemplates().size() > 0)) {
                throw new TemplateManagerException("Invalid TemplateGroup configuration file found");
            }
            for (RuleTemplate ruleTemplate : templateGroup.getRuleTemplates()) {
                validateRuleTemplate(ruleTemplate);
            }
        } catch (TemplateManagerException x) {
            System.out.println("TemplateGroup Not Valid");
        }

    }

    /**
     * Checks whether a given RuleTemplate object has valid content
     * Validation Criteria : todo: confirm validation criteria for RuleTemplate
     * - name is available
     * - type is either 'app', 'source' or 'sink'
     * - At least one template available
     * - At least one property available
     * - All properties have defaultValue
     * - Each property of type 'option' should have at least one option
     * - Each template type is either 'siddhiApp', 'gadget' or 'dashboard'
     * - Each templated element in each template, should have a matching property
     *
     * @param ruleTemplate
     * @throws TemplateManagerException
     */
    public static void validateRuleTemplate(RuleTemplate ruleTemplate) throws TemplateManagerException {
        ArrayList<String> validTemplateTypes = new ArrayList<String>(Arrays.asList(TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE, "gadget", "dashboard")); //todo: more types might come

        if (ruleTemplate == null) {
            // todo: throw exception
        }
        if (ruleTemplate.getName() == null) {
            // todo: throw exception
        }
        if (!(ruleTemplate.getName().equals("app") || ruleTemplate.getName().equals("source") || ruleTemplate.getName().equals("sink"))) {
            // todo: throw exception
        }
        if (ruleTemplate.getTemplates().size() < 1) {
            // todo: throw exception
        }
        if (ruleTemplate.getProperties().size() < 1) {
            // todo: throw exception
        }
        for (String property : ruleTemplate.getProperties().keySet()) {
            validateRuleTemplateProperty(ruleTemplate.getProperties().get(property));
            // If template type is not valid
            if (!validTemplateTypes.contains(ruleTemplate.getProperties().get(property).getType())) {
                // todo: throw exception
            }
        }
        validateTemplatesAndProperties(ruleTemplate.getTemplates(), ruleTemplate.getProperties());
    }

    /**
     * Checks whether a given ruleTemplateProperty object has valid content
     * Validation Criteria :
     * - All properties have defaultValue
     * - Each ruleTemplateProperty of type 'option' should have at least one option
     *
     * @param ruleTemplateProperty
     * @throws TemplateManagerException
     */
    public static void validateRuleTemplateProperty(RuleTemplateProperty ruleTemplateProperty) throws TemplateManagerException { //todo: conversion null pointer exception
        if (ruleTemplateProperty.getDefaultValue() == null) {
            // todo: throw exception
        }
        if (ruleTemplateProperty.getType().equals("option") && (ruleTemplateProperty.getOptions() == null || ruleTemplateProperty.getOptions().size() < 1)) {
            // todo: throw exception
        }
    }

    /**
     * Checks whether all the templated elements of each template, has matching values in properties
     *
     * @param templates  Templates
     * @param properties RuleTemplateProperty names, denoting RuleTemplateProperty objects
     * @throws TemplateManagerException
     */
    public static void validateTemplatesAndProperties(Collection<Template> templates, Map<String, RuleTemplateProperty> properties) throws TemplateManagerException {
        Collection<String> templatedElements = new ArrayList();

        // Add all templated elements to Collection
        for (Template template : templates) {
            String templatedContent = template.getContent();

            // Find all templated elements from the siddhiApp
            Pattern templatedElementPattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_REGEX_PATTERN);
            Matcher templatedElementMatcher = templatedElementPattern.matcher(templatedContent);

            // When each templated element is found
            while (templatedElementMatcher.find()) {
                // Add templated element (inclusive of template pattern)
                String templatedElement = templatedElementMatcher.group(1);

                // Find Templated Element's Name
                Pattern templatedElementNamePattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_NAME_REGEX_PATTERN);
                Matcher templatedElementNameMatcher = templatedElementNamePattern.matcher(templatedElement);

                // When the Templated Element's Name is found
                if (templatedElementNameMatcher.find()) {
                    // Templated Element's Name
                    String templatedElementName = templatedElementNameMatcher.group(1);

                    templatedElements.add(templatedElementName);
                }
            }

        }

        // All templated elements are not given in properties
        if (!properties.keySet().containsAll(templatedElements)) {
            // todo: throw exception
        }
    }

    /**
     * Checks whether a given Template file has valid content.
     * Validation criteria : //todo: confirm validation criteria for templates
     * - name
     * - maximumInstances
     * - maxNumberOfNodes
     * - javascript
     * - siddhiApps
     * - properties //todo: validate whether all templated elements are referred as properties?
     *
     * @param template Given Template object
     * @throws TemplateManagerException
     */
    public static void validateTemplate(Template template) throws TemplateManagerException {
        //todo: no need mostly.
    }

    public static void validateBusinessRuleFromTemplate(BusinessRuleFromTemplate businessRuleFromTemplate) throws TemplateManagerException {
        // todo: implement
    }

    /**
     * Generates UUID for the given Template
     *
     * @param template
     * @return
     */
    public static String generateUUID(Template template) throws TemplateManagerException {
        // SiddhiApp Template
        if (template.getType().equals(TemplateManagerConstants.SIDDHI_APP_TEMPLATE_TYPE)) {
            return getSiddhiAppName(template);
        }
        // Other template types are not considered for now
        throw new TemplateManagerException("Invalid template type. Unable to generate UUID"); // todo: (Q) is this correct?
    }

    /**
     * Gives the name of the given SiddhiApp
     *
     * @param siddhiAppTemplate
     * @return
     * @throws TemplateManagerException
     */
    public static String getSiddhiAppName(Template siddhiAppTemplate) throws TemplateManagerException {
        // Content of the SiddhiApp
        String siddhiApp = siddhiAppTemplate.getContent();
        // Regex match and find name
        Pattern siddhiAppNamePattern = Pattern.compile(TemplateManagerConstants.SIDDHI_APP_NAME_REGEX_PATTERN);
        Matcher siddhiAppNameMatcher = siddhiAppNamePattern.matcher(siddhiApp);
        if (siddhiAppNameMatcher.find()) {
            return siddhiAppNameMatcher.group(1);
        }

        throw new TemplateManagerException("Invalid SiddhiApp Name Found"); //todo: (Q) Is this correct?
    }

    /**
     * Generates UUID from the given values, entered for the BusinessRuleFromTemplate
     * todo: This will be only called after user's form values come from the API (Read below)
     * 1. User enters values (propertyName : givenValue)
     * 2. TemplateGroupName, and RuleTemplateName is already there
     * 3. A Map with above details will be given from the API, to the backend
     * 4. These details are combined and the UUID is got
     * 5. BR object is created with those entered values, + the uuid in the backend
     *
     * @param givenValuesForBusinessRule
     * @return
     */
    public static String generateUUID(Map<String, String> givenValuesForBusinessRule) {
        return UUID.nameUUIDFromBytes(givenValuesForBusinessRule.toString().getBytes()).toString();
    }
}
