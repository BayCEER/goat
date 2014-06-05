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

import de.unibayreuth.bayceer.bayeos.objekt.Mess_Kompartiment;
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
public class JKompartimentPanel extends UpdatePanel {
    
        
    final static Logger logger = Logger.getLogger(JKompartimentPanel.class.getName()); 
    
    private KompartimentForm frm;
           
    /** Creates new form dpMessungen */
    public JKompartimentPanel(JMainFrame app) {       
        super(app);
        frm = new KompartimentForm();
        add(frm,BorderLayout.CENTER);
     }
    
    
    
    
    public boolean loadData() {
        try {
        Mess_Kompartiment kom = (Mess_Kompartiment)helper.getObjekt(objektNode.getId(),objektNode.getObjektart());
        if (kom == null) return false;
        frm.setId(kom.getId());
        frm.setObjektArt(kom.getObjektart());
        frm.setBeschreibung(kom.getBeschreibung());
        frm.setCTime(kom.getCtime());
        frm.setUTime(kom.getUtime());
        frm.setCBenutzer(kom.getCbenutzer());
        frm.setUBenutzer(kom.getUbenutzer());
        boolean enabled = kom.getCheck_write().booleanValue();
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
        attrib.add(objektNode);
        attrib.add(frm.getBeschreibung());
        return helper.updateObjekt(frm.getId(),frm.getObjektArt(),attrib);
        } catch (NumberFormatException n) {
            logger.error(n.getMessage());
            MsgBox.error(JKompartimentPanel.this,"Wrong Format " + n.getMessage());
            return false;
        
      }
    }
        
        
        
        
        
    
   
}
