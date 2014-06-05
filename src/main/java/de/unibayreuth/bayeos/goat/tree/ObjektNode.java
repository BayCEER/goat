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
package de.unibayreuth.bayeos.goat.tree;

import java.util.Vector;

import de.unibayreuth.bayceer.bayeos.objekt.ObjektArt;
import de.unibayreuth.bayeos.goat.Constants;


/**
 *
 * @author  oliver
 */
public class ObjektNode implements Constants {
    
    private Boolean check_write;        // 0
    private Boolean check_exec;         // 1
    private Integer id;                 // 2
    private Integer id_super;           // 3
    private ObjektArt objektart;        // 4
    private String  de;                 // 5
    private java.util.Date rec_start;   // 6
    private java.util.Date rec_end;     // 7
    private java.util.Date plan_start;  // 8
    private java.util.Date plan_end;    // 9
    private Boolean active;             // 10
    private Boolean recordsMissing;     // 11
    private Boolean hasChild;           // 12
      
    public String toString(){
//      return check_write.toString() + ";" + check_exec.toString() + ";" + id + ";" + id_super + ";" + 
//      objektart.toString() + ";" + de + ";" + 
//      ((rec_start == null)?"": defaultDateFormat.format(rec_start)) +     
//      ";" + ((rec_end == null)?"": defaultDateFormat.format(rec_end)) + 
//      ((plan_start == null)?"": defaultDateFormat.format(plan_start)) +     
//      ";" + ((plan_end == null)?"": defaultDateFormat.format(plan_end)) + 
//      ";" + active + ";" + recordsMissing + ";" + hasFiles;
      return de ;
    }
    
    public ObjektNode(final Vector v){
        setCheck_write((Boolean)v.elementAt(0));
        setCheck_exec((Boolean)v.elementAt(1));
        setId((Integer)v.elementAt(2));
        setId_super((Integer)v.elementAt(3));
        setObjektart(ObjektArt.get((String)v.elementAt(4))); 
        setDe((String)v.elementAt(5));
        setRec_start((java.util.Date)v.elementAt(6));
        setRec_end((java.util.Date)v.elementAt(7));
        setPlan_start((java.util.Date)v.elementAt(8));
        setPlan_end((java.util.Date)v.elementAt(9));
        setActive((Boolean)v.elementAt(10));
        setRecordsMissing((Boolean)v.elementAt(11));
        setHasChild((Boolean)v.elementAt(12));
        
    }
    
    
    /** Getter for property id.
     * @return Value of property id.
     *
     */
    public java.lang.Integer getId() {
        return id;
    }    
     
    /** Setter for property id.
     * @param id New value of property id.
     *
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    
    /** Getter for property id_super.
     * @return Value of property id_super.
     *
     */
    public java.lang.Integer getId_super() {
        return id_super;
    }
    
    /** Setter for property id_super.
     * @param id_super New value of property id_super.
     *
     */
    public void setId_super(java.lang.Integer id_super) {
        this.id_super = id_super;
    }
    
    
    /** Getter for property de.
     * @return Value of property de.
     *
     */
    public java.lang.String getDe() {
        return de;
    }
    
    /** Setter for property de.
     * @param de New value of property de.
     *
     */
    public void setDe(java.lang.String de) {
        this.de = de;
    }
    
        
    /** Getter for property check_write.
     * @return Value of property check_write.
     *
     */
    public java.lang.Boolean getCheck_write() {
        return check_write;
    }
    
    /** Setter for property check_write.
     * @param check_write New value of property check_write.
     *
     */
    public void setCheck_write(java.lang.Boolean check_write) {
        this.check_write = check_write;
    }
    
    /** Getter for property check_exec.
     * @return Value of property check_exec.
     *
     */
    public java.lang.Boolean getCheck_exec() {
        return check_exec;
    }
    
    /** Setter for property check_exec.
     * @param check_exec New value of property check_exec.
     *
     */
    public void setCheck_exec(java.lang.Boolean check_exec) {
        this.check_exec = check_exec;
    }
    
    /** Getter for property objektart.
     * @return Value of property objektart.
     *
     */
    public ObjektArt getObjektart() {
        return objektart;
    }    
    
    /** Setter for property objektart.
     * @param objektart New value of property objektart.
     *
     */
    public void setObjektart(ObjektArt objektart) {
        this.objektart = objektart;
    }
    
   
    
    public boolean hasFullAccess() {
        return (this.check_exec.booleanValue() && this.check_write.booleanValue());
    }
        
    
    /**
     * Getter for property mrec_start.
     * @return Value of property mrec_start.
     */
    public java.util.Date getRec_start() {
        return rec_start;
    }
    
    /**
     * Setter for property mrec_start.
     * @param mrec_start New value of property mrec_start.
     */
    public void setRec_start(java.util.Date rec_start) {
        this.rec_start = rec_start;
    }
    
    /**
     * Getter for property mrec_end.
     * @return Value of property mrec_end.
     */
    public java.util.Date getRec_end() {
        return rec_end;
    }
    
    /**
     * Setter for property mrec_end.
     * @param mrec_end New value of property mrec_end.
     */
    public void setRec_end(java.util.Date rec_end) {
        this.rec_end = rec_end;
    }
    
    /**
     * Getter for property plan_start.
     * @return Value of property plan_start.
     */
    public java.util.Date getPlan_start() {
        return plan_start;
    }
    
    /**
     * Setter for property plan_start.
     * @param plan_start New value of property plan_start.
     */
    public void setPlan_start(java.util.Date plan_start) {
        this.plan_start = plan_start;
    }
    
    /**
     * Getter for property plan_end.
     * @return Value of property plan_end.
     */
    public java.util.Date getPlan_end() {
        return plan_end;
    }
    
    /**
     * Setter for property plan_end.
     * @param plan_end New value of property plan_end.
     */
    public void setPlan_end(java.util.Date plan_end) {
        this.plan_end = plan_end;
    }
    
    /**
     * Getter for property isActive.
     * @return Value of property isActive.
     */
    public java.lang.Boolean getActive() {
        return active;
    }
    
    /**
     * Setter for property isActive.
     * @param isActive New value of property isActive.
     */
    public void setActive(java.lang.Boolean active) {
        this.active = active;
    }
    
    /**
     * Getter for property recordsMissing.
     * @return Value of property recordsMissing.
     */
    public java.lang.Boolean getRecordsMissing() {
        return recordsMissing;
    }
    
    /**
     * Setter for property recordsMissing.
     * @param recordsMissing New value of property recordsMissing.
     */
    public void setRecordsMissing(java.lang.Boolean recordsMissing) {
        this.recordsMissing = recordsMissing;
    }
    

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}
    
}
