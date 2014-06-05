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
package de.unibayreuth.bayeos.goat.list;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.Objekt;

public class JObjektPickerDialog extends JDialog {
    private boolean picked = false;
    
    private Objekt objekt;
    
    // GUI
    private JPanel objektPanel;
    private JPanel buttonPanel;
    
    // on objektPanel
    private JScrollPane objektScrollPane;
    private JList objektList;
    
    // on buutonPanel;
    private JButton okButton;
    private JButton cancelButton;
    
    
    protected final static Logger logger = Logger.getLogger(JObjektPickerDialog.class.getName());
    
    /**
     * The resource file name
     */
    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.list.JObjektPickerDialog";
    
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
    
        
    public JObjektPickerDialog(JFrame frame, DefaultListModel listModel,DefaultListCellRenderer renderer) throws JObjektPickerException {
        super(frame,resources.getString("Dialog.Title"),true);
        objektList = new JList(listModel);
        
        objektList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e){
                    okButton.setEnabled(!objektList.isSelectionEmpty());
            }
        });
        
        objektList.setCellRenderer(renderer);
        objektScrollPane = new JScrollPane(objektList);
        
        objektPanel = new JPanel();
        objektPanel.setLayout(new BorderLayout());
        objektPanel.add(objektScrollPane,BorderLayout.CENTER);
        
        
       
        // create buttons
        okButton = new JButton(resources.getString("OkButton.Text"));
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               logger.debug("Ok Button pressed");
               objekt = (Objekt)objektList.getSelectedValue();
               picked = true;
               setVisible(false);
            }
        });
        okButton.setEnabled(false);
        cancelButton = new JButton(resources.getString("CancelButton.Text"));
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                picked = false;
                setVisible(false);
            }
        });
        
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        getContentPane().add(objektPanel,BorderLayout.CENTER);
        getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        pack();
        
    }
    
    
        
    /** Getter for property picked.
     * @return Value of property picked.
     *
     */
    public boolean isPicked() {
        return picked;
    }    
    
    /** Getter for property objekt.
     * @return Value of property objekt.
     *
     */
    public Objekt getObjekt() {
        return objekt;
    }    
   
    
    
   
    
}
