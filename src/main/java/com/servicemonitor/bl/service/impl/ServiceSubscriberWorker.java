package com.servicemonitor.bl.service.impl;

import com.servicemonitor.enums.StatusEnum;
import com.servicemonitor.bean.MonitorService;
import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.ServiceOutageConfig;
import com.servicemonitor.ui.MonitorFrame;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;

/**
* @author manuja
* This Will do the Background Work and update the window
*/  
public class ServiceSubscriberWorker extends SwingWorker<MonitorService, MonitorService> implements ServiceOutageConfig{

    private final DefaultListModel<MonitorService> serviceModel;
    private final MonitorFrame window ;
    private final MonitorService registeredService;
    private Service service ;
    
    public ServiceSubscriberWorker(MonitorFrame window,MonitorService service)
    {
        this.window = window;
        this.serviceModel  = new DefaultListModel<MonitorService>(); 
        this.registeredService = service;
    }
    
    @Override
    protected MonitorService doInBackground() throws Exception {
        try{
            while(window.checkServiceIsRunning(registeredService.getServiceName()) && !isCancelled()){
                service = ServiceBroker.getServiceByName(registeredService.getServiceName());
                if(service!=null){
                    Integer status = checkIsOnOutageTimer(registeredService);
                    if(status!=null){
                        registeredService.setServiceStatus(StatusEnum.OUTAGE.getId());
                    }else{
                        status = service.getServiceStatus();
                    }
                    registeredService.setServiceStatus(status);
                    publish(registeredService);
                    Thread.sleep(service.getPollingFrequency());
                }else{
                    window.showMessage("Service Not Found");
                    cancel(true);
                }
            }
        } catch (InterruptedException e){
            doInBackground();
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
        return registeredService;
    }
    
    /**
    * Process the messages from Background Process     *
    * @param  List<Service>
    */
    @Override
    protected void process(List<MonitorService> chunk) {
        if(registeredService.getServiceStatus()!=StatusEnum.TERMINATED.getId()){
            for(MonitorService s:chunk){
                window.showServiceStatus(s);
            }
        }
    }    
    
    /**
    * Process after the Background Process is done 
    */
    @Override
    protected void done() {
        if(service!=null){
            registeredService.setServiceStatus(StatusEnum.TERMINATED.getId());
            window.showServiceStatus(registeredService);
        }
    }

    @Override
    public Integer checkIsOnOutageTimer(MonitorService registeredService) {
        if(registeredService.getOutageStartTime()!=null && registeredService.getOutageEndTime()!=null){
            Calendar now = Calendar.getInstance();
            now.set(Calendar.DAY_OF_MONTH, 1);
            now.set(Calendar.MONTH, Calendar.JANUARY);
            now.set(Calendar.YEAR, 1970);

            if(registeredService.getOutageStartTime().getTime()<=now.getTimeInMillis() && registeredService.getOutageEndTime().getTime()>=now.getTimeInMillis()){
                return StatusEnum.OUTAGE.getId();
            }
        }
        return null; 
    }

}
