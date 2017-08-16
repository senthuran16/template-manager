package core;

import services.TemplateManagerService;
import services.TemplateManagerServiceOldOld;

/**
 * Singleton class for the exposed root.Template Manager Service
 */
public class TemplateManagerInstance {
    private static TemplateManagerService templateManagerInstance;

    private TemplateManagerInstance() {

    }

    /**
     * @return Singleton root.Template Manager Service instance
     */
    public static TemplateManagerService getInstance() {
        if (templateManagerInstance == null) {
            templateManagerInstance = new TemplateManagerService();
        }
        return templateManagerInstance;
    }
}
