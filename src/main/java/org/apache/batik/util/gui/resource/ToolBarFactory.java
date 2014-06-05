/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.util.gui.resource;

import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * This class represents a tool bar factory which builds
 * tool bars from the content of a resource file.<br>
 *
 * The resource entries format is (for a tool bar named 'ToolBar'):<br>
 * <pre>
 *   ToolBar           = Item1 Item2 - Item3 ...
 *   See ButtonFactory.java for details about the items
 *   ...
 * '-' represents a separator
 * </pre>
 * All entries are optional.
 *
 * @author <a href="mailto:stephane@hillion.org">Stephane Hillion</a>
 * @version $Id: ToolBarFactory.java,v 1.1 2006/05/16 14:55:33 oliver Exp $
 */
public class ToolBarFactory extends ResourceManager {
    // Constants
    //
    private final static String SEPARATOR = "-";

    /**
     * The table which contains the actions
     */
    private ActionMap actions;

    /**
     * The button factory
     */
    private ButtonFactory buttonFactory;

    /**
     * Creates a new tool bar factory
     * @param rb the resource bundle that contains the menu bar
     *           description.
     * @param am the actions to add to menu items
     */
    public ToolBarFactory(ResourceBundle rb, ActionMap am) {
	super(rb);
	actions = am;
	buttonFactory = new ButtonFactory(rb, am);
    }

    /**
     * Creates a tool bar
     * @param name the name of the menu bar in the resource bundle
     * @throws MissingResourceException if one of the keys that compose the
     *         tool bar is missing.
     *         It is not thrown if the action key is missing.
     * @throws ResourceFormatException  if a boolean is malformed
     * @throws MissingListenerException if an item action is not found in the
     * action map.
     */
    public JToolBar createJToolBar(String name)
	throws MissingResourceException,
               ResourceFormatException,
	       MissingListenerException {
	JToolBar result  = new JToolBar();
        List     buttons = getStringList(name);
        Iterator it      = buttons.iterator();

        while (it.hasNext()) {
	    String s = (String)it.next();
	    if (s.equals(SEPARATOR)) {
		result.add(new JToolbarSeparator());
	    } else {
                result.add(createJToolbarButton(s));
	    }
        }
	return result;
    }


    /**
     * Creates and returns a new swing button
     * @param name the name of the button in the resource bundle
     * @throws MissingResourceException if key is not the name of a button.
     *         It is not thrown if the mnemonic and the action keys are missing
     * @throws ResourceFormatException if the mnemonic is not a single
     *         character
     * @throws MissingListenerException if the button action is not found in
     *         the action map.
     */
    public JButton createJToolbarButton(String name)
	throws MissingResourceException,
	       ResourceFormatException,
	       MissingListenerException {
	return buttonFactory.createJToolbarButton(name);
    }
}
    