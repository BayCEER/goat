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

import java.awt.Image;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.batik.util.gui.resource.ResourceManager;
import org.apache.log4j.Logger;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.utils.ImageFactory;
import de.unibayreuth.bayeos.utils.ImageFactoryException;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class ObjektImageFactory {
    
    private static ObjektImageFactory factory;
    
    
    protected final static Logger logger = Logger.getLogger("ObjektImageIconFactory.class");

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
    
    private static Image[] iMassendaten;
    private static Image[] iLabordaten;
    private static Image[] iBenutzer;
    private static Image[] iGruppe;
    private static Image[] iGerät;
    private static Image[] iKompartiment;
    private static Image[] iOrt;
    private static Image[] iZiel;
    
    private static Image[] iMessung;
    private static Image[] iEinheit;
    private static Image[] iEinbau;
    private static Image[] iDataFrame;
    private static Image[] iDataColumn;
    
    
    // Externe Objekte
    private static Image[] iProjekt;
    private static Image[] iMitarbeiter;
    private static Image[] iLehrstuhl;

    
        
    /** Creates a new instance of ObjektImageIconFactory */
    private ObjektImageFactory() {
         iMassendaten = setUpImage(ObjektArt.MESSUNG_MASSENDATEN);
         iLabordaten = setUpImage(ObjektArt.MESSUNG_LABORDATEN);
         iBenutzer = setUpImage(ObjektArt.BENUTZER);
         iGruppe = setUpImage(ObjektArt.GRUPPE);
         iGerät = setUpImage(ObjektArt.MESS_GERAET);
         iKompartiment = setUpImage(ObjektArt.MESS_KOMPARTIMENT);
         iOrt = setUpImage(ObjektArt.MESS_ORT);
         iZiel  = setUpImage(ObjektArt.MESS_ZIEL);
         iLehrstuhl  = setUpImage(ObjektArt.LEHRSTUHL);
         iProjekt = setUpImage(ObjektArt.PROJEKT);
         iMitarbeiter  = setUpImage(ObjektArt.MITARBEITER);
         iMessung  = setUpImage(ObjektArt.MESSUNG_ORDNER);
         iEinheit = setUpImage(ObjektArt.MESS_EINHEIT);
         iEinbau = setUpImage(ObjektArt.MESS_EINBAU);
         iDataFrame = setUpImage(ObjektArt.DATA_FRAME);
         iDataColumn = setUpImage(ObjektArt.DATA_COLUMN);
    }
    
    private Image[] setUpImage(ObjektArt o){
        try {
        Image[] i = new Image[2];
        StringBuffer b = new StringBuffer("ObjektTree.Icon.");
        b.append(o.toString());
        i[1] = ImageFactory.getImage(resources.getString(b.toString()));
        i[0] = ImageFactory.getImage(resources.getString(b.append("_at").toString()));
        return i;
        } catch (ImageFactoryException i) {
            MsgBox.warn("Can't create image  for " + o.toString());
            return null;
        }
    }
    
    public static Image getImage(ObjektArt objektart, boolean hasFile) {
        if (factory == null) {
            factory = new ObjektImageFactory();
        }
        return (getObjektImage(objektart, hasFile));
       
    }
        
    
    private static Image getObjektImage(ObjektArt objektArt, boolean hasFile) {
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
            }
            return null;
    }
    
}
