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
package de.unibayreuth.bayeos.goat.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.goat.chart.StatusColors;

/**
 * This is like TableEditDemo, except that it substitutes a
 * Favorite Color column for the Last Name column and specifies
 * a custom cell renderer and editor for the color data.
 */
public class StatusTableEditor extends JPanel {
    
    private StatusTableModel tableModel;
    private JTable table;
    
    private static final Preferences pref  = Preferences.userNodeForPackage(JMainFrame.class);
    
    
    public StatusTableEditor() {
        tableModel = new StatusTableModel();
        table = new JTable(tableModel);
        
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        //Create the scroll pane and add the table to it. 
        JScrollPane scrollPane = new JScrollPane(table);
        
        setLayout(new BorderLayout());

        //Set up renderer and editor for the Favorite Color column.
        setUpColorRenderer(table);
        setUpColorEditor(table);
        
        //Add the scroll pane to this frame
        add(scrollPane, BorderLayout.CENTER);
    }

    class ColorRenderer extends JLabel
                        implements TableCellRenderer {
        Border unselectedBorder = null;
        Border selectedBorder = null;
        boolean isBordered = true;

        public ColorRenderer(boolean isBordered) {
            super();
            this.isBordered = isBordered;
            setOpaque(true); //MUST do this for background to show up.
        }

        public Component getTableCellRendererComponent(
                                JTable table, Object color, 
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
            setBackground((Color)color);
            if (isBordered) {
                if (isSelected) {
                    if (selectedBorder == null) {
                        selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                                  table.getSelectionBackground());
                    }
                    setBorder(selectedBorder);
                } else {
                    if (unselectedBorder == null) {
                        unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                                  table.getBackground());
                    }
                    setBorder(unselectedBorder);
                }
            }
            return this;
        }
    }

    private void setUpColorRenderer(JTable table) {
        table.setDefaultRenderer(Color.class,
                                 new ColorRenderer(true));
    }

    //Set up the editor for the Color cells.
    private void setUpColorEditor(JTable table) {
        //First, set up the button that brings up the dialog.
        final JButton button = new JButton("") {
            public void setText(String s) {
                //Button never shows text -- only color.
            }
        };
        button.setBackground(Color.white);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0,0,0,0));

        //Now create an editor to encapsulate the button, and
        //set it up as the editor for all Color cells.
        final ColorEditor colorEditor = new ColorEditor(button);
        table.setDefaultEditor(Color.class, colorEditor);

        //Set up the dialog that the button brings up.
        final JColorChooser colorChooser = new JColorChooser();
        ActionListener okListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorEditor.currentColor = colorChooser.getColor();
            }
        };
        final JDialog dialog = JColorChooser.createDialog(button,
                                        "Pick a Color",
                                        true,
                                        colorChooser,
                                        okListener,
                                        null); //XXXDoublecheck this is OK

        //Here's the code that brings up the dialog.
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setBackground(colorEditor.currentColor);
                colorChooser.setColor(colorEditor.currentColor);
                //Without the following line, the dialog comes up
                //in the middle of the screen.
                //dialog.setLocationRelativeTo(button);
                dialog.show();
            }
        });
    }
    
    public boolean load(){
        table.clearSelection();
        return tableModel.load();   
    }

    
    /*
     * The editor button that brings up the dialog.
     * We extend DefaultCellEditor for convenience,
     * even though it mean we have to create a dummy
     * check box.  Another approach would be to copy
     * the implementation of TableCellEditor methods
     * from the source code for DefaultCellEditor.
     */
    class ColorEditor extends DefaultCellEditor {
        Color currentColor = null;

        public ColorEditor(JButton b) {
                super(new JCheckBox()); //Unfortunately, the constructor
                                        //expects a check box, combo box,
                                        //or text field.
            editorComponent = b;
            setClickCountToStart(1); //This is usually 1 or 2.

            //Must do this so that editing stops when appropriate.
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        public Object getCellEditorValue() {
            return currentColor;
        }

        public Component getTableCellEditorComponent(JTable table, 
                                                     Object value,
                                                     boolean isSelected,
                                                     int row,
                                                     int column) {
            ((JButton)editorComponent).setText(value.toString());
            currentColor = (Color)value;
            return editorComponent;
        }
    }
   
    

    class StatusTableModel extends AbstractTableModel {
        final String[] columnNames = {"Status","Color"};
        Object[][] data;
        
        
        public boolean load(){
            
            data = new Object[][] {
            {"not set",StatusColors.getNullColor() ,"status_color_null" }   ,
            {"valid", StatusColors.getOkColor(),"status_color_ok"}     ,
            {"invalid",StatusColors.getInvalidColor(),"status_color_invalid" }     ,
            {"others",StatusColors.getOthersColor(),"status_color_others" }     
            };
            return true;
        }

            
        
        public int getColumnCount() {
            return columnNames.length;
        }
        
        public int getRowCount() {
            if (data == null) return 0;
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            return (col == 1?true:false);
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            int i = ((Color)value).getRGB();
            pref.putInt((String)data[row][2],i);
            fireTableCellUpdated(row, col);
        }

    
    }

}
