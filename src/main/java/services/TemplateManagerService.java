package services;

import core.BusinessRule;
import core.Template;
import core.TemplateManagerConstants;
import core.TemplateManagerException;
import core.TemplateManagerHelper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Exposed root.Template Manager service, that handles Templates and Business Rules
 */
public class TemplateManagerService implements TemplateManager, BusinessRulesService {
    public static void main(String[] args) throws TemplateManagerException {
        TemplateManagerService templateManagerService = new TemplateManagerService();

        System.out.println("=====================================");
        System.out.println("CREATED BUSINESS RULE - FROM TEMPLATE");
        System.out.println("=====================================");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sensorInStream", "TemperatureSensorInStream");
        map.put("property", "sensorName");
        map.put("sensorValue", "roomTemperature");
        //map.put("outStream1","sensorReadingsCopy");
        map.put("thresholdValue", "100");
        map.put("filteredSensorOutStream", "highRoomTemperatures");

        File templateJsonFile = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY + "sample-template.json");
        BusinessRule businessRuleFromTemplate = templateManagerService.createBusinessRuleFromTemplate(TemplateManagerHelper.jsonToTemplate(templateJsonFile), "TemperatureSensorsLoggingBR", map);
        System.out.println(TemplateManagerHelper.jsonToTemplate(templateJsonFile));
        System.out.println(businessRuleFromTemplate);

        Template template = TemplateManagerHelper.jsonToTemplate(templateJsonFile);
        templateManagerService.addTemplate(template, "mySampleTemplate");

        templateManagerService.addBusinessRule(businessRuleFromTemplate, "mySampleBusinessRule");

    }

    private static final Log log = LogFactory.getLog(TemplateManagerService.class);

    /**
     * Saves the given Business Rule to the directory as a JSON file, and saves its SiddhiApps
     *
     * @param businessRule Given Business Rule Object
     * @param fileName     Given File Name
     */
    public void addBusinessRule(BusinessRule businessRule, String fileName) throws TemplateManagerException {
        // Save Business Rule
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(TemplateManagerConstants.BUSINESS_RULES_DIRECTORY + fileName + ".json");
            writer.println(TemplateManagerHelper.businessRuleToJson(businessRule));
        } catch (IOException e) {
            throw new TemplateManagerException(e.getMessage(), e.getCause());
        } finally {
            writer.close();
        }

        // Save siddhiApps
        addSiddhiApps(businessRule);
    }

    /**
     * Saves each SiddhiApp from a Business Rule, to the directory as *.siddhi files
     *
     * @param businessRule Given Business Rule Object
     */
    public void addSiddhiApps(BusinessRule businessRule) throws TemplateManagerException {
        // SiddhiApps from given Business Rule
        Collection<String> siddhiApps = businessRule.getSiddhiApps();

        for (String siddhiApp : siddhiApps) {
            // Find SiddhiApp's name
            Pattern siddhiAppNamePattern = Pattern.compile(TemplateManagerConstants.SIDDHI_APP_NAME_REGEX_PATTERN);
            Matcher siddhiAppNamePatternMatcher = siddhiAppNamePattern.matcher(siddhiApp);

            // When SiddhiApp's Name is found
            if (siddhiAppNamePatternMatcher.find()) {
                String siddhiAppName = siddhiAppNamePatternMatcher.group(1);

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(TemplateManagerConstants.BUSINESS_RULES_DIRECTORY + siddhiAppName + ".siddhi"); //todo: seperate folder for siddhiApps?
                    writer.println(siddhiApp);
                } catch (IOException e) {
                    throw new TemplateManagerException(e.getMessage(), e.getCause()); //todo: IO Exception occured. Give proper error message
                } finally {
                    writer.close();
                }
            }
        }
    }

    /**
     * Deletes each SiddhiApp from a given BusinessRule, from the directory
     *
     * @param businessRule Given Business Rule Object
     * @return Collection of undeleted siddhiApp names if any. Otherwise null
     */
    public Collection<String> deleteSiddhiApps(BusinessRule businessRule) {
        Collection<String> undeletedSiddhiApps = new ArrayList<String>();
        Collection<String> siddhiApps = businessRule.getSiddhiApps();

        for (String siddhiApp : siddhiApps) {
            // Find SiddhiApp's name
            Pattern siddhiAppNamePattern = Pattern.compile(TemplateManagerConstants.SIDDHI_APP_NAME_REGEX_PATTERN);
            Matcher siddhiAppNamePatternMatcher = siddhiAppNamePattern.matcher(siddhiApp);

            // When SiddhiApp's Name is found
            if (siddhiAppNamePatternMatcher.find()) {
                String siddhiAppName = siddhiAppNamePatternMatcher.group(1);
                File file = new File(TemplateManagerConstants.BUSINESS_RULES_DIRECTORY + siddhiAppName + ".siddhi"); //todo: siddhiAppLocation (if seperate folder is decided)
                // if unable to delete
                if (!file.delete()) {
                    undeletedSiddhiApps.add(siddhiAppName);
                }
            }
        }

        // If all SiddhiApps are successfully deleted
        if (undeletedSiddhiApps.size() == 0) {
            return null;
        }

        return undeletedSiddhiApps;
    }

    /**
     * Deletes the Business Rule with the given name, from the directory
     *
     * @param businessRuleName Given Name of Business Rule
     * @return Deleted Business Rule object //todo: delete related siddhiApps too
     */
    public BusinessRule deleteBusinessRule(String businessRuleName) throws TemplateManagerException {
        // Get as object before deleting
        BusinessRule businessRule = TemplateManagerHelper.jsonToBusinessRule(new File(TemplateManagerConstants.BUSINESS_RULES_DIRECTORY + businessRuleName + ".json"));
        File businessRuleFile = new File(TemplateManagerConstants.BUSINESS_RULES_DIRECTORY + businessRuleName + ".json"); //todo: exception handling for unfound file

        // Delete SiddhiApps and store undeleted ones, if any
        Collection<String> undeletedSiddhiApps = deleteSiddhiApps(businessRule);

        // If BusinessRule is successfully deleted
        if(businessRuleFile.delete()){
            // If all SiddhiApps are deleted
            if(undeletedSiddhiApps == null){
                return businessRule;
            }
            throw new TemplateManagerException("Unable to delete following SiddhiApps : " + undeletedSiddhiApps.toString()); //todo: proper exception message
        }else{
            // If all SiddhiApps are deleted
            if(undeletedSiddhiApps == null){
                throw new TemplateManagerException("Unable to delete the Business Rule"); //todo: proper exception message
            }
            throw new TemplateManagerException("Unable to delete the Business Rule and the following SiddhiApps : " + undeletedSiddhiApps.toString()); //todo: proper exception message
        }
    }

    /**
     * Overwrites the existing Business Rule, that has the same name as the given Business Rule
     *
     * @param businessRule Given Business Rule
     */
    public void editBusinessRule(BusinessRule businessRule) throws TemplateManagerException {
        PrintWriter writer = null;
        try {
            // Same file name
            writer = new PrintWriter(TemplateManagerConstants.TEMPLATES_DIRECTORY + businessRule.getName() + ".json"); //todo: filename & BRname both are same
            // Overwrite file
            writer.println(TemplateManagerHelper.businessRuleToJson(businessRule));
        } catch (IOException e) {
            throw new TemplateManagerException(e.getMessage(), e.getCause()); //todo: proper exception message
        } finally {
            writer.close();
        }
    }

    /**
     * Returns a list of available Business Rules, from the directory
     *
     * @return List of Business Rule names, and denoting BusinessRule objects
     */
    public Map<String, BusinessRule> listBusinessRules() {
//        File directory = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY);
//        Map<String, BusinessRule> businessRules = new HashMap<String, BusinessRule>();
//
//        // array to store templates in directory
//        File[] files = directory.listFiles();
//
//        if (files != null) {
//            for (final File fileEntry : files) {
//                // If file is a valid json file
//                if (fileEntry.isFile() && fileEntry.getName().endsWith("json")) {
//                    // convert and store
//                    BusinessRule businessRule = TemplateManagerHelper.jsonToBusinessRule(fileEntry);
//                    if (businessRule != null) {
//                        try {
//                            TemplateManagerHelper.validateBusinessRule(businessRule);
//                        } catch (TemplateManagerException e) {
//                            //In case an invalid template configuration is found, this loader logs
//                            // an error message and aborts loading that particular template domain config.
//                            //However, this will load all the valid template domain configurations.
//                            log.error("Invalid Template Domain configuration file found: " + fileEntry.getName(), e);
//                        }
//                        businessRules.put(template.getName(), template);
//                    } else {
//                        log.error("Invalid Template Domain configuration file found: " + fileEntry.getName());
//                    }
//
//                }
//            }
//        }
//
//        return templates;
        return null;
    }

    /**
     * Saves the given root.Template to the directory
     *
     * @param template Given Template Object
     * @param fileName Given Name of the file when saving todo: filename - Should automatically get from template name / should be a parameter
     * @throws TemplateManagerException
     */
    public void addTemplate(Template template, String fileName) throws TemplateManagerException { //todo: this method won't be mostly there (deploying manually to a folder)
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(TemplateManagerConstants.TEMPLATES_DIRECTORY + fileName + ".json");
            writer.println(TemplateManagerHelper.templateToJson(template));
        } catch (IOException e) {
            throw new TemplateManagerException(e.getMessage(), e.getCause());
        } finally {
            writer.close();
        }
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
     * Returns a list of available Templates, from the directory
     *
     * @return List of Template names, and denoting Template objects
     */
    public Map<String, Template> listTemplates() {
//        File directory = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY);
//        Map<String, Template> templates = new HashMap<String, Template>();
//
//        // array to store templates in directory
//        File[] files = directory.listFiles();
//
//        if (files != null) {
//            for (final File fileEntry : files) {
//                // If file is a valid json file
//                if (fileEntry.isFile() && fileEntry.getName().endsWith("json")) {
//                    // convert and store
//                    Template template = TemplateManagerHelper.jsonToTemplate(fileEntry);
//                    if (template != null) {
//                        try {
//                            TemplateManagerHelper.validateTemplate(template);
//                        } catch (TemplateManagerException e) {
//                            //In case an invalid template configuration is found, this loader logs
//                            // an error message and aborts loading that particular template domain config.
//                            //However, this will load all the valid template domain configurations.
//                            log.error("Invalid Template Domain configuration file found: " + fileEntry.getName(), e);
//                        }
//                        templates.put(template.getName(), template);
//                    } else {
//                        log.error("Invalid Template Domain configuration file found: " + fileEntry.getName());
//                    }
//
//                }
//            }
//        }
//
//        return templates;
        return null;
    }

    /**
     * Returns a Business Rule, with given Template, name and given values for templated elements
     *
     * @param template         Given template
     * @param businessRuleName Given name for the Business Rule
     * @param propertyValues   Given values for templated elements (properties) in the template
     * @return Business Rule object
     */
    public BusinessRule createBusinessRuleFromTemplate(Template template, String businessRuleName, Map<String, String> propertyValues) {
//        // Value entered siddhiApps to store in Business Rule
//        ArrayList<String> valueEnteredSiddhiApps = new ArrayList<String>();
//
//        for (String siddhiApp : template.getSiddhiApps()) {
//            // To replace Templated Elements with given values
//            StringBuffer editableSiddhiApp = new StringBuffer();
//            // Find all templated elements from the siddhiApp
//            Pattern templatedElementPattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_REGEX_PATTERN);
//            Matcher templatedElementMatcher = templatedElementPattern.matcher(siddhiApp);
//
//            // When each templated element is found
//            while (templatedElementMatcher.find()) {
//                // Templated Element (inclusive of template pattern)
//                String templatedElement = templatedElementMatcher.group(1);
//                // Find Templated Element's Name
//                Pattern templatedElementNamePattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_NAME_REGEX_PATTERN);
//                Matcher templatedElementNameMatcher = templatedElementNamePattern.matcher(templatedElement);
//
//                // When the Templated Element's Name is found
//                if (templatedElementNameMatcher.find()) {
//                    // Templated Element's Name
//                    String templatedElementName = templatedElementNameMatcher.group(1);
//
//                    // Find whether a value is given for the templated element
//                    String elementReplacement = propertyValues.get(templatedElementName);
//                    // If value is given
//                    if (elementReplacement != null) {
//                        if (!elementReplacement.equals("")) {
//                            // Replace Element with given value
//                            templatedElementMatcher.appendReplacement(editableSiddhiApp, elementReplacement);
//                        } else {
//                            // Replace Element with default value
//                            templatedElementMatcher.appendReplacement(editableSiddhiApp, template.getDefaultValue(templatedElementName));
//                        }
//                    } else {
//                        // Replace Element with default value
//                        templatedElementMatcher.appendReplacement(editableSiddhiApp, template.getDefaultValue(templatedElementName));
//                    }
//                }
//
//            }
//            templatedElementMatcher.appendTail(editableSiddhiApp);
//            valueEnteredSiddhiApps.add(editableSiddhiApp.toString());
//        }
//
//        // Create a Business Rule
//        BusinessRule businessRule = new BusinessRule(businessRuleName, valueEnteredSiddhiApps);
//
//        return businessRule;
        return null;
    }

    /**
     * Returns a Business Rule, with the given name and set of Siddhi Apps
     *
     * @param businessRuleName Given name of Business Rule
     * @param siddhiApps       Given Siddhi Apps
     * @return Business Rule object
     */
    public BusinessRule createBusinessRuleFromScratch(String businessRuleName, Collection<String> siddhiApps) {
        BusinessRule businessRule = new BusinessRule(businessRuleName, siddhiApps);
        return businessRule; //todo : hold for now
    }
}
