package com.servicemonitor.bl.service.impl;

import com.servicemonitor.enums.StatusEnum;
import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.ServiceConfig;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import javax.swing.SwingWorker;

/**
* @author manuja
* This Will do the service details publishing in background thread 
*/  
public class ServicePublisherWorker extends SwingWorker<Service, Service> implements ServiceConfig{

    private final Service service;
    private Socket socket;	
    
    public ServicePublisherWorker(Service service)
    {
        this.service = service;
    }
    
    @Override
    protected Service doInBackground() throws Exception {
        try{
            while(ServiceBroker.isServiceRunning(service.getServiceName().toUpperCase())){
                if(service!=null){
                    StatusEnum status = pingService(service.getPort(),service.getHost());
                    service.setServiceStatus(status.getId());
                    publish(service);
                    if(service.getServiceStatus() == StatusEnum.DOWN.getId()){
                        Thread.sleep(service.getGracePeriod());
                    }
                    System.out.println(service.getServiceName()+" IS "+ status.getStatus() + new Date());
                }
            }
        } catch (InterruptedException e){
            doInBackground();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return service;
    }
    
    /**
    * Process the messages from Background Process     *
    * @param  List<Service>
    */
    @Override
    protected void process(List<Service> chunk) {
        for(Service s:chunk){
            ServiceBroker.updateServiceStatus(service.getServiceName().toUpperCase(), service);
        }
    }    
    
    /**
    * Process after the Background Process is done 
    */
    @Override
    protected void done() {
        service.setServiceStatus(StatusEnum.TERMINATED.getId());
        ServiceBroker.updateServiceStatus(service.getServiceName().toUpperCase(), service);
    }

    @Override
    public StatusEnum pingService(int port, String host) {
        try{
            Thread.sleep(service.getPollingFrequency());
            socket = new Socket(host, port);
            if(socket.isConnected()){
                return StatusEnum.UP;
            }
        }catch (Exception e) {
            return StatusEnum.DOWN;
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
        return StatusEnum.DOWN;
    }
}
