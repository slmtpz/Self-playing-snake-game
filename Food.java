package cmpe160Project2;

public class Food implements Animatable {

	private SnakesGame game;
	
	private int x;
	private int y;
	
	public Food(SnakesGame game, int x, int y) {
		this.game = game;
		
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		
	}
	
	public void draw() {
		game.drawFood(x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}