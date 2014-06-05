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
 * JMessungTable.java
 *
 * Created on 5. Mai 2004, 12:45
 */

package de.unibayreuth.bayeos.goat.filter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.JMessungPickerDialog;
import de.unibayreuth.bayeos.goat.JMessungPickerException;
import de.unibayreuth.bayeos.goat.table.ObjektNodeTableCellRenderer;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class JPanelMessungTable extends javax.swing.JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JMessungPickerDialog messungPicker;
    private JMainFrame app;
    private boolean withLabordaten;
    
              
    
    /** Creates new form JMessungTable */
    public JPanelMessungTable(JMainFrame app) {
        this.app = app;
        initComponents();
        tableModel = new MessungTableModel();
        table = new JTable(tableModel); 
        
        jScrollPane.setViewportView(table);        
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent e) {
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    boolean singleRow = table.getSelectedRowCount() == 1;
                    
                    if (lsm.isSelectionEmpty()){
                        jButtonUp.setEnabled(false);
                        jButtonDown.setEnabled(false);
                        jButtonRemove.setEnabled(false);
                    } else {
                        jButtonUp.setEnabled( (lsm.getMinSelectionIndex() > 0) && singleRow);
                        jButtonDown.setEnabled((lsm.getMinSelectionIndex() < tableModel.getRowCount() -1) && singleRow);
                        jButtonRemove.setEnabled(true);
                    }
               }
        });
        
        table.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_DELETE){
                    removeRows();
                }
            }
            
            public void keyReleased(KeyEvent e){
            }
            public void keyTyped(KeyEvent e){
            }

 
        });
 
        
        // Name
        table.setDefaultRenderer(ObjektNode.class, new ObjektNodeTableCellRenderer());
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
               
        jButtonRemove.setEnabled(false);
        jButtonUp.setEnabled(false);
        jButtonDown.setEnabled(false);
        
    }
    
    public boolean isEmpty(){
        return (table.getRowCount() < 1);
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane = new javax.swing.JScrollPane();
        jPanelButtons = new javax.swing.JPanel();
        jButtonRemove = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonDown = new javax.swing.JButton();
        jButtonUp = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        setBorder(new javax.swing.border.TitledBorder("Measurements"));
        setMinimumSize(new java.awt.Dimension(460, 145));
        setPreferredSize(new java.awt.Dimension(460, 180));
        jScrollPane.setPreferredSize(new java.awt.Dimension(300, 100));
        add(jScrollPane, java.awt.BorderLayout.CENTER);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jPanelButtons.setMinimumSize(new java.awt.Dimension(182, 300));
        jPanelButtons.setPreferredSize(new java.awt.Dimension(150, 180));
        jButtonRemove.setText("Remove");
        jButtonRemove.setPreferredSize(new java.awt.Dimension(110, 25));
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        gridBagConstraints.weightx = 1.0;
        jPanelButtons.add(jButtonRemove, gridBagConstraints);

        jButtonAdd.setText("Add");
        jButtonAdd.setPreferredSize(new java.awt.Dimension(110, 25));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        gridBagConstraints.weightx = 1.0;
        jPanelButtons.add(jButtonAdd, gridBagConstraints);

        jButtonDown.setText("Move Down");
        jButtonDown.setPreferredSize(new java.awt.Dimension(110, 25));
        jButtonDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        gridBagConstraints.weightx = 1.0;
        jPanelButtons.add(jButtonDown, gridBagConstraints);

        jButtonUp.setText("Move Up");
        jButtonUp.setMinimumSize(new java.awt.Dimension(100, 25));
        jButtonUp.setPreferredSize(new java.awt.Dimension(110, 25));
        jButtonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        gridBagConstraints.weightx = 1.0;
        jPanelButtons.add(jButtonUp, gridBagConstraints);

        add(jPanelButtons, java.awt.BorderLayout.EAST);

    }//GEN-END:initComponents

    private void jButtonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpActionPerformed
        int i = table.getSelectedRow();
        tableModel.moveRow(i,i,i-1);
        table.getSelectionModel().setSelectionInterval(i-1,i-1);
        
    }//GEN-LAST:event_jButtonUpActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        removeRows();
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    
    private void removeRows(){
        int r[] = table.getSelectedRows();
        Arrays.sort(r);
        for (int i=r.length-1;i>=0;i--){
          tableModel.removeRow(r[i]);
        }
        checkLabordaten();
    }
    private void checkLabordaten(){
                
        for (int i = 0;i<tableModel.getRowCount();i++){
            ObjektNode o = (ObjektNode)tableModel.getValueAt(i, 0);
            if (o.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN)){
                withLabordaten = true;
                return;
            } else {
                withLabordaten = false;
            }
        }
    }
    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        if (messungPicker == null) {
                try {
                    messungPicker = new JMessungPickerDialog(app,true,true,false);
                    } catch (JMessungPickerException z) {
                     MsgBox.error(app,"Couldn't create dialog JMessungPicker.");
                     return;
                    }
                } 
                messungPicker.setLocationRelativeTo(jButtonAdd);
                messungPicker.show();
                if (messungPicker.isPicked()) {
                  add(messungPicker.getObjektNode());
                }
       
    }//GEN-LAST:event_jButtonAddActionPerformed

    public Vector getObjektNodes(){
        Vector v = new Vector();
        Iterator t = tableModel.getDataVector().iterator();
        while (t.hasNext()){
            v.add(((Vector)t.next()).elementAt(0));
        }
        return v;    
    }
    
    public void setObjektNodes(Vector nodes){      
        withLabordaten = false;
        tableModel = new MessungTableModel();
        table.setModel(tableModel);
        if (nodes == null) return;
        Iterator t = nodes.iterator();
        while(t.hasNext()){
            ObjektNode o = (ObjektNode)t.next();
            add(o);
        }
        
    }
           
    
    public void add(ObjektNode node){        
          if (node.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN)){
              withLabordaten = true;
          }
          Vector v = new Vector();
          v.add(node); 
          tableModel.addRow(v);                                               
    }
    
    private void jButtonDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDownActionPerformed
        int i = table.getSelectedRow();
        tableModel.moveRow(i,i,i+1);
        table.getSelectionModel().setSelectionInterval(i+1,i+1);
    }//GEN-LAST:event_jButtonDownActionPerformed

    /**
     * Getter for property tableModel.
     * @return Value of property tableModel.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }    
    
    /**
     * Setter for property tableModel.
     * @param tableModel New value of property tableModel.
     */
    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }    
    
    /**
     * Getter for property withLabordaten.
     * @return Value of property withLabordaten.
     */
    public boolean isWithLabordaten() {
        return withLabordaten;
    }
    
    /**
     * Setter for property withLabordaten.
     * @param withLabordaten New value of property withLabordaten.
     */
    public void setWithLabordaten(boolean withLabordaten) {
        this.withLabordaten = withLabordaten;
    }
    
    /**
     * Getter for property jButtonAdd.
     * @return Value of property jButtonAdd.
     */
    public javax.swing.JButton getJButtonAdd() {
        return jButtonAdd;
    }
    
    /**
     * Setter for property jButtonAdd.
     * @param jButtonAdd New value of property jButtonAdd.
     */
    public void setJButtonAdd(javax.swing.JButton jButtonAdd) {
        this.jButtonAdd = jButtonAdd;
    }
    
    /**
     * Getter for property jButtonRemove.
     * @return Value of property jButtonRemove.
     */
    public javax.swing.JButton getJButtonRemove() {
        return jButtonRemove;
    }
    
    /**
     * Setter for property jButtonRemove.
     * @param jButtonRemove New value of property jButtonRemove.
     */
    public void setJButtonRemove(javax.swing.JButton jButtonRemove) {
        this.jButtonRemove = jButtonRemove;
    }
    
    /**
     * Getter for property table.
     * @return Value of property table.
     */
    public javax.swing.JTable getTable() {
        return table;
    }
    
    /**
     * Setter for property table.
     * @param table New value of property table.
     */
    public void setTable(javax.swing.JTable table) {
        this.table = table;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDown;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JButton jButtonUp;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JScrollPane jScrollPane;
    // End of variables declaration//GEN-END:variables

 
class MessungTableModel extends DefaultTableModel {
  final String[] columnNames = {"Name"};
  final Class[] columnClasses = {ObjektNode.class};
    
  public MessungTableModel(){
      setColumnIdentifiers(columnNames);
  }
    
  public Class getColumnClass(int columnIndex) {
      return columnClasses[columnIndex];
  }      
}

}
