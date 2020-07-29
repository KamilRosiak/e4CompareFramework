package de.tu_bs.cs.isf.e4cf.core.application_model;

import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class FrameworkE4Application implements IApplication {
    private static FrameworkE4Application application;
    private Integer exit_code = IApplication.EXIT_OK;
    private E4Application e4Application;
    
    public static FrameworkE4Application getInstance() {
        return application;
    }
    
    public void setRestartCode() {
        exit_code = IApplication.EXIT_RESTART;
    }

    @Override
    public Object start(IApplicationContext context) throws Exception {
        application = this;
        e4Application = new E4Application();
        e4Application.start(context);
 
        return exit_code;
    }

    @Override
    public void stop() {
        e4Application.stop();
    }
}
