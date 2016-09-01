/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Your name> <Your login>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    /** Create a new World object. */
	private TiledMap map;
	private Player player;
	private Camera camera;
	// An array indicating which tiles block movement
	private boolean[][] blocked;
	private final int TILE_SIZE = 72;
	private final int widthInTiles = 13;
	private final int heightInTiles = 10;
	
	private int playerTileOffsetX;
	private int playerTileOffsetY;

    public World()
    throws SlickException
    {
    	map = new TiledMap("assets/map.tmx","assets");

		// build a collision map based on tile properties in the map
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


    	player = new Player(756,684,RPG.screenwidth,RPG.screenheight);//initial player location
    	camera = new Camera(player,RPG.screenwidth,RPG.screenheight);
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(double dir_x, double dir_y, int delta)
    throws SlickException
    {
		int playerX = (int) player.getxPos();
		int playerY = (int) player.getyPos();
		//intended movement in x,y direction
		int newX = (int) (playerX + dir_x *delta);
		int newY = (int) (playerY + dir_y *delta);

		if (blocked[newX / TILE_SIZE][playerY / TILE_SIZE]) {
			dir_x = 0;
		}
		if (blocked[playerX / TILE_SIZE][newY / TILE_SIZE]) {
			dir_y = 0;
		}

		player.update(dir_x, dir_y, delta);
    	camera.update();
		// calculate the offset of the player from the edge of the tile
		playerTileOffsetX = (int) ((player.getxPos() % TILE_SIZE) - TILE_SIZE);
		playerTileOffsetY = (int) ((player.getyPos() % TILE_SIZE) - TILE_SIZE);
		// playerTileOffsetX = (int) ((playerX - player.getxPos()) * TILE_SIZE);
		// playerTileOffsetY = (int) ((playerY - player.getyPos()) * TILE_SIZE);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
		map.render(playerTileOffsetX, playerTileOffsetY, (int) camera.getMinX() / TILE_SIZE,
				(int) camera.getMinY() / TILE_SIZE, widthInTiles,
				heightInTiles);
    	player.render();
    	
    }
}
