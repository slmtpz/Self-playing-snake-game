package cmpe160Project2;

import java.awt.Point;
import java.util.LinkedList;

/**
 * 
 * @author Abdullah Seleme Topuz
 *
 */
public class Snake implements Animatable {
	
	private SnakesGame snakesGame;          
	private LinkedList<Point> theLList = new LinkedList<Point>();
	private Direction dir = Direction.RIGHT;
	private static int numberOfSnakes = 1;
	
	/**
	 * Constructs an initial snake whose head is at (4,1) and body is 4-boxes length.
	 * @param snakesGame provides snake in a snakesGame object.
	 */
	public Snake(SnakesGame snakesGame){
		this.snakesGame = snakesGame;		
		theLList.add(new Point(4,1)); //head
		theLList.add(new Point(3,1));
		theLList.add(new Point(2,1));
		theLList.add(new Point(1,1));
	}
	
	/**
	 * Constructs a new snake dividing from 8-boxes snake.
	 * @param dividingList provides body from 8-boxes snake.
	 * @param snakesGame provides snake in a snakesGame object.
	 */
	public Snake(LinkedList<Point> dividingList, SnakesGame snakesGame){
		this.snakesGame = snakesGame;
		theLList.add(dividingList.get(7));
		theLList.add(dividingList.get(6));
		theLList.add(dividingList.get(5));
		theLList.add(dividingList.get(4));
	}
	
	
	/**
	 * Returns true if the given coordinates exist in game world.
	 * 		   false if the given coordinates does not exist in game world.
	 */
	boolean containsLocation(int x,int y){
		for(int i=0; i<theLList.size(); i++){
			if(theLList.get(i).x==x && theLList.get(i).y==y) return true;
		}
		return false;
	}

	/**
	 * Finds a direction where to go.
	 * If a snake sees the food, it keeps going the same direction,
	 * if not it chooses a random direction, left of right of current direction.
	 * If a snake get stuck, due to trying to go on a snake part or go out of grid world, it chooses a direction where it can go.
	 */
	public void correctDirection(){
		if(dir==null)dir=Direction.RIGHT;
		Point H = theLList.getFirst();
		if(snakesGame.foodInDirection(H.x,H.y,dir)){ 
			
		} else {
			double a = Math.random();
			if(a<0.5){
				dir = turnRight(dir);
			} else {
				dir = turnLeft(dir);
			}
		}
		
		Point movingPoint = new Point(0,0);
		if(dir==Direction.UP){
			movingPoint = new Point(H.x,H.y-1);
		} else if (dir==Direction.DOWN){
			movingPoint = new Point(H.x,H.y+1);
		} else if (dir==Direction.LEFT){
			movingPoint = new Point(H.x-1,H.y);
		} else if (dir==Direction.RIGHT){
			movingPoint = new Point(H.x+1,H.y);
		} 
		int i=0;
		while(!(snakesGame.isLocationFree(movingPoint.x,movingPoint.y) && movingPoint.x>=0 && movingPoint.x<snakesGame.getWorldWidth() && movingPoint.y>=0 && movingPoint.y<snakesGame.getWorldHeight())){
			double a = Math.random();
			if(a<0.5){
				dir = turnRight(dir);
			} else {
				dir = turnLeft(dir);
			}
			if(dir==Direction.UP){
				movingPoint = new Point(H.x,H.y-1);
			} else if (dir==Direction.DOWN){
				movingPoint = new Point(H.x,H.y+1);
			} else if (dir==Direction.LEFT){
				movingPoint = new Point(H.x-1,H.y);
			} else if (dir==Direction.RIGHT){
				movingPoint = new Point(H.x+1,H.y);
			}
			if(i==8)break;
			i++;
		}
		if(i==4)dir=null;
		
		
	}
	
	
	/**
	 * Gives right direction of current direction.
	 * @param dir current direction
	 * @return direction that needed
	 */
	public Direction turnRight(Direction dir){
		if(dir==Direction.UP){return Direction.RIGHT;}
		else if(dir==Direction.DOWN){return Direction.LEFT;}
		else if(dir==Direction.LEFT){return Direction.UP;}
		else if (dir==Direction.RIGHT){return Direction.DOWN;}
		else return null;
	}
	
	/**
	 * Gives left direction of current direction.
	 * @param dir current direction
	 * @return direction that needed
	 */
	public Direction turnLeft(Direction dir){
		if(dir==Direction.UP){return Direction.LEFT;}
		else if(dir==Direction.DOWN){return Direction.RIGHT;}
		else if(dir==Direction.LEFT){return Direction.DOWN;}
		else if (dir==Direction.RIGHT){return Direction.UP;}
		else return null;
	}
	
	/**
	 * Gives information about where a snake will be at.
	 * Also, separates a snake into 2 when it get 8-boxes.
	 */
	public void move() {
		Point theHead = theLList.getFirst();
		correctDirection();
		if(dir==Direction.UP){
			theLList.add(0,new Point(theHead.x,theHead.y-1));
		} else if (dir==Direction.DOWN){
			theLList.add(0,new Point(theHead.x,theHead.y+1));
		} else if (dir==Direction.LEFT){
			theLList.add(0,new Point(theHead.x-1,theHead.y));
		} else if (dir==Direction.RIGHT){
			theLList.add(0,new Point(theHead.x+1,theHead.y));
		} else theLList.add(new Point(0,0));

		if(snakesGame.foodCloseInDirection(theHead.x,theHead.y,dir)){
			snakesGame.consumeFood(theLList.get(0).x,theLList.get(0).y);
			
			if(theLList.size()==8){
				snakesGame.addSnake(new Snake(theLList,snakesGame));
				theLList.remove(7);
				theLList.remove(6);
				theLList.remove(5);
				theLList.remove(4);
				numberOfSnakes++;
				System.out.println(numberOfSnakes);
			}
		}
		else theLList.removeLast();
		
	}

	/**
	 * Draws a snake on grid world.
	 */
	public void draw() {
		snakesGame.drawSnakeHead(theLList.get(0).x,theLList.get(0).y);
		for(int i=1; i<theLList.size(); i++){
			snakesGame.drawSnakePart(theLList.get(i).x,theLList.get(i).y);
		}		
	}

	
}
