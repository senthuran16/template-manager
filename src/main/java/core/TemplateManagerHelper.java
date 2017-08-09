package core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Consists of methods for additional features for the exposed root.Template Manager service
 */
public class TemplateManagerHelper {

    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<String>();
        a.add("siddhiApp1");
        a.add("siddhiApp2");

        Map<String,String> m1 = new HashMap<String, String>();
        m1.put("defaultValue","TestTemperature");
        m1.put("type","String");
        Map<String,String> m2 = new HashMap<String, String>();
        m2.put("defaultValue","SampleDescription");
        m2.put("type","String");

        Map<String,Map<String,String>> m = new HashMap<String, Map<String, String>>();

        m.put("sensorName",m1);
        m.put("sensorDescription",m2);


        Template tem = new Template("tem1",7,"Sample template","<javascript",a,m);

        TemplateManagerHelper t = new TemplateManagerHelper();
        System.out.println(t.templateToJson(tem));
    }

    /**
     * Converts a JSON file to a root.Template object
     * @param jsonFile Given JSON file
     * @return root.Template object
     */
    public Template jsonToTemplate(File jsonFile){
        // todo: implement
        return null;
    }

    /**
     * Converts a root.Template object to a JSON file
     * @param template Given root.Template object
     */
    public String templateToJson(Template template){
        // todo: refer gson and decide return type
        // todo: implement
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonObject = gson.toJson(template);

        return jsonObject;
    }

    /**
     * Converts a JSON file to a Business Rule object
     * @param jsonFile Given JSON file
     * @return Business Rule object
     */
    public BusinessRule jsonToBusinessRule(File jsonFile){
        // todo: implement
        return null;
    }

    /**
     * Converts a Business Rule object to a JSON file
     * @param businessRule Given Business Rule object
     */
    public String businessRuleToJson(BusinessRule businessRule){
        // todo: implement
        // todo : refer gson and decide return type
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonObject = gson.toJson(businessRule);

        return jsonObject;
    }
}
