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
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.batik.util.gui.resource.ResourceManager;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;

public class JNewObjektsDialog extends JDialog {
	
protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.JNewObjektsDialog";
    
    /**
     * The resource bundle
     */
    protected static ResourceBundle bundle;
    
    /**
     * The resource manager
     */
    protected static ResourceManager resources;
    
    static {
        bundle = ResourceBundle.getBundle(RESOURCES, Locale.getDefault());
        resources = new ResourceManager(bundle);
    }

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOk = null;
	private JButton jButtonCancel = null;
	private JTextArea jTextArea = null;
	private JLabel jLabelComment = null;
	private JPanel jPanelComment = null;
	private boolean okPressed = false;
	private ObjektArt art;

	private JScrollPane jScrollPane = null;
	
	public boolean isOkPressed() {
		return okPressed;
	}




	/**
	 * @param owner
	 * @param art 
	 */
	public JNewObjektsDialog(Frame owner, ObjektArt art) {
		super(owner,resources.getString("Dialog.Title." + art.toString()),true);	
		setIconImage(ObjektImageFactory.getImage(art, false));
		this.art = art;
		initialize();
		
	}
	
	
	
	
	public ArrayList<String> getNames(){
		
		ArrayList<String> ret = new ArrayList<String>();
		String[] lines = jTextArea.getText().split("\\n");
	    for (int i = 0; i < lines.length; i++) {
	    	if (!lines[i].isEmpty()){
	    		ret.add(lines[i].trim());	
	    	}
		}	
	    return ret;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(388, 287);		
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelComment = new JLabel();
			jLabelComment.setText("Please enter names separated by line.");
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelButtons(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanelComment(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(flowLayout);
			jPanelButtons.add(getJButtonOk(), null);
			jPanelButtons.add(getJButtonCancel(), null);
		}
		return jPanelButtons;
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
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {    
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
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setText("");
		}
		return jTextArea;
	}

	/**
	 * This method initializes jPanelComment	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelComment() {
		if (jPanelComment == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(FlowLayout.LEFT);
			jPanelComment = new JPanel();
			jPanelComment.setLayout(flowLayout1);
			jPanelComment.add(jLabelComment, null);
		}
		return jPanelComment;
	}




	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
