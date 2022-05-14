package com.example.androidchess;
/**
 * The board class
 * @author Jiaqi He, Weizheng Liang
 *
 */
public class Board {
	/**
	 * @param chessboard Chess board for game
	 */
private Piece [][] chessboard;
/**
 * A default constructor that will create an array named chess board, and
 * store the pieces in it. The constructor will initialize the board after 
 * being calling.
 */
public Board () {
	chessboard = new Piece[8][8];
	initializeBoard();
}
/**
 * Getter for chess board
 * @return Get the chess board
 */
public Piece[][] getBoard() {
	return chessboard;
}
/**
 * Setter for chess board
 * @param chessboard the current chess board
 */
public void setBoard(Piece[][] chessboard) {
	this.chessboard = chessboard;
}
/**
 * The initialize board function. 
 */
public void initializeBoard (){
	for(int i = 0; i<8;i++) {
		for(int j = 0 ; j<8;j++) {
			chessboard[i][j] =null;
		}
	}
	//initialize the positions of pawns on each side
	for(int k = 0 ; k <8; k++) chessboard[1][k] = new Pawn(false);
	for(int k = 0 ; k <8; k++) chessboard[6][k] = new Pawn(true);
	//initialize the positions of main pieces on each side
	chessboard[0][0] = new Rook(false);
	chessboard[0][1] = new Knight(false);
	chessboard[0][2] = new Bishop(false);
	chessboard[0][3] = new Queen(false);
	chessboard[0][4] = new King(false);
	chessboard[0][5] = new Bishop(false);
	chessboard[0][6] = new Knight(false);
	chessboard[0][7] = new Rook(false);
	//
	chessboard[7][0] = new Rook(true);
	chessboard[7][1] = new Knight(true);
	chessboard[7][2] = new Bishop(true);
	chessboard[7][3] = new Queen(true);
	chessboard[7][4] = new King(true);
	chessboard[7][5] = new Bishop(true);
	chessboard[7][6] = new Knight(true);
	chessboard[7][7] = new Rook(true);
}

}
