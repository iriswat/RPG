
/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Yuxin Zhang> <yuxinz1>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Represents the entire game world. (Designed to be instantiated just once for
 * the whole game).
 */
public class World {
	/** Create a new World object. */
	private TiledMap map;
	private Player player;
	private Camera camera;
	/** An array indicating which tiles block movement. */
	private boolean[][] blocked;
	private final int TILE_SIZE = 72;
	/** screen width in tiles. */
	private final int widthInTiles = 13;
	/** screen height in tiles. */
	private final int heightInTiles = 10;
	/** initial position of the player in the world, in x and y coordinates. */
	private final int initialPlayerX = 756;
	private final int initialPlayerY = 684;

	/**
	 * measures how far to offset the tile based rendering to give the smooth
	 * motion of scrolling as the player moves around.
	 */
	private int offsetX;
	private int offsetY;

	public World() throws SlickException {
		map = new TiledMap("assets/map.tmx", "assets");

		// build a collision map based on tile properties
		blocked = new boolean[map.getWidth()][map.getHeight()];
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 0);
				String blockProperty = map.getTileProperty(tileID, "block", "0");
				if ("1".equals(blockProperty)) {
					blocked[x][y] = true;
				}
			}
		}

		// create a new Player object and Camera object
		player = new Player(initialPlayerX, initialPlayerY);
		camera = new Camera(player, RPG.screenwidth, RPG.screenheight);
	}

	/**
	 * Update the game state for a frame.
	 * 
	 * @param dir_x
	 *            The player's movement in the x axis (-1, 0 or 1).
	 * @param dir_y
	 *            The player's movement in the y axis (-1, 0 or 1).
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 */
	public void update(double dir_x, double dir_y, int delta) throws SlickException {
		int playerX = (int) player.getxPos();
		int playerY = (int) player.getyPos();
		// intended movement in x,y direction
		double newX = player.getxPos() + dir_x * delta;
		double newY = player.getyPos() + dir_y * delta;
		// check if the tile in the player's moving direction blocks movement
		if (blocked[(int) (newX / TILE_SIZE)][playerY / TILE_SIZE]) {
			dir_x = 0;
		}
		if (blocked[playerX / TILE_SIZE][(int) (newY / TILE_SIZE)]) {
			dir_y = 0;
		}
		// let the player update his position in the world
		player.update(dir_x, dir_y, delta, camera);
		// recentre the camera around the player
		camera.update();
		// calculate the offset of the top-left corner to be rendered from the
		// edge of the tile
		offsetX = (int) (camera.getMinX() % TILE_SIZE);
		offsetY = (int) (camera.getMinY() % TILE_SIZE);

	}

	/**
	 * Render the entire screen, so it reflects the current game state.
	 * 
	 * @param g
	 *            The Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) throws SlickException {
		// render a portion of the entire map and the player on the screen
		map.render(-offsetX, -offsetY, (int) camera.getMinX() / TILE_SIZE, (int) camera.getMinY() / TILE_SIZE,
				widthInTiles, heightInTiles);
		player.render();

	}
}
