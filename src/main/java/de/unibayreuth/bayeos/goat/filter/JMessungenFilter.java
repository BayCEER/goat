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
package de.unibayreuth.bayeos.goat.filter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.JMainFrame;



public class JMessungenFilter extends JDialog implements Constants {
    private JMainFrame app;
    
    private boolean okPressed;
    
    
    //private JFilter filterDialog;    
    
    private JPanelMessungTable messungPanel;
    private JPanel filterPanel, buttonPanel ;
    
    private static final Preferences  pref = Preferences.userNodeForPackage(JMainFrame.class);
    
    private JButton okButton, cancelButton, filterButton;
    
    // Filter
    private JMessungFilter messungFilter;

    protected final static Logger logger = Logger.getLogger(JMessungenFilter.class.getName());
    
    
    public JMessungenFilter(JMainFrame application){
        super(application,true);
        this.app = application;

        setTitle("Load Data");
        
        /* messungPanel
         */
        messungPanel = new JPanelMessungTable(application);
        messungPanel.getTableModel().addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e){
                DefaultTableModel m = (DefaultTableModel)e.getSource();
                boolean b = (m.getRowCount() > 0);
                okButton.setEnabled(b);
                messungFilter.setAggregateEnabled(messungPanel.isWithLabordaten());
            }
            
        });
              
        
        messungFilter = new JMessungFilter(app);
        
        /* filterPanel
        */
        filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter records"));
        
        filterButton = new JButton("Set Filter");
        filterButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                messungFilter.setLocationRelativeTo(filterButton);
                messungFilter.setMaxRecNumber(pref.getInt("maxrecnumber",200000));
                messungFilter.setVisible(true);
            }
        });
        filterButton.setPreferredSize(new Dimension(500,25));
        filterPanel.add(filterButton);
        
        // create buttons
        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                okPressed = true;
                setVisible(false);
            }
        });
        getRootPane().setDefaultButton(okButton);
     
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){                
                okPressed = false;
                setVisible(false);
            }
        });
        
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        
        Container content = getContentPane();
        LayoutManager lm = new BoxLayout(content, BoxLayout.Y_AXIS);
        content.setLayout(lm);
        content.add(messungPanel);
        content.add(filterPanel);
        content.add(buttonPanel);

        pack();       
        setLocationRelativeTo(application);
        
        
        
        
    }    
    
    public void setTimeFilter(TimeFilter t){
        messungFilter.setTimeFilter(t);
    }
    
    public void setOriginalSelected(){
        messungFilter.setOriginalSelected();
    }
    
    public boolean isOriginalSelected(){
        return messungFilter.isOriginalSelected();
    }
    
    public TimeFilter getTimeFiler(){
        return messungFilter.getTimeFilter();
    }
    
    public void setAggregateFilter(AggregateFilter a){
        messungFilter.setAggrFilter(a);
    }
    
    public AggregateFilter getAggregateFilter(){
        return messungFilter.getAggrFilter();
    }
    
    public void setStatusFilter(StatusFilter s){
        messungFilter.setStatusFilter(s);
    }
    
    public StatusFilter getStatusFilter(){
        return messungFilter.getStatusFilter();
    }
    
    public boolean isAggregateEnabled(){
        return messungFilter.isAggregateEnabled();
    }
    
    public void setObjektNodes(Vector nodes){
       messungPanel.setObjektNodes(nodes);
       messungFilter.setAggregateEnabled(!messungPanel.isWithLabordaten());
    }
    
    public Vector getObjektNodes(){
        return messungPanel.getObjektNodes();
    }
    
    public boolean isOkPressed(){
        return okPressed;
    }
           
    
}
