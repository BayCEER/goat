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
package de.unibayreuth.bayeos.goat.panels.others;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableColumn;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.panels.DetailPanel;
import de.unibayreuth.bayeos.goat.table.DateEditor;
import de.unibayreuth.bayeos.goat.table.DateRenderer;
import de.unibayreuth.bayeos.goat.table.ObjektArtTableCellRenderer;
import de.unibayreuth.bayeos.goat.table.ReferenceTableModel;
import de.unibayreuth.bayeos.goat.tree.DefaultObjektNode;
import de.unibayreuth.bayeos.goat.tree.JObjektTree;
import de.unibayreuth.bayeos.goat.tree.JObjektTreePanel;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;

public class JRefPanel extends DetailPanel {
    
    final static Logger logger = Logger.getLogger(JRefPanel.class.getName()); 
    
     /**
     * The resource file name
     */
    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.panels.JRefPanel";

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
    
    
    private JTable dirTable, inhTable;
    private ReferenceTableModel dirModel, inhModel;
    private JPanel dirPanel, inhPanel;
    private JButton dirAddButton, dirRemoveButton, dirExpandButton, inhExpandButton;
    
    private JScrollPane dirScrollPane, inhScrollPane;
    private JPanel dirButtonPanel, inhButtonPanel;
    private XmlRpcClient xmlClient;
    private boolean fullAccess = false;
    
    private ObjektNode refNode;
    
    
    
    private JMainFrame app;
    
    public JRefPanel(JMainFrame app) throws MissingResourceException {
        super(app);
        this.app = app;
        this.xmlClient = app.getXmlClient();
        
        makeDirPanel();
        makeInhPanel();
                
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(dirPanel);
        add(inhPanel);
        Iterator it = app.getTreePane().getObjektTreePanels().values().iterator();
        TreeSelectionListener l = new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null){
                    setRefNode(null);
                } else {
                    DefaultObjektNode node = (DefaultObjektNode)e.getPath().getLastPathComponent();
                    setRefNode((ObjektNode)node.getUserObject());    
                }
                
                
            }    
        };
        while(it.hasNext()) {
            JObjektTree tree = ((JObjektTreePanel)it.next()).getObjektTree();
            tree.addTreeSelectionListener(l);
        }
               
    }
    
    
    private void makeDirPanel(){
        
        dirModel = new ReferenceTableModel(xmlClient);
        dirTable = new JTable(dirModel);
        dirTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dirTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent l) {
                if (dirTable.getSelectedRowCount() > 0) {
                 dirRemoveButton.setEnabled(fullAccess);
                 dirExpandButton.setEnabled(true);    
                } else {
                 dirRemoveButton.setEnabled(false);
                 dirExpandButton.setEnabled(false);    
                }
                
              }
        });
        
        TableColumn col = dirTable.getColumnModel().getColumn(ReferenceTableModel.INDEX_TYPE);
        col.setCellRenderer(new ObjektArtTableCellRenderer(ReferenceTableModel.INDEX_UNAME));
        col.setPreferredWidth(130);
        
        col = dirTable.getColumnModel().getColumn(ReferenceTableModel.INDEX_VON);
        col.setCellEditor(new DateEditor(new SimpleDateFormat()));
        col.setCellRenderer(new DateRenderer(new SimpleDateFormat()));
        
        col = dirTable.getColumnModel().getColumn(ReferenceTableModel.INDEX_BIS);
        col.setCellEditor(new DateEditor(new SimpleDateFormat()));
        col.setCellRenderer(new DateRenderer(new SimpleDateFormat()));
        
        
        dirScrollPane = new JScrollPane(dirTable);
        dirScrollPane.setPreferredSize(new Dimension(300,100));
        
        dirButtonPanel = new JPanel();
        dirButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dirButtonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                
        dirAddButton = new JButton(resources.getString("AddButton.Text"));
        dirAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i){
              logger.debug("Add " + app.getLastSelectedNode().getDe());
              dirModel.addRow(refNode, getObjektNode());
            }
        });
        dirAddButton.setEnabled(false);
                
        dirRemoveButton = new JButton(resources.getString("RemoveButton.Text"));
        dirRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i) {
                     dirModel.removeRow(dirTable.getSelectedRow());     
                     dirTable.revalidate();
                     dirTable.repaint();
            }
        });    
        dirRemoveButton.setEnabled(false);
        
        dirExpandButton = new JButton(resources.getString("ExpandButton.Text"));
        dirExpandButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i) {
                int row = dirTable.getSelectedRow();
                String strObjArt = (String)dirModel.getValueAt(row, ReferenceTableModel.INDEX_UNAME);
                ObjektArt objArt = ObjektArt.get(strObjArt);
                Integer objId = (Integer)dirModel.getValueAt(row, ReferenceTableModel.INDEX_ID_VON);
                JRefPanel.this.app.getTreePane().expandObjektNode(objArt,objId);
            }
        });    
        dirExpandButton.setEnabled(false);
        
        dirButtonPanel.add(dirAddButton);
        dirButtonPanel.add(Box.createHorizontalStrut(10));
        dirButtonPanel.add(dirRemoveButton);
        dirButtonPanel.add(Box.createHorizontalStrut(10));
        dirButtonPanel.add(dirExpandButton);
        
        dirPanel = new JPanel(new BorderLayout());
        dirPanel.setBorder(BorderFactory.createTitledBorder(resources.getString("DirectPanelBorder.Text")));
       
        dirPanel.add(dirScrollPane,BorderLayout.CENTER);
        dirPanel.add(dirButtonPanel,BorderLayout.SOUTH);        
        
    }
    
    private void makeInhPanel(){
        
        inhModel = new ReferenceTableModel(xmlClient);
        inhTable = new JTable(inhModel);
        inhTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inhTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent l) {
                 inhExpandButton.setEnabled(inhTable.getSelectedRowCount() > 0 ? true:false);
              }
        });
        
        TableColumn col = inhTable.getColumnModel().getColumn(ReferenceTableModel.INDEX_TYPE);
        col.setCellRenderer(new ObjektArtTableCellRenderer(ReferenceTableModel.INDEX_UNAME));
        col.setPreferredWidth(130);
        
        col = inhTable.getColumnModel().getColumn(ReferenceTableModel.INDEX_VON);
        col.setCellEditor(new DateEditor(new SimpleDateFormat()));
        col.setCellRenderer(new DateRenderer(new SimpleDateFormat()));
        
        col = inhTable.getColumnModel().getColumn(ReferenceTableModel.INDEX_BIS);
        col.setCellEditor(new DateEditor(new SimpleDateFormat()));
        col.setCellRenderer(new DateRenderer(new SimpleDateFormat()));
             
        
        inhScrollPane = new JScrollPane(inhTable);
        inhScrollPane.setPreferredSize(new Dimension(300,100));
        
        inhButtonPanel = new JPanel();
        inhButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        inhButtonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                
        
        inhExpandButton = new JButton(resources.getString("ExpandButton.Text"));
        inhExpandButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i) {
                int row = inhTable.getSelectedRow();
                Integer objId = (Integer)inhModel.getValueAt(row, ReferenceTableModel.INDEX_ID_VON);
                String strObjArt = (String)inhModel.getValueAt(row, ReferenceTableModel.INDEX_UNAME);
                ObjektArt objArt = ObjektArt.get(strObjArt);
                JRefPanel.this.app.getTreePane().expandObjektNode(objArt,objId);
            }
        });    
        inhExpandButton.setEnabled(false);
        
        inhButtonPanel.add(Box.createHorizontalStrut(10));
        inhButtonPanel.add(inhExpandButton);
        
        inhPanel = new JPanel(new BorderLayout());
        inhPanel.setBorder(BorderFactory.createTitledBorder(resources.getString("InheritedPanelBorder.Text")));
       
        inhPanel.add(inhScrollPane,BorderLayout.CENTER);
        inhPanel.add(inhButtonPanel,BorderLayout.SOUTH);        

        
    }
                
  
    public boolean loadData() {
        // setBorderText("References of " + objektNode.getDe());
        Vector params = new Vector(1);
        params.add(objektNode.getId());
        fullAccess = objektNode.hasFullAccess();
        inhModel.loadData(objektNode,true);
        inhTable.revalidate();
        dirModel.loadData(objektNode,false);
        dirTable.revalidate();
        setObjektNode(objektNode);
        return true;
    }
    
       
     /** Setter for property refNode.
      * @param refNode New value of property refNode.
      *
      */
     public void setRefNode(ObjektNode refNode) {
         this.refNode = refNode;
         if (refNode == null) {
            dirAddButton.setEnabled(false);
         } else {
            dirAddButton.setEnabled(fullAccess);
         }
     }     
    
     
     
} // end JRefPanel



