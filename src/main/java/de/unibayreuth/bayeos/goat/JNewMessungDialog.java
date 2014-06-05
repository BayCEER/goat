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
 * JNewMessungDialog.java
 *
 * Created on 12. M�rz 2003, 15:48
 */

package de.unibayreuth.bayeos.goat;

import javax.swing.JComboBox;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class JNewMessungDialog extends javax.swing.JDialog {
    
    private static ObjektArt[] types = {ObjektArt.MESSUNG_ORDNER,ObjektArt.MESSUNG_LABORDATEN,ObjektArt.MESSUNG_MASSENDATEN};
    
    private javax.swing.JLabel labelName;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel selectPanel;
    private javax.swing.JComboBox comboBoxObjektArt;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel labelObjekArt;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JTextField textFieldName;

    
    private boolean canceled = true;

    
    /** Creates new form JNewMessungDialog */
    public JNewMessungDialog(java.awt.Frame parentFrame, boolean modal) {
        super(parentFrame,"New node",modal);
        initComponents();
        setLocationRelativeTo(parentFrame);
        getRootPane().setDefaultButton(okButton);
    }
    
        
   private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        okButton.setDefaultCapable(true);
        cancelButton = new javax.swing.JButton();
        selectPanel = new javax.swing.JPanel();
        textFieldName = new javax.swing.JTextField();
        labelName = new javax.swing.JLabel();
        labelObjekArt = new javax.swing.JLabel();
        comboBoxObjektArt = new JComboBox(types);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        selectPanel.setLayout(new java.awt.GridBagLayout());

        selectPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 107;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        selectPanel.add(textFieldName, gridBagConstraints);

        labelName.setText("Name:");
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        selectPanel.add(labelName, gridBagConstraints);

        labelObjekArt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelObjekArt.setText("Object Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
        selectPanel.add(labelObjekArt, gridBagConstraints);


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 107;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
        selectPanel.add(comboBoxObjektArt, gridBagConstraints);

        getContentPane().add(selectPanel, java.awt.BorderLayout.CENTER);

        pack();
    }

    
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (textFieldName.getText() == null || (textFieldName.getText() == "")) {
            MsgBox.info(this,"Please insert a valid name !");
        } else 
        { doClose(false);
        }
    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(true);
    }
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {
        doClose(true);
    }
    
    
    public ObjektArt getObjektart() {
        return (ObjektArt)comboBoxObjektArt.getSelectedItem();
    }
    
    public String getName() {
        return textFieldName.getText();
    }
    
    private void doClose(boolean canceled) {
        this.canceled = canceled;
        setVisible(false);
        dispose();
    }
    
   
    
    /** Getter for property canceled.
     * @return Value of property canceled.
     *
     */
    public boolean isCanceled() {
        return canceled;
    }
    
    

    
    
    
}


