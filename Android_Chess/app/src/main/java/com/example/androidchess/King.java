package com.example.androidchess;

import android.util.Log;

/**
 * Represents a King.
 * @author  Jiaqi He, Weizheng Liang
 * @version 1.0
 * @since   2022-03-19
 */

public class King extends Piece{
	Integer move;
	boolean castling;
	boolean ableToCastleLeft;
	boolean ableToCastleRight;
	boolean castleSuccess;

	/**
	 * Create a King with specified color.
	 * @param isWhite This is the only parameter. To determined if this is a white King or black one.
	 */
	King(boolean isWhite) {
		super();
		this.move = 0;
		this.castling = false;
		this.ableToCastleLeft = false;
		this.ableToCastleRight = false;
		this.castleSuccess =false;
		this.isWhite = isWhite;

		if(isWhite) {
			this.name = "wK";
		}else {
			this.name = "bK";
		}
	}

	/**
	 * To check if this King's current move is a valid move
	 * @param oldX, King's original x-position
	 * @param oldY, King's original y-position
	 * @param newX, King's destination x-position
	 * @param newY, King's destination x-position
	 * @param Mychessboard, the Board object.
	 * @return if returns true, the move is valid. if returns false, the move is invalid
	 */
	@Override
	boolean validMove(int oldX, int oldY, int newX, int newY, Piece[][] Mychessboard) {

		Log.d("this move Y ", String.valueOf(newY));
		Log.d("this move Y ", String.valueOf(newX));

		// check if destination in valid range
		if (inValidRange(oldX, oldY, newX, newY)) {
			if (this.castling && ableToCastleLeft(oldX, oldY, newX, newY, Mychessboard)) {
				// check if there is obstacle between origin and destination
				if (!hasObstacle(oldX, oldY, newX, newY, Mychessboard)
						&& !willInCheck(newX, newY, Mychessboard)
						&& !isInCheck(oldX, oldY,Mychessboard)
						&& !willPassCheck(oldX, oldY, newX, newY, Mychessboard,true)) {


					this.castling =false;

					this.ableToCastleLeft = true;

				}
			}
			if (this.castling && ableToCastleRight(oldX, oldY, newX, newY, Mychessboard)) {
				if (!hasObstacle(oldX, oldY, newX, newY, Mychessboard) && !willInCheck(newX, newY, Mychessboard) && !isInCheck(oldX, oldY,Mychessboard) && !willPassCheck(oldX, oldY, newX, newY, Mychessboard,false)) {


					this.castling =false;

					this.ableToCastleRight = true;

				}
			}

			if (!willInCheck(newX, newY, Mychessboard) && this.castling==false) {

				Log.d("validMove","true");
				return true;
			}
			this.castling =false;
		}
		Log.d("validMove","false");
		return false;
	}


	/**
	 * To check if this King's destination position is in valid range
	 * @param oldX, King's original x-position
	 * @param oldY, King's original y-position
	 * @param newX, King's destination x-position
	 * @param newY, King's destination x-position
	 * @return if returns true, the destination position is valid. if returns false, the destination position is invalid
	 */
	@Override
	boolean inValidRange(int oldX, int oldY, int newX, int newY) {

		int dy = Math.abs(newY-oldY);
		int dx = Math.abs(newX-oldX);
		//regular move
		if (dy <= 1 && dx <= 1) {
			Log.d("inValidRange","true");
			return true;
		}
		//castling
		Log.d("King's move",String.valueOf(this.move));
		if (this.move==0 && dy ==0 && dx ==2) {
			this.castling = true;
			Log.d("inValidRange","true");
			return true;
		}
		Log.d("inValidRange","false");
		return false;
	}

	/**
	 * To check if there is obstacle between the original position and the destination position
	 * @param oldX, King's original x-position
	 * @param oldY, King's original y-position
	 * @param newX, King's destination x-position
	 * @param newY, King's destination x-position
	 * @param Mychessboard, the Board object.
	 * @return if returns true, there is an obstacle. if returns false, there isn't an obstacle
	 */
	boolean hasObstacle(int oldX, int oldY, int newX, int newY, Piece[][] Mychessboard) {
		if (newX < oldX) {
			if (Mychessboard[oldY][oldX-1] == null && Mychessboard[oldY][oldX-2] == null && Mychessboard[oldY][oldX-3] == null) {
				Log.d("hasObstacle","false");
				return false;
			}
		}
		if (newX > oldX) {
			if (Mychessboard[oldY][oldX+1] == null && Mychessboard[oldY][oldX+2] == null) {
				Log.d("hasObstacle","false");
				return false;
			}
		}
		Log.d("hasObstacle","true");
		return true;

	}
	/**
	 * To check if this King is able to castle to the Queen side
	 * @param oldX, King's original x-position
	 * @param oldY, King's original y-position
	 * @param newX, King's destination x-position
	 * @param newY, King's destination x-position
	 * @param Mychessboard, the Board object.
	 * @return if returns true, this King is able to castle to the Queen side. if returns false, this King is NOT able to castle to the Queen side
	 */
	boolean ableToCastleLeft(int oldX, int oldY, int newX, int newY, Piece[][] Mychessboard) {

		if (this.move==0 && newX<oldX) {
			if (Mychessboard[oldY][0] !=null){
				Log.d("Rook's move",String.valueOf(((Rook)Mychessboard[oldY][0]).move));
				if (((Rook)Mychessboard[oldY][0]).move==0){
					Log.d("ableToCastleLeft","true");
					return true;
				}
			}


		}
		Log.d("ableToCastleLeft","false");
		return false;
	}

	/**
	 * To check if this King is able to castle to the King side
	 * @param oldX, King's original x-position
	 * @param oldY, King's original y-position
	 * @param newX, King's destination x-position
	 * @param newY, King's destination x-position
	 * @param Mychessboard, the Board object.
	 * @return if returns true, this King is able to castle to the King side. if returns false, this King is NOT able to castle to the King side
	 */
	boolean ableToCastleRight(int oldX, int oldY, int newX, int newY, Piece[][] Mychessboard) {
		if (this.move==0 && newX>oldX) {
			if (Mychessboard[oldY][7] != null){
				if (((Rook)Mychessboard[oldY][7]).move==0){
					Log.d("ableToCastleRight","true");
					return true;
				}
			}
		}
		Log.d("ableToCastleRight","false");
		return false;
	}

	/**
	 * To check if this King will be in check after moving to a specific position
	 * @param newX, the specific x-position
	 * @param newY, the specific y-position
	 * @param Mychessboard, the Board object.
	 * @return if returns true, this King will be in check. if returns false, this King will NOT be in check
	 */
	boolean willInCheck(int newX, int newY, Piece[][] Mychessboard) {
		//to find if King will be checked after this particular move.
		if (isInCheck(newX,newY,Mychessboard)) {
			Log.d("willInCheck","true");
			return true;
		}
		Log.d("willInCheck","false");
		return false;

	}
	/**
	 * To check if this King will pass through a in-check position while castling.
	 * only applicable under castling
	 * @param oldX, King's original x-position
	 * @param oldY, King's original y-position
	 * @param newX, King's destination x-position
	 * @param newY, King's destination x-position
	 * @param Mychessboard, the Board object.
	 * @param isLeft, true if castling Queen side
	 * @return if returns true, this King will be in check. if returns false, this King will NOT be in check
	 */
	boolean willPassCheck(int oldX, int oldY,int newX, int newY, Piece[][] Mychessboard, boolean isLeft) {
		if (isLeft) {
			if (!isInCheck(oldX-1,oldY,Mychessboard) && !isInCheck(oldX-2,oldY,Mychessboard)) {
				Log.d("willPassCheck","false");
				return false;
			}
		}
		if (!isLeft) {
			if (!isInCheck(oldX+1,oldY,Mychessboard) && !isInCheck(oldX+2,oldY,Mychessboard)) {
				Log.d("willPassCheck","false");
				return false;
			}
		}
		Log.d("willPassCheck","true");
		return true;
	}

	/**
	 * To check this King's current position is in check.
	 * @param oldX, King's original x-position
	 * @param oldY, King's original y-position
	 * @param Mychessboard, the Board object.
	 * @return if returns true, this King is in check. if returns false, this King is NOT in check
	 */
	boolean isInCheck(int oldX, int oldY, Piece[][] Mychessboard) {


		//to see if will be check by a Queen or Bishop or Pawn or King, i.e. will be check from diagonal direction
		for (int i=1; i<8;i++) {
			if (oldX-i >= 0 && oldY-i >= 0) {
				Piece tem = Mychessboard[oldY-i][oldX-i] ;

				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'p'|| tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'B')) {
						Log.d("isInCheck","true");
						return true;
					}else{
						break;
					}
				}
			}else break;
		}
		for (int i=1; i<8;i++) {
			if (oldX-i >= 0 && oldY+i < 8) {
				Piece tem = Mychessboard[oldY+i][oldX-i];

				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'p'|| tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'B' )) {
						Log.d("isInCheck","true");
						return true;
					}break;
				}
			}else break;
		}
		for (int i=1; i<8;i++) {
			if (oldX+i < 8 && oldY+i < 8) {
				Piece tem = Mychessboard[oldY+i][oldX+i];
				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'p'|| tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'B' )) {
						Log.d("isInCheck","true");
						return true;
					}break;
				}
			}else break;
		}
		for (int i=1; i<8;i++) {
			if (oldX+i < 8 && oldY-i >= 0) {
				Piece tem = Mychessboard[oldY-i][oldX+i];

				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'p' || tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'B' )) {
						Log.d("isInCheck","true");
						return true;
					}break;
				}
			}else break;

		}




		// to see if will be check by a Queen or Rook, i.e. will be check from horizontal or vertical direction
		for (int i = 1; i<8;i++) {
			//up
			if (oldY+i < 8 ) {
				Piece tem = Mychessboard[oldY+i][oldX];

				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'R' || tem.name.charAt(1) == 'K') ){
						Log.d("isInCheck","true");
						return true;
					}break;
				}
			}

		}
		for (int i = 1; i<8;i++) {
			//down
			if (oldY-i >=0) {
				Piece tem = Mychessboard[oldY-i][oldX];

				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'R') ){
						Log.d("isInCheck","true");
						return true;
					}break;
				}
			}

		}
		for (int i = 1; i<8;i++) {
			//left
			if (oldX-i >=0) {
				Piece tem = Mychessboard[oldY][oldX-i];

				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'R') ){
						Log.d("isInCheck","true");
						return true;
					}break;
				}
			}

		}
		for (int i = 1; i<8;i++) {
			//right
			if (oldX+i < 8 ) {
				Piece tem = Mychessboard[oldY][oldX+i];

				if (tem != null) {
					if (i==1){
						if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'K')) {
							Log.d("isInCheck","true");
							return true;
						}
					}
					if (tem.isWhite != this.isWhite && (tem.name.charAt(1) == 'Q' || tem.name.charAt(1) == 'R') ){
						Log.d("isInCheck","true");
						return true;
					}break;
				}
			}

		}
		// to see if will be check by a Knight
		if (oldY+2<8 && oldX+1 <8) {
			if (Mychessboard[oldY+2][oldX+1] != null) {
				if (Mychessboard[oldY+2][oldX+1].isWhite != this.isWhite && (Mychessboard[oldY+2][oldX+1].name.charAt(1) == 'N') ){
					Log.d("isInCheck","true");
					return true;
				}
			}
		}if (oldY+2<8 && oldX-1 >=0) {
			if (Mychessboard[oldY+2][oldX-1] != null) {
				if (Mychessboard[oldY+2][oldX-1].isWhite != this.isWhite && (Mychessboard[oldY+2][oldX-1].name.charAt(1) == 'N') ){
					Log.d("isInCheck","true");
					return true;
				}
			}

		}if (oldY-2>=0 && oldX+1 <8) {
			if (Mychessboard[oldY-2][oldX+1] != null) {
				if (Mychessboard[oldY-2][oldX+1].isWhite != this.isWhite && (Mychessboard[oldY-2][oldX+1].name.charAt(1) == 'N') ){
					Log.d("isInCheck","true");
					return true;
				}
			}

		}if(oldY-2>=0 && oldX-1 >=0) {
			if (Mychessboard[oldY-2][oldX-1] != null) {
				if (Mychessboard[oldY-2][oldX-1].isWhite != this.isWhite && (Mychessboard[oldY-2][oldX-1].name.charAt(1) == 'N') ){
					Log.d("isInCheck","true");
					return true;
				}
			}
		}if (oldY+1<8 && oldX+2 <8) {
			if (Mychessboard[oldY+1][oldX+2] != null) {
				if (Mychessboard[oldY+1][oldX+2].isWhite != this.isWhite && (Mychessboard[oldY+1][oldX+2].name.charAt(1) == 'N') ){
					Log.d("isInCheck","true");
					return true;
				}
			}
		}if (oldY-1>=0 && oldX+2 <8) {
			if (Mychessboard[oldY-1][oldX+2] != null) {
				if (Mychessboard[oldY-1][oldX+2].isWhite != this.isWhite && (Mychessboard[oldY-1][oldX+2].name.charAt(1) == 'N') ){
					Log.d("isInCheck","true");
					return true;
				}
			}
		}if (oldY+1<8 && oldX-2 >=0) {
			if (Mychessboard[oldY+1][oldX-2] != null) {
				if (Mychessboard[oldY+1][oldX-2].isWhite != this.isWhite && (Mychessboard[oldY+1][oldX-2].name.charAt(1) == 'N') ){
					Log.d("isInCheck","true");
					return true;
				}
			}
		}if (oldY-1>=0 && oldX-2 >=0) {
			if (Mychessboard[oldY-1][oldX-2] != null) {
				if (Mychessboard[oldY-1][oldX-2].isWhite != this.isWhite && (Mychessboard[oldY-1][oldX-2].name.charAt(1) == 'N') ) {
					Log.d("isInCheck","true");
					return true;
				}
			}
		}



		Log.d("isInCheck","false");
		return false;
	}



	/**
	 * An override toString() method
	 * @return it returns this King's name
	 */
	public String toString() {
		return name;

	}

}
