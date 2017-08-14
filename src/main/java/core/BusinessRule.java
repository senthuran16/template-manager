package core;

import java.util.Collection;

/**
 * Java representation of a Business Rule
 */
public class BusinessRule {
    private String name;
    private Collection<String> siddhiApps;

    public BusinessRule(String name, Collection<String> siddhiApps) {
        this.name = name;
        this.siddhiApps = siddhiApps;
    }

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

    @Override
    public String toString() {
        return "BusinessRule{" +
                "\n\tname = '" + name + '\'' +
                ", \n\tsiddhiApps = " + siddhiApps +
                "\n}";
    }
}
