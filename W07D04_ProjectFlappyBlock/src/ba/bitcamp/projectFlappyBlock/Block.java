package ba.bitcamp.projectFlappyBlock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class Block {

	public int x;
	public int y;
	private int moveY;
	public int ground;

	private static final int JUMP = 20;
	private static final int DOWN = 2;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 25;
	
	private BufferedImage image;

	public Block(int width, int height) {
		super();
		this.x = width;
		this.y = height;
		this.ground = height * 2 + 147;
		
		try {
			image = ImageIO.read(new File("src\\ba\\bitcamp\\projectFlappyBlock\\brick1.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public void draw(Graphics g) {
		g.setColor(new Color(185, 185, 185));
		g.drawImage(image, x, (int) y, WIDTH, HEIGHT, null, null);
	}

	public void jump() {

		if (moveY > 0) {
			moveY = 0;
		}
		moveY -= JUMP;
	}

	public void down() {
		if (moveY < 20) {
			moveY += DOWN;
		}
		y += moveY;

	}

	public boolean crashOnGround() {

		if (y > ground) {
			y = ground;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isPassed(Obstacles obstacle){
		if (x == obstacle.x + 150) {
			return true;
		}
		return false;
	}

}
