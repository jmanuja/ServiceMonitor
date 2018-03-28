/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicemonitor.bl.service.impl;

import com.servicemonitor.enums.StatusEnum;
import java.util.Map;
import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.ServicePublisher;
import javax.swing.SwingWorker;

/**
 *
 * @author manuja
 */
public class ServicePublisherImpl implements ServicePublisher{
    private Map<String,Service> serviceList;	
    SwingWorker swingWorker;

    public Map<String, Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(Map<String, Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public void startAllServices() {
        try {
            if(serviceList!=null){
                for (Map.Entry<String, Service> entry : serviceList.entrySet()){
                    startService(entry.getValue());
		}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startService(Service service) {
        ServiceBroker.addToRunningServices(service.getServiceName().toUpperCase(),service);
        swingWorker = new ServicePublisherWorker(service);
        swingWorker.execute();
    }
}
