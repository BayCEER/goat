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
 * JPanelStatus.java
 *
 * Created on 10. Mai 2004, 11:10
 */

package de.unibayreuth.bayeos.goat.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayeos.utils.LookUpTableFactory;

/**
 *
 * @author  oliver
 */
public class JPanelStatus extends javax.swing.JPanel {
        
    private StatusFilter filter = new StatusFilter();
    private Vector status;
    
    private HashMap boxs = new HashMap();
   
    /** Creates new form JPanelStatus */
    public JPanelStatus() {
        initComponents();
        status = LookUpTableFactory.getStatus();
        Iterator it = status.iterator();        
        while(it.hasNext()){
            Vector row = (Vector)it.next();
            Integer id = (Integer)row.elementAt(0);
            
            JCheckBox cb = new JPanelStatus.JStatusBox(id,(String)row.elementAt(1),true);
            cb.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e) { 
    	         JStatusBox cb = (JStatusBox) e.getSource(); 
 	        if(cb.isSelected()) { 
                  filter.addId(cb.getId());
 	        } else { 
    		  filter.removeId(cb.getId());
 	        } 
 	      } 
            }); 
            boxs.put(cb,id);
            add(cb);
            filter.addId(id);
            
        }
        
    }
      
    
    public void setFilter(StatusFilter f){
        Iterator it = status.iterator();        
        while(it.hasNext()){
            Vector row = (Vector)it.next();
            Integer id = (Integer)row.elementAt(0);            
            JCheckBox cb = (JCheckBox)boxs.get(id);
            cb.setSelected(f.getVector().contains(id));
        }
    }
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.GridLayout(7, 1));

        setBorder(new javax.swing.border.TitledBorder("Status"));
    }//GEN-END:initComponents

    /**
     * Getter for property filter.
     * @return Value of property filter.
     */
    public StatusFilter getFilter() {
        return filter;
    }    
    
    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
  
    public class JStatusBox extends JCheckBox {
        private Integer id;
        public JStatusBox(Integer id, String bezeichnung, boolean selected) {
            super(bezeichnung,selected);
            this.id = id;
        }
        public Integer getId() {
            return id;
        }
   
    } 
    
}
