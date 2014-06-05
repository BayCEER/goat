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
package de.unibayreuth.bayeos.goat.panels;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;

public abstract class DetailPanel extends JPanel implements LoadableForm {
    protected ObjektNode objektNode; 
    private String borderText;
    private boolean editable;
    protected JMainFrame app;
    protected XmlRpcObjektHelper helper;
    
    private final static Logger logger = Logger.getLogger(DetailPanel.class.getName()); 
    
    public DetailPanel(JMainFrame app) {
       this.app = app;
       this.helper = new XmlRpcObjektHelper();
       setLayout(new BorderLayout());
    }
    
    
    /** Getter for property objektNode.
     * @return Value of property objektNode.
     *
     */
    public ObjektNode getObjektNode() {
        return objektNode;
    }
    
    /** Setter for property objektNode.
     * @param objektNode New value of property objektNode.
     *
     */
    public void setObjektNode(ObjektNode node) {
        this.objektNode = node;
    }
    
    public void setEditable(boolean enabled) {
        editable = enabled;
    }    
    
    public boolean getEditable() {
        return editable;
    }
    
    public void setBorderText(java.lang.String text) {
        this.borderText = text;
        this.setBorder(BorderFactory.createTitledBorder(text));
    }
    
    public java.lang.String getBorderText() {
        return borderText;
    }
    
    public boolean loadData(){
       logger.debug("Loading " + objektNode.getDe() + " Id: " + objektNode.getId());       
       setBorderText("Data of " + this.objektNode.getDe());
       return true;
    }
    
    
} // end DetailPanel



