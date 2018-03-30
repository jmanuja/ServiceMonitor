package com.servicemonitor.bl.service.impl;

import java.util.Map;
import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.ServicePublisher;
import javax.swing.SwingWorker;

/**
 *
 * @author manuja
 * This Class responsible for Publishing service details.
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
        if(serviceList!=null){
            for(Map.Entry<String, Service> entry : serviceList.entrySet()){
                startService(entry.getValue());
            }
        }
    }

    @Override
    public void startService(Service service) {
        ServiceBroker.addToRunningServices(service.getServiceName().toUpperCase(),service);
        swingWorker = new ServicePublisherWorker(service);
        swingWorker.execute();
    }
}
