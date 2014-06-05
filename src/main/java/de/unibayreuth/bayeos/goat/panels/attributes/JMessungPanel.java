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
import java.text.ParseException;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.Messung;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.panels.UpdatePanel;
import de.unibayreuth.bayeos.utils.MsgBox;

public class JMessungPanel extends UpdatePanel {
    
    private MessungForm formMessung ;
    
    final static Logger logger = Logger.getLogger(JMessungPanel.class.getName()); 
    
           
    /** Creates new form dpMessungen */
    public JMessungPanel(JMainFrame app) {       
        super(app);
        this.formMessung = new MessungForm();
        add(formMessung,BorderLayout.CENTER);
    }
   
    public boolean loadData() {
      try {
        Messung mes = null;
        mes = (Messung)helper.getObjekt(objektNode.getId(),objektNode.getObjektart());
        if (mes == null) return false;
        
        
        
        formMessung.setId(mes.getId());
        formMessung.setObjektArt(mes.getObjektart());
        formMessung.setBeschreibung(mes.getBeschreibung());
        formMessung.setRec_start(mes.getRec_start());
        formMessung.setRec_end(mes.getRec_end());
        formMessung.setPlan_start(mes.getPlan_start());
        formMessung.setPlan_end(mes.getPlan_end());
        formMessung.setAufloesung(mes.getAufloesung());
        formMessung.setCTime(mes.getCtime());
        formMessung.setUTime(mes.getUtime());
        formMessung.setCBenutzer(mes.getCbenutzer());
        formMessung.setUBenutzer(mes.getUbenutzer());
        formMessung.setTimeZone(mes.getTimeZoneId());
        formMessung.setIdIntervaltyp(mes.getIntervalTypId());
        
        
        boolean enabled = mes.getCheck_write().booleanValue();
        setEditable(enabled); // Buttons
        formMessung.setEditable(enabled); // Fields
        return true;
        } catch (ClassCastException c) {
          logger.error(c.getMessage());
          return false;
        }

        
        
    }
    
    public boolean updateData() {
      try {
        Vector attrib = new Vector();     
        attrib.add(getObjektNode().getDe()); // 0
        attrib.add(formMessung.getBeschreibung()); //1
        attrib.add(formMessung.getAufloesung()); //2
        attrib.add(formMessung.getPlan_start()); //3
        attrib.add(formMessung.getPlan_end());  //4
        attrib.add(formMessung.getIdIntervalTyp());//5
        attrib.add(formMessung.getTimeZone());// 6
        
        return helper.updateObjekt(formMessung.getId(),formMessung.getObjektArt(),attrib) ;
        } catch (NumberFormatException n) {
            logger.error(n.getMessage());
            MsgBox.error(JMessungPanel.this,"Wrong Format " + n.getMessage());
            return false;
        } catch (ParseException p) {
            logger.error(p.getMessage());
            MsgBox.error(JMessungPanel.this,"Wrong Format " + p.getMessage());
            return false;
        }

    }
    
} // end JMessungPanel



