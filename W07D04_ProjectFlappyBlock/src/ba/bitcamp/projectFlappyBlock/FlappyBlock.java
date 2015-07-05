package ba.bitcamp.projectFlappyBlock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
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

	// Declaring block and obstacles.
	private Block mainBlock;
	private Obstacles firstObstacle;
	private Obstacles secondObstacle;
	private Obstacles thirdObstacle;
	private Obstacles fourthObstacle;

	// Declaring timers.
	private Timer timer;
	private Timer timerBlock;

	// Declaring variables that will check if the block has intersect with
	// obstacle.
	private boolean isBlockActive = true;
	private boolean isColideFirst = false;
	private boolean isColideSecond = false;
	private boolean isColideThird = true;

	// Declaring variable that will import image.
	private BufferedImage image;

	// Declaring variable that will count number of passed obstacles.
	private int pointCounter = 0;

	/**
	 * Constructor
	 * 
	 * @param width
	 *            - Width of a game.
	 * @param height
	 *            - Height of a game.
	 */
	public FlappyBlock(int width, int height) {

		// Importing image.
		try {
			image = ImageIO.read(new File("images\\building.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Declaring block
		mainBlock = new Block(width / 4, height / 2 - 100);

		// Declaring obstacles
		firstObstacle = new Obstacles(width + 150, height - 130, width, height,
				150, false);
		secondObstacle = new Obstacles(width + 700, height - 120, width,
				height, 150, false);
		thirdObstacle = new Obstacles(width + 150, 0, width, height, 150, true);
		fourthObstacle = new Obstacles(width + 700, 0, width, height, 150, true);

		// Setting first timer.
		timer = new Timer(25, this);

		// Setting second timer.
		timerBlock = new Timer(25, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Calling method down
				mainBlock.down();

				// Checking with which obstacle has block intersect and putting
				// it on declared position.
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

		// Adding key listener that will enable that with key SPACE block
		// changes position.
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					// If the block has intersect with obstacle disabling the
					// key SPACE.
					if (isBlockActive == true) {
						timer.start();
						mainBlock.jump();
					}
				}
				repaint();
			}
		});

		// Adding mouse listener that adds focus.
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				((Component) e.getSource()).requestFocus();
			}
		});

		// Adding focus listener
		addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				timer.stop();
			}

			public void focusGained(FocusEvent e) {
			}
		});

	}

	/**
	 * Restarts game.
	 */
	public void restartWindow() {
		// Opening option dialog that asks user if he wants to play again.
		int choice = JOptionPane.showOptionDialog(null,
				String.format("Your score is: %d\nDo you want to play again? ",
						pointCounter), "", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		// If option YES is chosen closing old window and restarting game.
		if (choice == JOptionPane.YES_OPTION) {
			GamePlay.window.dispose();
			GamePlay.restart();
			// If option NO is chosen exiting game.
		} else if (choice == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Paints all elements on panel.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Drawing image.
		g.drawImage(image, 0, 0, null);
		// Declaring variables that will represent heigth of obstacles.
		int heigthOne = firstObstacle.obstacleHeigth;
		int heigthTwo = secondObstacle.obstacleHeigth;
		// Drawing block and obstacles.
		mainBlock.draw(g);
		firstObstacle.draw(g, heigthOne);
		secondObstacle.draw(g, heigthTwo);
		thirdObstacle.draw(g, heigthOne);
		fourthObstacle.draw(g, heigthTwo);
		// Drawing number of passed obsacles.
		g.setColor(new Color(255, 64, 0));
		g.setFont(new Font("Serif", Font.BOLD, 60));
		g.drawString("" + pointCounter, 380, 100);
	}

	public void actionPerformed(ActionEvent e) {

		// Checking if the block has passed and decreasing point counter for
		// one.
		if (mainBlock.isPassed(firstObstacle)) {
			pointCounter++;
		} else if (mainBlock.isPassed(secondObstacle)) {
			pointCounter++;
		}

		// Checking if the block value of y is lower than zero and stopping game
		// if it is true.
		if (mainBlock.y < 0) {
			timer.stop();
			timerBlock.start();
			isBlockActive = false;
			// Checking if the block is on ground and if the block has intersect
			// with side of the obstacles that are down.
		} else if (mainBlock.crashOnGround()
				|| firstObstacle.colideDownX(mainBlock)
				|| secondObstacle.colideDownX(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
			// Checking if the block has intersect with side of obstacle that is
			// up.
		} else if (thirdObstacle.colideUpX(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
			// Checking if the block has intersect with side of obstacle that is
			// up.
		} else if (fourthObstacle.colideUpX(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
			// Checking if the block has intersect with the obstacles that are
			// down.
		} else if (firstObstacle.colideDown(mainBlock)
				|| secondObstacle.colideDown(mainBlock)) {
			timer.stop();
			isBlockActive = false;
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = false;
			// Checking if the block has intersect with the obstacles that is
			// up.
		} else if (thirdObstacle.colideUp(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideFirst = true;
			isColideSecond = false;
			isColideThird = false;
			isBlockActive = false;
			// Checking if the block has intersect with the obstacles that is
			// up.
		} else if (fourthObstacle.colideUp(mainBlock)) {
			timer.stop();
			timerBlock.start();
			isColideSecond = true;
			isColideFirst = false;
			isColideThird = false;
			isBlockActive = false;
			// Mowing block and obstacles if they don't intersect.
		} else {
			isBlockActive = true;
			mainBlock.down();
			firstObstacle.move();
			secondObstacle.move();
			thirdObstacle.move();
			fourthObstacle.move();
		}

		repaint();

		// Checking if the first timer is stopped and if it is true restarting
		// game.
		if (!timer.isRunning()) {
			restartWindow();
		}
	}

}
