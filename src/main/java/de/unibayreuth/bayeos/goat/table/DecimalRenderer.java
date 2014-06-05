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
package de.unibayreuth.bayeos.goat.table;

  

import javax.swing.table.DefaultTableCellRenderer;

import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.NumericFormat;


  public class DecimalRenderer extends DefaultTableCellRenderer {
          
    
    public DecimalRenderer() {
      setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    }
        
    public void setValue(Object value) {
      if ( value != null && value instanceof java.lang.Number ) {
       value = NumericFormat.defaultDecimalFormat.format(value);
      }
     super.setValue(value);
    } 

  } 

  
