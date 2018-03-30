package com.servicemonitor.bl.service.impl;

import com.servicemonitor.bean.Service;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author manuja
 * This Class is working as a intermediate layer for service publishing and subscribing process
 */
public class ServiceBroker {
    private static Map<String,Service> runningServiceList = new HashMap<>();	

    /**
    * To add a service to running services map
    * @param key - service name 
    * @param service 
    */
    public static void addToRunningServices(String key , Service service){
        if(runningServiceList!=null && !runningServiceList.containsKey(key)){
            runningServiceList.put(key, service);
        }
    }

    /**
    * To remove service from running services map
    * @param key - service name 
    */
    public static void stopService(String key){
        runningServiceList.remove(key);
    }
    
    /**
    * To remove service from running services map
    * @param key - service name 
    * @return  boolean - If service is on running map returns true
    *                    else false
    */
    public static boolean isServiceRunning(String key){
        return runningServiceList.containsKey(key);
    }
    
    /**
    * To retrieve all running services 
    * @return Map<String,Service>
    */
    public static Map<String,Service> getRunningServices(){
        return runningServiceList;
    }
    
    /**
    * To retrieve running service by name
    * @param name
    * @return Service
    */
    public static Service getServiceByName(String name){
        if(runningServiceList.containsKey(name.toUpperCase())){
            return runningServiceList.get(name.toUpperCase());
        }
        return null;
    }

    /**
    * To update service status. used by publisher service
    * @param name
    * @param status
    */
    public static void updateServiceStatus(String name,Service status){
        runningServiceList.put(name.toUpperCase(),status);
    }
}
