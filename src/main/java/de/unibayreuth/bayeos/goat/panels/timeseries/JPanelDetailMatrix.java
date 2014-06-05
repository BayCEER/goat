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
 * JPanelMassendaten.java
 *
 * Created on 29. Juni 2004, 17:54
 */

package de.unibayreuth.bayeos.goat.panels.timeseries;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JSplitPane;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.XYDataset;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.chart.DefaultItemRenderer;
import de.unibayreuth.bayeos.goat.chart.TableModelXYDataset;
import de.unibayreuth.bayeos.goat.panels.DetailPanel;
import de.unibayreuth.bayeos.goat.table.OctetMatrixTableModel;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;

/**
 *
 * @author  oliver
 */
public class JPanelDetailMatrix extends DetailPanel  {
    
    
    private JPanelChart panelChart;
    private JPanelTable panelTab;
    private JSplitPane splitPane;
        
    private TimeFilter t;
    private StatusFilter s;
    private AggregateFilter a;
    private Vector nodes;
    
    private boolean aggregateEnabled = false;
    
    private Boolean withAggrCounts = Boolean.FALSE;
    private Boolean withStatusCols = Boolean.FALSE;
    
    private static final Logger logger = Logger.getLogger(JPanelDetailMatrix.class);
    
    
    /** Creates a new instance of JPanelMassendaten */
    public JPanelDetailMatrix(JMainFrame app)  {   
        super(app);
        panelChart = new JPanelChart();
        panelTab = new JPanelTableMatrix();
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelChart,panelTab);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(450);
        add(splitPane, BorderLayout.CENTER);
    }
    
    public boolean loadData() {
        super.loadData();
        
        OctetMatrixTableModel m = new OctetMatrixTableModel(app.getXmlClient());
        if (aggregateEnabled){
           m.load(nodes, t, a, withAggrCounts );    
        } else {
           m.load(nodes, t, s, withStatusCols);
        }
        
        panelTab.setModel(m);
        panelTab.setObjektNode(objektNode);
        
        // Grafik
        XYDataset xyDataset = new TableModelXYDataset(m);                  

        DateAxis domainAxis = new DateAxis("Time");
        XYPlot plot = new XYPlot(xyDataset, domainAxis, new NumberAxis("Values"), new DefaultItemRenderer()); 
        
        
        panelChart.setChart(new JFreeChart(plot));
        panelChart.setLegend(new StandardLegend());
        return true;
    }
    
    public void setTimeFilter(TimeFilter t){
        this.t = t;
    }
    
    public void setStatusFilter(StatusFilter s){
        this.s = s;
    }
    
    public void setAggregateFilter(AggregateFilter a){
        this.a = a;
    }
    
    public void setAggregateEnabled(boolean value){
        this.aggregateEnabled = value;
    }
    
   
    
    public void setObjektNodes(Vector nodes){
        this.nodes = nodes;
    }
    
    public void setObjektNode(ObjektNode node) {
        super.setObjektNode(node);
        panelTab.setObjektNode(node);
    }    
    
    /**
     * Getter for property withAggrCounts.
     * @return Value of property withAggrCounts.
     */
    public java.lang.Boolean getWithAggrCounts() {
        return withAggrCounts;
    }    
    
    /**
     * Setter for property withAggrCounts.
     * @param withAggrCounts New value of property withAggrCounts.
     */
    public void setWithAggrCounts(java.lang.Boolean withAggrCounts) {
        this.withAggrCounts = withAggrCounts;
    }    
    
    /**
     * Getter for property withStatusCols.
     * @return Value of property withStatusCols.
     */
    public java.lang.Boolean getWithStatusCols() {
        return withStatusCols;
    }    
    
    /**
     * Setter for property withStatusCols.
     * @param withStatusCols New value of property withStatusCols.
     */
    public void setWithStatusCols(java.lang.Boolean withStatusCols) {
        this.withStatusCols = withStatusCols;
    }
    
}
