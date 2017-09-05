package internal.util;

/**
 * Consists of constants related to root.Template Manager
 */
public class TemplateManagerConstants {
    // Directory locations
    public static final String TEMPLATES_DIRECTORY = "/home/senthuran/Desktop/rough-templates/"; // todo: not finalized

    // Pattern of templated elements in Templates
    public static final String TEMPLATED_ELEMENT_REGEX_PATTERN = "(\\$\\{[^}]+\\})"; // ${templatedElement}
    public static final String TEMPLATED_ELEMENT_NAME_REGEX_PATTERN = "\\$\\{(\\S+)\\}"; // to extract element name, from [element with template pattern]

    // Pattern of Template names
    public static final String SIDDHI_APP_NAME_REGEX_PATTERN = "@App:name\\[[\\\",\\\'](\\S+)[\\\",\\\']\\]"; // @App:name("SiddhiAppName")

    // Template types
    public static final String SIDDHI_APP_TEMPLATE_TYPE = "siddhiApp";
    public static final String SOURCE = "source";
    public static final String SINK = "sink";
    public static final String GADGET = "gadget";
    public static final String DASHBOARD = "dashboard";



}
