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
package de.unibayreuth.bayeos.goat.exp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.Constants;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.JPanelSaveCsvFile;
import de.unibayreuth.bayeos.goat.filter.JMessungFilter;
import de.unibayreuth.bayeos.goat.filter.JPanelMessungTable;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;



public class JExportMatrixDialog extends JDialog implements Constants {
    private JMainFrame app;
    
    //private JFilter filterDialog;    
    
    private JPanelMessungTable messungPanel;
    private JPanel filterPanel, optionsPanel, buttonPanel ;
    
    private JPanelSaveCsvFile filePanel;    
    
    private JButton okButton, cancelButton, filterButton;
    
    private JCheckBox statusCheckBox, countsCheckBox;
    private Boolean withStatusCols = Boolean.FALSE;
    private Boolean withCountsCols = Boolean.FALSE;

    // Filter
    private JMessungFilter filterDialog;
    

    protected final static Logger logger = Logger.getLogger(JExportMatrixDialog.class.getName());
    
    private static final Preferences  pref = Preferences.userNodeForPackage(JMainFrame.class);
    
    /**
     * The resource file name
     */
    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.exp.JExportMatrixDialog";
    
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
    
    
    public JExportMatrixDialog(JMainFrame application){
        super(application,true);
        this.app = application;

        super.setTitle(resources.getString("Dialog.Title"));
        
        /* messungPanel
         */
        messungPanel = new JPanelMessungTable(application);
        
        
        // 
        filterDialog = new JMessungFilter(app);
       
        
        /* filterPanel
        */
        filterPanel = new JPanel(new BorderLayout(10,0));
        filterPanel.setBorder(BorderFactory.createTitledBorder(resources.getString("FilterPanel.Border.Text")));
        filterButton = new JButton(resources.getString("FilterButton.Text"));
        filterButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                filterDialog.setLocationRelativeTo(filterButton);
                filterDialog.setMaxRecNumber(pref.getInt("maxrecnumber",200000));
                filterDialog.setVisible(true);
            }
        });
        filterPanel.add(filterButton,BorderLayout.CENTER);
        
        filePanel = new JPanelSaveCsvFile();
        
        
        // Status Panel
        optionsPanel = new JPanel(new GridLayout(1,2));
        optionsPanel.setBorder(BorderFactory.createTitledBorder(resources.getString("OptionsPanel.Border.Text")));
        
        statusCheckBox = new JCheckBox(resources.getString("StatusCheckBox.Text"),false);
        statusCheckBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {                
                  if (e.getStateChange() == ItemEvent.DESELECTED){
                    withStatusCols = Boolean.FALSE;
                  } else if (e.getStateChange() == ItemEvent.SELECTED){
                    withStatusCols = Boolean.TRUE;
                  }
            }
            
        });
        optionsPanel.add(statusCheckBox);
        
        countsCheckBox = new JCheckBox(resources.getString("CountsCheckBox.Text"),false);
        countsCheckBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {                
                  if (e.getStateChange() == ItemEvent.DESELECTED){
                    withCountsCols = Boolean.FALSE;
                  } else if (e.getStateChange() == ItemEvent.SELECTED){
                    withCountsCols = Boolean.TRUE;
                  }
            }
            
        });
        optionsPanel.add(countsCheckBox);
        
        
        
        // create buttons
        okButton = new JButton(resources.getString("OkButton.Text"));
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              try {
                    
                if (filePanel.getFilePath().length() == 0){
                 JOptionPane.showMessageDialog(JExportMatrixDialog.this,"Please specify an export file !",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                 return;
                } else if (messungPanel.isEmpty()){
                 JOptionPane.showMessageDialog(JExportMatrixDialog.this,"Please specify some messungen to export !",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                 return;
                }
                    
                 
                logger.debug("Exporting records ...");                
                Vector params = new Vector();
                params.add(getIds());
                params.add(filterDialog.getTimeFilter().getVector());
                
                InputStream in;
                if (filterDialog.isOriginalSelected()) {
                  params.add(filterDialog.getStatusFilter().getVector());
                  params.add(withStatusCols);
                  params.add("char_stream");
                  in = app.getXmlClient().executeStream("OctetMatrixHandler.getMatrixOrg",params);                    
                } else {
                  params.add(filterDialog.getAggrFilter().getVector());                
                  params.add(withCountsCols);
                  params.add("char_stream");
                  in = app.getXmlClient().executeStream("OctetMatrixHandler.getMatrixAggr",params);                    
                }
                
                
                if (writeFile(in,filePanel.getFilePath()))  {
                    JOptionPane.showMessageDialog(JExportMatrixDialog.this,"Records exported !",getTitle(),JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
              	}
                } catch (XmlRpcException x) {
                    JOptionPane.showMessageDialog(JExportMatrixDialog.this,x.getMessage(),getTitle(),JOptionPane.ERROR_MESSAGE);               
                }
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
        content.add(filterPanel);
        content.add(optionsPanel);
        content.add(filePanel);
        content.add(buttonPanel);

        pack();       
        setLocationRelativeTo(application);
        
        
    }
    
    private Vector getIds() {
      DefaultTableModel tm = (DefaultTableModel)messungPanel.getTableModel();
      Vector ids = new Vector(tm.getRowCount());
      for (int y=0;y<tm.getRowCount();y++) {
        ids.add(((ObjektNode)tm.getValueAt(y,0)).getId());
      }
      return ids;
    }
    
    
    private boolean writeFile(InputStream in, String filePath){
        logger.info("Export Stream to file " + filePath + ".");
              BufferedOutputStream bout = null;
              try {
               bout = new BufferedOutputStream(new FileOutputStream(filePath));
               int i;
               while( (i = in.read()) != -1){
                   bout.write(i);
               }
               bout.flush();

              } catch (IOException io) {
                 JOptionPane.showMessageDialog(this,"Error in exporting file " + filePath + 
                 ".\n" + io.getMessage(), this.getTitle(), JOptionPane.ERROR_MESSAGE);
                 return false;
               } catch (IllegalArgumentException a) {
                 JOptionPane.showMessageDialog(this,"Error in exporting file " + filePath + 
                 ".\n" + a.getMessage(), this.getTitle(), JOptionPane.ERROR_MESSAGE);
                 return false;
               } finally {
                   try {
                   if (bout != null) bout.close();
                   } catch (IOException i){logger.error(i.getMessage());}
               }
               
               return true;
        
    }
    
    public void setObjektNodes(final Vector objektNodes) {
        messungPanel.setObjektNodes(objektNodes);
        Iterator it = objektNodes.iterator();
        while (it.hasNext()){
            ObjektNode n = (ObjektNode)it.next();
            if (n.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN)){
                filterDialog.setAggregateEnabled(false);
            } 
        }
    }   
    
    public void setTimeFilter(TimeFilter f){
        filterDialog.setTimeFilter(f);
    }
    
    public void setOriginalSelected(){
        filterDialog.setOriginalSelected();
    }
    
    public void setFilePath(String name){
        filePanel.setFilePath(name);
    }
    
}
