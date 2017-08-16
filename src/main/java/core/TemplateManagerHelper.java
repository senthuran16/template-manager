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
            jsonObject = gson.fromJson(reader,JsonObject.class);
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
    public static RuleCollection jsonToRuleCollection(JsonObject jsonObject){
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
    public static RuleCollection jsonToRuleCollection(String jsonDefinition){
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
    public static BusinessRule jsonToBusinessRule(JsonObject jsonObject){
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
    public static BusinessRule jsonToBusinessRule(String jsonDefinition){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        BusinessRule businessRule = gson.fromJson(jsonDefinition, BusinessRule.class);

        return businessRule;
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
