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
 * JPanelTable.java
 *
 * Created on 29. Juni 2004, 15:18
 */

package de.unibayreuth.bayeos.goat.panels.timeseries;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import de.unibayreuth.bayeos.goat.table.DateRenderer;
import de.unibayreuth.bayeos.goat.table.DecimalRenderer;
import de.unibayreuth.bayeos.goat.table.StatusRenderer;
import de.unibayreuth.bayeos.goat.table.TableSorter;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.CSVAdapter;
import de.unibayreuth.bayeos.utils.ExcelAdapter;
import de.unibayreuth.bayeos.utils.ImageFactory;
import de.unibayreuth.bayeos.utils.ImageFactoryException;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class JPanelTable extends JPanel implements ActionListener {
    protected JScrollPane dataScrollPane;
    protected JTable table;    
    protected TableSorter sorter;
    public final static String ACTION_TABLE_EXPORT = "table_export";

    protected AbstractButton tableExportButton;
    protected JToolBar toolBarData;
    protected String exportName;
    protected JLabel statusText;
    
    
    protected ObjektNode objektNode;
    
       
    /** Creates a new instance of JPanelTable */
    public JPanelTable() {
        setLayout(new BorderLayout());
        toolBarData = createTableToolbar();
        add(toolBarData,BorderLayout.NORTH);        
        
        dataScrollPane = new JScrollPane();        
        add(dataScrollPane, BorderLayout.CENTER);
        
        statusText = new JLabel();
        add(statusText, BorderLayout.SOUTH);
        
        
       
        
                
    }
    
    private JToolBar createTableToolbar() {
        JToolBar toolbar = new JToolBar();
        tableExportButton = new JButton();
        prepareButton(tableExportButton, ACTION_TABLE_EXPORT, "de/unibayreuth/bayeos/goat/panels/Export16.gif", "Save data as CSV file ...");
        toolbar.add(tableExportButton);
        toolbar.addSeparator();
        return toolbar;
    }
    
    public void setExportName(String name){
        this.exportName = name;
    }
    
    public void setModel(TableModel m){
       sorter = new TableSorter(m);
       table = new JTable(sorter);
       sorter.addMouseListenerToHeaderInTable(table);              
       sorter.setTableToolTips(table);
       
       statusText.setText("Table filled with " + table.getRowCount() + " rows.");      
       ExcelAdapter t = new ExcelAdapter(table);
       dataScrollPane.setViewportView(table);
       renderTable();
    }
    
    
    public void actionPerformed(ActionEvent evt) {
        try {
            String acmd = evt.getActionCommand();
            if (acmd.equals(ACTION_TABLE_EXPORT)) {
                CSVAdapter c = new CSVAdapter(this,table);
                c.save(exportName);
            }

        }  catch (Exception e) {
            MsgBox.error(e.getMessage());
        }
            
        }
    
    protected void prepareButton(AbstractButton button, 
                               String actionKey, 
                               String iconResource,
                               String toolTipText) {
        button.setActionCommand(actionKey);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        try {
        button.setIcon(ImageFactory.getIcon(iconResource));
        } catch (ImageFactoryException i){
            MsgBox.error(i.getMessage());
        }
    }
    
   protected void renderTable() {
     table.setAutoCreateColumnsFromModel(true);
     table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
     table.setRowHeight(20);
     table.setDefaultRenderer(java.util.Date.class,new DateRenderer()); 
     table.setDefaultRenderer(java.lang.Double.class, new DecimalRenderer());
     table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);                 
     
     for (int i = 0; i < sorter.getColumnCount();i++){
        TableColumn tc = table.getColumnModel().getColumn(i);
        String cName = tc.getHeaderValue().toString();
        if  (cName.equalsIgnoreCase("STATUS")){
          tc.setMinWidth(240);
          tc.setCellRenderer(new StatusRenderer());
        } else if (cName.equalsIgnoreCase("VON") || cName.equalsIgnoreCase("BIS")) {
          tc.setMinWidth(180);
        }
                   
      }
     
   }
   
   /**
    * Getter for property objektNode.
    * @return Value of property objektNode.
    */
   public ObjektNode getObjektNode() {
       return objektNode;
   }
   
   /**
    * Setter for property objektNode.
    * @param objektNode New value of property objektNode.
    */
   public void setObjektNode(ObjektNode objektNode) {
       this.objektNode = objektNode;
       this.exportName = objektNode.getDe();
   }
   
}
