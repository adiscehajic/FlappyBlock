package ba.bitcamp.projectFlappyBlock;

import javax.swing.JFrame;

/**
 * 
 * @author adis.cehajic
 *
 */
public class GamePlay extends JFrame {

	private static final long serialVersionUID = 1356236529790095779L;

	// Declaring main JFrame.
	public static JFrame window = new JFrame("Flappy block");
	
	/**
	 * Constructor
	 */
	public GamePlay() {

		FlappyBlock flappyBlock = new FlappyBlock(800, 600);
		
		// Setting JFrame appearance.
		window.setSize(800, 600);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setContentPane(flappyBlock);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public static void main(String[] args) {
		// Calling constructor.
		new GamePlay();
	}

	/**
	 * Restarts opening of JFrame.
	 */
	public static void restart() {
		main(null);
	}

}
