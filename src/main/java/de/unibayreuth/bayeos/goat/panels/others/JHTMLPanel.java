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
 * JHTMLPanel.java
 *
 * Created on 27. November 2003, 14:05
 */

package de.unibayreuth.bayeos.goat.panels.others;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.panels.DetailPanel;
import de.unibayreuth.bayeos.utils.StartBrowser;

/**
 *
 * @author  oliver
 */
public class JHTMLPanel extends DetailPanel {   
    
    protected final static Logger logger = Logger.getLogger("JHTMLPanel.class");
    private JScrollPane p;
    
    
    public JHTMLPanel(JMainFrame app){
        super(app);
        setLayout(new BorderLayout());
        p = new JScrollPane();
        add(p,BorderLayout.CENTER);      
    }
         
    public boolean loadData() {        
        String url = helper.getUrl(getObjektNode().getId(),getObjektNode().getObjektart());        
        if (url == null) return false;
        return StartBrowser.open(url);        
        
    }
    
}
