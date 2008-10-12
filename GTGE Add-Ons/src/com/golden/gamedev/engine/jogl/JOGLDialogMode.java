package com.golden.gamedev.engine.jogl;

// JFC
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;

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
public class JOGLDialogMode implements BaseGraphics {
	
	/** *************************** AWT COMPONENT ******************************* */
	
	private JDialog frame;
	private Dimension size;
	
	/** *************************** JOGL COMPONENT ****************************** */
	
	private GLCanvas canvas;
	
	/** *************************** JOGL RENDERER ******************************* */
	
	private JOGLRenderer renderer;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new instance of Windowed Graphics Engine with specified size, and
	 * bufferstrategy.
	 */
	public JOGLDialogMode(Dimension d, boolean vsync, boolean drawdecorations) {
		this.size = d;
		
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
	/** ************************ GRAPHICS FUNCTION ****************************** */
	/** ************************************************************************* */
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public Graphics2D getBackBuffer() {
		return this.renderer.getRenderer();
	}
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public boolean flip() {
		this.canvas.display();
		
		return true;
	}
	
	/** ************************************************************************* */
	/** ******************* DISPOSING GRAPHICS ENGINE *************************** */
	/** ************************************************************************* */
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
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
			System.exit(-1);
		}
	}
	
	/** ************************************************************************* */
	/** *************************** PROPERTIES ********************************** */
	/** ************************************************************************* */
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public Dimension getSize() {
		return this.size;
	}
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public Component getComponent() {
		return this.canvas;
	}
	
	/**
	 * Returns the top-level frame of this graphics engine.
	 */
	public JDialog getFrame() {
		return this.frame;
	}
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public String getGraphicsDescription() {
		return "JOGL Windowed Mode [" + this.getSize().width + "x"
		        + this.getSize().height + "]"
		        + ((this.isVSync()) ? " with VSync" : "");
	}
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public void setWindowTitle(String st) {
		this.frame.setTitle(st);
	}
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public String getWindowTitle() {
		return this.frame.getTitle();
	}
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public void setWindowIcon(Image icon) {
		try {
			this.frame.setIconImage(icon);
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * <i>Please refer to super class method documentation.</i>
	 */
	public Image getWindowIcon() {
		return null;
	}
	
	/**
	 * Returns whether this graphics engine is vsync to display refresh rate or
	 * not.
	 */
	public boolean isVSync() {
		return (this.renderer != null) ? this.renderer.isVSync() : false;
	}
	
	/**
	 * Returns JOGL event listener and renderer.
	 */
	public JOGLRenderer getRenderer() {
		return this.renderer;
	}
	
}
