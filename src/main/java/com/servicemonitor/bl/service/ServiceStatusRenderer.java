/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicemonitor.bl.service;

import javax.swing.ImageIcon;
import com.servicemonitor.bean.MonitorService;

/**
 *
 * @author manuja
 */
public interface ServiceStatusRenderer {
    
    public ImageIcon getIconByStatus(int serviceStatus);
    public String getStatusText(MonitorService service);

}
