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


import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.MenuComponent;
import java.awt.MenuContainer;

import javax.swing.SwingUtilities;
  
  public class WaitCursorEventQueue extends EventQueue {
  
      public WaitCursorEventQueue(int delay) {
          this.delay = delay;
          waitTimer = new WaitCursorTimer();
         waitTimer.setDaemon(true);
         waitTimer.start();
     }

     protected void dispatchEvent(AWTEvent event) {
         waitTimer.startTimer(event.getSource());
         try {
             super.dispatchEvent(event);
         }
         finally {
             waitTimer.stopTimer();
         }
     }

     private int delay;
     private WaitCursorTimer waitTimer;

     private class WaitCursorTimer extends Thread {

        synchronized void startTimer(Object source) {
             this.source = source;
             notify();
         }

         synchronized void stopTimer() {
             if (parent == null)
                 interrupt();
             else {
                 parent.setCursor(null);
                 parent = null;
             }
         }

         public synchronized void run() {
             while (true) {
                 try {
                     //wait for notification from startTimer()
                     wait();

                     //wait for event processing to reach the threshold, or
                     //interruption from stopTimer()
                     wait(delay);

                     if (source instanceof Component)
                         parent = SwingUtilities.getRoot((Component)source);
                     else if (source instanceof MenuComponent) {
                         MenuContainer mParent =
                                 ((MenuComponent)source).getParent();
                         if (mParent instanceof Component)
                             parent = SwingUtilities.getRoot(
                                     (Component)mParent);
                     }

                     if (parent != null && parent.isShowing())
                         parent.setCursor(
                                 Cursor.getPredefinedCursor(
                                     Cursor.WAIT_CURSOR));
                 }
                 catch (InterruptedException ie) { }
             }
         }

         private Object source;
         private Component parent;
     }
 }
