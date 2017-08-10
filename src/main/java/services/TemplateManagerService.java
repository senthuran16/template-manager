package services;

import core.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Exposed root.Template Manager service, that handles Templates and Business Rules
 */
public class TemplateManagerService implements TemplateManager, BusinessRulesService {
    public static void main(String[] args) throws TemplateManagerException {
        TemplateManagerService templateManagerService = new TemplateManagerService();
        TemplateManagerHelper templateManagerHelper = new TemplateManagerHelper();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("${inStream1}","TemperatureSensorReadings");
        map.put("${property1}","sensorName");
        map.put("${property2}","sensorValue");
        //map.put("${outStream1}","sensorReadingsCopy");
        map.put("${value1}","100");
        map.put("${outStream2}","highValueSensors");
        templateManagerService.createBusinessRuleFromTemplate(templateManagerHelper.jsonToTemplate("sample-template.json"), map);
    }
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

    /**
     * Returns a Business Rule, with given Template and given values for templated elements
     *
     * @param template Given template
     * @param givenValues Given Values for templated elements
     * @return Business Rule object
     */
    public BusinessRule createBusinessRuleFromTemplate(Template template, Map<String, String> givenValues) {
        // New siddhiApps to store in Business Rule
        ArrayList<String> valueEnteredSiddhiApps = new ArrayList<String>();

        // For each siddhiApp in given template
        for(String siddhiApp : template.getSiddhiApps()){
            StringBuffer stringBuffer = new StringBuffer();
            Pattern pattern = Pattern.compile(TemplateManagerConstants.TEMPLATED_ELEMENT_REGEX_PATTERN);
            Matcher templatedElementMatcher = pattern.matcher(siddhiApp);

            // When a templated element is found
            while (templatedElementMatcher.find()){
                // Find whether a value is given for the templated element
                String elementReplacement = givenValues.get(templatedElementMatcher.group(1));
                // If value is given
                if ((elementReplacement != null)){
                    // Replace with given value
                    templatedElementMatcher.appendReplacement(stringBuffer, elementReplacement);
                }else{
                    // todo: default value is specified as ${elementName} in the template. it should be just 'elementName'
                    // Replace with default value
                    templatedElementMatcher.appendReplacement(stringBuffer, template.getDefaultValue(templatedElementMatcher.group(1)));
                }

            }
            templatedElementMatcher.appendTail(stringBuffer);
            valueEnteredSiddhiApps.add(stringBuffer.toString());
        }

        // test
        for (String valueEnteredSiddhiApp : valueEnteredSiddhiApps) {
            System.out.println(valueEnteredSiddhiApp);
        }

        return null;
    }
}
