package com.example.androidchess;

/**
* Represents a Bishop.
* @author  Jiaqi He, Weizheng Liang
* @version 1.0
* @since   2022-03-19 
*/
public class Bishop extends Piece {
	/**
    * Create a Bishop with specified color.
    * @param isWhite This is the only parameter. To determined if this is a white Bishop or black one.
    */
	Bishop(boolean isWhite) {
		super();
		this.isWhite = isWhite;
		if(isWhite) {
			this.name = "wB";
		}else {
		this.name = "bB";
		}
	}
	
	/**
    * To check if this Bishop's current move is a valid move
    * @param oldX, Bishop's original x-position
    * @param oldY, Bishop's original y-position
    * @param newX, Bishop's destination x-position
    * @param newY, Bishop's destination x-position
    * @param Mychessboard, the Board object.
    * @return if returns true, the move is valid. if returns false, the move is invalid
    */
	@Override
	// check in general if the second input is a valid move
	boolean validMove(int oldX, int oldY, int newX, int newY, Piece[][] chessboard) {
		// check if destination in valid range
		if (inValidRange(oldX, oldY, newX, newY)) {
			// check if there is obstacle between origin and destination
			if (!hasObstacle(oldX, oldY, newX, newY, chessboard)) {
				return true;
			}
		}
		
		return false;
	}
	/**
    * To check if this Bishop's destination position is in valid range
    * @param oldX, Bishop's original x-position
    * @param oldY, Bishop's original y-position
    * @param newX, Bishop's destination x-position
    * @param newY, Bishop's destination x-position
    * @return if returns true, the destination position is valid. if returns false, the destination position is invalid
    */
	// check if the second input is in the valid range of a specific piece movement 
	boolean inValidRange(int oldX, int oldY, int newX, int newY) {
		// from the four direction Bishop can go --- (top left, top right, bot left, bot right)
		for (int i=1; i<8;i++) {
			if (oldX-i >= 0 && oldY-i >= 0) {
				if (newX==oldX-i && newY==oldY-i) return true;
			}
			if (oldX-i >= 0 && oldY+i <= 8) {
				if (newX==oldX-i && newY==oldY+i) return true;
			}
			if (oldX+i <= 8 && oldY+i <= 8) {
				if(newX==oldX+i && newY==oldY+i) return true;
			}
			if (oldX+i <= 8 && oldY-i >= 0) {
				if(newX==oldX+i && newY==oldY-i) return true;
			}
		}

		return false;
	}
	/**
    * To check if there is obstacle between the original position and the destination position
    * @param oldX, Bishop's original x-position
    * @param oldY, Bishop's original y-position
    * @param newX, Bishop's destination x-position
    * @param newY, Bishop's destination x-position
    * @param Mychessboard, the Board object.
    * @return if returns true, there is an obstacle. if returns false, there isn't an obstacle
    */
	boolean hasObstacle(int oldX, int oldY, int newX, int newY, Piece[][] Mychessboard) {
		
		if (Math.abs(newY-oldY)>1) {
			// go top left direction
			for (int i = 1;i < Math.abs(newY-oldY);i++) {
				//bot
				if (newX < oldX) {
					//bot left
					if (newY < oldY) {
						if (Mychessboard[oldY-i][oldX-i] != null) {
							return true;
						}
					}
					//bot right
					else {
						if (Mychessboard[oldY+i][oldX-i] != null) {
							return true;
						}
					}
				}
				//top
				else {
					// top left
					if (newY < oldY) {
						if (Mychessboard[oldY-i][oldX+i] != null) {
							return true;
						}
					}
					//top right
					else {
						if (Mychessboard[oldY+i][oldX+i] != null) {
							return true;
						}
					}
					
				}
			}
		}
		
		
		
		return false;
		
	}
	/**
    * An override toString() method
    * @return it returns this Bishop's name
    */
	public String toString() {
		return name;
		
	}

}
