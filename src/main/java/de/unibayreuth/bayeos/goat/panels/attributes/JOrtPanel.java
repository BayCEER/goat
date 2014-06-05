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

import java.awt.BorderLayout;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.Mess_Ort;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.panels.UpdatePanel;
import de.unibayreuth.bayeos.utils.MsgBox;

/*
 * dpMessungen.java
 *
 * Created on 10. Februar 2003, 13:35
 */

/**
 *
 * @author  oliver
 */
public class JOrtPanel extends UpdatePanel {
    
        
    final static Logger logger = Logger.getLogger(JOrtPanel.class.getName()); 
    
    private OrtForm frm;
           
    /** Creates new form dpMessungen */
    public JOrtPanel(JMainFrame app) {       
        super(app);
        frm = new OrtForm();
        add(frm,BorderLayout.CENTER);
   }
    
    
    public boolean loadData() {
        try {
        Mess_Ort ort = (Mess_Ort)helper.getObjekt(objektNode.getId(),objektNode.getObjektart());
        if (ort == null) return false;
        frm.setId(ort.getId());
        frm.setObjektArt(ort.getObjektart());
        frm.setBeschreibung(ort.getBeschreibung());
        frm.setXValue(ort.getX());
        frm.setYValue(ort.getY());
        frm.setZValue(ort.getZ());
        frm.setCRSId(ort.getCRSId());
        frm.setCTime(ort.getCtime());
        frm.setUTime(ort.getUtime());
        frm.setCBenutzer(ort.getCbenutzer());
        frm.setUBenutzer(ort.getUbenutzer());
        boolean enabled = ort.getCheck_write().booleanValue();      
        frm.setEditable(enabled); // Fields
        setEditable(enabled); // Buttons
        return true;
        } catch (ClassCastException c) {
          logger.error(c.getMessage());
          return false;
        }
    }    
       
   
    
    public boolean updateData() {
        try {
        Vector attrib = new Vector();     
        attrib.add(objektNode.getDe());
        attrib.add(frm.getBeschreibung());
        attrib.add(frm.getXValue());
        attrib.add(frm.getYValue());
        attrib.add(frm.getZValue());
        attrib.add(frm.getCRSId());
        return helper.updateObjekt(frm.getId(),frm.getObjektArt(),attrib);
        } catch (NumberFormatException n) {
            logger.error(n.getMessage());
            MsgBox.error(JOrtPanel.this,"Wrong number format.");
            return false;
        } 
        
       
        
    }
        
        
        
        
        
    
   
}
