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
package de.unibayreuth.bayeos.goat.panels.attributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.LookUpTableFactory;

/*
 * 
 *
 * Created on 10. Februar 2003, 13:35
 */

/**
 *
 * @author  oliver
 */
public class MessungForm extends JPanel {
    
    private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
    private boolean editable = false;
    
    private static ObjektArt[] types = {ObjektArt.MESSUNG_ORDNER,ObjektArt.MESSUNG_LABORDATEN,ObjektArt.MESSUNG_MASSENDATEN};
    
    private ArrayList idIntIndex = new ArrayList(10);
    private ArrayList idTzIndex = new ArrayList(10);
    
    /** Creates new Form Messungen */
    public MessungForm() {       
        initComponents();

        Vector tz = LookUpTableFactory.getTimeZones();
        Iterator tzi = tz.iterator();
        while(tzi.hasNext()){
            Vector row = (Vector)tzi.next();
            Integer id = (Integer)row.elementAt(0);
            idTzIndex.add(id);
            cboTimeZone.addItem((String)row.elementAt(1));   
        }
        
        
        Vector intervalTypes = LookUpTableFactory.getIntervalTyps();    
        Iterator it = intervalTypes.iterator();
        while(it.hasNext()){
          Vector row = (Vector)it.next();
          Integer id = (Integer)row.elementAt(0);
          idIntIndex.add(id);
          cboIntervalTyp.addItem((String)row.elementAt(1));   
        }
    }
    
    public void setEditable(boolean enabled){
      this.editable = enabled;
      textBeschreibung.setEditable(enabled);
      textFieldAufloesung.setEditable(enabled);
      cboTimeZone.setEnabled(enabled);
    }
        
    public boolean getEditable() {
        return this.editable;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        labelMrecStart = new javax.swing.JLabel();
        labelMrecEnd = new javax.swing.JLabel();
        textFieldAufloesung = new javax.swing.JFormattedTextField();
        labelAufloesung = new javax.swing.JLabel();
        cboTimeZone = new javax.swing.JComboBox();
        labelTimeZone = new javax.swing.JLabel();
        labelBeschreibung = new javax.swing.JLabel();
        textBeschreibung = new javax.swing.JTextArea();
        labelCTime = new javax.swing.JLabel();
        labelUTime = new javax.swing.JLabel();
        labelId = new javax.swing.JLabel();
        textFieldId = new javax.swing.JTextField();
        labelObjektArt = new javax.swing.JLabel();
        textFieldMrecStart = new javax.swing.JTextField();
        textFieldMrecEnd = new javax.swing.JTextField();
        textFieldCTime = new javax.swing.JTextField();
        textFieldUpdated = new javax.swing.JTextField();
        textFieldObjektArt = new javax.swing.JTextField();
        textFieldCBenutzer = new javax.swing.JTextField();
        textFieldUBenutzer = new javax.swing.JTextField();
        lblCBenutzer = new javax.swing.JLabel();
        lblUBenutzer = new javax.swing.JLabel();
        textFieldPlanStart = new javax.swing.JTextField();
        textFieldPlanEnd = new javax.swing.JTextField();
        labelPlanStart = new javax.swing.JLabel();
        labelPlanEnd = new javax.swing.JLabel();
        cboIntervalTyp = new javax.swing.JComboBox();
        labelIntervalTyp = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        setMinimumSize(new java.awt.Dimension(420, 147));
        setPreferredSize(new java.awt.Dimension(420, 147));
        labelMrecStart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMrecStart.setText("Records from:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelMrecStart, gridBagConstraints);

        labelMrecEnd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMrecEnd.setText("Records until:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelMrecEnd, gridBagConstraints);

        textFieldAufloesung.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldAufloesung, gridBagConstraints);

        labelAufloesung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelAufloesung.setText("Resolution [secs]:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelAufloesung, gridBagConstraints);

        cboTimeZone.setPreferredSize(new java.awt.Dimension(50, 19));
        

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(cboTimeZone, gridBagConstraints);

        labelTimeZone.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelTimeZone.setText("Timezone:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelTimeZone, gridBagConstraints);

        labelBeschreibung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelBeschreibung.setText("Description:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelBeschreibung, gridBagConstraints);

        textBeschreibung.setLineWrap(true);
        textBeschreibung.setWrapStyleWord(true);
        textBeschreibung.setMinimumSize(new java.awt.Dimension(100, 64));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(new JScrollPane(textBeschreibung), gridBagConstraints);

        labelCTime.setText("Created:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelCTime, gridBagConstraints);

        labelUTime.setText("Updated:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelUTime, gridBagConstraints);

        labelId.setText("Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelId, gridBagConstraints);

        textFieldId.setEditable(false);
        textFieldId.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldId, gridBagConstraints);

        labelObjektArt.setText("Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelObjektArt, gridBagConstraints);

        textFieldMrecStart.setEditable(false);
        textFieldMrecStart.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldMrecStart, gridBagConstraints);

        textFieldMrecEnd.setEditable(false);
        textFieldMrecEnd.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldMrecEnd, gridBagConstraints);

        textFieldCTime.setEditable(false);
        textFieldCTime.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldCTime, gridBagConstraints);

        textFieldUpdated.setEditable(false);
        textFieldUpdated.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldUpdated, gridBagConstraints);

        textFieldObjektArt.setEditable(false);
        textFieldObjektArt.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldObjektArt, gridBagConstraints);

        textFieldCBenutzer.setEditable(false);
        textFieldCBenutzer.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldCBenutzer, gridBagConstraints);

        textFieldUBenutzer.setEditable(false);
        textFieldUBenutzer.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldUBenutzer, gridBagConstraints);

        lblCBenutzer.setText("Created from:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(lblCBenutzer, gridBagConstraints);

        lblUBenutzer.setText("Updated from:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(lblUBenutzer, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldPlanStart, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldPlanEnd, gridBagConstraints);

        labelPlanStart.setText("Planned from:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelPlanStart, gridBagConstraints);

        labelPlanEnd.setText("Planned until:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelPlanEnd, gridBagConstraints);

        cboIntervalTyp.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(cboIntervalTyp, gridBagConstraints);

        labelIntervalTyp.setText("Interval Label:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelIntervalTyp, gridBagConstraints);

    }//GEN-END:initComponents
  
    public void setIdIntervaltyp(Integer id){
        cboIntervalTyp.setSelectedIndex(idIntIndex.indexOf(id));        
    }
    
    public Integer getIdIntervalTyp() {
        return (Integer)idIntIndex.get(cboIntervalTyp.getSelectedIndex());
        
    }

    
    public void setTimeZone(Integer id) {
        cboTimeZone.setSelectedIndex(idTzIndex.indexOf(id));
    }
    
    public Integer getTimeZone(){
    	return (Integer)idTzIndex.get(cboTimeZone.getSelectedIndex());
    }
    
    
    
    /** Getter for property dateBis.
     * @return Value of property dateBis.
     *
     */
    public Date getRec_end() throws ParseException {
      return (textFieldMrecEnd.getText().length() > 0 ? df.parse(textFieldMrecEnd.getText()):null);
    }    
    
    /** Setter for property dateBis.
     * @param dateBis New value of property dateBis.
     *
     */
    public void setRec_end(Date date) {
        if (date != null) {
          textFieldMrecEnd.setText(df.format(date));
        } else {
          textFieldMrecEnd.setText(null);
        }
        
    }
    
    
    /** Getter for property dateBis.
     * @return Value of property dateBis.
     *
     */
    public Date getPlan_end() throws ParseException {
      return (textFieldPlanEnd.getText().length() > 0 ? df.parse(textFieldPlanEnd.getText()):null);
    }    
    
    /** Setter for property dateBis.
     * @param dateBis New value of property dateBis.
     *
     */
    public void setPlan_end(Date date) {
        if (date != null) {
          textFieldPlanEnd.setText(df.format(date));
        } else {
          textFieldPlanEnd.setText(null);
        }
        
    }
    
    
    /** Getter for property dateCTime.
     * @return Value of property dateCTime.
     *
     */
    public Date getCTime() throws ParseException {
        return (textFieldCTime.getText().length() > 0 ? df.parse(textFieldCTime.getText()):null);
    }
    
    /** Setter for property dateCTime.
     * @param dateCTime New value of property dateCTime.
     *
     */
    public void setCTime(Date date) {
        if (date != null) {
          textFieldCTime.setText(df.format(date));
        } else {
          textFieldCTime.setText(null);
        } 
        
    }
    
    /** Getter for property dateUTime.
     * @return Value of property dateUTime.
     *
     */
    public Date getUTime() throws ParseException {
        return (textFieldUpdated.getText().length() > 0 ? df.parse(textFieldUpdated.getText()):null);
    }
    
    /** Setter for property dateUTime.
     * @param dateUTime New value of property dateUTime.
     *
     */
    public void setUTime(Date date) {
        if (date != null) {
          textFieldUpdated.setText(df.format(date));
        } else {
          textFieldUpdated.setText(null);
        }
    }
    
    /** Getter for property dateVon.
     * @return Value of property dateVon.
     *
     */
    public Date getRec_start() throws ParseException {
      return (textFieldMrecStart.getText().length() > 0 ? df.parse(textFieldMrecStart.getText()):null);
    }
    
    /** Setter for property dateVon.
     * @param dateVon New value of property dateVon.
     *
     */
    public void setRec_start(Date date) {
        if (date != null) {
          textFieldMrecStart.setText(df.format(date));
        } else {
          textFieldMrecStart.setText(null);
        }
    }
    
    /** Getter for property dateVon.
     * @return Value of property dateVon.
     *
     */
    public Date getPlan_start() throws ParseException {
      return (textFieldPlanStart.getText().length() > 0 ? df.parse(textFieldPlanStart.getText()):null);
    }
    
    /** Setter for property dateVon.
     * @param dateVon New value of property dateVon.
     *
     */
    public void setPlan_start(Date date) {
        if (date != null) {
          textFieldPlanStart.setText(df.format(date));
        } else {
          textFieldPlanStart.setText(null);
        }
    }
    
    /** Getter for property labelAufloesung.
     * @return Value of property labelAufloesung.
     *
     */
    public Integer getAufloesung() throws NumberFormatException {
       return (textFieldAufloesung.getText().length() > 0 ? Integer.valueOf(textFieldAufloesung.getText()):null);
    }
    
    /** Setter for property labelAufloesung.
     * @param labelAufloesung New value of property labelAufloesung.
     *
     */
    public void setAufloesung(Integer Aufloesung) {
        if (Aufloesung != null) {
         this.textFieldAufloesung.setText(String.valueOf(Aufloesung));
        } else {
         this.textFieldAufloesung.setText(null);
        }
    }
    
    public String getBeschreibung() {
        return textBeschreibung.getText();
    }
    
    /** Setter for property textBeschreibung.
     * @param textBeschreibung New value of property textBeschreibung.
     *
     */
    public void setBeschreibung(String text) {
        this.textBeschreibung.setText(text);
    }
    
    
    
    /** Getter for property textFieldId.
     * @return Value of property textFieldId.
     *
     */
    public Integer getId() throws NumberFormatException {
        return (textFieldId.getText().length() > 0 ? Integer.valueOf(textFieldId.getText()):null);
    }
    
    /** Setter for property textFieldId.
     * @param textFieldId New value of property textFieldId.
     *
     */
    public void setId(Integer Id) {
        if (Id != null) {
            this.textFieldId.setText(String.valueOf(Id));
        }else {
            this.textFieldId.setText(null);
        }
    }
    
    
    public ObjektArt getObjektArt() {
        return ObjektArt.get(textFieldObjektArt.getText());
    }
    
    /** Setter for property textFieldObjektArt.
     * @param textFieldObjektArt New value of property textFieldObjektArt.
     *
     */
    public void setObjektArt(ObjektArt art) {
        this.textFieldObjektArt.setText(art.toString());
        
        boolean extField = art != ObjektArt.MESSUNG_ORDNER;
        
        cboIntervalTyp.setVisible(extField);
        labelIntervalTyp.setVisible(extField);
        textFieldAufloesung.setVisible(extField);
        labelAufloesung.setVisible(extField);
        
        cboTimeZone.setVisible(extField);
        labelTimeZone.setVisible(extField);
        
        
    }
    
    public void setUBenutzer(String benutzer) {
        textFieldUBenutzer.setText(benutzer);
    }    
    
    public void setCBenutzer(String benutzer) {
        textFieldCBenutzer.setText(benutzer);
    }    
    
    public String getCBenutzer() {
        return textFieldCBenutzer.getText();
    }    
    
    public String getUBenutzer() {
        return textFieldUBenutzer.getText();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboIntervalTyp;
    private javax.swing.JComboBox cboTimeZone;
   
    private javax.swing.JLabel labelAufloesung;
    private javax.swing.JLabel labelBeschreibung;
    private javax.swing.JLabel labelCTime;
    private javax.swing.JLabel labelId;
    private javax.swing.JLabel labelIntervalTyp;
    private javax.swing.JLabel labelMrecEnd;
    private javax.swing.JLabel labelMrecStart;
    private javax.swing.JLabel labelObjektArt;
    private javax.swing.JLabel labelPlanEnd;
    private javax.swing.JLabel labelPlanStart;
    private javax.swing.JLabel labelTimeZone;
   
    private javax.swing.JLabel labelUTime;
    private javax.swing.JLabel lblCBenutzer;
    private javax.swing.JLabel lblUBenutzer;
    private javax.swing.JTextArea textBeschreibung;
    private javax.swing.JFormattedTextField textFieldAufloesung;
    private javax.swing.JTextField textFieldCBenutzer;
    private javax.swing.JTextField textFieldCTime;
    private javax.swing.JTextField textFieldId;
    private javax.swing.JTextField textFieldMrecEnd;
    private javax.swing.JTextField textFieldMrecStart;
    private javax.swing.JTextField textFieldObjektArt;
    private javax.swing.JTextField textFieldPlanEnd;
    private javax.swing.JTextField textFieldPlanStart;
    private javax.swing.JTextField textFieldUBenutzer;
    private javax.swing.JTextField textFieldUpdated;
    // End of variables declaration//GEN-END:variables
    
}
