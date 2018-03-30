package com.servicemonitor.bean;

import java.util.Date;

/**
 * @author manuja
 * Name : MonitorService Class
 * To Configuring the monitoring service properties
 */
public class MonitorService extends Service {
    private Date outageStartTime;
    private Date outageEndTime;

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

   



    
    
}
