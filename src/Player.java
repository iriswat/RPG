
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Yuxin Zhang> <yuxinz1>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player {
	Image character;

	/** The player's position in the world, in x and y coordinates. */
	private double xPos;
	private double yPos;
	/** The player's position on the screen, in pixels. */
	private float screenX;
	private float screenY;

	/** Rate of moving in each direction, in pixel per millisecond */
	public final double velocity = 0.25;

	public Player(double xPos, double yPos) throws SlickException {
		character = new Image("assets/units/player.png");
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public double getxPos() {
		return xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void update(double dir_x, double dir_y, int delta, Camera camera) {
		// compute displacement in x and y directions
		xPos += dir_x * (velocity * delta);
		yPos += dir_y * (velocity * delta);
		// compute screenX and screenY using its relative position to the camera
		screenX = (float) (xPos - camera.getxPos() + camera.screenwidth / 2);
		screenY = (float) (yPos - camera.getyPos() + camera.screenheight / 2);
	}

	public void render() {
		character.drawCentered(screenX, screenY);

	}

}
