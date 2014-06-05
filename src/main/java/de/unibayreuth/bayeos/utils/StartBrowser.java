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

import org.apache.log4j.Logger;

public class StartBrowser {
	public static Logger logger = Logger.getLogger(StartBrowser.class);
	
	public static boolean open(String url){		
		String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime(); 
	
        try{ 
	    if (os.indexOf( "win" ) >= 0) { 
	        rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
	    } else if (os.indexOf( "mac" ) >= 0) { 
	        rt.exec( "open " + url); 
        } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) { 
	        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror", "netscape","opera","links","lynx"};
	        StringBuffer cmd = new StringBuffer();
	        for (int i=0; i<browsers.length; i++)
	            cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" "); 
	        rt.exec(new String[] { "sh", "-c", cmd.toString() });	        	        
         } else {
                logger.warn("Can't start browser on platform " + os +".");
                return false;
           }
       } catch (Exception e){
    	   logger.error(e.getMessage());
    	   return true;
       }
		return true;

	}

}
