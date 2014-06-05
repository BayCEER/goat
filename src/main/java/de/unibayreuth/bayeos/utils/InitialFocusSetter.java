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
package de.unibayreuth.bayeos.utils;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InitialFocusSetter {
        public static void setInitialFocus(Window w, Component c) {
            w.addWindowListener(new FocusSetter(c));
        }
    
        public static class FocusSetter extends WindowAdapter {
            Component initComp;
            FocusSetter(Component c) {
                initComp = c;
            }
            public void windowOpened(WindowEvent e) {
                initComp.requestFocus();
    
                // Since this listener is no longer needed, remove it
                e.getWindow().removeWindowListener(this);
            }
        }
    }
