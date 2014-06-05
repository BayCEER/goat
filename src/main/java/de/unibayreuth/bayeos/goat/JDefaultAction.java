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
 * JDefaultAction.java
 *
 * Created on 23. Oktober 2002, 16:31
 */

package de.unibayreuth.bayeos.goat;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import org.apache.batik.util.gui.resource.JComponentModifier;

public class JDefaultAction extends AbstractAction implements JComponentModifier {
        java.util.List components = new LinkedList();
                
        public JDefaultAction(){
        }

        public void actionPerformed(ActionEvent e){
        }
        
        /* alle zugeh�rigen Components disablen */
        public void setEnabled(boolean newValue) {
            Iterator it = components.iterator();
            while (it.hasNext()) {
                ((JComponent)it.next()).setEnabled(newValue);
            }
        }
        /** Gives a reference to a component to this object
         * @param comp the component associed with this object
         *
         */
        public void addJComponent(JComponent comp) {
               components.add(comp);
        }        
        
        public JComponent getJComponent(int index){
            return (JComponent)components.get(index);
        }
        
        public int getComponentsSize(){
            return components.size();
        }
                
        
     }

    
