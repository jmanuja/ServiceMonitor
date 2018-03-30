package com.servicemonitor.bl.service;

import com.servicemonitor.bean.Service;

/**
 *
 * @author manuja
 */
public interface ServicePublisher {
    
    /**
    * Starting all services
    */
    public void startAllServices();
    
    /**
    * Starting the service specified
    * @param service 
    */
    public void startService(Service service);
    
    
}
