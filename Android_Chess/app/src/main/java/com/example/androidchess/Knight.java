package com.example.androidchess;

/**
* Represents a King.
* @author  Jiaqi He, Weizheng Liang
* @version 1.0
* @since   2022-03-19 
*/
public class Knight extends Piece {
	/**
    * Create a Knight with specified color.
    * @param isWhite This is the only parameter. To determined if this is a white Knight or black one.
    */
	Knight(boolean isWhite) {
		super();
		this.isWhite = isWhite;
		if(isWhite) {
			this.name = "wN";
		}else {
		this.name = "bN";
		}
	}
	/**
    * To check if this Knight's current move is a valid move
    * @param oldX, Knight's original x-position
    * @param oldY, Knight's original y-position
    * @param newX, Knight's destination x-position
    * @param newY, Knight's destination x-position
    * @param Mychessboard, the Board object.
    * @return if returns true, the move is valid. if returns false, the move is invalid
    */
	@Override
	// check in general if the second input is a valid move
	boolean validMove(int oldX, int oldY, int newX, int newY, Piece[][] chessboard) {
		// check if destination in valid range

		int dx,dy;
		dx = newX - oldX;
		dy = newY- oldY;
		//System.out.println("this is knight!");
		if(Math.abs(dy) == 2 && Math.abs(dx) ==1) return true;
		if(Math.abs(dx) == 2 && Math.abs(dy) ==1) return true;
		return false;
	}
	
	// check if the second input is in the valid range of a specific piece movement 
	/**
    * To check if this Knight's destination position is in valid range
    * @param oldX, Knight's original x-position
    * @param oldY, Knight's original y-position
    * @param newX, Knight's destination x-position
    * @param newY, Knight's destination x-position
    * @return if returns true, the destination position is valid. if returns false, the destination position is invalid
    */
	boolean inValidRange(int oldX, int oldY, int newX, int newY) {
	return false;
		
	}
	/**
    * An override toString() method
    * @return it returns this Knight's name
    */
	public String toString() {
		return name;
		
	}
}
