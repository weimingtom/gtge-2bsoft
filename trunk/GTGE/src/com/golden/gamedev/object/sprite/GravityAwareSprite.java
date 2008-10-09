package com.golden.gamedev.object.sprite;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;

/**
 * <code>GravityAwareSprite</code> makes an ordinary sprite aware of gravity. It retains
 * it's movement capabilities.
 * Bouncing is implemented but a collision check must be done.
 */
public class GravityAwareSprite extends Sprite {

	    /**
	 * 
	 */
		private static final long serialVersionUID = -4295803656769067918L;
		private double bounce = 0.3; 
	    private double gravity = 0.5; 

	    private boolean onground;

	    private double velocity;
	    
		/** ************************************************************************* */
		/** ***************************** CONSTRUCTOR ******************************* */
		/** ************************************************************************* */
		
		/**
		 * Creates new <code>GravityAwareSprite</code>, leaving default gravity and
		 * bounce values.
		 */
	    public GravityAwareSprite (BufferedImage image, int x, int y) {
	        super(image, x, y);
	    }
	    
	    /**
		 * Creates new <code>GravityAwareSprite</code>, setting custom gravity and
		 * bounce values
		 * @param _gravity Used to set gravity. 0.5 = Earth, 0.05 = Moon
		 * @param _bounce Used to set bouncing constant. 0.3 = Tire, 0.7 = Tennis ball
		 */
	    public GravityAwareSprite (BufferedImage image, int x, int y, double _gravity, double _bounce) {
	        super(image, x, y);
	        bounce=_bounce;
	        gravity=_gravity;
	    }
		/** ************************************************************************* */
		/** ************************* UPDATE THE SPRITE ***************************** */
		/** ************************************************************************* */
	    /**
		 * Main method. Takes set gravity into acount and corrects y axis position. Bear
		 * in mind that this does not override your set vertical speed, so handle it with
		 * care.
		 */
	    public void update(long elapsedTime) {
	    	/*Taking horizontal and vertical movement into account*/
	    	super.update(elapsedTime);
	    	
	        if (velocity > 0.3 || velocity < -0.6) {
	        	if(!((onground==true) && (bounce==0)))
	        		setY (getY() + velocity);
	        } else {
	            velocity = 0;
	        }
	        
	        /*Checks if the sprite collided with the ground.
	        The correct way to handle onground would be to do a collision check against
	        the ground acting sprite. If a collision took place, onground should be set
	        to true
	        */
	        if(onground == false) {
	            velocity += gravity;
	        }else{
	            if(velocity > 0.5) {
	            	// The Bounce Effect
	                velocity = (velocity * -bounce); 
	            }
	        }
	        /*onground must be set to false. If a collision occours, the collision handler
	        should set the correct value.*/
	        this.setNoGround();
	    }
	    /**
	     * Sets velocity with given parameter.
		 * If used correctly, setVelocity can simulate jumping. For example, setting
		 * velocity to -0.5 means jumping power of the same amount, whiles gravity 
		 * calculations take care of the landing
		 */
	    public void setVelocity(double _velocity) {
	        velocity=_velocity;
	    }
	    public double getVelocity() {
	        return velocity;
	    }
	    public void setOnGround() {
	        onground = true;
	    }
	    public void setNoGround() {
	        onground = false;
	    }
	    public boolean isOnGround() {
	        return onground;
	    }
	    
}
