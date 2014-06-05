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
 * JRightPanel.java
 *
 * Created on 11. Februar 2003, 13:40
 */

package de.unibayreuth.bayeos.goat.panels.others;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.objekt.Objekt;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.list.BenutzerListModel;
import de.unibayreuth.bayeos.goat.list.JObjektPickerDialog;
import de.unibayreuth.bayeos.goat.list.JObjektPickerException;
import de.unibayreuth.bayeos.goat.list.ObjektListCellRenderer;
import de.unibayreuth.bayeos.goat.panels.DetailPanel;
import de.unibayreuth.bayeos.goat.table.ObjektArtTableCellRenderer;
import de.unibayreuth.bayeos.goat.table.RightTableModel;

/**
 *
 * @author  oliver
 */
public class JRightPanel extends DetailPanel {
    
    final static Logger logger = Logger.getLogger(JRightPanel.class.getName()); 
    
     /**
     * The resource file name
     */
    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.panels.JRightPanel";

    /**
     * The resource bundle
     */
    protected static ResourceBundle bundle;

    /**
     * The resource manager
     */
    protected static ResourceManager resources;
    
    
    /**
     * Picks user or group 
     */
    private JObjektPickerDialog objektPicker;
    private BenutzerListModel listModel;
    
    private JTable rightTable;
    private RightTableModel rightTableModel;
    private JButton addButton, removeButton;
    private JScrollPane rightScrollPane;
    private JPanel rightButtonPanel;
        
            
    // init value
    private XmlRpcClient xmlClient;
    private boolean fullAccess = false;
    
        
    static {
        bundle = ResourceBundle.getBundle(RESOURCES, Locale.getDefault());
        resources = new ResourceManager(bundle);
    }
     
    /** Creates a new instance of JRightPanel */
    public JRightPanel(JMainFrame application) throws MissingResourceException {
      super(application);
      this.xmlClient = application.getXmlClient();
      
            
      rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      addButton = new JButton(resources.getString("AddButton.Text"));
      addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i){
              if (objektPicker == null) {
                try {
                listModel = new BenutzerListModel(xmlClient);
                logger.debug("" + listModel.getSize());
                } catch (XmlRpcException e) {
                    logger.error(e.getMessage()); return;
                } catch (IOException o) {
                    logger.error(o.getMessage()); return;
                }
                   
                try {
                objektPicker = new JObjektPickerDialog(app,listModel, new ObjektListCellRenderer());
                objektPicker.setTitle(resources.getString("ObjektPicker.Title"));
                } catch (JObjektPickerException z) {
                  logger.error(z.getMessage()); return;
                }
              }

              objektPicker.setLocationRelativeTo(addButton);
              objektPicker.show();
              if (objektPicker.isPicked()) {
                   RightTableModel mo = (RightTableModel)rightTable.getModel();
                   Objekt o = objektPicker.getObjekt();
                   Vector r = new Vector(7);
                   r.addElement(o.getDe());    // Benutzer
                   r.addElement(Boolean.TRUE); // Read
                   r.addElement(Boolean.FALSE);// Write
                   r.addElement(Boolean.FALSE);// Execute
                   r.addElement(Boolean.TRUE); // Inherit Permission
                   r.addElement(Boolean.TRUE); // Editable Permission
                   r.addElement(o.getObjektart().toString());// Uname
                   r.addElement(o.getId());    // Id Benutzer
                   mo.addRow(r);
              }
            }
        });
        
        
        removeButton = new JButton(resources.getString("RemoveButton.Text"));
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent i) {
                     RightTableModel tm = (RightTableModel)rightTable.getModel();
                     tm.removeRow(rightTable.getSelectedRow());     
                     rightTable.revalidate();
                     rightTable.repaint();
            }
        });    
        removeButton.setEnabled(false);

        rightButtonPanel.add(addButton);
        rightButtonPanel.add(removeButton);
        
        setLayout(new BorderLayout());
        add(rightButtonPanel, BorderLayout.SOUTH);
        
        rightTableModel = new RightTableModel(xmlClient);
        rightTable = new JTable(rightTableModel);
        rightScrollPane = new JScrollPane(rightTable);
        rightScrollPane.setPreferredSize(new Dimension(300,100));
        
        rightTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent l) {
                boolean enableRemove = false;
                if (rightTable.getSelectedRowCount() > 0) {
                    int index = rightTable.getSelectedRow();
                    enableRemove = ((RightTableModel)rightTable.getModel()).isRowRemovable(index);
                } 
                removeButton.setEnabled(enableRemove);
            }
        });
        
        rightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(rightScrollPane,BorderLayout.CENTER);

    }
    
    public boolean loadData() {
      try {
        Vector params = new Vector(1);
        params.add(objektNode.getId());
        fullAccess = ((Boolean)xmlClient.execute("RightHandler.hasFullAccess",params)).booleanValue();
        boolean ret = rightTableModel.loadData(objektNode.getId());
        doRendering();
        return ret;
      } catch (XmlRpcException r) {
          logger.error(r.getMessage());
          return false;
      }
   }
    
    private void doRendering() {
        
        TableColumn col = rightTable.getColumnModel().getColumn(RightTableModel.INDEX_USER_NAME);
        col.setCellRenderer(new JRightPanel.RightObjektArtRenderer(RightTableModel.INDEX_OBJEKTART,fullAccess));
        col.setPreferredWidth(300);
        
        JRightPanel.RightBoxRenderer rend = new JRightPanel.RightBoxRenderer(fullAccess);
        
        col = rightTable.getColumnModel().getColumn(RightTableModel.INDEX_READ);
        col.setCellRenderer(rend);
        
        
        col = rightTable.getColumnModel().getColumn(RightTableModel.INDEX_WRITE);
        col.setCellRenderer(rend);
        
        col = rightTable.getColumnModel().getColumn(RightTableModel.INDEX_EXEC);
        col.setCellRenderer(rend);
        
        col = rightTable.getColumnModel().getColumn(RightTableModel.INDEX_INHERIT_PERM);
        col.setCellRenderer(rend);
        
        rightTable.setRowSelectionAllowed(fullAccess);
        rightTable.setEnabled(fullAccess);
        addButton.setEnabled(fullAccess);
        
    }
      
      
    private class RightBoxRenderer extends JCheckBox implements TableCellRenderer {
      private boolean fullAccess = true;
      
      public RightBoxRenderer(boolean fullAccess) {
        setHorizontalAlignment(SwingConstants.CENTER);
        this.fullAccess = fullAccess;
      }
    
    public Component getTableCellRendererComponent(JTable table, Object value, 
    boolean isSelected, boolean hasFocus,int row, int col) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setSelected(((Boolean)value).booleanValue());
        RightTableModel mod = (RightTableModel)table.getModel();
        boolean editable = ((Boolean)mod.getValueAt(row, RightTableModel.INDEX_EDITABLE)).booleanValue(); 
        setEnabled(fullAccess && editable);
        return this;
    }
    
  }     
  
  private class RightObjektArtRenderer extends ObjektArtTableCellRenderer {
      public RightObjektArtRenderer(int indexObjektArt, boolean enabled){
          super(indexObjektArt, enabled);
      }
          
  }      
  
  public void setBezeichnung(String bezeichnung) {
         this.setBorder(BorderFactory.createTitledBorder("Rights of " + bezeichnung));
 }     
        
    
}
