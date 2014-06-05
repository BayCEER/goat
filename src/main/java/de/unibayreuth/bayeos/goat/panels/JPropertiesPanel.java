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
 * JPropertiesPanel.java
 *
 * Created on 16. Dezember 2003, 11:07
 */

package de.unibayreuth.bayeos.goat.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.goat.JMainFrame;

/**
 *
 * @author  oliver
 */
public class JPropertiesPanel extends DetailPanel {
    
            
    private LoadableForm rightPanel;
    private LoadableForm attributePanel;
    private LoadableForm refPanel;
    private FormFactory formFactory;
    
    private static Logger logger = Logger.getLogger(JPropertiesPanel.class);
    
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
    
    /** Creates a new instance of JPropertiesPanel */
    public JPropertiesPanel(JMainFrame app) {       
        super(app);
        this.app = app;     
        this.formFactory = new FormFactory(app);
        rightPanel = formFactory.getRightPanel();
        refPanel = formFactory.getRefPanel();
        setLayout(new BorderLayout());
     }
    
    public boolean loadData() {       
        removeAll();
        tabbedPane.removeAll();
        try {
        setBorderText("Properties of " + getObjektNode().getDe());
        ObjektArt art = getObjektNode().getObjektart();
        // Attribute 
     
        attributePanel = formFactory.getAttributePanel(art);        
        attributePanel.setObjektNode(getObjektNode());
        if (!attributePanel.loadData()) return false;
        tabbedPane.addTab("Attributes",(JPanel)attributePanel);
        
        if (art.equals(ObjektArt.MESSUNG_ORDNER) || art.equals(ObjektArt.MESSUNG_MASSENDATEN) ||
        art.equals(ObjektArt.MESSUNG_LABORDATEN)) {
          refPanel.setObjektNode(getObjektNode());
          if (!refPanel.loadData()) return false;
          
          
          tabbedPane.addTab("References",(JPanel)refPanel);
        }
        if (!art.isExtern()){
          rightPanel.setObjektNode(getObjektNode());
          if (!rightPanel.loadData()) return false;
          tabbedPane.addTab("Rights",(JPanel)rightPanel);
            
        }
        } catch (IllegalArgumentException e) {
        	logger.error(e.getMessage());
        }
        add(tabbedPane,BorderLayout.CENTER);
        return true;
    }
    
    
}
