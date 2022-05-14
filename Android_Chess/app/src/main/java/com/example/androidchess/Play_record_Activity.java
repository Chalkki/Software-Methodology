package com.example.androidchess;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Play_record_Activity extends MainActivity {
    private ImageAdapter myImgAdapter;
    private ConstraintLayout constraintLayout;
    private Button nextButton;
    private Button backButton;
    private ImageView imageView;
    private GridView Board;
    private Board Mychessboard = new Board();
    private ChessStack currentChessStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_record_page);
        nextButton = (Button) findViewById(R.id.nextButton);
        backButton = (Button)findViewById(R.id.BackButton);
        imageView = (ImageView) findViewById(R.id.imageView2);
        Piece[][] chessboard = Mychessboard.getBoard();
        currentChessStack = (ChessStack) GameRecordActivity.currentPlaying.chessStack.clone();
        start_game();


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Game Ends");
        alertDialogBuilder.setMessage(GameRecordActivity.currentPlaying.getResult());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentChessStack.isEmpty()){
                    nextButton.setClickable(true);
                    readMove(chessboard, alertDialog);
                }else{
                    // this is the last move

                    alertDialog.show();


                }

            }

        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });




    }
    @Override
    public void start_game() {

        Mychessboard = new Board();
        myImgAdapter = new ImageAdapter(this);
        Board = (GridView) findViewById(R.id.gridViewRcord);
        Board.setAdapter(myImgAdapter);
        Board.setClickable(false);

    }


    public void openStartingPage(){
        Intent intent = new Intent(this, StartingPageActivity.class);
        startActivity(intent);
    }

    public void readMove(Piece[][] chessboard, AlertDialog alertDialog){
            if (!currentChessStack.isEmpty()){
                ArrayList<Integer> currentMove = (ArrayList<Integer>) currentChessStack.pop();
                if (currentMove.get(0).equals(1)) {

                    myImgAdapter.changeImage((Integer) currentMove.get(3) * 8 + (Integer) currentMove.get(4), (Integer) currentMove.get(5));
                    myImgAdapter.changeImage((Integer) currentMove.get(1) * 8 + (Integer) currentMove.get(2), R.drawable.blank_square);


                } else if (currentMove.get(0).equals(2)) {

                    // switch back King image
                    myImgAdapter.changeImage((Integer) currentMove.get(3) * 8 + (Integer) currentMove.get(4), (Integer) currentMove.get(7));
                    myImgAdapter.changeImage((Integer) currentMove.get(1) * 8 + (Integer) currentMove.get(2), R.drawable.blank_square);
                    // switch back Rook image
                    myImgAdapter.changeImage((Integer) currentMove.get(6), myImgAdapter.getImgID((Integer) currentMove.get(5)));
                    myImgAdapter.changeImage((Integer) currentMove.get(5), R.drawable.blank_square);
                } else if (currentMove.get(0).equals(3)) {

                    myImgAdapter.changeImage((Integer) currentMove.get(3) * 8 + (Integer) currentMove.get(4), (Integer) currentMove.get(7));
                    myImgAdapter.changeImage((Integer) currentMove.get(1) * 8 + (Integer) currentMove.get(2), R.drawable.blank_square);
                }
            }else {
                //        show alert dialog : game result.
                nextButton.setClickable(false);
                alertDialog.show();

            }

        }








}