package cmpe160Project2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

enum Direction { UP, DOWN, LEFT, RIGHT }

public class SnakesGame implements Game {

	private GamePanel gamePanel;

	private List<Snake> snakes;
	private List<Snake> snakesToBeAdded;
	private List<Food> foods;

	public SnakesGame() {
		snakes = new ArrayList<Snake>();
		snakesToBeAdded = new ArrayList<Snake>();
		foods = new ArrayList<Food>();
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		snakes.add(new Snake(this));
		foods.add(createRandomFood());
	}

	public void animateObjects() {
		if (gamePanel != null) {
			for (Animatable s:snakes) {
				s.move();
				s.draw();
			}
			for (Animatable f:foods) {
				f.draw();
			}
			for (Snake s:snakesToBeAdded) {
				snakes.add(s);
				s.draw();
			}
			snakesToBeAdded.clear();
		}
	}

	public int getWorldWidth() {
		return gamePanel.getGridWidth();
	}

	public int getWorldHeight() {
		return gamePanel.getGridHeight();
	}

	public void addSnake(Snake snake) {
		snakesToBeAdded.add(snake);
	}

	public void consumeFood(int x, int y) {
		for (int i=0; i<foods.size(); i++) {
			Food food = foods.get(i);
			if (food.getX()==x && food.getY()==y) {
				foods.remove(i);
				if (foods.size()<1) {
					foods.add(createRandomFood());
				}
				return;
			}
		}
	}
	
	public boolean isLocationFree(int x, int y) {
		for (Snake s:snakes) {
			if (s.containsLocation(x, y)) {
				return false;
			}
		}
		return true;
	}

	public boolean foodInDirection(int x, int y, Direction dir) {
		for (Food food:foods) {
			int foodX = food.getX();
			int foodY = food.getY();
			int deltaX = foodX-x;
			int deltaY = foodY-y;
			if (dir == Direction.UP && deltaY < 0 && Math.abs(deltaX)<=Math.abs(deltaY)) {
				return true;
			} else if (dir == Direction.DOWN && deltaY > 0 && Math.abs(deltaX)<=Math.abs(deltaY)) {
				return true;
			} else if (dir == Direction.LEFT && deltaX < 0 && Math.abs(deltaY)<=Math.abs(deltaX)) {
				return true;
			} else if (dir == Direction.RIGHT && deltaX > 0 && Math.abs(deltaY)<=Math.abs(deltaX)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean foodCloseInDirection(int x, int y, Direction dir) {
		for (Food food:foods) {
			int foodX = food.getX();
			int foodY = food.getY();
			int deltaX = foodX-x;
			int deltaY = foodY-y;
			if (dir == Direction.UP && deltaX==0 && deltaY==-1) {
				return true;
			} else if (dir == Direction.DOWN && deltaX==0 && deltaY==1) {
				return true;
			} else if (dir == Direction.LEFT && deltaX==-1 && deltaY==0) {
				return true;
			} else if (dir == Direction.RIGHT && deltaX==1 && deltaY==0) {
				return true;
			}
		}
		return false;
	}

	public void drawSnakePart(int gridX, int gridY) {
		gamePanel.drawRectangle(gridX, gridY, Color.BLUE);
	}

	public void drawSnakeHead(int gridX, int gridY) {
		gamePanel.drawRectangle(gridX, gridY, Color.RED);
	}

	public void drawFood(int gridX, int gridY) {
		gamePanel.drawSmallRectangle(gridX, gridY, Color.ORANGE);
	}
	
	private Food createRandomFood() {
		int x, y;
		do {
			x = (int)(Math.random()*getWorldWidth());
			y = (int)(Math.random()*getWorldHeight());
		} while (!isLocationFree(x, y));
		return new Food(this, x, y);
	}
}