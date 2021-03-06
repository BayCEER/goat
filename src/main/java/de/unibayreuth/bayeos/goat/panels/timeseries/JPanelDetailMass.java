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

import javax.swing.JSplitPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.data.XYDataset;

import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.StatusFilter;
import de.unibayreuth.bayceer.bayeos.xmlrpc.filter.TimeFilter;
import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.chart.ChartUtils;
import de.unibayreuth.bayeos.goat.chart.StatusItemRenderer;
import de.unibayreuth.bayeos.goat.chart.StatusXYPlot;
import de.unibayreuth.bayeos.goat.chart.TableModelXYDataset;
import de.unibayreuth.bayeos.goat.panels.DetailPanel;
import de.unibayreuth.bayeos.goat.table.MassenTableModel;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;

/**
 *
 * @author  oliver
 */
public class JPanelDetailMass extends DetailPanel  {
    
    
    private JPanelChart panelChart;
    private JPanelTableEditMass panelMass;
    private JSplitPane splitPane;
        
    private TimeFilter t;
    private StatusFilter s;
    
    
    private static final Logger logger = Logger.getLogger(JPanelDetailMass.class);
    
    
    
    
    /** Creates a new instance of JPanelMassendaten */
    public JPanelDetailMass(JMainFrame app)  {   
        super(app);
        panelChart = new JPanelChart();
        
        panelMass = new JPanelTableEditMass();
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelChart,panelMass);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(450);
        add(splitPane, BorderLayout.CENTER);
    }
    
       
    
    public boolean loadData() {
        super.loadData();
        
        // Tabelle 
        MassenTableModel m = new MassenTableModel(app.getXmlClient());
        m.load(objektNode, t, s);
        panelMass.setModel(m);
        panelMass.setObjektNode(objektNode);
        
        
        // Grafik
        XYDataset xyDataset = new TableModelXYDataset(m,true);                  
        StatusItemRenderer statusRenderer = new StatusItemRenderer(m,2);
        
        NumberAxis rangeAxis = new NumberAxis(ChartUtils.getNumberAxisLabel(app.getXmlClient(),objektNode.getId()));
        DateAxis domainAxis = new DateAxis("Time");
        Plot plot = new StatusXYPlot(xyDataset, domainAxis, rangeAxis, statusRenderer); 
        
        JFreeChart f = new JFreeChart(plot);
        StandardLegend l = new StandardLegend(f);
        
        panelChart.setChart(f);
        panelChart.setLegend(l);
        
        // Link 
        m.addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e){
                panelChart.repaint();
            }
           
       });
        
        
        
        return true;
    }
    
    public void setTimeFilter(TimeFilter t){
        this.t = t;
    }
    
    public void setStatusFilter(StatusFilter s){
        this.s = s;
    }
    
    public void setObjektNode(ObjektNode node) {
        super.setObjektNode(node);
        panelMass.setObjektNode(node);
    }    
    
    
    
    
}
