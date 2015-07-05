package ba.bitcamp.projectFlappyBlock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyBlock extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4055775700740040546L;

	private int gameWidth;
	private int gameHeight;

	private Block mainBlock;
	private Obstacles firstObstacle;
	private Obstacles secondObstacle;

	private Obstacles thirdObstacle;
	private Obstacles fourthObstacle;

	private Timer timer;
	private Timer timerBlock;

	private boolean isBlockActive = true;
	private boolean isColideFirst = false;
	private boolean isColideSecond = false;
	private boolean isColideThird = true;

	private BufferedImage image;

	private int counter = 0;

	public FlappyBlock(int width, int height) {
		try {
			image = ImageIO.read(new File("src\\ba\\bitcamp\\projectFlappyBlock\\building.jpg"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.gameWidth = width;
		this.gameHeight = height;

		mainBlock = new Block(width / 4, height / 2 - 100);

		firstObstacle = new Obstacles(width + 150, height - 130, width, height,
				150, false);
		secondObstacle = new Obstacles(width + 700, height - 120, width,
				height, 150, false);

		thirdObstacle = new Obstacles(width + 150, 0, width, height, 150, true);
		fourthObstacle = new Obstacles(width + 700, 0, width, height, 150, true);

		timer = new Timer(25, this);

		timerBlock = new Timer(25, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainBlock.down();

				if (isColideThird) {
					mainBlock.crashOnGround();
				} else if (isColideFirst) {
					firstObstacle.crashOnObstacle(mainBlock);
				} else if (isColideSecond) {
					secondObstacle.crashOnObstacle(mainBlock);
				}

				repaint();

			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (isBlockActive == true) {
						timer.start();
						mainBlock.jump();
					}
				}
				repaint();
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				((Component) e.getSource()).requestFocus();
			}
		});

		addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				timer.stop();
			}

			public void focusGained(FocusEvent e) {
			}
		});

	}

	public void restartWindow() {
		// timerBlock.stop();
		int choice = JOptionPane.showOptionDialog(null, String.format(
				"Your score is: %d\nDo you want to play again? ", counter), "",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);

		if (choice == JOptionPane.YES_OPTION) {

			GamePlay.window.dispose();
			GamePlay.restart();

		} else if (choice == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(image, 0, 0, null);

		int heigthOne = firstObstacle.obstacleHeigth;
		int heigthTwo = secondObstacle.obstacleHeigth;
		mainBlock.draw(g);
		firstObstacle.draw(g, heigthOne);
		secondObstacle.draw(g, heigthTwo);
		thirdObstacle.draw(g, heigthOne);
		fourthObstacle.draw(g, heigthTwo);
		g.setColor(new Color(255, 64, 0));
		g.setFont(new Font("Serif", Font.BOLD, 60));
		g.drawString("" + counter, 380, 100);
	}

	public void actionPerformed(ActionEvent e) {

		if (mainBlock.isPassed(firstObstacle)) {
			counter++;
		} else if (mainBlock.isPassed(secondObstacle)) {
			counter++;
		}

		if (mainBlock.y < 0) {
			timer.stop();
			timerBlock.start();
			isBlockActive = false;
		} else if (mainBlock.crashOnGround()
				|| firstObstacle.colideDownX(mainBlock)
				|| secondObstacle.colideDownX(mainBlock)) {

			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
		} else if (thirdObstacle.colideUpX(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
		} else if (fourthObstacle.colideUpX(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
		} else if (firstObstacle.colideDown(mainBlock)
				|| secondObstacle.colideDown(mainBlock)) {
			timer.stop();
			isBlockActive = false;
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = false;
		} else if (thirdObstacle.colideUp(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideFirst = true;
			isColideSecond = false;
			isColideThird = false;
			isBlockActive = false;

		} else if (fourthObstacle.colideUp(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideSecond = true;
			isColideFirst = false;
			isColideThird = false;
			isBlockActive = false;
		} else {
			isBlockActive = true;
			mainBlock.down();
			firstObstacle.move();
			secondObstacle.move();
			thirdObstacle.move();
			fourthObstacle.move();
		}

		repaint();

		if (!timer.isRunning()) {
			restartWindow();
		}

	}

}
