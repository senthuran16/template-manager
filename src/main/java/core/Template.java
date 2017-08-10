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
