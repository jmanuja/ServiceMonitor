
package com.servicemonitor.model;

import com.servicemonitor.enums.StatusEnum;
import com.servicemonitor.bean.MonitorService;
import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.ServiceStatusRenderer;
import com.servicemonitor.main.ServiceMonitorMain;
import java.awt.Component;
import java.awt.Image;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
* @author manuja
* This Will Render the Service List According to Service Model      *
*/  
public class ServiceStatusRendererImpl extends JLabel implements ListCellRenderer<MonitorService>,ServiceStatusRenderer {
    
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public ServiceStatusRendererImpl() {
        setOpaque(true);
    }
    
    /**
    * Render the list Style by its event      *
    */ 
    @Override
    public Component getListCellRendererComponent(JList<? extends MonitorService> list, MonitorService serviceModel, int index, boolean isSelected, boolean cellHasFocus) {        
        setVerticalAlignment(JLabel.TOP);
        setVerticalTextPosition(JLabel.TOP);
        setIcon(getIconByStatus(serviceModel.getServiceStatus()));
        setText(getStatusText(serviceModel));

        return this;
    }

    @Override
    public ImageIcon getIconByStatus(int serviceStatus) {
        ImageIcon imageIcon = null;
        if(serviceStatus == StatusEnum.UP.getId()){
            imageIcon = new ImageIcon( getClass().getResource("/images/online.png"));
            Image image = imageIcon.getImage(); 
            Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  
        } if(serviceStatus == StatusEnum.DOWN.getId()){
            imageIcon = new ImageIcon( getClass().getResource("/images/offline.png"));
            Image image = imageIcon.getImage(); 
            Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  
        }else if(serviceStatus == StatusEnum.TERMINATED.getId()){
            imageIcon = new ImageIcon( getClass().getResource("/images/terminated.jpg"));
            Image image = imageIcon.getImage(); 
            Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  
        }
        else if(serviceStatus == StatusEnum.OUTAGE.getId()){
            imageIcon = new ImageIcon( getClass().getResource("/images/silence.png"));
            Image image = imageIcon.getImage(); 
            Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  
        }    
        return imageIcon;
    }

    @Override
    public String getStatusText(MonitorService service) {
        String date = service.getLastSyncTime()!=null?" ( " +dateFormatter.format(service.getLastSyncTime()) + " ) ":"";

        return "<html>"+ service.getServiceName() + " "+  service.getServiceStatus()  + " " + date+ " </html>";
    }
    
}
