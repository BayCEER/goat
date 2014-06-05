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

import de.unibayreuth.bayceer.bayeos.objekt.Mess_Einbau;
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
public class JEinbauPanel extends UpdatePanel {
    
   
    private EinbauForm frm;
    
    final static Logger logger = Logger.getLogger(JEinbauPanel.class.getName()); 
    
           
    /** Creates new form dpMessungen */
    public JEinbauPanel(JMainFrame app) {       
        super(app);
        frm = new EinbauForm();
        add(frm,BorderLayout.CENTER);
    }
    
    public boolean loadData() {
        try {
        Mess_Einbau ein = (Mess_Einbau)helper.getObjekt(objektNode.getId(),objektNode.getObjektart());
        if (ein == null) return false;
        frm.setId(ein.getId());
        frm.setObjektArt(ein.getObjektart());        
        frm.setHoehe(ein.getHoehe());
        frm.setCTime(ein.getCtime());
        frm.setUTime(ein.getUtime());
        frm.setCBenutzer(ein.getCbenutzer());
        frm.setUBenutzer(ein.getUbenutzer());
        boolean enabled = ein.getCheck_write().booleanValue();
        setEditable(enabled); // Buttons
        frm.setEditable(enabled); // Fields
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
        attrib.add(frm.getHoehe());
        return helper.updateObjekt(frm.getId(),frm.getObjektArt(),attrib);
        } catch (NumberFormatException n) {
            logger.error(n.getMessage());
            MsgBox.error(JEinbauPanel.this,"Wrong Format " + n.getMessage());
            return false;
        }
    }
        
}
