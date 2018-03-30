package com.servicemonitor.bl.service;

import com.servicemonitor.bean.Service;

/**
 *
 * @author manuja
 */
public interface ServiceStatusRenderer {
    
    /**
    * To retrieve the current status text of the monitoring service screen
    * @param service
    * @return StatusEnum Status of the Service
    */    
    public String getStatusText(Service service);

}
