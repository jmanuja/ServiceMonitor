/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interview.servicemonitor.service;

import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.impl.ServicePublisherWorker;
import com.servicemonitor.enums.StatusEnum;
import java.util.Calendar;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;

/**
 *
 * @author manuja
 */
public class ServicePublisherTest {

     /**
     * Test of pingService method, of class ServiceCheckSwingWorker.
     */
    @org.junit.Test
    public void testPingService() {
        Service service = new Service();
        ServicePublisherWorker instance =null;
        
        //Test When Server is UP
        service.setHost("www.google.lk");
        service.setPort(80);
        service.setServiceName("Google");
        service.setPollingFrequency(1000*60);
        instance =new ServicePublisherWorker(service);
        StatusEnum expResultUp = StatusEnum.UP;
        StatusEnum resultUp = instance.pingService(service.getPort(),service.getHost());
        assertEquals(expResultUp, resultUp);

        //Test When Server is Not working
        service.setHost("www.notworking.lk");
        service.setPort(123);
        service.setServiceName("NotWorking");
        service.setPollingFrequency(1000*20);
        service.setGracePeriod(1000*20);
        instance =new ServicePublisherWorker(service);
        StatusEnum expResult = StatusEnum.DOWN;
        StatusEnum resultdown = instance.pingService(service.getPort(),service.getHost());
        assertEquals(expResult, resultdown);
    }    
}
