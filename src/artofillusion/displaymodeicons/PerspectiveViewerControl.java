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

import java.awt.Dimension;
import java.awt.Insets;

import buoy.event.ValueChangedEvent;
import buoy.widget.LayoutInfo;
import buoy.widget.RowContainer;
import buoy.widget.Widget;
import artofillusion.ViewerCanvas;
import artofillusion.ui.ThemeManager;
import artofillusion.ui.ToolButtonWidget;
import artofillusion.view.ViewChangedEvent;
import artofillusion.view.ViewerControl;

public class PerspectiveViewerControl implements ViewerControl {

	public Widget createWidget(ViewerCanvas canvas) {
	return new PerspectiveViewerControlWidget(canvas);
	}

	public String getName() {
		return ("Perspective Toggle Button");
	}
	
	public class PerspectiveViewerControlWidget extends RowContainer {
		
		private ViewerCanvas canvas;
		private ToolButtonWidget button;
		
		public PerspectiveViewerControlWidget(ViewerCanvas canvas) {
			super();
			this.canvas = canvas;
			canvas.addEventLink(ViewChangedEvent.class, this, "reflectCanvasState");
			button = new ToolButtonWidget(ThemeManager.getToolButton(this, "displaymodeicons:perspective"));
			button.addEventLink(ValueChangedEvent.class, this, "doButtonPressed");
			reflectCanvasState();
			setDefaultLayout(new LayoutInfo(LayoutInfo.CENTER, LayoutInfo.NONE, new Insets(0, 3, 0, 3), new Dimension(0,0)));
			add(button);
		}

		private void reflectCanvasState() {
			button.setSelected(canvas.isPerspective());
		}

		private void doButtonPressed() {
			if (canvas.getRenderMode() != ViewerCanvas.RENDER_RENDERED)
			{
				canvas.setPerspective(button.isSelected());
				canvas.repaint();
			}
		}
	}
}


