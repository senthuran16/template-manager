package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import netscape.javascript.JSObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

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
        File jsonFile = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY + "sensorDataAnalysis.json");
        //RuleCollection ruleCollection = jsonToRuleCollection(jsonFile);

        //System.out.println(jsonToRuleCollection(jsonFile).get("ruleCollection").get("name"));

        jsonToRuleCollection("{\n" +
                "  \"ruleCollection\" : {\n" +
                "    \"name\" : \"SensorDataAnalysis\",\n" +
                "    \"ruleTemplates\" : [\n" +
                "      {\n" +
                "        \"name\" : \"SensorAnalytics\" ,\n" +
                "        \"type\" : \"<app>\",\n" +
                "        \"instanceCount\" : \"many\",\n" +
                "        \"script\" : \"<script> (optional)\",\n" +
                "        \"description\" : \"Configure a sensor analytics scenario to display statistics for a given stream of your choice\",\n" +
                "        \"templates\" : [\n" +
                "          { \"type\" : \"siddhiApp\", \"content\" : \"<from ${inStream1} select ${property1} insert into ${outStream1}>\" },\n" +
                "          { \"type\" : \"siddhiApp\", \"content\" : \"<from ${inStream1} select ${property2} insert into ${outStream2}>\" }\n" +
                "        ],\n" +
                "        \"properties\" : {\n" +
                "          \"property1\" : {\"description\" : \"Unique Identifier for the sensor\", \"defaultValue\" : \"sensorName\" , \"type\" : \"options\", \"options\" : [\"sensorID\",\"sensorName\"]},\n" +
                "          \"property2\" : {\"description\" : \"Type of value, the sensor measures\", \"defaultValue\" : \"sensorValue\" , \"type\" : \"String\"}\n" +
                "        }\n" +
                "      },\n" +
                "    ]\n" +
                "  }\n" +
                "}\n");


    }

    /**
     * Converts Given JSON Element to JSON Object
     * @param jsonElement Given JSON Element
     * @return JSON Object
     */
    public static JsonObject jsonElementToJsonObject(JsonElement jsonElement){
        String jsonElementString = jsonElement.toString();
        Gson gson = new Gson();
        JsonObject  jsonObject = gson.fromJson(jsonElementString, JsonObject.class);

        return jsonObject;
    }

    /**
     * Converts given JSON File to a JSON object
     *
     * @param jsonFile Given JSON File
     * @return JSON object
     */
    public static JsonObject fileToJsonObject(File jsonFile) {
        Gson gson = new Gson();
        JsonObject jsonObject = null;

        try {
            Reader reader = new FileReader(jsonFile);
            jsonObject = gson.fromJson(reader,JsonObject.class);
        } catch (FileNotFoundException e) {
            log.error("FileNotFound Exception occurred when converting JSON file to JSON Object", e); //todo: FileNotFound exception occured. error message?
        }

        return jsonObject;
    }


    /**
     * Converts given JSON String to a JSON object
     *
     * @param jsonDefinition Given JSON Definition String
     * @return RuleCollection object
     */
    public static JsonObject stringToJsonObject(String jsonDefinition) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonDefinition,JsonObject.class);
        return jsonObject;
    }

    public static ArrayList<String> stringToJsonArrayList(String jsonDefinition) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        ArrayList<String> jsonArrayList = gson.fromJson(jsonDefinition,ArrayList.class);
        return jsonArrayList;
    }

    public static RuleCollection jsonToRuleCollection(String jsonDefinition){
//        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
//        RuleCollection ruleCollection = new RuleCollection();
//        // Get the JSON object denoted by "ruleCollection", from given JSON definition
//        JsonObject ruleCollectionJSON = stringToJsonObject(stringToJsonObject(jsonDefinition).get("ruleCollection").toString());
//
//        ruleCollection.setName(ruleCollectionJSON.get("name").toString());
//
//        // rule templates array
//        Collection<String> ruleTemplatesJSON = stringToJsonArrayList(ruleCollectionJSON.get("ruleTemplates").toString());
//
//        // templates array
//        Collection<String> templatesJSON = stringToJsonArrayList(ruleTemplatesJSON.);
//
//        System.out.println("ruleCollectionName : "+ruleCollection.getName());
//        System.out.println("ruleTemplates : "+ruleTemplatesJSON);
//        System.out.println("templates : ");

        return null;
    }

    /**
     * Converts given JSON String to a RuleCollection object
     *
     * @param jsonDefinition Given JSON String
     * @return RuleCollection object
     */
    public static RuleCollection jsonToRuleCollectione(String jsonDefinition) {
        RuleCollection ruleCollection = null;
        Gson gson = new Gson();
        ruleCollection = gson.fromJson(jsonDefinition, RuleCollection.class);

        return ruleCollection;
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
//        if (template.getName() == null ||
//                template.getMaximumInstances() <= 0 ||
//                template.getMaximumNumberOfNodes() <= 0 ||
//                template.getJavascript() == null ||
//                template.getSiddhiApps() == null ||
//                template.getProperties() == null) {
//            throw new TemplateManagerException("Invalid Template found. Please check the definitions"); //todo: invalid template found error message?
//        }
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

    /**
     * Checks whether a given Template file has valid content.
     * Validation criteria : //todo: confirm validation criteria for templates
     * - name is compulsory
     * - Has at least one SiddhiApp
     *
     * @param businessRule Given BusinessRule object
     * @throws TemplateManagerException
     */
    public static void validateBusinessRule(BusinessRule businessRule) throws TemplateManagerException {

    }
}
