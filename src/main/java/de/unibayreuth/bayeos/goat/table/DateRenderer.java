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

  import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

import de.unibayreuth.bayceer.bayeos.xmlrpc.formats.DateFormat;

  public class DateRenderer extends DefaultTableCellRenderer  {
      
    private static SimpleDateFormat fmt = DateFormat.defaultDateFormat;
    
    public DateRenderer() {
      setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    }
    
    public DateRenderer(SimpleDateFormat fmt){
        this();
        this.fmt = fmt;
    }
    
    public void setValue(Object value) {
      if ((value != null) && (value instanceof java.util.Date)) {
      java.util.Date dateValue = (java.util.Date) value;
      value = fmt.format(dateValue);
      }
     super.setValue(value);
    } 

  } 

  
