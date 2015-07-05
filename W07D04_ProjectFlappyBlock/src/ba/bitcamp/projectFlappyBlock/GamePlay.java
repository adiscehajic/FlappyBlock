package ba.bitcamp.projectFlappyBlock;

import javax.swing.JFrame;

public class GamePlay extends JFrame {

	private static final long serialVersionUID = 1356236529790095779L;

	public static JFrame window = new JFrame("Flappy block");
	
	public GamePlay(){
		
		FlappyBlock flappyBlock = new FlappyBlock(800, 600);

		window.setSize(800, 600);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setContentPane(flappyBlock);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

	}
	
	public static void main(String[] args) {

		new GamePlay();
	}

	public static void restart() {
		main(null);
	}

}
