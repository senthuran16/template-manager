package root;

import services.TemplateManagerService;

/**
 * Singleton class for the exposed root.Template Manager Service
 */
public class TemplateManagerInstance {
    private TemplateManagerService templateManagerInstance;

    private TemplateManagerInstance(){

    }

    /**
     * @return Singleton root.Template Manager Service instance
     */
    public TemplateManagerService getInstance(){
        if(templateManagerInstance == null){
            templateManagerInstance = new TemplateManagerService();
        }
        return templateManagerInstance;
    }
}
