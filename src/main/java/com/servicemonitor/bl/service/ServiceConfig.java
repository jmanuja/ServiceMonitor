package com.servicemonitor.bl.service;

import com.servicemonitor.enums.StatusEnum;

/**
 *
 * @author manuja
 */
public interface ServiceConfig {
    
    /**
    * Pinging the Service
    * @param port - Port number of the host to connect
    * @param host - Host name to connect
    * @return StatusEnum - Status of the Service
    */
    public StatusEnum pingService(int port, String host);

}
