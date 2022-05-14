package com.example.androidchess;
/**
* Represents a general chess piece, a super class for all chess pieces.
* @author  Jiaqi He, Weizheng Liang
* @version 1.0
* @since   2022-03-19 
*/
public abstract class Piece {
	/**
	* every chess piece has this to determined their color
	*@param isWhite This is the only parameter. To determined if this is a white Bishop or black one.
	*/
	boolean isWhite;
	/**
	* every chess piece has this to determined their name
	*/
	String name;

	/**
    * Create a Bishop with specified color.
    */
	public Piece() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	* Abstract method.
    * check in general if the second input is a valid move
    * @param oldX, this piece's original x-position
    * @param oldY, this piece's original y-position
    * @param newX, this piece's destination x-position
    * @param newY, this piece's destination x-position
    * @param chessboard, the Board object.
    * @return if returns true, the move is valid. if returns false, the move is invalid
    */
	abstract boolean validMove(int oldX, int oldY, int newX, int newY, Piece[][] chessboard);
	
	
	/**
	* Abstract method.
    * check if the second input is in the valid range of a specific piece movement 
    * @param oldX, this piece's original x-position
    * @param oldY, this piece's original y-position
    * @param newX, this piece's destination x-position
    * @param newY, this piece's destination x-position
    * @return if returns true, the destination position is valid. if returns false, the destination position is invalid
    */
	abstract boolean inValidRange(int oldX, int oldY, int newX, int newY);
	

	
	
	
	
	

}
