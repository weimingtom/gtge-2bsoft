package com.golden.gamedev.engine.jogl;

// JFC
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import net.java.games.jogl.GLCanvas;
import net.java.games.jogl.GLCapabilities;
import net.java.games.jogl.GLDrawableFactory;

import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.graphics.WindowExitListener;
import com.golden.gamedev.util.ImageUtil;

/**
 * Graphics engine for OpenGL JOGL Dialog Environment, <br>
 * JOGL is available to download at <a href="https://jogl.dev.java.net/"
 * target="_blank">https://jogl.dev.java.net/</a>.
 * <p>
 * 
 * Make sure the downloaded library is included into your game classpath before
 * using this graphics engine.
 * <p>
 * 
 * <b>Note: GTGE is not associated in any way with JOGL, this class is only
 * interfacing JOGL to be used in GTGE. <br>
 * This class is created and has been tested to be working properly using
 * <em>JOGL v1.1b-08</em>.</b>
 * <p>
 * 
 * Use {@link com.golden.gamedev.OpenGLGameLoader} to load the game in OpenGL
 * JOGL graphics engine environment.
 * 
 * @see com.golden.gamedev.OpenGLGameLoader
 * @see <a href="https://jogl.dev.java.net/" target="_blank">JOGL official site</a>
 */
public class JOGLDialogMode extends JOGLWindowedMode {
	
	/** *************************** AWT COMPONENT ******************************* */
	
	private JDialog frame;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new instance of Windowed Graphics Engine with specified size, and
	 * bufferstrategy.
	 */
	public JOGLDialogMode(Dimension d, boolean vsync, boolean drawdecorations) {
		super(d,vsync,drawdecorations);
	}

	@Override
	public void initialize(boolean vsync, boolean drawdecorations){
		
		// sets game frame		
		this.frame=new JDialog();
		this.frame.setUndecorated(!drawdecorations);
		
		this.frame.addWindowListener(WindowExitListener.getInstance());
		this.frame.setResizable(false); // not resizable frame
		this.frame.setIgnoreRepaint(true); // turn off all paint events
		// since we doing active rendering
		
		this.renderer = new JOGLRenderer(vsync);
		
		this.canvas = GLDrawableFactory.getFactory().createGLCanvas(
		        new GLCapabilities());
		this.canvas.addGLEventListener(this.renderer);
		this.canvas.setNoAutoRedrawMode(true);
		
		this.canvas.setFocusable(true);
		this.canvas.setSize(this.size);
		
		// frame title bar and border (frame insets) makes
		// game screen smaller than requested size
		// we must enlarge the frame by it's insets size
		this.frame.setVisible(true);
		Insets inset = this.frame.getInsets();
		this.frame.setVisible(false);
		this.frame.setSize(this.size.width + inset.left + inset.right,
		        this.size.height + inset.top + inset.bottom);
		this.frame.add(this.canvas);
		this.frame.pack();
		this.frame.setLayout(null);
		this.frame.setLocationRelativeTo(null); // centering game frame
		this.frame.setVisible(true);
		
		this.canvas.setRenderingThread(Thread.currentThread());
		this.canvas.display();
	}

	/** ************************************************************************* */
	/** *************************** PROPERTIES ********************************** */
	/** ************************************************************************* */
	
	@Override
	public Image getWindowIcon() {
		return null;
	}	
	
	public void addWindowListener(WindowListener wl){
		this.frame.addWindowListener(wl);
	}
	
	public void removeWindowListener(WindowListener wl){
		this.frame.removeWindowListener(wl);
	}	
	
}
