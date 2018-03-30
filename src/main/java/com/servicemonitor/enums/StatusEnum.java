package com.servicemonitor.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author manuja
 * Name : StatusEnum
 * To Identify the current status of the service
 * And Show the Status in user interface
 */
public enum StatusEnum {
    UP(1,"Server is Up","/images/online.png"),
    DOWN(2,"Server is Down","/images/offline.png"),
    OUTAGE(3,"Server is on Outage Timer","/images/silence.png"),
    TERMINATED(4,"Server is Terminated","/images/terminated.jpg"),
    INITIALIZE(5,"Server is Initialized","/images/offline.png");
    
    private int id ;
    private String status;
    private String imageIconPath;
    
    
    private StatusEnum(int id , String status,String imageIconPath) {
        this.id=id;
        this.status=status;
        this.imageIconPath=imageIconPath;
        Holder.MAP.put(id, this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageIconPath() {
        return imageIconPath;
    }

    public void setImageIconPath(String imageIconPath) {
        this.imageIconPath = imageIconPath;
    }
    
    private static class Holder {
        static Map<Integer, StatusEnum> MAP = new HashMap<>();
    }
    
    public static StatusEnum find(int val) {
        StatusEnum statusEnum = Holder.MAP.get(val);
        if(statusEnum == null) {
            throw new IllegalStateException(String.format("Unsupported type %s.", val));
        }
        return statusEnum;
    }
}
