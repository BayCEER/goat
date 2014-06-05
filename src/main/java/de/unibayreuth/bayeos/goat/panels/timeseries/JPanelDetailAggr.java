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
 * 
 *
 * Created on 29. Juni 2004, 17:54
 */

package de.unibayreuth.bayeos.goat.panels.timeseries;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.prefs.Preferences;

import javax.swing.JSplitPane;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.DefaultXYItemRenderer;
import org.jfree.data.XYDataset;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.AggregateFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.chart.ChartUtils;
import de.unibayreuth.bayeos.goat.chart.DefaultItemRenderer;
import de.unibayreuth.bayeos.goat.chart.StatusXYPlot;
import de.unibayreuth.bayeos.goat.chart.TableModelXYDataset;
import de.unibayreuth.bayeos.goat.panels.DetailPanel;
import de.unibayreuth.bayeos.goat.table.AggregationTableModel;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;
import de.unibayreuth.bayeos.utils.LookUpTableFactory;

/**
 *
 * @author  oliver
 */
public class JPanelDetailAggr extends DetailPanel  {
    
    
    private JPanelChart panelChart;
    private JPanelTable panelAggr;
    private JSplitPane splitPane;
        
    private TimeFilter t;
    private AggregateFilter a;
    private HashMap hashi, hashf;
    
    private final static Preferences pref  = Preferences.userNodeForPackage(JMainFrame.class);
    private static final Logger logger = Logger.getLogger(JPanelDetailAggr.class);
        
    
    /** Creates a new instance of JPanelMassendaten */
    public JPanelDetailAggr(JMainFrame app)  {   
        super(app);
        panelChart = new JPanelChart();
        
        panelAggr = new JPanelTable();
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelChart,panelAggr);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(450);
        add(splitPane, BorderLayout.CENTER);
        
        hashi = LookUpTableFactory.getHash(LookUpTableFactory.getAggregationsIntervalle());
        hashf = LookUpTableFactory.getHash(LookUpTableFactory.getAggregationsFunktionen());         
    }
    
    
    
    
    
    public boolean loadData() {
        super.loadData();
        
        AggregationTableModel m = new AggregationTableModel(app.getXmlClient());
        m.load(objektNode, t, a);
        panelAggr.setModel(m);
        panelAggr.setObjektNode(objektNode);
        
        // Grafik
        
       XYDataset xyDataset = new TableModelXYDataset(m,true);      
       
       DateAxis domainAxis = new DateAxis("Time");
       
       StringBuffer buf = new StringBuffer();
       buf.append(hashf.get(a.getFunctionId()));
       buf.append(" per ");
       buf.append(hashi.get(a.getIntervallId()));
       buf.append(" of ");       
       buf.append(ChartUtils.getNumberAxisLabel(app.getXmlClient(),objektNode.getId()));
       NumberAxis rangeAxis = new NumberAxis(buf.toString());
       int rend = pref.getInt("xyitemrenderer",DefaultXYItemRenderer.LINES);
       
       Plot plot = new StatusXYPlot(xyDataset, domainAxis, rangeAxis,new DefaultItemRenderer()); 
       panelChart.setChart(new JFreeChart(plot));
                       
       return true;
    }
    
    public void setTimeFilter(TimeFilter t){
        this.t = t;
    }
    
    public void setAggregateFilter(AggregateFilter a){
        this.a= a;
    }
    
    public void setObjektNode(ObjektNode node) {
        super.setObjektNode(node);
        panelAggr.setObjektNode(node);
    }    
    
    
    
    
}
