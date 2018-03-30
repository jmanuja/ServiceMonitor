
package com.servicemonitor.bl.service.impl;

import com.servicemonitor.enums.StatusEnum;
import com.servicemonitor.bean.MonitorService;
import com.servicemonitor.bean.Service;
import com.servicemonitor.bl.service.ImageConfig;
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
public class ServiceStatusRendererImpl extends JLabel implements ListCellRenderer<MonitorService>,ServiceStatusRenderer,ImageConfig {
    
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
        Image image = getImageByStatus(StatusEnum.find(serviceStatus));
        ImageIcon imageIcon = resizeImageIcon(image, 40, 40);
       
        return imageIcon;
    }

    @Override
    public String getStatusText(Service service) {
        String date = service.getLastSyncTime()!=null?" ( " +dateFormatter.format(service.getLastSyncTime()) + " ) ":"";
        return "<html>"+ service.getServiceName() + " "+  StatusEnum.find(service.getServiceStatus()).getStatus()  + " " + date+ " </html>";
    }

    @Override
    public Image getImageByStatus(StatusEnum statusEnum) {
        ImageIcon imageIcon = new ImageIcon( getClass().getResource(statusEnum.getImageIconPath()));
        return imageIcon.getImage();    
    }

    @Override
    public ImageIcon resizeImageIcon(Image image, int width, int height) {
        Image newImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); 
        return  new ImageIcon(newImage);  
    }
    
}
