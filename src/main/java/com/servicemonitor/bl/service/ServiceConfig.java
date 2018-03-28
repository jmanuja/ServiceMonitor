package com.servicemonitor.bl.service;

import com.servicemonitor.enums.StatusEnum;
import com.servicemonitor.bean.Service;

/**
 *
 * @author manuja
 */
public interface ServiceConfig {
    
    /**
    * Pinging the Service
    * @return StatusEnum Status of the Service
    */
    public StatusEnum pingService(int port, String host) ;

}
