package core;

import services.TemplateManagerServiceOldOld;

/**
 * Singleton class for the exposed root.Template Manager Service
 */
public class TemplateManagerInstance {
    private TemplateManagerServiceOldOld templateManagerInstance;

    private TemplateManagerInstance() {

    }

    /**
     * @return Singleton root.Template Manager Service instance
     */
    public TemplateManagerServiceOldOld getInstance() {
        if (templateManagerInstance == null) {
            templateManagerInstance = new TemplateManagerServiceOldOld();
        }
        return templateManagerInstance;
    }
}
