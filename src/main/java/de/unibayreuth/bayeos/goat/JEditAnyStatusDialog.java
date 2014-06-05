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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayeos.goat.list.ObjektNodeListCellRenderer;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;
import de.unibayreuth.bayeos.utils.date.JDefaultDateSpinner;



public class JEditAnyStatusDialog extends JDialog implements Constants {
    private JMainFrame app;
    
    private JMessungPickerDialog messungPicker;
    
    private JPanel messungPanel, intervalPanel, statusPanel, buttonPanel, messungButtonPanel;
    
    private JList messungJList;
    private JButton addButton, removeButton, okButton, cancelButton;
    private JDefaultDateSpinner vonSpinner, bisSpinner;
    private JComboBox statusCombo;
    private JLabel vonLabel, bisLabel;
    private JScrollPane messungPane;
        
    private ArrayList idIndex = new ArrayList(10);
    
    protected final static Logger logger = Logger.getLogger(JEditAnyStatusDialog.class.getName());
    
    /**
     * The resource file name
     */
    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.JEditAnyStatusDialog";
    
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
    
    
    public JEditAnyStatusDialog(JMainFrame application) throws JEditAnyStatusException {
        super(application,true);
        try {
        this.app = application;
        super.setTitle(resources.getString("Dialog.Title"));
        
        /* messungPanel
         */
        messungPanel = new JPanel(new BorderLayout(10,0));
        messungJList = new JList(new DefaultListModel());
        messungJList.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e ) {
             removeButton.setEnabled(!messungJList.isSelectionEmpty());
         }
        });
        messungJList.setCellRenderer(new ObjektNodeListCellRenderer());
                
        messungPane = new JScrollPane(messungJList);
        messungPane.setPreferredSize(new Dimension(300,100));
        messungPanel.add(messungPane,BorderLayout.WEST);
        
        messungButtonPanel = new JPanel();
        messungButtonPanel.setLayout(new BoxLayout(messungButtonPanel, BoxLayout.Y_AXIS));
        
        addButton = new JButton(resources.getString("AddButton.Text"));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i){
              if (messungPicker == null) {
                try {
                    messungPicker = new JMessungPickerDialog(app);
                    } catch (JMessungPickerException z) {
                     logger.error(z.getMessage());
                     MsgBox.error(JEditAnyStatusDialog.this,"Couldn't create Dialog JMessungPicker.");
                     return;
                    }
                } 
                messungPicker.setLocationRelativeTo(addButton);
                messungPicker.setVisible(true);
                if (messungPicker.isPicked()) {
                    DefaultListModel lm = (DefaultListModel)messungJList.getModel();
                    for (int y=0;y<lm.getSize();y++) {
                       if (messungPicker.getObjektNode().equals(lm.getElementAt(y))) {
                        MsgBox.info(JEditAnyStatusDialog.this,"Objekt already exists in list.");
                        return;
                       }
                    };
                    lm.addElement(messungPicker.getObjektNode());
                }
            }
        });
        
        
        // ActionListener
        removeButton = new JButton(resources.getString("RemoveButton.Text"));
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i) {
                    DefaultListModel lm = (DefaultListModel)messungJList.getModel();
                    lm.remove(messungJList.getSelectedIndex());
            }
        });    
        removeButton.setEnabled(false);
        // ActionListener
        messungButtonPanel.add(addButton);
        messungButtonPanel.add(Box.createVerticalStrut(10));
        messungButtonPanel.add(removeButton);
        
        messungPanel.add(messungButtonPanel, BorderLayout.EAST);
        messungPanel.setBorder(BorderFactory.createTitledBorder(resources.getString("MessungPanel.Border.Text")));
        
        
        /* intervalPanel
        */
        intervalPanel = new JPanel(new GridLayout(1,1));
        
        vonLabel = new JLabel(resources.getString("VonLabel.Text"));
        vonSpinner = new JDefaultDateSpinner();
        JPanel leftPanel = new JPanel();
        leftPanel.add(vonLabel);
        leftPanel.add(vonSpinner);
        
        bisLabel = new JLabel(resources.getString("BisLabel.Text"));
        bisSpinner = new JDefaultDateSpinner();
        JPanel rightPanel = new JPanel();
        rightPanel.add(bisLabel);
        rightPanel.add(bisSpinner);

        intervalPanel.add(leftPanel);
        intervalPanel.add(rightPanel);
        intervalPanel.setBorder(BorderFactory.createTitledBorder(resources.getString("IntervalPanel.Border.Text")));
        
        
        /* statusPanel
         */
        statusCombo = new JComboBox();
        try {
            Vector params = new Vector();
            //params.add(ObjektArt.MESSUNG_MASSENDATEN.toString());
            Vector vReturn = (Vector)app.getXmlClient().execute("LookUpTableHandler.getStatus",params);
            Iterator it = vReturn.iterator();
            while(it.hasNext()){
                Vector row = (Vector)it.next();
                Integer id = (Integer)row.elementAt(0);
                idIndex.add(id);
                String bez = (String)row.elementAt(1);
                statusCombo.addItem(bez);
            }
        } catch (XmlRpcException x) {
            throw new JEditAnyStatusException("Can't create Dialog.", x);
        }
        
        // add statusCombo to panel
        statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel(resources.getString("StatusCombo.Label")));
        statusPanel.add(statusCombo);
        statusPanel.setBorder(BorderFactory.createTitledBorder(resources.getString("StatusPanel.Border.Text")));
        
        
        // create buttons
        okButton = new JButton(resources.getString("OkButton.Text"));
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                logger.debug("Updating selected objekts ...");
                DefaultListModel lm = (DefaultListModel)messungJList.getModel();
                for (int y=0;y<lm.getSize();y++) {
                ObjektNode n = (ObjektNode)lm.getElementAt(y);
                try {
                  Vector params = new Vector();
                  params.add(n.getId());
                  params.add(n.getObjektart().toString());
                  params.add((java.util.Date)vonSpinner.getModel().getValue());
                  params.add((java.util.Date)bisSpinner.getModel().getValue());
                  params.add((Integer)idIndex.get(statusCombo.getSelectedIndex()));
                  Integer ret  = (Integer)app.getXmlClient().execute("ToolsHandler.updateRows",params);
                  MsgBox.info(JEditAnyStatusDialog.this,"Updated " + ret.intValue() + " rows in " + n.getDe() + ".");
                } catch (XmlRpcException x) {
                    logger.error(x.getMessage());
                    MsgBox.error(JEditAnyStatusDialog.this,"Couldn't update " + n.getDe() + ".");
                }
                };
            }
        });
        
        cancelButton = new JButton(resources.getString("CancelButton.Text"));
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setVisible(false);
            }
        });
        
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        
        Container content = getContentPane();
        LayoutManager lm = new BoxLayout(content, BoxLayout.Y_AXIS);
        content.setLayout(lm);
        content.add(messungPanel);
        content.add(intervalPanel);
        content.add(statusPanel);
        content.add(buttonPanel);

        pack();       
        setLocationRelativeTo(application);
        // setResizable(false);
     } catch (MissingResourceException r) {
         throw new JEditAnyStatusException("Can't find resource for JEditAnyStatusDialog.", r);
     }
        
    }
    
    
}
