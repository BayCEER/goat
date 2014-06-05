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
 * JLoadData.java
 *
 * Created on 10. Mai 2004, 13:06
 */

package de.unibayreuth.bayeos.goat.filter;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JRadioButton;

import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.client.Client;
import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;
import de.unibayreuth.bayeos.utils.date.DateChangedEvent;
import de.unibayreuth.bayeos.utils.date.DateChangedListener;

/**
 *
 * @author  oliver
 */
public class JMessungFilter extends javax.swing.JDialog {

    private JPanelVonBis panelVonBis;
    private JPanelStatus panelStatus;
    private JPanelAggregate panelAggr;
    
    private ObjektNode objektNode;
    
    private boolean checkLoad;
    private boolean aggregateEnabled = false;
    
    private boolean okPressed;
    private JMainFrame app;
    private int resolution;
	private int maxrecnumber;
        
    
    
    /** Creates new form JLoadData */
    public JMessungFilter(JMainFrame parent) {
        super(parent,true);
        this.app = parent;
        initComponents();
        
        panelVonBis = new JPanelVonBis(new TimeFilter(new java.util.Date(),new java.util.Date()));       
        jPanelTime.add(panelVonBis,BorderLayout.CENTER);
        
        panelStatus = new JPanelStatus();
        jPanelStatus.add(panelStatus,BorderLayout.CENTER);
        
        panelAggr = new JPanelAggregate(new AggregateFilter());
        jPanelAggr.add(panelAggr,BorderLayout.CENTER);
                
        DateChangedListener d = new JMessungFilter.DateListener();       
        panelVonBis.getJSpinnerStart().addDateChangedListener(d);
        panelVonBis.getJSpinnerEnd().addDateChangedListener(d);
        panelAggr.addDateChangedListener(d);
        
       
        pack();
        setResizable(false);
        
        panelAggr.getJRadioButtonOriginal().addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JRadioButton b = (JRadioButton)evt.getSource();
                panelStatus.setEnabled(b.isSelected());
            }
        });
        getRootPane().setDefaultButton(jButtonOk);
       
    }
    
    private class DateListener implements DateChangedListener {
        public void changed(DateChangedEvent evt) {
            checkLoad();
        }        
    }
    
    public void setOriginalSelected(){
        panelAggr.setOriginalSelected();
    }
    
    public boolean isOriginalSelected(){
        return panelAggr.isOriginalSelected();
    }
    
    
    
    
    public void setCheckLoad(boolean value){
        this.checkLoad = value;
        jLabelEstimatedRecords.setVisible(value);
        jButtonOk.setEnabled(!value);
    }
    
    public boolean isCheckload(){
        return this.checkLoad;
    }
    
    private void checkLoad() {
       if (checkLoad){
        long r = getRecords();
        jButtonOk.setEnabled(r <= maxrecnumber); 
        jLabelEstimatedRecords.setText("Estimated records:" + r);   
       }
    }
    
    public void setMaxRecNumber(int value){
    	this.maxrecnumber = value;
    }
    
   private void checkStatus() 
    {        
       System.out.println("" + panelAggr.isOriginalSelected());
       panelStatus.setEnabled(panelAggr.isOriginalSelected());
       
    }
   
          
       
    public boolean isAggregateEnabled() {
        return aggregateEnabled;
    }
    
    public void setAggregateEnabled(boolean aggregateEnabled) {
        this.aggregateEnabled = aggregateEnabled;
        panelAggr.setAggregationLocked(!aggregateEnabled);
    }

    
    private long getRecords(){
        long ret;
        if (panelAggr.isOriginalSelected()){  // original
            if (resolution == 0) return 0;
            ret =  (getSeconds() + resolution)/resolution;
        } else { // aggregation     
            int secs = panelAggr.getIntervallSeconds();
            if (secs == 0) return 0;
            ret =  (getSeconds() + resolution)/secs;
        }
        
        if (ret < 0) { 
            return 0;
        } else {
            return ret;
        }
    }    
    
    
    private long getSeconds(){
       long ret = (panelVonBis.getFilter().getBis().getTime() - panelVonBis.getFilter().getVon().getTime())/1000;
       return ret;      
    }
    
    private void setResolution(Integer id){
           try {
           Vector params = new Vector(1);
           params.add(id);
           resolution = ((Integer)Client.getInstance().getXmlRpcClient().execute("MessungHandler.getResolution",params)).intValue();
           } catch (XmlRpcException e){
               MsgBox.error(e.getMessage());
           }
     }
    

    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelFilter = new javax.swing.JPanel();
        jPanelTime = new javax.swing.JPanel();
        jPanelStatus = new javax.swing.JPanel();
        jPanelAggr = new javax.swing.JPanel();
        jPanelButtom = new javax.swing.JPanel();
        jPanelButton = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelEstimatedRecords = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Set Data Filter");
        jPanelFilter.setLayout(new java.awt.GridBagLayout());

        jPanelTime.setLayout(new java.awt.BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelFilter.add(jPanelTime, gridBagConstraints);

        jPanelStatus.setLayout(new java.awt.BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelFilter.add(jPanelStatus, gridBagConstraints);

        jPanelAggr.setLayout(new java.awt.BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelFilter.add(jPanelAggr, gridBagConstraints);

        getContentPane().add(jPanelFilter, java.awt.BorderLayout.CENTER);

        jPanelButtom.setLayout(new java.awt.BorderLayout());

        jPanelButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonOk.setText("Ok");
        jButtonOk.setContentAreaFilled(false);
        jButtonOk.setDefaultCapable(false);
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jPanelButton.add(jButtonOk);

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jPanelButton.add(jButtonCancel);

        jPanelButtom.add(jPanelButton, java.awt.BorderLayout.CENTER);

        jLabelEstimatedRecords.setMinimumSize(new java.awt.Dimension(100, 15));
        jLabelEstimatedRecords.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanelButtom.add(jLabelEstimatedRecords, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanelButtom, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        okPressed = false;
        setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
         okPressed = true;
         setVisible(false);
    }//GEN-LAST:event_jButtonOkActionPerformed


    /**
     * Getter for property tFilter.
     * @return Value of property tFilter.
     */
    public TimeFilter getTimeFilter()  {
        return panelVonBis.getFilter();
    }
    
    /**
     * Setter for property tFilter.
     * @param tFilter New value of property tFilter.
     */
    public void setTimeFilter(TimeFilter tFilter) {
        panelVonBis.setFilter(tFilter);        
    }
    
    /**
     * Getter for property sFilter.
     * @return Value of property sFilter.
     */
    public StatusFilter getStatusFilter() {
        return panelStatus.getFilter();
    }
    
    /**
     * Setter for property sFilter.
     * @param sFilter New value of property sFilter.
     */
    public void setStatusFilter(StatusFilter sFilter) {
        panelStatus.setFilter(sFilter);
    }
    
    
    /**
     * Getter for property aFilter.
     * @return Value of property aFilter.
     */
    public AggregateFilter getAggrFilter() {
        return panelAggr.getFilter();
    }
    
    /**
     * Setter for property aFilter.
     * @param aFilter New value of property aFilter.
     */
    public void setAggrFilter(AggregateFilter aFilter) {
        panelAggr.setFilter(aFilter);
    }
    
    
    public boolean okPressed(){
        return okPressed;
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
        TimeFilter f = new TimeFilter(objektNode.getRec_start(),objektNode.getRec_end());
        setTimeFilter(f);        
        
        if (objektNode.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN)) {
          panelAggr.setAggregationLocked(false);
          setCheckLoad(true);
          setResolution(objektNode.getId());
          checkLoad();        
        } else if (objektNode.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN)) {
          panelAggr.setAggregationLocked(true);
          setCheckLoad(false);
        }
        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelEstimatedRecords;
    private javax.swing.JPanel jPanelAggr;
    private javax.swing.JPanel jPanelButtom;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JPanel jPanelFilter;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JPanel jPanelTime;
    // End of variables declaration//GEN-END:variables
    
}
