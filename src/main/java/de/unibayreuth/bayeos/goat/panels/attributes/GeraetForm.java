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
import java.util.Date;

import javax.swing.JPanel;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;

/*
 * 
 *
 * Created on 10. Februar 2003, 13:35
 */

/**
 *
 * @author  oliver
 */
public class GeraetForm extends JPanel {
    
    private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
    private boolean editable = false;
    
    /** Creates new Form Messungen */
    public GeraetForm() {       
        initComponents();
    }
    
    public void setEditable(boolean enabled){
      this.editable = enabled;
      textBeschreibung.setEditable(enabled);
      textFieldSeriennr.setEditable(enabled);
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

        labelVon = new javax.swing.JLabel();
        labelBis = new javax.swing.JLabel();
        textFieldSeriennr = new javax.swing.JFormattedTextField();
        labelSeriennr = new javax.swing.JLabel();
        labelBeschreibung = new javax.swing.JLabel();
        textBeschreibung = new javax.swing.JTextArea();
        labelCTime = new javax.swing.JLabel();
        labelUTime = new javax.swing.JLabel();
        labelId = new javax.swing.JLabel();
        textFieldId = new javax.swing.JTextField();
        labelObjektArt = new javax.swing.JLabel();
        textFieldObjektArt = new javax.swing.JTextField();
        textFieldVon = new javax.swing.JTextField();
        textFieldBis = new javax.swing.JTextField();
        textFieldCTime = new javax.swing.JTextField();
        textFieldUpdated = new javax.swing.JTextField();
        textFieldCBenutzer = new javax.swing.JTextField();
        textFieldUBenutzer = new javax.swing.JTextField();
        lblCBenutzer = new javax.swing.JLabel();
        lblUBenutzer = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        setMinimumSize(new java.awt.Dimension(420, 147));
        setPreferredSize(new java.awt.Dimension(420, 147));
        labelVon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelVon.setText("Start:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelVon, gridBagConstraints);

        labelBis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelBis.setText("End:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldSeriennr, gridBagConstraints);

        labelSeriennr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSeriennr.setText("Serial number:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelSeriennr, gridBagConstraints);

        labelBeschreibung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelBeschreibung.setText("Description:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelBeschreibung, gridBagConstraints);

        textBeschreibung.setLineWrap(true);
        textBeschreibung.setRows(4);
        textBeschreibung.setWrapStyleWord(true);
        textBeschreibung.setMinimumSize(new java.awt.Dimension(100, 64));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textBeschreibung, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 1, 5);
        add(labelObjektArt, gridBagConstraints);

        textFieldObjektArt.setEditable(false);
        textFieldObjektArt.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldObjektArt, gridBagConstraints);

        textFieldVon.setEditable(false);
        textFieldVon.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldVon, gridBagConstraints);

        textFieldBis.setEditable(false);
        textFieldBis.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        add(textFieldBis, gridBagConstraints);

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

    }//GEN-END:initComponents
        
    
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
    
    
    /** Getter for property textFieldBezeichnung.
    
    
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
        if (Id != null){
            this.textFieldId.setText(String.valueOf(Id));
        } else {
          textFieldId.setText(null);
        }
    }
    
    /** Getter for property textFieldObjektArt.
     * @return Value of property textFieldObjektArt.
     *
     */
    public ObjektArt getObjektArt() {
        return ObjektArt.get(textFieldObjektArt.getText());
    }
    
    /** Setter for property textFieldObjektArt.
     * @param textFieldObjektArt New value of property textFieldObjektArt.
     *
     */
    public void setObjektArt(ObjektArt art) {
        if (art != null)  { 
            this.textFieldObjektArt.setText(art.toString());
        } else {
          textFieldObjektArt.setText(null);
        }
    }
    
    /** Getter for property textFieldSeriennr.
     * @return Value of property textFieldSeriennr.
     *
     */
    public String getSeriennr() {
        return textFieldSeriennr.getText();
    }
    
    /** Setter for property textFieldSeriennr.
     * @param textFieldSeriennr New value of property textFieldSeriennr.
     *
     */
    public void setSeriennr(String serialNumber) {
        textFieldSeriennr.setText(serialNumber);
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
    private javax.swing.JLabel labelBeschreibung;
    private javax.swing.JLabel labelBis;
    private javax.swing.JLabel labelCTime;
    private javax.swing.JLabel labelId;
    private javax.swing.JLabel labelObjektArt;
    private javax.swing.JLabel labelSeriennr;
    private javax.swing.JLabel labelUTime;
    private javax.swing.JLabel labelVon;
    private javax.swing.JLabel lblCBenutzer;
    private javax.swing.JLabel lblUBenutzer;
    private javax.swing.JTextArea textBeschreibung;
    private javax.swing.JTextField textFieldBis;
    private javax.swing.JTextField textFieldCBenutzer;
    private javax.swing.JTextField textFieldCTime;
    private javax.swing.JTextField textFieldId;
    private javax.swing.JTextField textFieldObjektArt;
    private javax.swing.JFormattedTextField textFieldSeriennr;
    private javax.swing.JTextField textFieldUBenutzer;
    private javax.swing.JTextField textFieldUpdated;
    private javax.swing.JTextField textFieldVon;
    // End of variables declaration//GEN-END:variables
    
}
