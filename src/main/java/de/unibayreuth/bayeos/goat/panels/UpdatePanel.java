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
package de.unibayreuth.bayeos.goat.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;

import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.utils.MsgBox;

public abstract class UpdatePanel extends DetailPanel implements UpdateableForm {
    
    protected  JPanel buttonPanel;
    protected  JButton commitButton;
    
    final static Logger logger = Logger.getLogger(UpdatePanel.class.getName()); 
    
    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.panels.JEditPanel";
    
    protected static ResourceBundle bundle;
    
    protected static ResourceManager resources;
    
    static {
        bundle = ResourceBundle.getBundle(RESOURCES, Locale.getDefault());
        resources = new ResourceManager(bundle);
    }
        
    
    /** Creates a new instance of JGenPanel */
    public UpdatePanel(JMainFrame app) {
     super(app);
        
     setLayout(new BorderLayout());
     
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     buttonPanel.add(commitButton = new JButton(resources.getString("Button.Commit.Text")));
     commitButton.setEnabled(false);
     buttonPanel.setPreferredSize(new Dimension(300,50));
     commitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {           
                if (!updateData()) { 
                    MsgBox.warn(UpdatePanel.this,"Couldn't commit changes !");
                } else {
                    if (!loadData()) {
                    MsgBox.warn(UpdatePanel.this,"Couldn't refresh form !");
                    }
                }
                
            }
        });
      add(buttonPanel,BorderLayout.SOUTH);
    }
        
           
    public void setEditable(boolean enabled) {       
       super.setEditable(enabled);
       commitButton.setEnabled(enabled);
    }
     
    
    
    
    
} // end UpdatePanel



