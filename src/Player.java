import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player {
	Image character;
	private double xPos;
	private double yPos;

    /** Screen width, in pixels. */
    public final int screenwidth;
    /** Screen height, in pixels. */
    public final int screenheight;
	/** Rate of moving in each direction, in pixel per millisecond */
	public final double velocity = 0.25;
	// public final double velocity = 0.6;
	
	public Player(double xPos,double yPos, int screenwidth, int screenheight) throws SlickException{
		character = new Image("assets/units/player.png");
		this.xPos = xPos;
		this.yPos = yPos;
		this.screenwidth = screenwidth;
		this.screenheight = screenheight;
	}
	
	
	public double getxPos() {
		return xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void update(double dir_x, double dir_y, int delta) {
		xPos += dir_x * (velocity * delta);
		yPos += dir_y * (velocity * delta);
	}
	
	public void render(){
		//System.out.println(xPos);
		character.drawCentered(screenwidth/2, screenheight/2);
		
	}

}
