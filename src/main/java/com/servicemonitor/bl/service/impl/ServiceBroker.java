/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicemonitor.bl.service.impl;

import com.servicemonitor.bean.Service;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author manuja
 */
public class ServiceBroker {
    private static Map<String,Service> runningServiceList = new HashMap<>();	

    public static void addToRunningServices(String key , Service service){
        if(runningServiceList!=null && !runningServiceList.containsKey(key)){
            runningServiceList.put(key, service);
        }
    }

    public static void stopService(String key){
        runningServiceList.remove(key);
    }
    
    public static boolean isServiceRunning(String key){
        return runningServiceList.containsKey(key);
    }
    
    public static Map<String,Service> getRunningServices(){
        return runningServiceList;
    }
    
    public static Service getServiceByName(String name){
        if(runningServiceList.containsKey(name.toUpperCase())){
            return runningServiceList.get(name.toUpperCase());
        }
        return null;
    }
    
    public static void updateServiceStatus(String name,Service status){
        runningServiceList.put(name.toUpperCase(),status);
    }
}
