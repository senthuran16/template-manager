package core;

import java.util.Collection;
import java.util.Map;

/**
 * Java representation of a Business Rule
 */
public class BusinessRule {
    private String name;
    private Collection<String> siddhiApps; // todo: regex matching
    private Map<String,String> properties; //todo: are these always strings?
}
