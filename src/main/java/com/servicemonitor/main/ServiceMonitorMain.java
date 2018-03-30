package com.servicemonitor.main;


import com.servicemonitor.bl.service.ServicePublisher;
import com.servicemonitor.ui.MonitorFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author manuja
 * Main Class to execute the service monitor
 */
public class ServiceMonitorMain {
    public static final String CONTEXT_PATH 	= "application_context.xml";
    private static ServiceMonitorMain serviceMonitorMain;														//Static instance of this

    public static ServiceMonitorMain getInstance(){
        if(serviceMonitorMain==null){
            serviceMonitorMain= new ServiceMonitorMain();
        }
        return serviceMonitorMain;
    }
    
    public static void main(String[] args)
    {
        try {
            //Set Jtottoo Theam
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            
            SwingUtilities.invokeLater(() -> {
                MonitorFrame window = new MonitorFrame();
                window.setVisible(true);
                getInstance().initServices();
            });
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }
        
    public void initServices(){
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONTEXT_PATH);
        ServicePublisher publisher = (ServicePublisher) applicationContext.getBean("publisher");	
        publisher.startAllServices();
    }
    
}

