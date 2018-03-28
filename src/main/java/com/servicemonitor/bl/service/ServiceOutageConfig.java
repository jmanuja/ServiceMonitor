package com.servicemonitor.bl.service;

import com.servicemonitor.enums.StatusEnum;
import com.servicemonitor.bean.MonitorService;

/**
 *
 * @author manuja
 */
public interface ServiceOutageConfig {
    /**
    * Check the Service is Set outage Time and current status
    * @return StatusEnum Status of the Service
    * OUTAGE if service currently on outage period else null
    */    
    public Integer checkIsOnOutageTimer(MonitorService registeredService);
}
