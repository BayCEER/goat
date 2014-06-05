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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.goat.tree.DefaultObjektNode;
import de.unibayreuth.bayeos.goat.tree.JObjektTree;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.goat.tree.ObjektNodeHelper;
import de.unibayreuth.bayeos.goat.tree.ObjektNodeHelperException;
import de.unibayreuth.bayeos.goat.tree.ObjektTreeModel;
import de.unibayreuth.bayeos.goat.tree.ObjektTreeRenderer;
import de.unibayreuth.bayeos.goat.tree.XmlRpcObjektNodeHelper;

public class JMessungPickerDialog extends JDialog implements Constants {
    private boolean picked = false;
    
    private ObjektNode objektNode;
    private JObjektTree tree;

    private JScrollPane messungPane;
    
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    
    
    private boolean pickLabordaten = true, pickMassendaten = true, pickOnlyWritable = true;
    
    
    protected final static Logger logger = Logger.getLogger(JMessungPickerDialog.class.getName());
    
    /**
     * The resource file name
     */
    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.JMessungPickerDialog";
    
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
    
    public JMessungPickerDialog(JMainFrame application, boolean pickMassendaten, boolean pickLabordaten, boolean pickOnlyWritable)
    throws JMessungPickerException {
        this(application);
        this.pickLabordaten = pickLabordaten;
        this.pickMassendaten = pickMassendaten;
        this.pickOnlyWritable = pickOnlyWritable;
    }
    
    private void setOkButton(ObjektNode objektNode) {
        if (objektNode == null) return;
                boolean pick;
                if (objektNode.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN)){
                  pick = pickMassendaten;     
                } else if (objektNode.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN)){
                  pick = pickLabordaten;                    
                } else {
                  pick = false;
                }                
                if (pickOnlyWritable) {
                  pick = pick && objektNode.getCheck_write().booleanValue();
                }
                okButton.setEnabled(pick);
    }
    
    
    public JMessungPickerDialog(JMainFrame application) throws JMessungPickerException {
        super(application,resources.getString("Dialog.Title"),true);
        // Init Tree
        try {
        ObjektNodeHelper helper = new XmlRpcObjektNodeHelper();
        DefaultTreeModel model = new ObjektTreeModel(helper);
        ObjektNode root = helper.getRoot(ObjektArt.MESSUNG_ORDNER.toString());
        if (root != null) {
            model.setRoot(new DefaultObjektNode(root, helper));
        }       
	tree = new JObjektTree(model);
        } catch (ObjektNodeHelperException t) {
            throw new JMessungPickerException(t.getMessage(),t);
        }
        tree.setCellRenderer(new ObjektTreeRenderer());
        tree.addTreeSelectionListener(new TreeSelectionListener(){
            public void valueChanged(TreeSelectionEvent e) {
                JObjektTree tree = (JObjektTree)e.getSource();
                objektNode = (ObjektNode)tree.getLastSelectedUserObjekt();
                setOkButton(objektNode); 
            }
        }
        );
        
        // double click
        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                 JObjektTree tree = (JObjektTree)e.getSource();
                 objektNode = (ObjektNode)tree.getLastSelectedUserObjekt();
                 setOkButton(objektNode);                
                 if (okButton.isEnabled()){
                    picked = true;
                    setVisible(false);
                 }
                }
            }
        });
        
        
        messungPane = new JScrollPane(tree);
              
        // create buttons
        okButton = new JButton(resources.getString("OkButton.Text"));
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               logger.debug("Ok Button pressed");
               picked = true;
               setVisible(false);
            }
        });
        okButton.setEnabled(false);
        cancelButton = new JButton(resources.getString("CancelButton.Text"));
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                picked = false;
                setVisible(false);
            }
        });
        
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        getContentPane().add(messungPane,BorderLayout.CENTER);
        getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        setResizable(false);
        pack();
        
    }
    
    /** Getter for property picked.
     * @return Value of property picked.
     *
     */
    public boolean isPicked() {
        return picked;
    }    
    
    /** Getter for property objektNode.
     * @return Value of property objektNode.
     *
     */
    public ObjektNode getObjektNode() {
        return objektNode;
    }    
   
    /** Setter for property objektNode.
     * @param objektNode New value of property objektNode.
     *
     */
    public void setObjektNode(ObjektNode objektNode) {
        this.objektNode = objektNode;
    }    
    
    
   
    
}
