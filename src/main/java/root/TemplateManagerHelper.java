package root;

import java.io.File;

/**
 * Consists of methods for additional features for the exposed root.Template Manager service
 */
public class TemplateManagerHelper {

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
    public void templateToJson(Template template){
        // todo: refer gson and decide return type
        // todo: implement
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
    public void businessRuleToJson(BusinessRule businessRule){
        // todo: implement
        // todo : refer gson and decide return type
    }
}
