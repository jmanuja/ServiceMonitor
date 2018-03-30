/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interview.servicemonitor.service;

import com.servicemonitor.bean.MonitorService;
import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.impl.ServiceSubscriberWorker;
import com.servicemonitor.enums.StatusEnum;
import java.util.Calendar;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;

/**
 *
 * @author manuja
 */
public class ServiceSubscriberTest {

    /**
     * Test of checkIsOnOutageTimer method, of class ServiceCheckSwingWorker.
     */
    @org.junit.Test
    public void testCheckIsOnOutageTimer() {
        System.out.println("Check is on Outage");
        MonitorService service = new MonitorService();
        ServiceSubscriberWorker instance =null;
        
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.MONTH, Calendar.JANUARY);
        now.set(Calendar.YEAR, 1970);
            
        //Test When Server is Outage
        service.setHost("www.google.lk");
        service.setPort(80);
        service.setServiceName("Google");
        service.setPollingFrequency(1000*60);
        //Set Outage Start Time 2 minutes earlier current time
        now.add(Calendar.MINUTE, -2);
        service.setOutageStartTime(now.getTime());
        //Set Outage End Time 1 minutes after current time        
        now.add(Calendar.MINUTE, 3);        
        service.setOutageEndTime(now.getTime());
        
        instance =new ServiceSubscriberWorker(null,service);
        Integer expResultUp = StatusEnum.OUTAGE.getId();
        Integer resultUp = instance.checkIsOnOutageTimer(service);
        assertEquals(expResultUp, resultUp);

                
       //Test When Server is not in Outage timer
        service.setHost("www.google.lk");
        service.setPort(80);
        service.setServiceName("Google");
        service.setPollingFrequency(1000*60);
        //Set Outage Start Time 10 minutes after current time
        now.add(Calendar.MINUTE, 10);
        service.setOutageStartTime(now.getTime());
        //Set Outage End Time 20 minutes after current time        
        now.add(Calendar.MINUTE, 20);        
        service.setOutageEndTime(now.getTime());
        
        instance =new ServiceSubscriberWorker(null,service);
        Integer expResult = null;
        Integer result = instance.checkIsOnOutageTimer(service);
        assertEquals(expResult, result);
    }
}
