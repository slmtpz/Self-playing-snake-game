package cmpe160Project2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GUI {

	private JFrame frame;
	private GamePanel gamePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new GamePanel(new SnakesGame());
		gamePanel.setBackground(Color.WHITE);
		frame.getContentPane().add(gamePanel);
		frame.pack();
	}

}

class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int GAME_PANEL_WIDTH = 500;
	private final int GAME_PANEL_HEIGHT = 500;

	private BufferedImage gameImage;
	private Timer gameTimer;
	
	private int frameRate = 500;
	private int gridSquareSize = 20;
	
	private Game game;

	public GamePanel(Game game) {
		super();
		game.setGamePanel(this);
		this.game = game;
		gameImage = new BufferedImage(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		setGameTimer();
	}
	
	private void animate() {
		clearCanvas();
		drawGrid();
		
		game.animateObjects();
	}
	
	private void setGameTimer() {
		gameTimer = new Timer(2000/frameRate, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animate();
				repaint();
			}
		});
		gameTimer.setInitialDelay(0);
		gameTimer.start();
	}

	public Dimension getPreferredSize() {
		return new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gameImage != null) {
			g.drawImage(gameImage, 0, 0, null);
		}
	}
	
	private void clearCanvas() {
		Graphics g = gameImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, gameImage.getWidth(), getHeight());
		g.dispose();
	}
	
	public int getGridWidth() {
		return GAME_PANEL_WIDTH/gridSquareSize;
	}
	
	public int getGridHeight() {
		return GAME_PANEL_HEIGHT/gridSquareSize;
	}
	
	private void drawGrid() {
		Graphics g = gameImage.getGraphics();
		g.setColor(Color.LIGHT_GRAY);

		// vertical grid
		for (int i=0; i<GAME_PANEL_WIDTH/gridSquareSize; i++) {
			int lineX = i*gridSquareSize;
			g.drawLine(lineX, 0, lineX, GAME_PANEL_HEIGHT);
		}
		// horizontal grid
		for (int i=0; i<GAME_PANEL_HEIGHT/gridSquareSize; i++) {
			int lineY = i*(gridSquareSize);
			g.drawLine(0, lineY, GAME_PANEL_WIDTH, lineY);
		}
		g.dispose();
	}

	public void drawRectangle(int gridX, int gridY, Color color) {
		if (gridX < 0 || gridY < 0 || 
				gridX >= GAME_PANEL_WIDTH/gridSquareSize || gridX >= GAME_PANEL_HEIGHT/gridSquareSize) {
			return;
		}
		Graphics g = gameImage.getGraphics();
		g.setColor(color);
		int x = gridX*gridSquareSize+1;
		int y = gridY*gridSquareSize+1;
		g.fillRect(x, y, gridSquareSize-1, gridSquareSize-1);
		g.dispose();
	}
	
	public void drawSmallRectangle(int gridX, int gridY, Color color) {
		if (gridX < 0 || gridY < 0 || 
				gridX >= GAME_PANEL_WIDTH/gridSquareSize || gridX >= GAME_PANEL_HEIGHT/gridSquareSize) {
			return;
		}
		Graphics g = gameImage.getGraphics();
		g.setColor(color);
		int x = gridX*gridSquareSize+3;
		int y = gridY*gridSquareSize+3;
		g.fillRect(x, y, gridSquareSize-5, gridSquareSize-5);
		g.dispose();
	}
}
