package ba.bitcamp.projectFlappyBlock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author adis.cehajic
 *
 */
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

	// Declaring variable that will check if the game is started.
	private boolean isStarted = false;

	// Declaring variable that will import image.
	private BufferedImage image;
	private BufferedImage start;

	// Declaring variable that will count number of passed obstacles.
	private int pointCounter = 0;
	private static int highScore;

	// Importing sounds from a file.
	private File point = new File("res/sounds/point.wav");
	private File hit = new File("res/sounds/hit.wav");
	private File swooshing = new File("res/sounds/swooshing.wav");

	/**
	 * Constructor
	 * 
	 * @param width
	 *            - Width of a game.
	 * @param height
	 *            - Height of a game.
	 */
	public FlappyBlock(int width, int height) {

		// Importing images.
		try {
			image = ImageIO.read(ResourceLoader.load("images/city3.png"));
			start = ImageIO.read(ResourceLoader.load("images/start.png"));
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
					playSound(swooshing);
					// If the block has intersect with obstacle disabling the
					// key SPACE.
					if (isBlockActive == true) {
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
				timer.start();
				isStarted = true;
				((Component) e.getSource()).requestFocus();

			}
		});
	}

	/**
	 * Restarts game.
	 */
	public void restartWindow() {
		// Opening option dialog that asks user if he wants to play again.
		int choice = JOptionPane
				.showOptionDialog(
						null,
						String.format(
								"Your score is: %d\nHighscore is: %d\nDo you want to play again? ",
								pointCounter, highScore), "",
						JOptionPane.YES_NO_OPTION,
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

		// Checking if the game is started and drawing images.
		if (!isStarted) {
			g.drawImage(start, 300, 65, null);
		} else {
			// Declaring variables that will represent height of obstacles.
			int heigthOne = firstObstacle.obstacleHeigth;
			int heigthTwo = secondObstacle.obstacleHeigth;
			// Drawing block and obstacles.
			mainBlock.draw(g);
			firstObstacle.draw(g, heigthOne);
			secondObstacle.draw(g, heigthTwo);
			thirdObstacle.draw(g, heigthOne);
			fourthObstacle.draw(g, heigthTwo);
			// Drawing number of passed obstacles.
			g.setColor(new Color(23, 105, 133));
			g.setFont(new Font("Serif", Font.BOLD, 60));
			g.drawString("" + pointCounter, 380, 100);
		}
	}

	public void actionPerformed(ActionEvent e) {

		// Checking if the block has passed and decreasing point counter for
		// one.
		if (mainBlock.isPassed(firstObstacle)) {
			pointCounter++;
			playSound(point);
		} else if (mainBlock.isPassed(secondObstacle)) {
			pointCounter++;
			playSound(point);
		}
		// Reading highscore from file and checking if the new score is higher
		// than highscore.
		TextIO.readFile("src/ba/bitcamp/projectFlappyBlock/text.txt");
		highScore = TextIO.getInt();
		TextIO.readStandardInput();
		if (highScore < pointCounter) {
			highScore = pointCounter;
			TextIO.writeFile("src/ba/bitcamp/projectFlappyBlock/text.txt");
			TextIO.putln(highScore);
			TextIO.writeStandardOutput();
		}
		// Checking if the block value of y is lower than zero and stopping game
		// if it is true.
		if (mainBlock.y < 0) {
			playSound(hit);
			timer.stop();
			timerBlock.start();
			isBlockActive = false;
			// Checking if the block is on ground and if the block has intersect
			// with side of the obstacles that are down.
		} else if (mainBlock.crashOnGround()
				|| firstObstacle.colideDownX(mainBlock)
				|| secondObstacle.colideDownX(mainBlock)) {
			playSound(hit);
			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
			// Checking if the block has intersect with side of obstacle that is
			// up.
		} else if (thirdObstacle.colideUpX(mainBlock)) {
			playSound(hit);
			timer.stop();
			timerBlock.start();
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = true;
			isBlockActive = false;
			// Checking if the block has intersect with side of obstacle that is
			// up.
		} else if (fourthObstacle.colideUpX(mainBlock)) {
			playSound(hit);
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
			playSound(hit);
			timer.stop();
			isBlockActive = false;
			isColideFirst = false;
			isColideSecond = false;
			isColideThird = false;
			// Checking if the block has intersect with the obstacles that is
			// up.
		} else if (thirdObstacle.colideUp(mainBlock)) {
			playSound(hit);
			timer.stop();
			timerBlock.start();
			isColideFirst = true;
			isColideSecond = false;
			isColideThird = false;
			isBlockActive = false;
			// Checking if the block has intersect with the obstacles that is
			// up.
		} else if (fourthObstacle.colideUp(mainBlock)) {
			playSound(hit);
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

	/**
	 * Plays sound that is imported in project.
	 * 
	 * @param sound
	 *            - Sound that needs to be played.
	 */
	public static void playSound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();

			Thread.sleep(clip.getMicrosecondLength() / 100000);
		} catch (Exception e) {

		}
	}

}
