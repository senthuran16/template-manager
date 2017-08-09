package services;

import core.*;

import java.util.Collection;

/**
 * Consists of back end methods to handle Templates
 */
public interface TemplateManager {
    /**
     * Saves the given root.Template to the directory
     * @param template Given root.Template
     */
    public void addTemplate(Template template);

    /**
     * Deletes the root.Template with the given name, from the directory
     * @param templateName Given Name of root.Template
     * @return Deleted root.Template object
     */
    public Template deleteTemplate(String templateName);

    /**
     * Returns available Templates, from the directory
     * @return
     */
    public Collection<Template> listTemplates();
}
