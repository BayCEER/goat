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
 * FormFactory.java
 *
 * Created on 18. März 2003, 09:13
 */

package de.unibayreuth.bayeos.goat.panels;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.panels.attributes.JEinbauPanel;
import de.unibayreuth.bayeos.goat.panels.attributes.JEinheitPanel;
import de.unibayreuth.bayeos.goat.panels.attributes.JGeraetPanel;
import de.unibayreuth.bayeos.goat.panels.attributes.JKompartimentPanel;
import de.unibayreuth.bayeos.goat.panels.attributes.JMessungPanel;
import de.unibayreuth.bayeos.goat.panels.attributes.JOrtPanel;
import de.unibayreuth.bayeos.goat.panels.attributes.JZielPanel;
import de.unibayreuth.bayeos.goat.panels.others.JRefPanel;
import de.unibayreuth.bayeos.goat.panels.others.JRightPanel;
import de.unibayreuth.bayeos.goat.panels.timeseries.JPanelDetailAggr;
import de.unibayreuth.bayeos.goat.panels.timeseries.JPanelDetailLab;
import de.unibayreuth.bayeos.goat.panels.timeseries.JPanelDetailMass;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class FormFactory {
    
    // Loadable Forms
    private JRefPanel refPanel;
    private JRightPanel rightPanel;
    
//    private JChartAggrPanel chartAggrPanel;
//    private JChartMessPanel chartMessPanel;
//    
    
    private JPanelDetailLab jPanelDetailLab;
    private JPanelDetailMass jPanelDetailMas;
    private JPanelDetailAggr jPanelDetailAggr;
//    private JPanelLabordaten jPanelLabordaten;
    
     
       
    // Updateable Forms 
    
    private static JEinbauPanel einPanel;
    private static JEinheitPanel einhPanel;
    private static JKompartimentPanel komPanel;
    private static JZielPanel zielPanel;
    private static JGeraetPanel gerPanel;
    private static JOrtPanel ortPanel;
    private static JMessungPanel messungPanel;
    
        
    
    private JMainFrame app;
    
    
    
    /** Creates a new instance of FormFactory */
    public FormFactory(JMainFrame app) {
        this.app = app;   
    }
    
    public  LoadableForm getRefPanel() {
        if (refPanel == null) refPanel = new JRefPanel(app);
        return refPanel;
    }
    
    public  LoadableForm getRightPanel() {
        if (rightPanel == null) rightPanel = new JRightPanel(app);
        return rightPanel;
    }
      
    public LoadableForm getDetailForm(ObjektNode o, StatusFilter s, TimeFilter t,AggregateFilter a){
            if (a==null && s != null){
                return getMessung(o,t,s);
            } else if (a!=null){
                return getAggregate(o,t,a);
            } else {
                MsgBox.error("No detail form found.");
                return null;
            }
    }
    
    private LoadableForm getAggregate(ObjektNode o, TimeFilter t, AggregateFilter a) {
                jPanelDetailAggr = new JPanelDetailAggr(app);
                jPanelDetailAggr.setObjektNode(o);
                jPanelDetailAggr.setTimeFilter(t);
                jPanelDetailAggr.setAggregateFilter(a);
                return jPanelDetailAggr;
    }
    
    
    private LoadableForm getMessung(ObjektNode o, TimeFilter t, StatusFilter s) {
        if (o.getObjektart().equals(ObjektArt.MESSUNG_MASSENDATEN)){
                jPanelDetailMas = new JPanelDetailMass(app);
                jPanelDetailMas.setObjektNode(o);
                jPanelDetailMas.setTimeFilter(t);
                jPanelDetailMas.setStatusFilter(s);
                return jPanelDetailMas;    
        } else if (o.getObjektart().equals(ObjektArt.MESSUNG_LABORDATEN)){
                if (jPanelDetailLab == null) {
                    jPanelDetailLab = new JPanelDetailLab(app);
                }
                jPanelDetailLab.setObjektNode(o);
                jPanelDetailLab.setTimeFilter(t);
                jPanelDetailLab.setStatusFilter(s);
                return jPanelDetailLab;    
        } else {
            MsgBox.error("No form found for " + o.getObjektart());
            return null;
        }
        
        
    }
    
  
    
    
        
    public LoadableForm getAttributePanel(ObjektArt objektArt) {
        if (objektArt == ObjektArt.MESSUNG_LABORDATEN || objektArt == ObjektArt.MESSUNG_MASSENDATEN || 
            objektArt == ObjektArt.MESSUNG_ORDNER) {
              if (messungPanel == null)  messungPanel = new JMessungPanel(app);
                return messungPanel;
            } else if (objektArt == ObjektArt.MESS_EINBAU) {
                if (einPanel == null)  einPanel = new JEinbauPanel(app);
                return einPanel;
            } else if (objektArt == ObjektArt.MESS_EINHEIT) {
                if (einhPanel == null)  einhPanel = new JEinheitPanel(app);
                return einhPanel;
            } else if (objektArt == ObjektArt.MESS_GERAET) {
                if (gerPanel == null)  gerPanel = new JGeraetPanel(app);
                return gerPanel;
            } else if (objektArt == ObjektArt.MESS_KOMPARTIMENT) {
                if (komPanel == null)  komPanel = new JKompartimentPanel(app);
                return komPanel;
            } else if (objektArt == ObjektArt.MESS_ORT) {
                if (ortPanel == null)  ortPanel = new JOrtPanel(app);
                return ortPanel;
            } else if (objektArt == ObjektArt.MESS_ZIEL) {
                if (zielPanel == null)  zielPanel = new JZielPanel(app);
                return zielPanel;
            } else {
                throw new IllegalArgumentException("No form available for ObjektArt: " + objektArt.toString()); 
            }
        
    }
    
    
    
}
