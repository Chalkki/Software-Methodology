package com.example.androidchess;

/**
* Represents a Rook.
* @author  Jiaqi He, Weizheng Liang
* @version 1.0
* @since   2022-03-19 
*/
public class Rook extends Piece {

	Integer move;
	/**
    * Create a Rook with specified color.
    * @param isWhite This is the only parameter. To determined if this is a white Rook or black one.
    */
	Rook(boolean isWhite) {
		super();
		this.move = 0;
		this.isWhite = isWhite;
		if(isWhite) {
			this.name = "wR";
		}else {
		this.name = "bR";
		}
	}
	
	/**
    * To check if this Rook's current move is a valid move
    * @param oldX, Rook's original x-position
    * @param oldY, Rook's original y-position
    * @param newX, Rook's destination x-position
    * @param newY, Rook's destination x-position
    * @param Mychessboard, the Board object.
    * @return if returns true, the move is valid. if returns false, the move is invalid
    */
	@Override
	// check in general if the second input is a valid move
	boolean validMove(int oldX, int oldY, int newX, int newY, Piece[][] Mychessboard) {

		// check if destination in valid range
		if (inValidRange(oldX, oldY, newX, newY)) {

			// check if there is obstacle between origin and destination
			if (!hasObstacle(oldX, oldY, newX, newY, Mychessboard)) {
				// check if destination is empty

				return true;
			}
		}

		return false;
	}
	/**
    * To check if this Rook's destination position is in valid range
    * @param oldX, Rook's original x-position
    * @param oldY, Rook's original y-position
    * @param newX, Rook's destination x-position
    * @param newY, Rook's destination x-position
    * @return if returns true, the destination position is valid. if returns false, the destination position is invalid
    */
	// check if the second input is in the valid range of a specific piece movement 
	boolean inValidRange(int oldX, int oldY, int newX, int newY) {
		// 2d array to contain coordinates this piece can go
		
		// fill in coordinates
		for (int i = 0; i<8;i++) {
			
			if (i != oldY) {
				if (oldX ==newX && i == newY) return true;
			}
			if (i != oldX) {
				if (i ==newX && oldY == newY) return true;
			}
			
		}
		

		return false;
	}
	
	/**
    * To check if there is obstacle between the original position and the destination position
    * @param oldX, Rook's original x-position
    * @param oldY, Rook's original y-position
    * @param newX, Rook's destination x-position
    * @param newY, Rook's destination x-position
    * @param Mychessboard, the Board object.
    * @return if returns true, there is an obstacle. if returns false, there isn't an obstacle
    */
	boolean hasObstacle(int oldX, int oldY, int newX, int newY, Piece[][] Mychessboard) {
		
		// Rook goes Y direction
		if (oldX == newX) {

			// if Math.abs(newY-oldY) equals 0, it does not move at all. if equals 1, it has no path, only the origin and destination.
			if (Math.abs(newY-oldY)>1) {

				for (int i = 1; i<Math.abs(newY-oldY);i++) {
					// going left
					if (newY<oldY){

						if (Mychessboard[oldY-i][oldX] != null) {

							return true;
						}
					}
					//going right
					if (newY>oldY) {
						if (Mychessboard[oldY+i][oldX] != null) {

							return true;
						}
					}
					
				}
			}else {
				return false;
			}
			
		}
		
		if (oldY == newY) {
			if (Math.abs(newX-oldX)>1) {
				for (int i = 1; i<Math.abs(newX-oldX);i++) {
					// going down
					if (newX<oldX){
						if (Mychessboard[oldY][oldX-i] != null) {
							return true;
						}
					}
					//going up
					if (newX>oldX) {
						if (Mychessboard[oldY][oldX+i] != null) {
							return true;
						}
					}
					
				}
			}else {
				return false;
			}
		}
		return false;
	}
	
	


	
	
	/**
    * An override toString() method
    * @return it returns this Rook's name
    */
	public String toString() {
		return name;
		
	}


}
