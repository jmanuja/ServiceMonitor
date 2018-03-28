/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicemonitor.bl.service;

import com.servicemonitor.bean.Service;

/**
 *
 * @author manuja
 */
public interface ServicePublisher {
    
    /**
    * Starting all Services
    */
    public void startAllServices();
    
    /**
    * Starting a Service
    */
    public void startService(Service service);
    
    
}
