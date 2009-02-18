/*
 * Copyright (c) 2008 Golden T Studios.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.golden.gamedev.engine.graphics;

// JFC
import java.awt.Canvas;
import java.awt.Dimension;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

/**
 * Graphics engine for a Dialog Environment.
 * <p>
 * 
 * See {@link com.golden.gamedev.engine.BaseGraphics} for how to use graphics
 * engine separated from Golden T Game Engine (GTGE) Frame Work.
 */
public class DialogMode extends WindowedMode {
	
	
	/** *************************** AWT COMPONENT ******************************* */
	private JDialog frame; // top frame where the canvas is put
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new instance of Dialog Graphics Engine with specified size, and
	 * whether want to use bufferstrategy or volatile image.
	 * @param d The resolution of the window.
	 * @param bufferstrategy If a buffer strategy shall be used.
	 * @param drawdecorations Send false if you don't want window decorations.
	 */
	public DialogMode(Dimension d, boolean bufferstrategy, boolean drawdecorations) {
		
		super(d,bufferstrategy,drawdecorations);
	}

	@Override
	public void initialize(boolean bufferstrategy, boolean drawdecorations){
	
		// sets game frame
		this.frame = new JDialog();
		
		this.frame.addWindowListener(WindowExitListener.getInstance());
		this.frame.setResizable(false); // non resizable frame
		this.frame.setIgnoreRepaint(true); // turn off all paint events
		// since we doing active rendering
		
		// the active component where the game drawn
		this.canvas = new Canvas(DialogMode.CONFIG);
		this.canvas.setIgnoreRepaint(true);
		this.canvas.setSize(this.size);
		
		// frame title bar and border (frame insets) makes
		// game screen smaller than requested size
		// we must enlarge the frame by it's insets size

		this.frame.setUndecorated(!drawdecorations);
		this.frame.setVisible(true);
		Insets inset = this.frame.getInsets();
		this.frame.setVisible(false);
		this.frame.setSize(this.size.width + inset.left + inset.right,
		        this.size.height + inset.top + inset.bottom);
		this.frame.add(this.canvas);
		this.frame.pack();
		this.frame.setLayout(null);
		this.frame.setLocationRelativeTo(null); // centering game frame
		if (this.frame.getX() < 0) {
			this.frame.setLocation(0, this.frame.getY());
		}
		if (this.frame.getY() < 0) {
			this.frame.setLocation(this.frame.getX(), 0);
		}
		this.frame.setVisible(true);
		
		// create backbuffer
		if (bufferstrategy) {
			bufferstrategy = this.createBufferStrategy();
		}
		
		if (!bufferstrategy) {
			this.createBackBuffer();
		}
		
		this.canvas.requestFocus();
	}
	
	/** ************************************************************************* */
	/** *************************** PROPERTIES ********************************** */
	/** ************************************************************************* */


	@Override
	public Image getWindowIcon() {
		//Dialogs don't have icons
		return null;
	}
	
	public void addWindowListener(WindowListener wl){
		this.frame.addWindowListener(wl);
	}
	
	public void removeWindowListener(WindowListener wl){
		this.frame.removeWindowListener(wl);
	}	
	
	public void cleanup() {
		try {
			Thread.sleep(200L);
		}
		catch (InterruptedException e) {
		}
		
		try {
			// dispose the frame
			if (this.frame != null) {
				this.frame.dispose();
			}
		}
		catch (Exception e) {
			System.err.println("ERROR: Shutting down graphics context " + e);
			this.frame.setVisible(false);
		}
	}
}
