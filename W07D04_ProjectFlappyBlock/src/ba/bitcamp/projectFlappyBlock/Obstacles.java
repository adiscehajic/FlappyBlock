package ba.bitcamp.projectFlappyBlock;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author adis.cehajic
 *
 */
public class Obstacles {

	// Declaring variables.
	// Variables of obstacle position.
	public int x;
	public int y;
	// Variables of obstacle width and height.
	public int gameWidth;
	public int gameHeight;
	// Variable that represents random obstacle height.
	public int obstacleHeigth = 100 + (int) (Math.random() * 200);
	// Variable that represents obstacle position of reseting.
	private int resetPosition;
	// Variable that represents random obstacle size.
	public int obstacleSize;
	// Variable that represents if the obstacle is up or down.
	private boolean isUp;

	// Declaring constants.
	public static final int MOVE = 5;
	public static final int RESET_POSITION = -150;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            - X coordinate
	 * @param y
	 *            - Y coordinate
	 * @param gameWidth
	 *            - Obstacle width.
	 * @param gameHeight
	 *            - Obstacle height.
	 * @param resetPosition
	 *            - Obstacle position of reseting.
	 * @param isUp
	 *            - Is the obstacle up or down.
	 */
	public Obstacles(int x, int y, int gameWidth, int gameHeight,
			int resetPosition, boolean isUp) {
		this.x = x;
		this.y = y;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.resetPosition = resetPosition;
		this.isUp = isUp;
	}

	/**
	 * Resets the obstacle on declared position of reseting.
	 */
	public void resetObstaclePosition() {
		// Setting random height of obstacle.
		obstacleHeigth = 100 + (int) (Math.random() * 200);
		// Checking if the obstacle is up or down and sets the position after
		// reseting.
		if (isUp) {
			x = gameWidth + resetPosition;
			y = 0;
		} else {
			x = gameWidth + resetPosition;
			y = gameHeight - obstacleHeigth;
		}
	}

	/**
	 * Changes the value of x.
	 */
	public void move() {
		// If the reached position of obstacle is RESET_POSITION
		if (x > RESET_POSITION) {
			x -= MOVE;
		} else {
			resetObstaclePosition();
		}
	}

	/**
	 * Draws the obstacle with declared size.
	 * 
	 * @param g
	 * @param obstacleSize
	 *            - Size of obstacle.
	 */
	public void draw(Graphics g, int obstacleSize) {
		this.obstacleSize = obstacleSize;

		// Checking what is the position of obstacle and drawing it.
		if (isUp) {
			//g.setColor(new Color(124, 155, 161));
			g.setColor(new Color(212, 129, 78));
			g.fill3DRect(x, y, 150, gameHeight - obstacleSize - 150, true);
		} else {
			//g.setColor(new Color(124, 155, 161));
			g.setColor(new Color(212, 129, 78));
			g.fill3DRect(x, y, 150, obstacleSize, true);
		}
	}

	/**
	 * Checks if the obstacle that is down intersects with the block.
	 * 
	 * @param block
	 *            - The block with whom obstacle intersects.
	 * @return - True if the obstacle intersects and false if not.
	 */
	public boolean colideDown(Block block) {
		if (block.y + Block.HEIGHT > y) {
			if (block.x < x && block.x + Block.WIDTH + 3 > x) {
				block.y = y - Block.HEIGHT;
				return true;
			} else if (block.x > x && x + 150 > block.x) {
				block.y = y - Block.HEIGHT;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the obstacle that is down intersects with the block side.
	 * 
	 * @param block
	 *            - The block with whom obstacle intersects.
	 * @return - True if the obstacle intersects and false if not.
	 */
	public boolean colideDownX(Block block) {
		if (block.y + Block.HEIGHT > y) {
			if (block.x + Block.WIDTH == x) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the obstacle that is up intersects with the block.
	 * 
	 * @param block
	 *            - The block with whom obstacle intersects.
	 * @return - True if the obstacle intersects and false if not.
	 */
	public boolean colideUp(Block block) {
		if (gameHeight - obstacleSize - 150 > block.y) {
			if (block.x < x && block.x + Block.WIDTH + 3 > x) {
				return true;
			} else if (block.x > x && x + 150 > block.x) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the obstacle that is up intersects with the block side.
	 * 
	 * @param block
	 *            - The block with whom obstacle intersects.
	 * @return - True if the obstacle intersects and false if not.
	 */
	public boolean colideUpX(Block block) {
		if (gameHeight - obstacleSize - 150 > block.y) {
			if (block.x + Block.WIDTH == x) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the block is on obstacle that is in down position.
	 * @param block - The block that is checked.
	 * @return True if the block is on obstacle and false if not.
	 */
	public boolean crashOnObstacle(Block block) {
		if (block.y > y - Block.HEIGHT - 1) {
			block.y = y - Block.HEIGHT;
			return true;
		}
		return false;
	}

}
