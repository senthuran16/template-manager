package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Consists of methods for additional features for the exposed root.Template Manager service
 */
public class TemplateManagerHelper {
    //private static final Log log = LogFactory.getLog(TemplateManagerHelper.class);

    /**
     * To avoid instantiation
     */
    private TemplateManagerHelper() {

    }

    // todo : hardcoded test. remove main()
    public static void main(String[] args) throws TemplateManagerException {
        File jsonFile = new File(TemplateManagerConstants.TEMPLATES_DIRECTORY + "sensorDataAnalysis.json");
        //RuleCollection ruleCollection = jsonToRuleCollection(jsonFile);

        System.out.println(TemplateManagerHelper.jsonToRuleCollection(TemplateManagerHelper.fileToJson(jsonFile)));
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
     * Converts given JSON object to RuleCollection object
     *
     * @param jsonObject Given JSON object
     * @return RuleCollection object
     */
    public static RuleCollection jsonToRuleCollection(JsonObject jsonObject) {
        String ruleCollectionJsonString = jsonObject.get("ruleCollection").toString();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        RuleCollection ruleCollection = gson.fromJson(ruleCollectionJsonString, RuleCollection.class);

        return ruleCollection;
    }

    /**
     * Converts given String JSON definition to RuleCollection object
     *
     * @param jsonDefinition Given String JSON definition
     * @return RuleCollection object
     */
    public static RuleCollection jsonToRuleCollection(String jsonDefinition) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        RuleCollection ruleCollection = gson.fromJson(jsonDefinition, RuleCollection.class);

        return ruleCollection;
    }

    /**
     * Converts given JSON object to BusinessRule object
     *
     * @param jsonObject Given JSON object
     * @return BusinessRule object
     */
    public static BusinessRule jsonToBusinessRule(JsonObject jsonObject) {
        String businessRuleJsonString = jsonObject.get("businessRule").toString();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        BusinessRule businessRule = gson.fromJson(businessRuleJsonString, BusinessRule.class);

        return businessRule;
    }

    /**
     * Converts given String JSON definition to RuleCollection object
     *
     * @param jsonDefinition Given String JSON definition
     * @return RuleCollection object
     */
    public static BusinessRule jsonToBusinessRule(String jsonDefinition) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        BusinessRule businessRule = gson.fromJson(jsonDefinition, BusinessRule.class);

        return businessRule;
    }

    /**
     * Checks whether a given RuleCollection object has valid content
     * Validation criteria : //todo: confirm
     * - name is available
     * - At least one ruleTemplate is available
     *
     * @param ruleCollection
     * @throws TemplateManagerException
     */
    public static void validateRuleCollection(RuleCollection ruleCollection) throws TemplateManagerException {
        if (ruleCollection.getName() == null) {
            throw new TemplateManagerException("Invalid RuleCollection configuration file found");
        }
        if (!(ruleCollection.getRuleTemplates().size() > 0)) {
            throw new TemplateManagerException("Invalid RuleCollection configuration file found");
        }
    }

    /**
     * Checks whether a given RuleTemplate object has valid content
     * Validation Criteria : todo: cofirm validation criteria for RuleTemplate
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
}
