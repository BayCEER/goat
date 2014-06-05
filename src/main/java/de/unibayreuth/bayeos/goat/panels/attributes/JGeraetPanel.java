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

import de.unibayreuth.bayceer.bayeos.objekt.Mess_Geraet;
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
public class JGeraetPanel extends UpdatePanel {
    
    final static Logger logger = Logger.getLogger(JGeraetPanel.class.getName()); 
    
    private GeraetForm frm;
           
    /** Creates new form dpMessungen */
    public JGeraetPanel(JMainFrame app) {       
        super(app);
        frm = new GeraetForm();
        add(frm,BorderLayout.CENTER);
    }
    
    public boolean loadData() {
        try {
        Mess_Geraet ger = (Mess_Geraet)helper.getObjekt(objektNode.getId(),objektNode.getObjektart());
        if (ger == null) return false;
        frm.setId(ger.getId());
        frm.setObjektArt(ger.getObjektart());
        frm.setBeschreibung(ger.getBeschreibung());
        
        frm.setSeriennr(ger.getSeriennr());
        frm.setCTime(ger.getCtime());
        frm.setUTime(ger.getUtime());
        frm.setCBenutzer(ger.getCbenutzer());
        frm.setUBenutzer(ger.getUbenutzer());
        boolean enabled = ger.getCheck_write().booleanValue();
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
        attrib.add(frm.getSeriennr());
        
        return helper.updateObjekt(frm.getId(),frm.getObjektArt(),attrib);
        } catch (NumberFormatException n) {
            logger.error(n.getMessage());
            MsgBox.error(JGeraetPanel.this,"Wrong Format " + n.getMessage());
            return false;       
        }
    }
        
        
        
        
        
    
   
}
