package core;

import java.util.Collection;
import java.util.Map;

/**
 * Java representation of a Business Rule
 */
public class BusinessRule {
    private String name;
    private Collection<String> siddhiApps;
    private Map<String,String> properties; //todo: are these always strings?

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getSiddhiApps() {
        return siddhiApps;
    }

    public void setSiddhiApps(Collection<String> siddhiApps) {
        this.siddhiApps = siddhiApps;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
