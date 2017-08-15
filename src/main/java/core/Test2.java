package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Test2 {

    public static void main(String[] args) {
        todo("{\n" +
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
                "      {\n" +
                "        \"name\" : \"SensorLoggings\" ,\n" +
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

    public static RuleCollectionMapper todo(String json){
        Gson outerGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        // RuleCollection referred by "ruleCollectionJson"
        JsonObject ruleCollectionJson = stringToJson(outerGson.fromJson(json, JsonObject.class).get("ruleCollection").toString());
        System.out.println(ruleCollectionJson);

        Gson ruleCollectionGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        RuleCollectionMapper ruleCollectionMapper = ruleCollectionGson.fromJson(ruleCollectionJson.toString(),RuleCollectionMapper.class);

        // ruleCollection:
        System.out.println(ruleCollectionMapper.toString());

        Collection<RuleTemplateMapper> ruleTemplates = ruleCollectionMapper.getRuleTemplates();

        System.out.println(ruleTemplates);

        for (Object ruleTemplate : ruleTemplates) {
            System.out.println(ruleTemplate+"$$$$$$$$$$$$$$$$$$$$$$$");
        }

        return null;
    }

    public static RuleCollection jsonToRuleCollection(String jsonDefinition){

        Gson outerGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        // RuleCollection referred by "ruleCollectionJson"
        JsonObject ruleCollectionJson = stringToJson(outerGson.fromJson(jsonDefinition, JsonObject.class).get("ruleCollection").toString());

        Gson ruleCollectionGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        RuleCollectionMapper ruleCollectionMapper = ruleCollectionGson.fromJson(ruleCollectionJson.toString(),RuleCollectionMapper.class);
        System.out.println(ruleCollectionMapper);

        ArrayList<RuleTemplate> ruleTemplates = new ArrayList();
//        for (String ruleTemplate : ruleCollectionMapper.getRuleTemplates()) {
//            Gson ruleTemplateGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
//            RuleTemplateMapper ruleTemplateMapper = ruleTemplateGson.fromJson(ruleTemplate,RuleTemplateMapper.class);
//
//            // Templates
//            ArrayList<Template> templates = new ArrayList();
//            for(Map<String,String> templateMap : ruleTemplateMapper.getTemplates()){
//                Template template = new Template(templateMap.get("type"), templateMap.get("content"));
//
//                // Add template to arraylist
//                templates.add(template);
//            }
//
//            // Properties
//            Map<String,Property> properties = new HashMap();
//            for(String propertyName : ruleTemplateMapper.getProperties().keySet()){
//                String propertyValue = ruleTemplateMapper.getProperties().get(propertyName);
//
//                Gson propertyGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
//                Property property = propertyGson.fromJson(propertyValue,Property.class);
//
//                // Add property to arraylist
//                properties.put(propertyName, property);
//            }
//
//            RuleTemplate ruleTemplateObject = new RuleTemplate(
//                        ruleTemplateMapper.getName(),
//                        ruleTemplateMapper.getType(),
//                        ruleTemplateMapper.getInstanceCount(),
//                        ruleTemplateMapper.getScript(),
//                        ruleTemplateMapper.getDescription(),
//                        templates,
//                        properties
//                    );
//
//            ruleTemplates.add(ruleTemplateObject);
//        }
//
//        RuleCollection ruleCollection = new RuleCollection(ruleCollectionMapper.getName(),ruleTemplates);
//
//        // RuleCollection name
//
//        System.out.println(ruleCollection);
        return null;
    }
}

class RuleCollectionMapper{
    private String name;
    private Collection<RuleTemplateMapper> ruleTemplates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<RuleTemplateMapper> getRuleTemplates() {
        return ruleTemplates;
    }

    public void setRuleTemplates(Collection<RuleTemplateMapper> ruleTemplates) {
        this.ruleTemplates = ruleTemplates;
    }

    @Override
    public String toString() {
        return "RuleCollectionMapper{" +
                "name='" + name + '\'' +
                ", ruleTemplates=" + ruleTemplates +
                '}';
    }
}

class RuleTemplateMapper{
    private String name;
    private String type;
    private String instanceCount;
    private String script;
    private String description;
    private Collection<Map<String,String>> templates;
    private Map<String,Map<String,String>> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(String instanceCount) {
        this.instanceCount = instanceCount;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Map<String, String>> getTemplates() {
        return templates;
    }

    public void setTemplates(Collection<Map<String, String>> templates) {
        this.templates = templates;
    }

    public Map<String, Map<String, String>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Map<String, String>> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "RuleTemplateMapper{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", instanceCount='" + instanceCount + '\'' +
                ", script='" + script + '\'' +
                ", description='" + description + '\'' +
                ", templates=" + templates +
                ", properties=" + properties +
                '}';
    }
}