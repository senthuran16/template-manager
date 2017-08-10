package core;

import java.util.Collection;
import java.util.Map;

/**
 * Java Representation of a root.Template
 */
public class Template {
    private String name;
    private int maximumInstances;
    private String description;
    private String javascript; // todo: revise whether String or not
    private Collection<String> siddhiApps;
    private Map<String, Map<String, String>> properties; // { {propertyName : {defaultValue:xxx, type:yyy}} , ...}

    // TODO : remove after rough testing
    public Template(String name, int maximumInstances, String description, String javascript, Collection<String> siddhiApps, Map<String, Map<String, String>> properties) {
        this.name = name;
        this.maximumInstances = maximumInstances;
        this.description = description;
        this.javascript = javascript;
        this.siddhiApps = siddhiApps;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaximumInstances() {
        return maximumInstances;
    }

    public void setMaximumInstances(int maximumInstances) {
        this.maximumInstances = maximumInstances;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJavascript() {
        return javascript;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    public Collection<String> getSiddhiApps() {
        return siddhiApps;
    }

    public void setSiddhiApps(Collection<String> siddhiApps) {
        this.siddhiApps = siddhiApps;
    }

    public Map<String, Map<String, String>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Map<String, String>> properties) {
        this.properties = properties;
    }

    /**
     * Returns default value of a property
     * @param propertyName Given property name
     * @return Default value for the given property
     */
    public String getDefaultValue(String propertyName){
        String defaultValue = this.properties.get(propertyName).get("defaultValue");
        return defaultValue;
    }

    @Override
    public String toString() {
        return "Template{" +
                "\n\tname='" + name + '\'' +
                ", \n\tmaximumInstances=" + maximumInstances +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tjavascript='" + javascript + '\'' +
                ", \n\tsiddhiApps=" + siddhiApps +
                ", \n\tproperties=" + properties +
                "\n}";
    }
}
