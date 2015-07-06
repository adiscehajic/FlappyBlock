package ba.bitcamp.projectFlappyBlock;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Block {

	// Declaring variables.
	// Variables of obstacle position.
	public int x;
	public int y;
	// Variable that represent changed value of value y.
	private int moveY;
	// Variable that represents value of x when obstacle is on ground.
	public int ground;

	// Declaring constants.
	private static final int JUMP = 20;
	private static final int DOWN = 2;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 25;

	// Declaring image variable.
	private BufferedImage image;

	/**
	 * Constructor
	 * 
	 * @param width
	 *            - Width of the block.
	 * @param height
	 *            - Height of the block.
	 */
	public Block(int width, int height) {
		super();
		this.x = width;
		this.y = height;
		this.ground = height * 2 + 147;

		// Importing image that will represent block.
		try {
		//	image = ImageIO.read(new File("images\\brick1.png"));
			image = ImageIO.read(ResourceLoader.load("images\\brick1.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Draws a image that represents block.
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		g.drawImage(image, x, (int) y, WIDTH, HEIGHT, null, null);
	}

	/**
	 * Decreases the value of y variable for constant JUMP.
	 */
	public void jump() {
		if (moveY > 0) {
			moveY = 0;
		}
		moveY -= JUMP;
	}

	/**
	 * Increases the value of y variable for constant DOWN.
	 */
	public void down() {
		if (moveY < 20) {
			moveY += DOWN;
		}
		y += moveY;
	}

	/**
	 * Checking if the value of y is larger than variable ground.
	 * 
	 * @return True if the value of y is larger than variable ground and false
	 *         if it is not.
	 */
	public boolean crashOnGround() {
		if (y > ground) {
			y = ground;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checking if the value of x is equal to the sum of obstacle value x and
	 * obstacle width.
	 * 
	 * @param obstacle
	 *            - Obstacle that user checks if x is passed.
	 * @return True if it is passed and false if it is not.
	 */
	public boolean isPassed(Obstacles obstacle) {
		if (x == obstacle.x + 150) {
			return true;
		}
		return false;
	}

}
