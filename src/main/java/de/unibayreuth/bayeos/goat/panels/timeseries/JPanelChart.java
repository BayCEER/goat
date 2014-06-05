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
 * JPanelChart.java
 *
 * Created on 24. Oktober 2002, 15:46
 */

package de.unibayreuth.bayeos.goat.panels.timeseries;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.BoundedRangeModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ChartPropertyEditPanel;
import org.jfree.data.Range;
import org.jfree.ui.RefineryUtilities;

import de.unibayreuth.bayeos.utils.ImageFactory;
import de.unibayreuth.bayeos.utils.ImageFactoryException;
import de.unibayreuth.bayeos.utils.MsgBox;

/**
 *
 * @author  oliver
 */
public class JPanelChart extends JPanel implements ChartChangeListener, ChangeListener, MouseListener, MouseMotionListener, ActionListener {
         
   protected ChartPanel chartPanel;
   protected JFreeChart jFreeChart;
   protected JScrollBar chartScrollBar;
   

   private Point2D panStartPoint;   
   private double scrollFactor = 1000;
   
   
   
   /** The min/max values for the y axis. */
   protected double yMin;
   protected double yMax;
   
   /** Action commands for the chart. */
   private static final String ACTION_CHART_PAN = "chart_pan";
   private static final String ACTION_CHART_ZOOM_BOX = "chart_zoombox";
   private static final String ACTION_CHART_ZOOM_TO_FIT = "chart_zoomfit";
   private static final String ACTION_CHART_ZOOM_IN = "chart_zoomin";
   private static final String ACTION_CHART_ZOOM_OUT = "chart_zoomout";
   private static final String ACTION_CHART_EXPORT = "chart_export";
   private static final String ACTION_CHART_PRINT = "chart_print";
   private static final String ACTION_CHART_PROPERTIES = "chart_properties";
   
   private static final String ACTION_TABLE_EXPORT = "table_export";


   protected AbstractButton chartZoomButton, chartPanButton, chartZoomInButton, chartZoomOutButton, chartFitButton,
   chartExportButton, chartPrintButton, chartPropertiesButton;
   
   protected static ResourceBundle  localizationResources = ResourceBundle.getBundle("org.jfree.chart.LocalizationBundle");

   
   
   /** The zoom factor. */
   private static final double ZOOM_FACTOR = 0.8;
    
    /** The toolbar. */
   protected JToolBar toolBarChart;
   
   
   /** Creates a new instance of JDetailPanel */
      public JPanelChart()  {
        setLayout(new BorderLayout());
        toolBarChart = createChartToolbar();
        add(toolBarChart,BorderLayout.NORTH);        
               
        chartScrollBar = createJScrollBar();
        add(chartScrollBar, BorderLayout.SOUTH);
        
    }
      
  
   private JScrollBar createJScrollBar(){
        JScrollBar b = new JScrollBar(JScrollBar.HORIZONTAL);
        b.setModel(new DefaultBoundedRangeModel());
        b.setEnabled(false);
        return b;
   }
   
   
 

    private JToolBar createChartToolbar() {
        JToolBar toolbar = new JToolBar();

        ButtonGroup groupedButtons = new ButtonGroup();

        
        // ACTION_CMD_PAN
        chartPanButton = new JToggleButton();
        prepareButton(chartPanButton, ACTION_CHART_PAN, "de/unibayreuth/bayeos/goat/panels/Pan16.gif", "Pan mode");
        groupedButtons.add(chartPanButton);
        toolbar.add(chartPanButton);

        // ACTION_CMD_ZOOM_BOX
        chartZoomButton = new JToggleButton();
        prepareButton(chartZoomButton, ACTION_CHART_ZOOM_BOX, "de/unibayreuth/bayeos/goat/panels/Zoom16.gif", "Zoom mode");
        groupedButtons.add(chartZoomButton);
        chartZoomButton.setSelected(true); // no other makes sense after startup
        toolbar.add(chartZoomButton);

        // end of toggle-button group for select/pan/zoom-box
        toolbar.addSeparator();

        // ACTION_CMD_ZOOM_IN
        chartZoomInButton = new JButton();
        prepareButton(chartZoomInButton, ACTION_CHART_ZOOM_IN, "de/unibayreuth/bayeos/goat/panels/ZoomIn16.gif", "Zoom in");
        toolbar.add(chartZoomInButton);

        // ACTION_CMD_ZOOM_OUT
        chartZoomOutButton = new JButton();
        prepareButton(chartZoomOutButton, ACTION_CHART_ZOOM_OUT,"de/unibayreuth/bayeos/goat/panels/ZoomOut16.gif" , "Zoom out");
        toolbar.add(chartZoomOutButton);

        // ACTION_CMD_ZOOM_TO_FIT
        chartFitButton = new JButton();
        prepareButton(chartFitButton, ACTION_CHART_ZOOM_TO_FIT,"de/unibayreuth/bayeos/goat/panels/ZoomExtent16.gif", "Zoom to extent");
        toolbar.add(chartFitButton);
        
        
        
        toolbar.addSeparator();
        
        
        chartExportButton = new JButton();
        prepareButton(chartExportButton, ACTION_CHART_EXPORT,"de/unibayreuth/bayeos/goat/panels/Export16.gif", "Export chart image ...");
        toolbar.add(chartExportButton);
        
        
        // ACTION_CMD_PRINT
        chartPrintButton = new JButton();
        prepareButton(chartPrintButton, ACTION_CHART_PRINT,"de/unibayreuth/bayeos/goat/panels/Print16.gif", "Print chart ...");
        toolbar.add(chartPrintButton);
        
        
        toolbar.addSeparator();
        // ACTION_CMD_PROPERTIES
        chartPropertiesButton = new JButton();
        prepareButton(chartPropertiesButton, ACTION_CHART_PROPERTIES,"de/unibayreuth/bayeos/goat/panels/Properties16.gif", "Chart properties ...");
        toolbar.add(chartPropertiesButton);
                
        
        chartZoomOutButton.setEnabled(false);
        chartFitButton.setEnabled(false);

        return toolbar;
    }
    
    /**
     * Handles an action event.
     * 
     * @param evt
     *            the event.
     */
    public void actionPerformed(ActionEvent evt) {
        try {
            String acmd = evt.getActionCommand();

            if (acmd.equals(ACTION_CHART_ZOOM_BOX)) {
                setPanMode(false);
            } 
            else if (acmd.equals(ACTION_CHART_PAN)) {
                setPanMode(true);
            } 
            else if (acmd.equals(ACTION_CHART_ZOOM_IN)) {
                ChartRenderingInfo info = this.chartPanel.getChartRenderingInfo();
                Rectangle2D rect = info.getPlotInfo().getDataArea();
                zoomBoth(rect.getCenterX(), rect.getCenterY(), ZOOM_FACTOR);
            } 
            else if (acmd.equals(ACTION_CHART_ZOOM_OUT)) {
                ChartRenderingInfo info = this.chartPanel.getChartRenderingInfo();
                Rectangle2D rect = info.getPlotInfo().getDataArea();
                zoomBoth(rect.getCenterX(), rect.getCenterY(), 1 / ZOOM_FACTOR);
            } 
            else if (acmd.equals(ACTION_CHART_ZOOM_TO_FIT)) {

                // X-axis (has no fixed borders)
                chartPanel.autoRangeHorizontal();                               
                Plot plot = chartPanel.getChart().getPlot();
                if (plot instanceof ValueAxisPlot) {
                    XYPlot vvPlot = (XYPlot) plot;
                    ValueAxis axis = vvPlot.getRangeAxis();
                    if (axis != null) {
                        axis.setLowerBound(yMin);
                        axis.setUpperBound(yMax);
                    }                    
                }
            }  else if (acmd.equals(ACTION_CHART_EXPORT)) {
                try {
                chartPanel.doSaveAs();
                } catch (IOException i){
                    MsgBox.error(i.getMessage());
                }
            }  else if (acmd.equals(ACTION_CHART_PRINT)) {
                chartPanel.createChartPrintJob();    

            }  else if (acmd.equals(ACTION_CHART_PROPERTIES)) {
                ChartPropertyEditPanel panel = new ChartPropertyEditPanel(jFreeChart);
                int result =  JOptionPane.showConfirmDialog(this, panel,localizationResources.getString("Chart_Properties"),
                                          JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    panel.updateChartProperties(jFreeChart);
                }
            }   


        } 
        catch (Exception e) {
            MsgBox.error(e.getMessage());
        }
    }
    
   private void setPanMode(boolean val) {

        this.chartPanel.setHorizontalZoom(!val);
        // chartPanel.setHorizontalAxisTrace(! val);
        this.chartPanel.setVerticalZoom(!val);
        // chartPanel.setVerticalAxisTrace(! val);

        if (val) {
            this.chartPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        else {
            this.chartPanel.setCursor(Cursor.getDefaultCursor());
        }
    }
    
   protected void prepareButton(AbstractButton button, 
                               String actionKey, 
                               String iconResource,
                               String toolTipText) {
        button.setActionCommand(actionKey);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        try {
        button.setIcon(ImageFactory.getIcon(iconResource));
        } catch (ImageFactoryException i){
            MsgBox.error(i.getMessage());
        }
    }

 
     
      
   
      
     public void setChart(JFreeChart chart){
        this.jFreeChart = chart;
        recalcScrollBar(jFreeChart.getPlot());
        if (chartPanel != null) {
            remove(chartPanel);
        }
        this.chartPanel = new ChartPanel(jFreeChart) ;
        jFreeChart.addChangeListener(this);

        // enable zoom
        actionPerformed(new ActionEvent(this, 0, ACTION_CHART_ZOOM_BOX));

        // MouseListeners for pan function
        this.chartPanel.addMouseListener(this);
        this.chartPanel.addMouseMotionListener(this);

        // remove popup menu to allow panning
        // with right mouse pressed
        this.chartPanel.setPopupMenu(null);
        
        XYPlot vvPlot = (XYPlot) chart.getXYPlot();
        ValueAxis axis = vvPlot.getRangeAxis();
        yMax = axis.getMaximumAxisValue();
        yMin = axis.getMinimumAxisValue();
        
        add(chartPanel,BorderLayout.CENTER);
        

    }

   public void setLegend(Legend legend){
       jFreeChart.setLegend(legend);
   }
      
    
  
       
   /** Getter for property chartPanel.
    * @return Value of property chartPanel.
    *
    */
   public ChartPanel getChartPanel() {
       return chartPanel;
   }
   
/**
     * Zooms in on an anchor point (measured in Java2D coordinates).
     * 
     * @param x  the x value.
     * @param y  the y value.
     * @param zoomFactor  the zoomFactor < 1 == zoom in; else out.
     */
    private void zoomBoth(double x, double y, double zoomFactor) {
        zoomHorizontal(x, zoomFactor);
        zoomVertical(y, zoomFactor);
    }

    /**
     * Decreases the range on the horizontal axis, centered about a Java2D x coordinate.
     * <P>
     * The range on the x axis is multiplied by zoomFactor
     * 
     * @param x  the x coordinate in Java2D space.
     * @param zoomFactor  the zoomFactor < 1 == zoom in; else out.
     */
    private void zoomHorizontal(double x, double zoomFactor) {

        JFreeChart chart = this.chartPanel.getChart();
        ChartRenderingInfo info = this.chartPanel.getChartRenderingInfo();
        if (chart.getPlot() instanceof XYPlot) {
            XYPlot hvp = (XYPlot) chart.getPlot();
            ValueAxis axis = hvp.getDomainAxis();
            if (axis != null) {
                double anchorValue = axis.java2DToValue(
                    (float) x, info.getPlotInfo().getDataArea(), hvp.getDomainAxisEdge()
                );
                if (zoomFactor < 1.0) {
                    axis.resizeRange(zoomFactor, anchorValue);
                } 
                else if (zoomFactor > 1.0) {
                    Range range = hvp.getDataRange(axis);
                    adjustRange(axis, range, zoomFactor, anchorValue);
                }
            }
        }
    }

    /**
     * Decreases the range on the vertical axis, centered about a Java2D y coordinate.
     * <P>
     * The range on the y axis is multiplied by zoomFactor
     * 
     * @param y  the y coordinate in Java2D space.
     * @param zoomFactor  the zoomFactor < 1 == zoom in; else out.
     */
    private void zoomVertical(double y, double zoomFactor) {

        JFreeChart chart = this.chartPanel.getChart();
        ChartRenderingInfo info = this.chartPanel.getChartRenderingInfo();

        if (chart.getPlot() instanceof XYPlot) {
            XYPlot vvp = (XYPlot) chart.getPlot();
            ValueAxis primYAxis = vvp.getRangeAxis();
            if (primYAxis != null) {
                double anchorValue =
                    primYAxis.java2DToValue(
                        (float) y, info.getPlotInfo().getDataArea(), vvp.getRangeAxisEdge()
                    );
                if (zoomFactor < 1.0) {
                    // zoom in
                    primYAxis.resizeRange(zoomFactor, anchorValue);

                } 
                else if (zoomFactor > 1.0) {
                    // zoom out
                    Range range = new Range(yMin, yMax);
                    adjustRange(primYAxis, range, zoomFactor, anchorValue);
                }
            }

           
        }
    }

    /**
     * used for zooming
     * 
     * @param axis  the axis.
     * @param range  the range.
     * @param zoomFactor  the zoom factor.
     * @param anchorValue  the anchor value.
     */
    private void adjustRange(ValueAxis axis, Range range, double zoomFactor, double anchorValue) {

        if (axis == null || range == null) {
            return;
        }

        double rangeMinVal = range.getLowerBound() - range.getLength() * axis.getLowerMargin();
        double rangeMaxVal = range.getUpperBound() + range.getLength() * axis.getUpperMargin();
        double halfLength = axis.getRange().getLength() * zoomFactor / 2;
        double zoomedMinVal = anchorValue - halfLength;
        double zoomedMaxVal = anchorValue + halfLength;
        double adjMinVal = zoomedMinVal;
        if (zoomedMinVal < rangeMinVal) {
            adjMinVal = rangeMinVal;
            zoomedMaxVal += rangeMinVal - zoomedMinVal;
        }
        double adjMaxVal = zoomedMaxVal;
        if (zoomedMaxVal > rangeMaxVal) {
            adjMaxVal = rangeMaxVal;
            zoomedMinVal -= zoomedMaxVal - rangeMaxVal;
            adjMinVal = Math.max(zoomedMinVal, rangeMinVal);
        }

        Range adjusted = new Range(adjMinVal, adjMaxVal);
        axis.setRange(adjusted);
    }
       
   private void recalcScrollBar(Plot plot) {
        if (plot instanceof XYPlot) {
            XYPlot hvp = (XYPlot) plot;
            ValueAxis axis = hvp.getDomainAxis();

            axis.setLowerMargin(0);
            axis.setUpperMargin(0);

            Range rng = axis.getRange();

            BoundedRangeModel scrollBarModel = this.chartScrollBar.getModel();
            int len = scrollBarModel.getMaximum() - scrollBarModel.getMinimum();
            if (rng.getLength() > 0) {
                scrollFactor = len / rng.getLength();
            }
            
            double dblow = rng.getLowerBound();
            int ilow = (int) (dblow * scrollFactor);
            scrollBarModel.setMinimum(ilow);
            int val = ilow;
            scrollBarModel.setValue(val);

            double dbup = rng.getUpperBound();
            int iup = (int) (dbup * scrollFactor);
            scrollBarModel.setMaximum(iup);
            int ext = iup - ilow;
            scrollBarModel.setExtent(ext);

            scrollBarModel.addChangeListener(this);
        }
    }
   
   
   public void chartChanged(ChartChangeEvent event) {
        try {
            if (event.getChart() == null) {
                return;
            }  

            BoundedRangeModel scrollBarModel = this.chartScrollBar.getModel();
            if (scrollBarModel == null) {
                return;
            }

            boolean chartIsZoomed = false;

            Plot plot = event.getChart().getPlot();
            if (plot instanceof XYPlot) {
                XYPlot hvp = (XYPlot) plot;
                ValueAxis xAxis = hvp.getDomainAxis();
                Range xAxisRange = xAxis.getRange();

                // avoid recursion
                scrollBarModel.removeChangeListener(this);

                int low = (int) (xAxisRange.getLowerBound() * scrollFactor);
                scrollBarModel.setValue(low);
                int ext = (int) (xAxisRange.getUpperBound() * scrollFactor - low);
                scrollBarModel.setExtent(ext);

                // restore
                scrollBarModel.addChangeListener(this);

                // check if zoomed horizontally
                //Range hdr = hvp.getHorizontalDataRange(xAxis);
                Range hdr = hvp.getDataRange(xAxis);

                double len = hdr == null ? 0 : hdr.getLength();
                chartIsZoomed |= xAxisRange.getLength() < len;
            }

            if (!chartIsZoomed && plot instanceof XYPlot) {
                // check if zoomed vertically
                XYPlot vvp = (XYPlot) plot;
                ValueAxis yAxis = vvp.getRangeAxis();
                if (yAxis != null) {
                    chartIsZoomed = yAxis.getLowerBound() > yMin || yAxis.getUpperBound() < yMax;
                }
            }

            // enable "zoom-out-buttons" if chart is zoomed
            // otherwise disable them
            chartPanButton.setEnabled(chartIsZoomed);
            chartZoomOutButton.setEnabled(chartIsZoomed);
            chartFitButton.setEnabled(chartIsZoomed);
            chartScrollBar.setEnabled(chartIsZoomed);
            if (!chartIsZoomed) {
                setPanMode(false);
                chartZoomButton.setSelected(true);
            }
        } 
        catch (Exception e) {
            MsgBox.error(e.getMessage());
        }
    }
     
    public void stateChanged(ChangeEvent event) {
        try {
            Object src = event.getSource();
            BoundedRangeModel scrollBarModel = this.chartScrollBar.getModel();
            if (src == scrollBarModel) {
                int val = scrollBarModel.getValue();
                int ext = scrollBarModel.getExtent();

                Plot plot = this.chartPanel.getChart().getPlot();
                if (plot instanceof XYPlot) {
                    XYPlot hvp = (XYPlot) plot;
                    ValueAxis axis = hvp.getDomainAxis();

                    // avoid problems
                    this.chartPanel.getChart().removeChangeListener(this);

                    axis.setRange(val / scrollFactor, (val + ext) / scrollFactor);

                    // restore chart listener
                    this.chartPanel.getChart().addChangeListener(this);
                }
            }
        } 
        catch (Exception e) {
            MsgBox.error(e.getMessage());
        }
    }
    
    
    public void mouseDragged(MouseEvent event) {
        try {
            if (this.panStartPoint != null) {
                Rectangle2D scaledDataArea = this.chartPanel.getScaledDataArea();

                this.panStartPoint = RefineryUtilities.getPointInRectangle(
                    this.panStartPoint.getX(),
                    this.panStartPoint.getY(),
                    scaledDataArea
                );
                Point2D panEndPoint = RefineryUtilities.getPointInRectangle(
                    event.getX(), event.getY(), scaledDataArea
                );

                // horizontal pan

                Plot plot = this.chartPanel.getChart().getPlot();
                if (plot instanceof XYPlot) {
                    XYPlot hvp = (XYPlot) plot;
                    ValueAxis xAxis = hvp.getDomainAxis();

                    if (xAxis != null) {
                        double translatedStartPoint = xAxis.java2DToValue(
                            (float) panStartPoint.getX(),
                            scaledDataArea,
                            hvp.getDomainAxisEdge()
                        );
                        double translatedEndPoint = xAxis.java2DToValue(
                            (float) panEndPoint.getX(),
                            scaledDataArea,
                            hvp.getDomainAxisEdge()
                        );
                        double dX = translatedStartPoint - translatedEndPoint;

                        double oldMin = xAxis.getLowerBound();
                        double newMin = oldMin + dX;

                        double oldMax = xAxis.getUpperBound();
                        double newMax = oldMax + dX;

                        // do not pan out of range
                        if (newMin >= hvp.getDataRange(xAxis).getLowerBound()
                            && newMax <= hvp.getDataRange(xAxis).getUpperBound()) {
                            xAxis.setLowerBound(newMin);
                            xAxis.setUpperBound(newMax);
                        }
                    }
                }

                // vertical pan (1. Y-Axis)

                if (plot instanceof XYPlot) {
                    XYPlot vvp = (XYPlot) plot;
                    ValueAxis yAxis = vvp.getRangeAxis();

                    if (yAxis != null) {
                        double translatedStartPoint = yAxis.java2DToValue(
                            (float) panStartPoint.getY(),
                            scaledDataArea,
                            vvp.getRangeAxisEdge()
                        );
                        double translatedEndPoint = yAxis.java2DToValue(
                            (float) panEndPoint.getY(),
                            scaledDataArea,
                            vvp.getRangeAxisEdge()
                        );
                        double dY = translatedStartPoint - translatedEndPoint;

                        double oldMin = yAxis.getLowerBound();
                        double newMin = oldMin + dY;

                        double oldMax = yAxis.getUpperBound();
                        double newMax = oldMax + dY;

                        // do not pan out of range
                        if (newMin >= yMin && newMax <= yMax) {
                            yAxis.setLowerBound(newMin);
                            yAxis.setUpperBound(newMax);
                        }
                    }
                }

                
                // for the next time
                this.panStartPoint = panEndPoint;
            }
        } 
        catch (Exception e) {
            MsgBox.error(e.getMessage());
        }
    }

    public void mousePressed(MouseEvent event) {
        try {
            if (chartPanButton.isSelected()
                || chartPanButton.isEnabled()
                && SwingUtilities.isRightMouseButton(event)) {
                Rectangle2D dataArea = this.chartPanel.getScaledDataArea();
                Point2D point = event.getPoint();
                if (dataArea.contains(point)) {
                    setPanMode(true);
                    panStartPoint = point;
                }
            }
        } 
        catch (Exception e) {
            MsgBox.error(e.getMessage());
        }
    }

    /**
     * Handles a mouse released event (stops panning).
     * 
     * @param event  the event.
     */
    public void mouseReleased(MouseEvent event) {
        try {
            this.panStartPoint = null; // stop panning
            if (!chartPanButton.isSelected()) {
                setPanMode(false);
            }
        } 
        catch (Exception e) {
            MsgBox.error(e.getMessage());
        }
    }
    /** 
     * Handles a mouse clicked event, in this case by ignoring it.
     * 
     * @param event  the event.
     */
    public void mouseClicked(MouseEvent event) {
        // ignored
    }

    /** 
     * Handles a mouse moved event, in this case by ignoring it.
     * 
     * @param event  the event.
     */
    public void mouseMoved(MouseEvent event) {
        // ignored
    }

    /** 
     * Handles a mouse entered event, in this case by ignoring it.
     * 
     * @param event  the event.
     */
    public void mouseEntered(MouseEvent event) {
        // ignored
    }

    /** 
     * Handles a mouse exited event, in this case by ignoring it.
     * 
     * @param event  the event.
     */
    public void mouseExited(MouseEvent event) {
        // ignored
    }


    
   }
   
   
