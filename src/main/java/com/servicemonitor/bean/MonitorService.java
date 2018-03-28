package com.servicemonitor.bean;

import java.util.Date;

/**
 *
 * @author manuja
 */
public class MonitorService extends Service {
    private Date outageStartTime;
    private Date outageEndTime;
    private Date lastSyncTime; 				

    public Date getOutageStartTime() {
        return outageStartTime;
    }

    public void setOutageStartTime(Date outageStartTime) {
        this.outageStartTime = outageStartTime;
    }

    public Date getOutageEndTime() {
        return outageEndTime;
    }

    public void setOutageEndTime(Date outageEndTime) {
        this.outageEndTime = outageEndTime;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }



    
    
}
