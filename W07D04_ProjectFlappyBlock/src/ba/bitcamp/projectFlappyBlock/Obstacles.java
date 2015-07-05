package ba.bitcamp.projectFlappyBlock;

import java.awt.Color;
import java.awt.Graphics;

public class Obstacles {

	public int x;
	public int y;
	public int gameWidth;
	public int gameHeight;
	public int obstacleHeigth = 100 + (int) (Math.random() * 200);
	private int resetPosition;
	public int obstacleSize;

	private boolean isUp;

	public Obstacles(int x, int y, int gameWidth, int gameHeight,
			int resetPosition, boolean isUp) {
		this.x = x;
		this.y = y;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.resetPosition = resetPosition;
		this.isUp = isUp;
	}

	public void resetObstaclePosition() {
		obstacleHeigth = 100 + (int) (Math.random() * 200);
		if (isUp) {
			x = gameWidth + resetPosition;
			y = 0;
		} else {
			x = gameWidth + resetPosition;
			y = gameHeight - obstacleHeigth;
		}
	}

	public void move() {
		if (x > -(150)) {
			x -= 5;
		} else {

			resetObstaclePosition();
		}
	}

	public void draw(Graphics g, int obstacleSize) {
		this.obstacleSize = obstacleSize;

		if (isUp) {
			g.setColor(new Color(124, 155, 161));
			g.fill3DRect(x, y, 150, gameHeight - obstacleSize - 150, true);
		} else {
			g.setColor(new Color(124, 155, 161));
			g.fill3DRect(x, y, 150, obstacleSize, true);
		}

	}

	public boolean colideDown(Block block) {
		if (block.y + block.HEIGHT > y) {

			if (block.x < x && block.x + block.WIDTH + 3 > x) {
				block.y = y - block.HEIGHT;
				return true;
			} else if (block.x > x && x + 150 > block.x) {
				block.y = y - block.HEIGHT;
				return true;
			}
		}
		return false;
	}

	public boolean colideDownX(Block block) {
		if (block.y + block.HEIGHT > y) {
			if (block.x + block.WIDTH == x) {
				return true;
			}
		}
		return false;
	}

	public boolean colideUp(Block block) {
		if (gameHeight - obstacleSize - 150 > block.y) {

			if (block.x < x && block.x + block.WIDTH + 3 > x) {
				return true;
			} else if (block.x > x && x + 150 > block.x) {
				return true;
			}
		}
		return false;
	}

	public boolean colideUpX(Block block) {
		if (gameHeight - obstacleSize - 150 > block.y) {
			if (block.x + block.WIDTH == x) {
				return true;
			}
		}
		return false;
	}

	public boolean crashOnObstacle(Block block) {
		if (block.y > y - block.HEIGHT - 1) {
			block.y = y - block.HEIGHT;
			return true;
		}
		return false;
	}

}
