package com.servicemonitor.main;


import com.servicemonitor.bl.service.ServicePublisher;
import com.servicemonitor.ui.MonitorFrame;

import javax.swing.SwingUtilities;
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
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override public void run()
                {   
                    MonitorFrame window = new MonitorFrame();
                    window.setVisible(true);
                    getInstance().getAllservicer();
                }
            });
            
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }
        
    public void getAllservicer(){
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONTEXT_PATH);
        ServicePublisher publisher = (ServicePublisher) applicationContext.getBean("publisher");	
        publisher.startAllServices();
    }
    
    public void addservicer(){
      //  runningServiceMap.put(runningServiceMap.size()+1+"", new Service());
    }
     
    public void startAllServices(){
        try {
            
        } catch (Exception e) {
        }
    }
}
