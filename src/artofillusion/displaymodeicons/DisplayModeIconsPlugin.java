/*
 *  Copyright 2007 Francois Guillet
 *  This program is free software; you can redistribute it and/or modify it under the
 *  terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 */

package artofillusion.displaymodeicons;

import java.util.Iterator;
import java.util.List;

import buoy.event.CommandEvent;
import buoy.widget.BCheckBoxMenuItem;
import buoy.widget.BMenu;
import buoy.widget.BMenuBar;
import buoy.widget.Widget;

import artofillusion.LayoutWindow;
import artofillusion.Plugin;
import artofillusion.ViewerCanvas;
import artofillusion.view.ViewerControl;
import artofillusion.view.ViewerPerspectiveControl;

public class DisplayModeIconsPlugin implements Plugin {

    private class AllViewsDisplayMenuManager {
	LayoutWindow window;

	public AllViewsDisplayMenuManager(LayoutWindow w) {
	    window = w;
	}

	public void doProcess(CommandEvent ev) {
	    BMenuBar menuBar = window.getMenuBar();
	    BMenu menu = menuBar.getChild(2);
	    menu = (BMenu) menu.getChild(3);
	    BCheckBoxMenuItem[] items = new BCheckBoxMenuItem[6];
	    for (int i = 0; i < items.length; i++) {
		items[i] = (BCheckBoxMenuItem)(menu.getChild(i));
		items[i].setState(false);
	    }
	    Widget w = ev.getWidget();
	    int mode = -1;
	    for (int i = 0; i < items.length; i++) {
		if (items[i] == w) {
		    switch(i) {
		    case 0:
			mode = ViewerCanvas.RENDER_WIREFRAME;
			break;
		    case 1:
			mode = ViewerCanvas.RENDER_FLAT;
			break;
		    case 2:
			mode = ViewerCanvas.RENDER_SMOOTH;
			break;
		    case 3:
			mode = ViewerCanvas.RENDER_TEXTURED;
			break;
		    case 4:
			mode = ViewerCanvas.RENDER_TRANSPARENT;
			break;
		    case 5:
			mode = ViewerCanvas.RENDER_RENDERED;
			break;
		    }
		    items[i].setState(true);
		}
	    }
	    ViewerCanvas[] views = window.getAllViews();
	    if (mode > -1) {
		if (mode == 5) {
		    for (int i = 0; i < 4; i++) {
			views[i].setRenderMode(ViewerCanvas.RENDER_TEXTURED);
		    }
		    window.getView().setRenderMode(mode);
		} else {
		    for (int i = 0; i < 4; i++) {
			views[i].setRenderMode(mode);
		    }
		}
	    }
	}
    }

    /**
     *  Process messages sent to plugin by AoI (see AoI API description)
     *
     *@param  message  The message
     *@param  args     Arguments depending on the message
     */
     public void processMessage( int message, Object args[] ) {
	 
	 System.out.println("DisplayModeIcons starting...");
	 
	 if ( message == Plugin.APPLICATION_STARTING ) {
	     //first, let's remove previous controls
	     List controls = ViewerCanvas.getViewerControls();
	     for (int i = 0; i < controls.size(); i++) {
		 ViewerControl control = (ViewerControl) controls.get(i);
		 if (control instanceof ViewerPerspectiveControl) {
		     ViewerCanvas.removeViewerControl(control);
		 }
	     }
	     ViewerCanvas.addViewerControl(new DisplayModeViewerControl());
	     ViewerCanvas.addViewerControl(new GridViewerControl());
	     ViewerCanvas.addViewerControl(new AxesViewerControl());
	     ViewerCanvas.addViewerControl(new PerspectiveViewerControl());
	 } else if ( message == Plugin.SCENE_WINDOW_CREATED) {
	     LayoutWindow window = (LayoutWindow) args[0];
	     BMenuBar menuBar = window.getMenuBar();
	     BMenu menu = menuBar.getChild(2);
	     menu = (BMenu) menu.getChild(3);
	     ((BCheckBoxMenuItem)(menu.getChild(0))).removeEventLink(CommandEvent.class, window);
	     ((BCheckBoxMenuItem)(menu.getChild(1))).removeEventLink(CommandEvent.class, window);
	     ((BCheckBoxMenuItem)(menu.getChild(2))).removeEventLink(CommandEvent.class, window);
	     ((BCheckBoxMenuItem)(menu.getChild(3))).removeEventLink(CommandEvent.class, window);
	     ((BCheckBoxMenuItem)(menu.getChild(4))).removeEventLink(CommandEvent.class, window);
	     ((BCheckBoxMenuItem)(menu.getChild(5))).removeEventLink(CommandEvent.class, window);
	     AllViewsDisplayMenuManager manager = new AllViewsDisplayMenuManager(window);
	     ((BCheckBoxMenuItem)(menu.getChild(0))).addEventLink(CommandEvent.class, manager, "doProcess");
	     ((BCheckBoxMenuItem)(menu.getChild(1))).addEventLink(CommandEvent.class, manager, "doProcess");
	     ((BCheckBoxMenuItem)(menu.getChild(2))).addEventLink(CommandEvent.class, manager, "doProcess");
	     ((BCheckBoxMenuItem)(menu.getChild(3))).addEventLink(CommandEvent.class, manager, "doProcess");
	     ((BCheckBoxMenuItem)(menu.getChild(4))).addEventLink(CommandEvent.class, manager, "doProcess");
	     ((BCheckBoxMenuItem)(menu.getChild(5))).addEventLink(CommandEvent.class, manager, "doProcess");

	 }
     }
}
