package com.servicemonitor.ui;

import com.servicemonitor.bl.service.impl.ServiceStatusRendererImpl;
import com.servicemonitor.bean.MonitorService;

import com.servicemonitor.bl.service.impl.ServiceSubscriberWorker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.DateFormatter;

/**
 * @author manuja
 * Name : MonitorFrame
 * Main Window of the System
 * Main Functions : Service Register and Stop Current Services 
 */
public class MonitorFrame extends JFrame{
    
    SwingWorker swingWorker;

    private DefaultListModel<MonitorService> serviceStatusModel;
    JList<MonitorService> serviceStatusList;
    private Map<String,Integer> statusMap = new HashMap<>();    
    private Map<String,MonitorService> registerServiceMap = new HashMap<>();    
     
    private JPanel serviceDetailPanel,serviceStatusPanel;
    private JTextField serviceNameTextField;
    private JButton registerButton , terminateButton;
    private JSpinner outageStartSpinner,outageEndSpinner;
    private JScrollPane jScrollPane;

    public MonitorFrame(){
        super("Service Monitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initilizeWindow();

        //Service Registering Function
        registerButton.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    String serviceName = serviceNameTextField.getText().trim();
                    Date outageStartTime = (Date) outageStartSpinner.getValue();
                    Date outageEndTime = (Date) outageEndSpinner.getValue();
                    if( !"".equals(serviceName)){
                        if(!registerServiceMap.containsKey(serviceName)){
                            MonitorService selectedItem = new MonitorService();
                            selectedItem.setServiceName(serviceName);
                            if(outageStartTime.compareTo(outageEndTime)!=0 && outageStartTime.before(outageEndTime)){
                                selectedItem.setOutageStartTime(outageStartTime);
                                selectedItem.setOutageEndTime(outageEndTime);
                            }
                            registerServiceMap.put(serviceName, selectedItem);
                            swingWorker = new ServiceSubscriberWorker(MonitorFrame.this,selectedItem);
                            swingWorker.execute();
                            clearWindow();
                        }else{
                            showMessage("Service Already Running");
                        }
                    }else{
                        showMessage("Please fill all Mandotory Fields");
                    }
                } catch (Exception e1) {
                    showMessage(e1.getMessage());
                    e1.printStackTrace();
                }
            });
        });
        
        //Service Terminate Function
        terminateButton.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    String serviceName = serviceNameTextField.getText().trim();
                    if( !"".equals(serviceName)){
                        if(checkServiceIsRunning(serviceName)){
                            registerServiceMap.remove(serviceName);
                            swingWorker.cancel(true);
                            serviceStatusModel.removeElement(serviceStatusList.getSelectedValue());
                            statusMap.remove(serviceName);
                            clearWindow();
                        }else{
                            showMessage("Service Not Running");
                        }
                    }else{
                        showMessage("Please Select a Service");
                    }
                } catch (Exception e1) {
                    showMessage(e1.getMessage());
                    e1.printStackTrace();
                }
            });
        });
        
        //Service Select Function 
        //Trigger Event : Mouse Click Event on the Service from the Running List
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    SwingUtilities.invokeLater(() -> {
                        if(serviceStatusList.getSelectedValue()!=null){
                            MonitorService service = serviceStatusList.getSelectedValue();
                            serviceNameTextField.setText(service.getServiceName());
                            if(service.getOutageStartTime()!=null && service.getOutageEndTime()!=null){
                                outageStartSpinner.setValue(service.getOutageStartTime());
                                outageEndSpinner.setValue(service.getOutageEndTime());
                            }else{
                                Calendar cal = Calendar.getInstance();
                                cal.set(Calendar.HOUR_OF_DAY, 0);
                                cal.set(Calendar.MINUTE, 0);
                                outageStartSpinner.setValue(cal.getTime());
                                outageEndSpinner.setValue(cal.getTime());
                            }
                        }
                    });
                }
            }
        };
        serviceStatusList.addMouseListener(mouseListener);
        
        this.setSize(350, 420);
        this.setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(serviceDetailPanel, BorderLayout.CENTER);
        contentPane.add(serviceStatusPanel, BorderLayout.SOUTH);        
        pack();
    }
    


    /**
    * Initialize Window Components    *
    */
    public void initilizeWindow(){  
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        
        serviceDetailPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        serviceStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        jScrollPane = new JScrollPane();
        
        serviceStatusModel = new DefaultListModel<>();
        serviceStatusList = new JList<>(serviceStatusModel);
        serviceStatusList.setFixedCellHeight(40);
        serviceStatusList.setFixedCellWidth(340);
        jScrollPane = new JScrollPane(serviceStatusList);
        jScrollPane.setPreferredSize(new Dimension(340, 240));
        serviceStatusPanel.add(jScrollPane);
        serviceStatusList.setCellRenderer(new ServiceStatusRendererImpl());        
        
        JLabel title = new JLabel("Service Monitor");
        title.setFont(new Font("Monospace", Font.PLAIN, 22));
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setPreferredSize(new Dimension(350, 50));

        serviceDetailPanel.setPreferredSize(new Dimension(350, 170));
        serviceStatusPanel.setBackground(Color.GRAY);
        serviceStatusPanel.setPreferredSize(new Dimension(350,250));

        JLabel serviceNameLabel = new JLabel("<html> Service Name <font color='red'> * </font></html>");
        serviceNameLabel.setPreferredSize(new Dimension(170,20));
        JLabel outageStartLabel= new JLabel("Outage Start Time");
        outageStartLabel.setPreferredSize(new Dimension(170,20));
        JLabel outageEndLabel= new JLabel("Outage End Time");
        outageEndLabel.setPreferredSize(new Dimension(170,20));

        serviceNameTextField= new JTextField();
        serviceNameTextField.setPreferredSize(new Dimension(150,20));
        outageStartSpinner = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor outageStartEditor = new JSpinner.DateEditor(outageStartSpinner, "HH:mm");
        DateFormatter startFormatter = (DateFormatter)outageStartEditor.getTextField().getFormatter();
        startFormatter.setAllowsInvalid(false); 
        startFormatter.setOverwriteMode(true);
        outageStartSpinner.setEditor(outageStartEditor);
        outageStartSpinner.setValue(cal.getTime()); 
        outageStartSpinner.setPreferredSize(new Dimension(150,20));
        outageEndSpinner = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor outageEndEditor = new JSpinner.DateEditor(outageEndSpinner, "HH:mm");
        DateFormatter endFormatter = (DateFormatter)outageEndEditor.getTextField().getFormatter();
        endFormatter.setAllowsInvalid(false); 
        endFormatter.setOverwriteMode(true);
        outageEndSpinner.setEditor(outageEndEditor);
        outageEndSpinner.setValue(cal.getTime()); 
        outageEndSpinner.setPreferredSize(new Dimension(150,20));
        
        serviceDetailPanel.add(title);
        serviceDetailPanel.add(serviceNameLabel);
        serviceDetailPanel.add(serviceNameTextField);
        serviceDetailPanel.add(outageStartLabel);
        serviceDetailPanel.add(outageStartSpinner);
        serviceDetailPanel.add(outageEndLabel);
        serviceDetailPanel.add(outageEndSpinner);
        
        registerButton = new JButton("Register");
        registerButton.setPreferredSize( new Dimension(80, 26));
        registerButton.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        serviceDetailPanel.add(registerButton);

        terminateButton = new JButton("Stop");
        terminateButton.setPreferredSize( new Dimension(80, 26));
        terminateButton.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        serviceDetailPanel.add(terminateButton);

        pack();
    }

    /**
    * Use to alert Messages     *
    */
    public void showMessage(String msg) {
        JOptionPane optionPane = new JOptionPane("Message");
        optionPane.showMessageDialog(this, msg); 
    }

    /**
    * Use to Add Service Status to interface *
    * @param : runningService Service Object with Current Status 
    */
    public void showServiceStatus(MonitorService runningService) {
        if(checkServiceIsRunning(runningService.getServiceName())){
            if(statusMap.containsKey(runningService.getServiceName().trim())){
               int index= statusMap.get(runningService.getServiceName().trim());
               serviceStatusModel.setElementAt(runningService,index);
            }else{
               serviceStatusModel.add(statusMap.size(), runningService);
               statusMap.put(runningService.getServiceName().trim(),statusMap.size());
            } 
        }
    }
    
   /**
    * Use to Get Service by Name *
    * @param : serviceName Name of the Service
    * @return : Service 
    */    
    public MonitorService getServiceByName(String serviceName) {
        return registerServiceMap.get(serviceName);
    }

    /**
    * Use to Check the Server is running *
    * @param : serviceName Name of the Service
    * @return : boolean TRUE if server is running else FALSE
    */    
    public boolean checkServiceIsRunning(String serviceName) {
        return registerServiceMap.containsKey(serviceName);
    }

    /**
    * Use too Clear the interface    *
    */    
    public void clearWindow(){				
        serviceNameTextField.setText(""); 
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        outageStartSpinner.setValue(cal.getTime());
        outageEndSpinner.setValue(cal.getTime());     
    }
}
