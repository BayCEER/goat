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
 * ObjektIconFactory.java
 *
 * Created on 5. Dezember 2002, 14:46
 */

package de.unibayreuth.bayeos.goat;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;
import org.jfree.util.Log;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.ImageFactory;
import de.unibayreuth.bayeos.utils.ImageFactoryException;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class ObjektIconFactory {
    
    private static ObjektIconFactory factory;
    
    
    protected final static Logger logger = Logger.getLogger("ObjektIconFactory.class");

    protected final static String RESOURCES = "de.unibayreuth.bayeos.goat.JMainFrame";
           
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
    
    private static Icon[] iMassendaten;
    private static Icon[] iLabordaten;
    private static Icon[] iBenutzer;
    private static Icon[] iGruppe;
    private static Icon[] iGerät;
    private static Icon[] iKompartiment;
    private static Icon[] iOrt;
    private static Icon[] iZiel;
    
    private static Icon[] iMessung;
    private static Icon[] iEinheit;
    private static Icon[] iEinbau;
    private static Icon[] iDataFrame;
    private static Icon[] iDataColumn;
    
    
    // Externe Objekte
    private static Icon[] iProjekt;
    private static Icon[] iMitarbeiter;
    private static Icon[] iLehrstuhl;

    
        
    /** Creates a new instance of ObjektIconFactory */
    private ObjektIconFactory() {
         iMassendaten = setUpIcon(ObjektArt.MESSUNG_MASSENDATEN);
         iLabordaten = setUpIcon (ObjektArt.MESSUNG_LABORDATEN);
         iBenutzer = setUpIcon(ObjektArt.BENUTZER);
         iGruppe = setUpIcon(ObjektArt.GRUPPE);
         iGerät = setUpIcon(ObjektArt.MESS_GERAET);
         iKompartiment = setUpIcon(ObjektArt.MESS_KOMPARTIMENT);
         iOrt = setUpIcon(ObjektArt.MESS_ORT);
         iZiel  = setUpIcon(ObjektArt.MESS_ZIEL);
         iLehrstuhl  = setUpIcon(ObjektArt.LEHRSTUHL);
         iProjekt = setUpIcon(ObjektArt.PROJEKT);
         iMitarbeiter  = setUpIcon(ObjektArt.MITARBEITER);
         iMessung  = setUpIcon(ObjektArt.MESSUNG_ORDNER);
         iEinheit = setUpIcon(ObjektArt.MESS_EINHEIT);
         iEinbau = setUpIcon(ObjektArt.MESS_EINBAU);
         iDataFrame = setUpIcon(ObjektArt.DATA_FRAME);
         iDataColumn = setUpIcon(ObjektArt.DATA_COLUMN);
         
    }
    
    private Icon[] setUpIcon(ObjektArt o){
        try {
        Icon[] i = new Icon[2];
        StringBuffer b = new StringBuffer("ObjektTree.Icon.");
        b.append(o.toString());
        i[1] = ImageFactory.getIcon(resources.getString(b.toString()));
        i[0] = ImageFactory.getIcon(resources.getString(b.append("_at").toString()));
        return i;
        } catch (ImageFactoryException i) {
            MsgBox.warn("Can't set tree icons for " + o.toString());
            return null;
        }
    }
    
    public static Icon getIcon(ObjektArt objektart, boolean hasFile) {
    	Log.debug(objektart);
        if (factory == null) {
            factory = new ObjektIconFactory();
        }
        return (getObjektIcon(objektart, hasFile));
       
    }
        
    
    private static Icon getObjektIcon(ObjektArt objektArt, boolean hasFile) {
             if (objektArt.equals(ObjektArt.MESSUNG_MASSENDATEN)) {
               return iMassendaten[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESSUNG_LABORDATEN)){
               return iLabordaten[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.BENUTZER)){
               return iBenutzer[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.GRUPPE)){
               return iGruppe[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESS_GERAET)){
               return iGerät[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESS_KOMPARTIMENT)){
               return iKompartiment[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESS_ORT)){
               return iOrt[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESS_ZIEL)){
               return iZiel[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESSUNG_ORDNER)){
               return iMessung[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESS_EINHEIT)){
               return iEinheit[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MESS_EINBAU)){
               return iEinbau[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.MITARBEITER)){
               return iMitarbeiter[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.PROJEKT)){
               return iProjekt[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.LEHRSTUHL)){
               return iLehrstuhl[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.DATA_FRAME)){
                return iDataFrame[(hasFile)?0:1];
            } else if (objektArt.equals(ObjektArt.DATA_COLUMN)){
                return iDataColumn[(hasFile)?0:1];
            } else {  
            return null;
            }
    }
    
}
