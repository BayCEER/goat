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
package de.unibayreuth.bayeos.goat.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import de.unibayreuth.bayeos.goat.JMainFrame;


public class ExAppTableEditor extends JPanel {
    
    public  static final String APP_PREFIX = "app_";
    
    private static final Preferences pref = Preferences.userNodeForPackage(JMainFrame.class); 
    
    private static final Logger logger = Logger.getLogger(ExAppTableEditor.class);
    
    private ExAppTableModel tableModel;
    private JTable table;
    private JButton jButtonAdd, jButtonRemove;
    
    

    public ExAppTableEditor() {
        
        setPreferredSize(new Dimension(400,50));
        tableModel = new ExAppTableModel();
        table = new JTable(tableModel);
        
        table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent e) {
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    jButtonRemove.setEnabled(!lsm.isSelectionEmpty());
                }
           });
           
         table.getColumnModel().getColumn(0).setMaxWidth(75);
           
          
        //Create the scroll pane and add the table to it. 
        setLayout(new BorderLayout());        
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this frame
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelAppButtons = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
       
       
        jButtonAdd = new JButton("Add");
        jButtonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i){
                Vector row = new Vector(2);
                row.add("");
                row.add("");
                tableModel.addRow(row);
            }
        });
        
        
        panelAppButtons.add(jButtonAdd);

        jButtonRemove = new JButton("Remove");
        jButtonRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i){
                tableModel.removeRow(table.getSelectedRow());                
            }
        });
        jButtonRemove.setEnabled(false);
        
        panelAppButtons.add(jButtonRemove);

        add(panelAppButtons, java.awt.BorderLayout.SOUTH);

    }
    
    public boolean load(){
        table.clearSelection();
        return tableModel.load();   
    }

    class ExAppTableModel extends DefaultTableModel {
        final String[] columnNames = {"Extension","Path"};
        
        Vector values;
                
        public int getColumnCount() {
            return columnNames.length;
        }
        
        public int getRowCount() {
            if (values == null) return 0;
            return (values.size());
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            Vector vrow = (Vector)values.elementAt(row);
            return vrow.elementAt(col);
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            Vector vrow = (Vector)values.elementAt(row);
            vrow.setElementAt(value, col);
            pref.put(APP_PREFIX + (String)vrow.elementAt(0),(String)vrow.elementAt(1));
            fireTableCellUpdated(row, col);
        }

        public void addRow(Vector row) {
            int i = values.size();
            pref.put(APP_PREFIX + (String)row.elementAt(0),(String)row.elementAt(1));
            values.add(row);
            fireTableRowsInserted(i,i);            
        }        
    
        public void removeRow(int row) {
            Vector vrow = (Vector)values.elementAt(row);
            pref.remove(APP_PREFIX + (String)vrow.elementAt(0));
            values.remove(row);
            fireTableRowsDeleted(row,row);            
        }
        
        
        
        public boolean load() {
           try {
            values = getAppVector();
            } catch (BackingStoreException e) {
                logger.error(e.getMessage());
                return false;
            }
           return true;            
        }
        
    }
    
    public static Vector getAppVector() throws BackingStoreException { 
            Vector values = new Vector();
            String[] s  = pref.keys();
            for (int i=0;i<s.length;i++){
                if (s[i].startsWith(APP_PREFIX) && !s[i].equals(APP_PREFIX)) {
                Vector row = new Vector(2);
                row.add(s[i].substring(APP_PREFIX.length()));
                row.add(pref.get(s[i],""));                
                values.add(row);
                }
            }
            return values;
            
    }
    
    public ExAppTableModel getTableModel(){
        return tableModel;
    }

}
