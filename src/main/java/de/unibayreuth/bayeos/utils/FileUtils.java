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
 * FileUtils.java
 *
 * Created on 4. November 2003, 12:37
 */

package de.unibayreuth.bayeos.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.apache.xmlrpc.Base64;

/**
 *
 * @author  oliver
 */
public class FileUtils {
    
    public static byte[] getByteArray(FileInputStream in) throws IOException {
        FileChannel fc = in.getChannel();
        int size = (int)fc.size();
        ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int)fc.size());
        byte[] b = new byte[size];
        bb.get(b);
        return b;
    }
    
    public static byte[] getPackedByteArray(FileInputStream in) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(in);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);
        DeflaterOutputStream dout = new DeflaterOutputStream(bout,compressor,1024);
        int i = bin.read();
        while(i != -1){
         dout.write(i);
         i = bin.read();
        }
        dout.close();
        in.close();
        return bout.toByteArray();
    }
    
    public static boolean writeBase64(File file, String base64 ){
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        
        try {
              byte[] b = Base64.decode(base64.getBytes());
               // Write the stream 
              fout = new FileOutputStream(file);
              bout = new BufferedOutputStream(fout);
              bout.write(b);
              bout.flush();
              } catch (FileNotFoundException e) {
                  MsgBox.error(e.getMessage());
                  return false;
              } catch (IOException i) {
                  MsgBox.error(i.getMessage());
                  return false;
              } finally {
                 try {bout.close();fout.close();} catch (Exception e){;}
              }             
        return true;    
    }
    
}
