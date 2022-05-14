package com.example.androidchess;

import android.util.Log;

/**
 * The pawn class
 * @author Jiaqi He, Weizheng Liang
 *
 */
public class Pawn extends Piece {
	int move = 0;
	int turn = 0;
	Pawn(boolean isWhite) {
		super();
		this.isWhite = isWhite;
		if(isWhite) {
			this.name = "wp";
		}else {
		this.name = "bp";
		}
	}

	
	public String toString() {
		return name;
		
	}



/**
 * A validMove method that will detect whether a basic move for pawn is
 * proper.
 * @return If the move is proper, return true. If no, return false.
 */
	@Override
	boolean validMove(int oldX, int oldY, int newX, int newY, Piece[][] chessboard) {
		// TODO Auto-generated method stub
		this.turn = MainActivity.turn;
		int dy,dx;
		boolean IsValid = false;
		dy = newY - oldY;
		dx = newX - oldX;
		if (!isWhite) {
			//pawn can move two steps in the first move
			if (this.move == 0 && dy == 2 && dx ==0 && chessboard[oldY + dy][oldX + dx] == null && chessboard[oldY + 1][oldX + dx] == null ) IsValid = true;
			//the regular move for pawn
			if(dy == 1 && dx ==0 && chessboard[oldY + dy][oldX + dx] == null) IsValid = true;
			if(dy == 1 && Math.abs(dx) == 1 && chessboard[oldY + dy][oldX + dx] !=null) IsValid = true;
			if(dy == 1 && Math.abs(dx) == 1 &&chessboard[oldY + dy][oldX + dx] ==null) IsValid = Enpassant(oldY,newX, chessboard, this.isWhite);
		}else {
			if (this.move == 0 && dy == -2 && dx ==0 && chessboard[oldY + dy][oldX + dx] == null &&chessboard[oldY - 1][oldX + dx] == null) IsValid = true;
			if(dy == -1 && dx ==0 && chessboard[oldY + dy][oldX + dx] == null) IsValid = true;
			if(dy == -1 && Math.abs(dx) == 1 && chessboard[oldY + dy][oldX + dx] !=null) IsValid = true;
			if(dy == -1 && Math.abs(dx) == 1 &&chessboard[oldY + dy][oldX + dx] ==null) IsValid = Enpassant(oldY,newX, chessboard, this.isWhite);
		}
		//if (!IsValid) IsValid = Enpassant(oldY,newX, chessboard, this.isWhite);
		return IsValid;
	}
	public void MoveConfirm(){
		this.move++;
	}
	/**
	 * Check if the chosen pawn can enpassant.
	 * @param oldY The Y-coordinate for if there exists an opponent's
	 * pawn that moves two steps.
	 * @param newX The X-coordinate for if there exists an opponent's
	 * pawn that moves two steps.
	 * @param chessboard Current chess board
	 * @param IsWhite The color of the chosen pawn. This parameter is used
	 * to avoid a player eliminating his own pawn.
	 * @return Return true if the Enpassant is valid.
	 */
	boolean Enpassant (int oldY, int newX, Piece[][] chessboard, boolean IsWhite) {
		if(chessboard[oldY][newX] != null && chessboard[oldY][newX] instanceof Pawn) {
			Pawn pawn = (Pawn) chessboard[oldY][newX];
			if(pawn.isWhite == this.isWhite) return false;
			if(pawn.turn == (this.turn - 1) && pawn.move == 1) {
				if(chessboard[oldY][newX].isWhite){
					if(oldY == 4) return true;
				}else{
					if(oldY == 3) return true;
				}
			}
		}
		return false;
	}
	@Override
	boolean inValidRange(int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
