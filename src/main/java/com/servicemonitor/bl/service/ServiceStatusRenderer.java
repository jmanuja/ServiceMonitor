package com.servicemonitor.bl.service;

import com.servicemonitor.bean.Service;

/**
 *
 * @author manuja
 */
public interface ServiceStatusRenderer {
    
    public String getStatusText(Service service);

}
