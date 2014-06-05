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
package de.unibayreuth.bayeos.goat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.MsgBox;

public class JNameObjektDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButton = null;
	private JButton jButtonOk = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelMsg = null;
	private JLabel jLabelMsg = null;
	private JTextField jTextField = null;
	private boolean okPressed = false;

	public boolean isOkPressed() {
		return okPressed;
	}
	
	public void setName(String text){
		this.jTextField.setText(text);
	}
	
	public String getName(){
		return this.jTextField.getText();
	}

	/**
	 * @param owner
	 */
	public JNameObjektDialog(Frame owner, ObjektArt art) {
		super(owner,true);
		setIconImage(ObjektImageFactory.getImage(art, false));		
		initialize();
		getRootPane().setDefaultButton(jButtonOk);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(357, 107);
		this.setResizable(true);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelButton(), BorderLayout.SOUTH);
			jContentPane.add(getJPanelMsg(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelButton	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButton() {
		if (jPanelButton == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			jPanelButton = new JPanel();
			jPanelButton.setLayout(flowLayout);
			jPanelButton.add(getJButtonOk(), null);
			jPanelButton.add(getJButtonCancel(), null);
		}
		return jPanelButton;
	}

	/**
	 * This method initializes jButtonOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton();
			jButtonOk.setText("Ok");
			jButtonOk.setSelected(true);
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTextField.getText() == null || (jTextField.getText().isEmpty())) {
			            MsgBox.info(JNameObjektDialog.this,"Please insert a valid name !");
			            return;
					}
					okPressed = true;
					setVisible(false);
				}
			});
			
		}
		return jButtonOk;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText("Cancel");
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okPressed = false;
					setVisible(false);
				}
			});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jPanelMsg	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelMsg() {
		if (jPanelMsg == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints.gridx = 1;
			jLabelMsg = new JLabel();
			jLabelMsg.setText("Name:");
			jPanelMsg = new JPanel();
			jPanelMsg.setLayout(new GridBagLayout());
			jPanelMsg.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanelMsg.add(jLabelMsg, gridBagConstraints1);
			jPanelMsg.add(getJTextField(), gridBagConstraints);
		}
		return jPanelMsg;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField;
	}

}  //  @jve:decl-index=0:visual-constraint="141,42"
