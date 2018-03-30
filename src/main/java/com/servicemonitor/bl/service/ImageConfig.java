package com.servicemonitor.bl.service;

import com.servicemonitor.enums.StatusEnum;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author manuja
 */
public interface ImageConfig {
    
    /**
    * Get the Image icon for the service status monitor
    * @param serviceStatus - Current Status of the service
    * @return ImageIcon 
    */
    public ImageIcon getIconByStatus(int serviceStatus);

    /**
    * Get the Image for the service status monitor
    * @param statusEnum - Current Status of the service
    * @return Image
    */
    public Image getImageByStatus(StatusEnum statusEnum);
    
    /**
    * Resize the image icon
    * @param image - Image needs to be resized
    * @param width - resize width 
    * @param height - resize height
    * @return ImageIcon 
    */
    public ImageIcon resizeImageIcon(Image image,int width , int height);
}
