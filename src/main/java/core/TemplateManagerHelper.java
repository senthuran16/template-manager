package core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Consists of methods for additional features for the exposed root.Template Manager service
 */
public class TemplateManagerHelper {

    // todo : hardcoded test. remove main()
    public static void main(String[] args) throws TemplateManagerException {
        TemplateManagerHelper t = new TemplateManagerHelper();

        // A template object //////
        ArrayList<String> siddhiApps = new ArrayList<String>();
        siddhiApps.add("siddhiApp1");
        siddhiApps.add("siddhiApp2");

        Map<String,String> property1 = new HashMap<String, String>();
        property1.put("defaultValue","Test Temperature");
        property1.put("type","String");
        Map<String,String> property2 = new HashMap<String, String>();
        property2.put("defaultValue","Sample Description");
        property2.put("type","String");

        Map<String,Map<String,String>> properties = new HashMap<String, Map<String, String>>();
        properties.put("sensorName",property1);
        properties.put("sensorDescription",property2);

        Template tem = new Template("Template Java Object",7,"Sample Template Java object","javascript goes here",siddhiApps,properties);
        ///////////////////////////

        // To store converted json as string
        String jsonRepresentation;

        System.out.println("=======================");
        System.out.println("Template Object to JSON");
        System.out.println("=======================");
        jsonRepresentation = t.templateToJson(tem);
        System.out.println(jsonRepresentation);

        // Sample JSON file's name
        String fileName = "sample-template.json";

        System.out.println("\n=======================");
        System.out.println("JSON to Template Object");
        System.out.println("=======================");
        System.out.println(t.jsonToTemplate(fileName));
    }

    /**
     * Converts given JSON Template to a Template object
     * @param jsonFileName Given JSON file's name //todo: or should give the file itself?
     * @return Template object
     * @throws TemplateManagerException
     */
    public Template jsonToTemplate(String jsonFileName) throws TemplateManagerException {
        String jsonFilePath = TemplateManagerConstants.TEMPLATES_DIRECTORY+jsonFileName;

        Template template;
        Gson gson = new Gson();

        try {
            Reader reader = new FileReader(jsonFilePath);
            // Convert to Java Object
            template = gson.fromJson(reader, Template.class);
        } catch (IOException e) {
            throw new TemplateManagerException(e.getMessage(),e.getCause());
        }

        return template;
    }

    /**
     * Converts a root.Template object to a JSON object
     * @param template Given root.Template object
     * @return Converted JSON object as a String
     */
    public String templateToJson(Template template){
        // todo: is return type String ok?
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // for pretty json indentation
        // Convert to JSON
        String jsonObject = gson.toJson(template);

        return jsonObject;
    }

    /**
     * Converts a JSON file to a Business Rule object
     * @param jsonFile Given JSON file
     * @return Business Rule object
     */
    public BusinessRule jsonToBusinessRule(String jsonFileName){
        // todo: as same as jsonToTemplate()
        return null;
    }

    /**
     * Converts a Business Rule object to a JSON object
     * @param businessRule Given Business Rule object
     * @return Converted JSON object as a String
     */
    public String businessRuleToJson(BusinessRule businessRule){
        // todo: as same as templateToJson()
        return null;
    }
}
