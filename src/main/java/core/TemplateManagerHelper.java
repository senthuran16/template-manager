package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Consists of methods for additional features for the exposed root.Template Manager service
 */
public class TemplateManagerHelper {
    private static final Log log = LogFactory.getLog(TemplateManagerHelper.class);

    /**
     * To avoid instantiation
     */
    private TemplateManagerHelper() {

    }

    // todo : hardcoded test. remove main()
    public static void main(String[] args) throws TemplateManagerException {
        TemplateManagerHelper t = new TemplateManagerHelper();

        // A template object //////
        ArrayList<String> siddhiApps = new ArrayList<String>();
        siddhiApps.add("siddhiApp1");
        siddhiApps.add("siddhiApp2");

        Map<String, String> property1 = new HashMap<String, String>();
        property1.put("defaultValue", "Test Temperature");
        property1.put("type", "String");
        Map<String, String> property2 = new HashMap<String, String>();
        property2.put("defaultValue", "Sample Description");
        property2.put("type", "String");

        Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
        properties.put("sensorName", property1);
        properties.put("sensorDescription", property2);

        Template tem = new Template("Template Java Object", "source", 7, 2, "Sample Template Java object", "javascript goes here", siddhiApps, properties);
        ///////////////////////////

        // To store converted json as string
        String jsonRepresentation;

        System.out.println("=======================");
        System.out.println("Template Object to JSON");
        System.out.println("=======================");
        jsonRepresentation = t.templateToJson(tem);
        System.out.println(jsonRepresentation);

        // Sample JSON file's name
        File jsonFile = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY + "sample-template.json");

        System.out.println("\n=======================");
        System.out.println("JSON to Template Object");
        System.out.println("=======================");
        System.out.println(t.jsonToTemplate(jsonFile));
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
     * @param template Given Template file
     * @throws TemplateManagerException
     */
    public static void validateTemplate(Template template) throws TemplateManagerException {
        if (template.getName() == null ||
                template.getMaximumInstances() <= 0 ||
                template.getMaximumNumberOfNodes() <= 0 ||
                template.getJavascript() == null ||
                template.getSiddhiApps() == null ||
                template.getProperties() == null) {
            throw new TemplateManagerException("Invalid Template found. Please check the definitions"); //todo: invalid template found error message?
        }
    }

    /**
     * Converts given JSON Template file to a Java Template object
     *
     * @param jsonFile Given JSON file
     * @return Template object
     * @throws TemplateManagerException
     */
    public static Template jsonToTemplate(File jsonFile) {
        Template template = null;
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader(jsonFile);
            template = gson.fromJson(reader, Template.class);
        } catch (IOException e) {
            log.error("IO Exception occurred when converting JSON definition to Template", e); //todo: IO exception occured. error message?
        }

        return template;
    }

    /**
     * Converts a Java Template object to a JSON Template String
     *
     * @param template Given Template object
     * @return Converted JSON object String
     */
    public static String templateToJson(Template template) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create(); // Avoid escaping < > etc as unicode characters & Pretty json indentation
        String jsonObject = gson.toJson(template);

        return jsonObject;
    }

    /**
     * Converts given JSON Business Rule file to a Java BusinessRule object
     *
     * @param jsonFile Given JSON file
     * @return BusinessRule object
     * @throws TemplateManagerException
     */
    public static BusinessRule jsonToBusinessRule(File jsonFile) {
        BusinessRule businessRule = null;
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader(jsonFile);
            businessRule = gson.fromJson(reader, BusinessRule.class);
        } catch (IOException e) {
            log.error("IO Exception occurred when converting JSON definition to Business Rule", e); //todo: IO Exception occured. Error message?
        }

        return businessRule;
    }

    /**
     * Converts a Java BusinessRule object to a JSON Business Rule String
     *
     * @param businessRule Given BusinessRule object
     * @return Converted JSON object String
     */
    public static String businessRuleToJson(BusinessRule businessRule) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // for pretty json indentation
        String jsonObject = gson.toJson(businessRule);

        return jsonObject;
    }
}
