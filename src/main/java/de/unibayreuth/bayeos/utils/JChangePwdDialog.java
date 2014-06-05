/*******************************************************************************
 * Copyright (c) 2011 University of Bayreuth - BayCEER.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     University of Bayreuth - BayCEER - initial API and implementation
 ******************************************************************************/
/*
 * JChangePwdDialog.java
 *
 * Created on 21. Mai 2002, 16:28
 */

package de.unibayreuth.bayeos.utils;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayeos.goat.JMainFrame;
  

/**
 *
 * @author  oliver
 */
public class JChangePwdDialog extends javax.swing.JDialog {
    
    protected final static Logger logger = Logger.getLogger("JChangePwdDialog.class");
    
     /**
     * The resource file name
     */
    protected final static String RESOURCES = "de.unibayreuth.bayeos.utils.JChangePwdDialog";

    /**
     * The resource bundle
     */
    protected static ResourceBundle bundle;

    /**
     * The resource manager
     */
    protected static ResourceManager resources;
    
    static {
        bundle = ResourceBundle.getBundle(RESOURCES, Locale.getDefault());
        resources = new ResourceManager(bundle);
    }
    
      
    private JLabel oldLabel;
    private JPasswordField oldField;

    private JLabel newLabel;
    private JPasswordField newField; 
    
    private JLabel renewLabel;
    private JPasswordField renewField; 
    
    private JButton clearButton, okButton, cancelButton;
    
    private JPanel contentPanel, buttonPanel;
    
    private JMainFrame app;
    
        
    /** Creates a new instance of JChangePwdDialog */
    public JChangePwdDialog(JMainFrame application) {
     super(application,resources.getString("Dialog.Title"),true);
     this.app = application;
     
      oldLabel = new JLabel(resources.getString("OldLabel.Text"), JLabel.LEFT);
      newLabel = new JLabel(resources.getString("NewLabel.Text"), JLabel.LEFT);
      renewLabel = new JLabel(resources.getString("RenewLabel.Text"), JLabel.LEFT);
     
      JPanel labelPanel = new JPanel(false);
      labelPanel.setLayout(new GridLayout(0, 1));
      labelPanel.add(oldLabel);
      labelPanel.add(newLabel);
      labelPanel.add(renewLabel);
      labelPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
     
      oldField = new JPasswordField(13);
      newField = new JPasswordField(13);
      renewField = new JPasswordField(13);
      
      JPanel fieldPanel = new JPanel(false);
      fieldPanel.setLayout(new GridLayout(0, 1));
      fieldPanel.add(oldField);
      fieldPanel.add(newField);  
      fieldPanel.add(renewField);  
      fieldPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      contentPanel = new JPanel();
      contentPanel.setLayout(new BorderLayout());
      contentPanel.add(labelPanel, BorderLayout.WEST);
      contentPanel.add(fieldPanel, BorderLayout.CENTER);
      
          
     okButton = new JButton(resources.getString("OkButton.Text"));
     okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             String oldPwd = new String(oldField.getPassword());
             String newPwd = new String(newField.getPassword());
             String renewPwd = new String(renewField.getPassword());
             if (!newPwd.equals(renewPwd)) {
              JOptionPane.showMessageDialog(JChangePwdDialog.this,resources.getString("Msg.Differences"),getTitle(),JOptionPane.INFORMATION_MESSAGE);
              newField.setText(null);
              renewField.setText(null);
             } else {
              changePassword(oldPwd,newPwd);
             }
            
        }

          
        
     });
     
     clearButton = new JButton(resources.getString("ClearButton.Text"));
     clearButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              oldField.setText("");
              newField.setText("");
              renewField.setText("");
          }
     });
     
     cancelButton = new JButton(resources.getString("CancelButton.Text"));
     cancelButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
              setVisible(false);
          }
     });
     

     buttonPanel = new JPanel();
     buttonPanel.add(okButton);
     buttonPanel.add(clearButton);
     buttonPanel.add(cancelButton);
     
     

     getContentPane().add(contentPanel,BorderLayout.CENTER);
     getContentPane().add(buttonPanel,BorderLayout.SOUTH);

     getRootPane().setDefaultButton(okButton);      
     pack();
     setLocationRelativeTo(app);
     // setResizable(false);
    }
    
    private void changePassword(String oldPwd, String newPwd){
      logger.debug("Change Password ...");
      try{
        // Change Password 
        Vector params = new Vector();
        params.addElement(oldPwd);
        params.addElement(newPwd);
        Boolean ret  = (Boolean)app.getXmlClient().execute("ToolsHandler.changePassword",params);
        if (ret.booleanValue()) {
         MsgBox.info(this,resources.getString("Msg.Success"));         
         setVisible(false);
        } else {
         MsgBox.warn(this,resources.getString("Msg.Failed"));
        }
      } catch (XmlRpcException e){
          logger.error(e.getMessage());
          MsgBox.error(this,e.getMessage());
      }
    }
    
    
}
