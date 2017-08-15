package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
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
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n");
    }

    public static JsonObject stringToJson(String jsonDefinition){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonObject jsonObject = gson.fromJson(jsonDefinition,JsonObject.class);

        return jsonObject;
    }

    public static JsonArray stringToJsonArray(String jsonDefinition){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonArray jsonArray = gson.fromJson(jsonDefinition,JsonArray.class);

        return jsonArray;
    }

    public static JsonObject fileToJson(File jsonFile){
        return null;
    }

    public static RuleCollection jsonToRuleCollection(String jsonDefinition){
        JsonObject fullJSON = stringToJson(jsonDefinition);

        JsonObject ruleCollectionJSON = stringToJson(fullJSON.get("ruleCollection").toString());
        System.out.println("RuleCollection = " + ruleCollectionJSON.toString());

        // RuleCollection name
        String ruleCollectionName = ruleCollectionJSON.get("name").toString();
        System.out.println("RULE COLLECTION : "+ruleCollectionName);

        JsonArray ruleTemplatesJSON = stringToJsonArray(ruleCollectionJSON.get("ruleTemplates").toString());

        System.out.println("Rule Templates = "+ruleTemplatesJSON);

        ArrayList<JsonObject> ruleTemplates = new ArrayList();

        // Convert from JSON array to Java array
        for (int i = 0; i < ruleTemplatesJSON.size(); i++) {
             ruleTemplates.add(stringToJson(ruleTemplatesJSON.get(i).toString()));
        }
        for (JsonObject ruleTemplate : ruleTemplates) {
            System.out.println("-" + ruleTemplate);
        }

        // Go into each RuleTemplate
        for(JsonObject ruleTemplate : ruleTemplates){
            System.out.println("RULE TEMPLATE : "+ruleTemplate.get("name"));
            //ruleTemplate.get("type");
            //ruleTemplate.get("instanceCount");
            //ruleTemplate.get("script"); // Optional
            //ruleTemplate.get("description"); // Optional

            // Templates
            JsonArray templatesJSON = stringToJsonArray(ruleTemplate.get("templates").toString());

            ArrayList<JsonObject> templates = new ArrayList();

            // Convert from JSON array to Java array
            for (int i = 0; i < templatesJSON.size(); i++) {
                templates.add(stringToJson(templatesJSON.get(i).toString()));
            }
            for (JsonObject template : templates) {
                System.out.println("-" + template);
            }

            // Properties
            JsonObject propertiesJSON = stringToJson(ruleTemplate.get("properties").toString());

            //System.out.println(propertiesJSON.entrySet());

            // Key : Value
//            Map<String,String>




        }

        return null;
    }
}
