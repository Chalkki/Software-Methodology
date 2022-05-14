package com.example.androidchess;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.androidchess.ChessStack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static DataSet data;
    public static Game currentGame;
    public static Activity activity;

     static int oldX,oldY,newX,newY;
     static int turn = 0;
     static boolean WhiteMove = true;
     private Board Mychessboard = new Board();
private ImageAdapter myImgAdapter;
private PopupWindow popupWindow_finish;
private  PopupWindow popupWindow_promotion;
private LayoutInflater layoutInflater;
private ConstraintLayout constraintLayout;
private RelativeLayout relativeLayout;
private Dialog myDialog;
private Button ResignButton;
private Button DrawButton;
private Button AIButton;
private Button undoButton;
private Button startButton;
private Button recordingButton;
private Button backButton;
private Button finishConfirmButton;
private TextView recordTextView;
private EditText recordText;
private Button save;
private Button cancel;
private boolean AIMove = false;
private boolean justCastled=false;
private boolean justPromoted = false;
private ArrayList<Integer> laststep;
private Piece tem_pawn = null;
private Piece tem_newPos_chess = null;
private Piece tem_oldPos_chess = null;
private boolean ableToUndo = false;
private boolean readyPromote = false;
private Integer promotedImg;
private Integer oldPosImg;
private Integer newPosImg;
private boolean GameEnd = false;
int oldPos = -1,newPos =-1; //for rook while castling
ChessStack currentChessStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);
        activity=this;
        data = new DataSet();
        data = data.load(activity);
        Log.d("list just lloaded", data.getRecordStack_list().toString());
        setup_starting_page();
    }
    public void setup_starting_page(){
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main);
                start_game();
            }
        });
        recordingButton = findViewById(R.id.recordingButton);
        recordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = data.load(activity);
                openRecordPage();
//                setContentView(R.layout.activity_game_record);
            }
        });
    }
    public void start_game(){
        // initialize the attribute again
        GameEnd = false;
        WhiteMove = true;
         justCastled=false;
         justPromoted = false;
         tem_pawn = null;
         tem_newPos_chess = null;
         tem_oldPos_chess = null;
         ableToUndo = false;
         oldPos = -1;
         newPos =-1;
        Mychessboard = new Board();
        currentChessStack = new ChessStack();
        //
        myImgAdapter = new ImageAdapter(this);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(myImgAdapter);

        myDialog =new Dialog(this);
        undoButton = (Button) findViewById(R.id.undoButton);
        ResignButton = findViewById(R.id.resignButton);
        DrawButton = findViewById(R.id.drawButton);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_starting_page);
                setup_starting_page();
            }
        });
        ResignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence s;
                if(WhiteMove){
                    s = "The game is over, Black wins";
                }else{
                    s = "The game is over, White wins";
                }
                setFinishPage(s);
            }
        });
        DrawButton = findViewById(R.id.drawButton);
        DrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence s = "The game is draw";
                setFinishPage(s);
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(myImgAdapter.getImgID(position)!=R.drawable.blank_square
                        && gridview.getChildAt(position).getBackground()==null){
                    int y  = position/8;
                    int x = position%8;
                    Piece[][] chessboard = Mychessboard.getBoard();
                    if(chessboard[y][x].isWhite==WhiteMove){
                        ClearColor();
                        oldY = y;
                        oldX = x;
                        AllPossibleValidMove(position);
                        Log.d("Click on piece","Check!!");
                    }
                }
                if(gridview.getChildAt(position).getBackground()!=null){
                    int OldPos = oldY*8 + oldX;
                    newY = position/8;
                    newX = position%8;
                    move();
                    ClearColor();
                    myImgAdapter.changeImage(position,myImgAdapter.getImgID(OldPos));
                    myImgAdapter.changeImage(OldPos,R.drawable.blank_square);
                    Log.d("Move","Clicked on piece");
                }
                Log.d("Position",String.valueOf(position));
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Piece[][] chessboard = Mychessboard.getBoard();
                undo(chessboard);
            }
        });
        AIButton = findViewById(R.id.aiButton);
        AIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearColor();
                Piece[][] chessboard = Mychessboard.getBoard();
                Random randY = new Random(); //instance of random class
                Random randX = new Random();
                int upperbound = 8;
                //generate random values from 0-7
                int randomY = randY.nextInt(upperbound);
                int randomX = randX.nextInt(upperbound);
                boolean ValidMove = false;
                boolean HasPossibleMove = false;
                while(!ValidMove){
                    oldY = randomY;
                    oldX = randomX;
                    //check if the piece is not existing at the random pos
                    if (chessboard[randomY][randomX]!=null){
                        // check if the piece is valid to move at this turn
                        if (chessboard[randomY][randomX].isWhite ==WhiteMove){
                            //check if the chosen piece could move
                            for(int i = 0; i<8;i++){
                                for(int j = 0; j<8;j++){
                                    newY = i;
                                    newX = j;
                                    if(ValidInput(chessboard,WhiteMove)){
                                        HasPossibleMove = true;
                                        break;
                                    }
                                }
                            }
                            // if the chosen piece could move, then randomly move
                            while(HasPossibleMove){
                                randomY = randY.nextInt(upperbound);
                                randomX = randX.nextInt(upperbound);
                                newY = randomY;
                                newX = randomX;
                                if(ValidInput(chessboard,WhiteMove)){
                                    move();
                                    int newPos = newY*8+newX;
                                    int oldPos = oldY*8+oldX;
                                    myImgAdapter.changeImage(newPos,myImgAdapter.getImgID(oldPos));
                                    myImgAdapter.changeImage(oldPos,R.drawable.blank_square);
                                    HasPossibleMove = false;
                                    ValidMove =true;
                                }
                            }
                        }
                    }
                    // if the chosen position's piece is null or does not have any valid move, then find a new one
                    randomY = randY.nextInt(upperbound);
                    randomX = randX.nextInt(upperbound);
                }
            }
        });
//        recordButton = (Button) findViewById(R.id.recordButton);
//        recordButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                openGameRecord();
//            }
//        });


    }

    public void setAllDisable(){
        //disable the click on the grid
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setEnabled(false);
        ResignButton.setEnabled(false);
        DrawButton.setEnabled(false);
        AIButton.setEnabled(false);
        undoButton.setEnabled(false);
        backButton.setEnabled(false);
    }
public void setAllEnable(){
    //disable the click on the grid
    GridView gridview = (GridView) findViewById(R.id.gridview);
    gridview.setEnabled(true);
    ResignButton.setEnabled(true);
    DrawButton.setEnabled(true);
    AIButton.setEnabled(true);
    undoButton.setEnabled(true);
    backButton.setEnabled(true);
}
public void setFinishPage(CharSequence s){
    setAllDisable();
    layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.finish_page,null);
    constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout_main);
    ImageView imageView = findViewById(R.id.imageView);
    TextView text = container.findViewById(R.id.finish_text);
    text.setText(s);
    finishConfirmButton = (Button) container.findViewById(R.id.finish_confirm);
    finishConfirmButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            confirmToNameRecord(s);

////            data.getRecordStack_list().add(currentGame);
//            try {
////                data.save(data, container.getContext());
//                Log.d("list just saved", data.getRecordStack_list().toString());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    });
    popupWindow_finish = new PopupWindow(container,constraintLayout.getWidth(),imageView.getHeight(),false);
    popupWindow_finish.showAtLocation(constraintLayout, Gravity.NO_GRAVITY,250,260);
}

    public void confirmToNameRecord(CharSequence s){
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.name_record_page,null);
        relativeLayout = (RelativeLayout) findViewById(R.id.namerecordRelative);

        recordText = (EditText) container.findViewById(R.id.recordText);
        recordTextView = (TextView) container.findViewById(R.id.recordTextView);
        save = (Button)container.findViewById(R.id.saveButton);
        cancel = (Button)container.findViewById(R.id.cancelButton);

        Date currentTime = Calendar.getInstance().getTime();


        // reverse the game stack to a new ChessStack in order to store in Game object


        Game thisGame = new Game();
        thisGame.chessStack = reverseStack(currentChessStack);
        Iterator value = thisGame.chessStack.iterator();
        Log.d("thisGame","----------");
        for (Iterator it = value; it.hasNext(); ) {
            System.out.println(it.next());
        }

        // show alert if input name is invalid
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("This name is invalid");
        alertDialogBuilder.setMessage("Please enter a different name");
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });



        myDialog.setContentView(container);
        myDialog.show();
        Log.d("dialog just shown", "------------------");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                String gameName = recordText.getText().toString();

                if (gameName.isEmpty()){
                    Log.d("gameName", "is empty");

                    alertDialog.show();


                }
                if (!data.getRecordStack_list().toString().equals("[null]")){
                    Log.d("list", data.getRecordStack_list().toString());

                    for (Game game : data.getRecordStack_list()){
                        if (game.getName().equals(gameName)){

                                count++;
                                alertDialog.show();



                        }

                    }
                }
                if (!gameName.isEmpty() && !data.getRecordStack_list().toString().equals("[null]")&& count==0){
                    thisGame.setResult(s);
                    thisGame.setName(gameName);
                    thisGame.setDate(currentTime);
                    currentGame = thisGame;
                    Log.d("currentGame","----------");
                    Iterator value = currentGame.chessStack.iterator();
                    for (Iterator it = value; it.hasNext(); ) {
                        System.out.println(it.next());
                    }
                    data.getRecordStack_list().add(currentGame);
                    Log.d("data.getRecordStack_list() just added currentGame","----------");
                    try {
                        data.save(data, activity);
                        Log.d("list just saved", data.getRecordStack_list().toString());
                        data = data.load(activity);
                        Log.d("list just loaded", data.getRecordStack_list().toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    popupWindow_finish.dismiss();
                    myDialog.dismiss();
                    setContentView(R.layout.activity_starting_page);
                    setup_starting_page();


                }
                count=0;


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow_finish.dismiss();
                myDialog.dismiss();
                setContentView(R.layout.activity_starting_page);
                setup_starting_page();
            }
        });





    }
    public void openStartingPage(){
        Intent intent = new Intent(this, StartingPageActivity.class);
        startActivity(intent);

    }
    public void openRecordPage(){
        Intent intent = new Intent(this, GameRecordActivity.class);
        startActivity(intent);

    }
    public ChessStack reverseStack(ChessStack oldStack){
        ChessStack newStack = new ChessStack();
        while (!oldStack.isEmpty()){
            newStack.push(oldStack.pop());
        }
        return newStack;
    }


//    public void openGameRecord(){
//        Intent intent = new Intent(this, GameRecordActivity.class);
//        startActivity(intent);
//    }

    public void castle(Piece[][] chessboard,Piece temp){
        if (temp instanceof King){

            if (((King) temp).ableToCastleLeft){
                if (newY==oldY && newX==2){
                    Piece tem = chessboard[oldY][0];
                    chessboard[oldY][0] = null;
                    chessboard[oldY][3] = tem;
                    tem = null;
                    ((King) temp).castleSuccess=true;
                    newPos = oldY*8+3;
                    oldPos = oldY*8+0;

                }
            }
            if (((King) temp).ableToCastleRight){
                if (newY==oldY && newX==6){
                    Piece tem = chessboard[oldY][7];
                    chessboard[oldY][7] = null;
                    chessboard[oldY][5] = tem;
                    tem = null;
                    ((King) temp).castleSuccess=true;
                    newPos = oldY*8+7-2;
                    oldPos = oldY*8+7;
                }
            }
            if (((King) temp).castleSuccess){
                myImgAdapter.changeImage(newPos,myImgAdapter.getImgID(oldPos));
                myImgAdapter.changeImage(oldPos,R.drawable.blank_square);
                ((King) temp).castling =false;
                ((King) temp).ableToCastleLeft =false;
                ((King) temp).ableToCastleRight=false;
                ((Rook) chessboard[newPos/8][newPos%8]).move ++;
                ((King) temp).castleSuccess=false;
                justCastled = true;
            }
        }
    }

    public void lastStepInfo(Integer oldPosImg, Integer newPosImg, Integer promotedImg){
        ArrayList<Integer> moveArray = new ArrayList<>();
        if (justCastled){
            moveArray.add(2);
            moveArray.add(oldY);
            moveArray.add(oldX);
            moveArray.add(newY);
            moveArray.add(newX);
            moveArray.add(oldPos); //Rook pos
            moveArray.add(newPos); //Rook pos
            moveArray.add(oldPosImg);
            moveArray.add(newPosImg);
            currentChessStack.push(moveArray);
            Log.d("castle step added", moveArray.toString());
            justCastled=false;
        }else if (justPromoted){
            moveArray.add(3);
            moveArray.add(oldY);
            moveArray.add(oldX);
            moveArray.add(newY);
            moveArray.add(newX);
            moveArray.add(oldPosImg);
            moveArray.add(newPosImg);
            moveArray.add(promotedImg);
            currentChessStack.push(moveArray);
            Log.d("promotion step added", moveArray.toString());
            justPromoted=false;
        }else{
            moveArray.add(1);
            moveArray.add(oldY);
            moveArray.add(oldX);
            moveArray.add(newY);
            moveArray.add(newX);
            moveArray.add(oldPosImg);
            moveArray.add(newPosImg);
            currentChessStack.push(moveArray);
            Log.d("normal step added", moveArray.toString());
        }


    }

    public void undo(Piece[][] chessboard){
        if (ableToUndo){
            ArrayList laststep = (ArrayList)currentChessStack.pop();
            ClearColor();

            if (laststep.get(0).equals(1)){
                //undo normal move
                chessboard[(Integer)laststep.get(1)][(Integer)laststep.get(2)] = tem_oldPos_chess;
                chessboard[(Integer)laststep.get(3)][(Integer)laststep.get(4)] = tem_newPos_chess;
                myImgAdapter.changeImage((Integer)laststep.get(1)*8+(Integer)laststep.get(2),(Integer)laststep.get(5));
                myImgAdapter.changeImage((Integer)laststep.get(3)*8+(Integer)laststep.get(4),(Integer)laststep.get(6));
                if (tem_oldPos_chess instanceof Pawn){
                    ((Pawn) tem_oldPos_chess).move--;
                }else if(tem_oldPos_chess instanceof Rook){
                    ((Rook) tem_oldPos_chess).move--;
                }else if(tem_oldPos_chess instanceof King){
                    ((King) tem_oldPos_chess).move--;
                }

            }else if (laststep.get(0).equals(2)){
                // undo castle
                chessboard[(Integer)laststep.get(1)][(Integer)laststep.get(2)] = tem_oldPos_chess;
                chessboard[(Integer)laststep.get(3)][(Integer)laststep.get(4)] = tem_newPos_chess;

                Piece tem2 = chessboard[(Integer)laststep.get(6)/8][(Integer)laststep.get(6)%8];
                chessboard[(Integer)laststep.get(6)/8][(Integer)laststep.get(6)%8] = null;
                chessboard[(Integer)laststep.get(5)/8][(Integer)laststep.get(5)%8] = tem2;
                Log.d("King's move",String.valueOf(((King)tem_oldPos_chess).move));
                ((Rook)chessboard[(Integer)laststep.get(5)/8][(Integer)laststep.get(5)%8]).move--;
                ((King)tem_oldPos_chess).move--;
                Log.d("King's move",String.valueOf(((King)tem_oldPos_chess).move));

                tem2 = null;
                // switch back King image
                myImgAdapter.changeImage((Integer)laststep.get(1)*8+(Integer)laststep.get(2),(Integer)laststep.get(7));
                myImgAdapter.changeImage((Integer)laststep.get(3)*8+(Integer)laststep.get(4),(Integer)laststep.get(8));
                // switch back Rook image
                Integer id = myImgAdapter.getImgID(oldPos);
                myImgAdapter.changeImage((Integer)laststep.get(5),myImgAdapter.getImgID(newPos));
                myImgAdapter.changeImage((Integer)laststep.get(6),R.drawable.blank_square);
            }else if (laststep.get(0).equals(3)){
                // reverse promotion: pawn goes back to old position, and restore new position's piece
                chessboard[(Integer)laststep.get(1)][(Integer)laststep.get(2)] = tem_oldPos_chess;
                chessboard[(Integer)laststep.get(3)][(Integer)laststep.get(4)] = tem_newPos_chess;
                myImgAdapter.changeImage((Integer)laststep.get(1)*8+(Integer)laststep.get(2),(Integer)laststep.get(5));
                myImgAdapter.changeImage((Integer)laststep.get(3)*8+(Integer)laststep.get(4),(Integer)laststep.get(6));
            }
//

            if (WhiteMove){
                WhiteMove=false;
            }else{
                WhiteMove=true;
            }
            ableToUndo=false;
        }

    }

    public void move(){
        Piece[][] chessboard = Mychessboard.getBoard();
        // mark down which image at old and new position, for undo purpose
        oldPosImg = myImgAdapter.getImgID(oldY*8+oldX);
        newPosImg = myImgAdapter.getImgID(newY*8+newX);
        promotedImg = -1;
        tem_newPos_chess = chessboard[newY][newX];
        tem_oldPos_chess = chessboard[oldY][oldX];

            if (WhiteMove) {

                // if the move is valid, then we can set the piece to its new position
                //the integer turn will record the round number
                Piece temp = chessboard[oldY][oldX];
                if (temp instanceof Pawn) {
                    ((Pawn) temp).MoveConfirm();
                    if (newY == 0) {
//                        tem_pawn = chessboard[oldY][oldX];
                         Promotion( WhiteMove);

                        justPromoted = true;

                    }
                    if (chessboard[oldY][newX] != null && chessboard[oldY][newX] instanceof Pawn) {
                        Pawn pawn = (Pawn) chessboard[oldY][newX];
                        if (pawn.turn == turn - 1 && pawn.move == 1) {
                            chessboard[oldY][newX] = null;
                            int newPos = oldY*8+newX;
                            Log.d("Enpass","Enpass test in Move method");
                            myImgAdapter.changeImage(newPos,R.drawable.blank_square);
                        }
                    }
                }
                // castling
                castle(chessboard,temp);

                if(chessboard[newY][newX]!=null&&chessboard[newY][newX] instanceof King){
                    CharSequence s = "The game is over. White wins";
                    setFinishPage(s);
                    if(justPromoted){
                        popupWindow_promotion.dismiss();
                    }
                }
                chessboard[newY][newX] = temp;
                chessboard[oldY][oldX] = null;
                Mychessboard.setBoard(chessboard);



                // Find opponent's King location
                int KingLoc[] = FindKingLoc(WhiteMove, chessboard);
                int KingX = KingLoc[0];
                int KingY = KingLoc[1];
                if (Check(newY, newX, KingX, KingY,  chessboard)) {
                    Context context = getApplicationContext();
                    CharSequence text = "Black is in Check";
                    Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
                    toast.show();
                }
                WhiteMove = false;

            } else {
               Log.d("Move","Black Move");
                // if the move is valid, then we can set the piece to its new position
                Piece temp = chessboard[oldY][oldX];
                if (temp instanceof Pawn) {
                    ((Pawn) temp).MoveConfirm();
                    if (newY == 7) {
//                        tem_pawn = chessboard[oldY][oldX];
                        Promotion( WhiteMove);

                        justPromoted = true;
                    }
                    if (chessboard[oldY][newX] != null && chessboard[oldY][newX] instanceof Pawn) {
                        Pawn pawn = (Pawn) chessboard[oldY][newX];
                        if (pawn.turn == turn - 1 && pawn.move == 1){
                            chessboard[oldY][newX] = null;
                            int newPos = oldY*8+newX;
                            Log.d("Enpass","Enpass test in Move method");
                            myImgAdapter.changeImage(newPos,R.drawable.blank_square);
                        }
                    }
                }
                boolean justCastled=false;
                // castling

                castle(chessboard,temp);
                if(chessboard[newY][newX]!=null&&chessboard[newY][newX] instanceof King){
                    CharSequence s = "The game is over. Black wins";
                    setFinishPage(s);
                    if(justPromoted){
                        popupWindow_promotion.dismiss();
                    }
                }
                chessboard[newY][newX] = temp;
                chessboard[oldY][oldX] = null;
                Mychessboard.setBoard(chessboard);
                int KingLoc[] = FindKingLoc(WhiteMove, chessboard);
                int KingX = KingLoc[0];
                int KingY = KingLoc[1];
                if (Check(newY, newX, KingX, KingY,  chessboard)) {
                    Context context = getApplicationContext();
                    CharSequence text = "White is in Check";
                    Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
                    toast.show();
                }
                WhiteMove = true;
        }

        if (tem_oldPos_chess instanceof King) ((King)tem_oldPos_chess).move ++;
        if (tem_oldPos_chess instanceof Rook) {
            Log.d("Rook's move",String.valueOf(((Rook)tem_oldPos_chess).move));
            ((Rook) tem_oldPos_chess).move++;
            Log.d("Rook's move",String.valueOf(((Rook)tem_oldPos_chess).move));
        }
//        if (tem_oldPos_chess instanceof Pawn) ((Pawn)tem_oldPos_chess).move ++;
        if (!justPromoted){
            lastStepInfo(oldPosImg,newPosImg, promotedImg);
        }
        justPromoted=false;

        ableToUndo=true;
        turn++;
    }
    public static boolean ValidInput( Piece[][] chessboard, boolean WhiteMove) {
            // check regular move

            if(oldX == newX && oldY == newY) {
                Log.d("ValidInput --> (oldX == newX && oldY == newY)","false");
                return false;
            }
           // if (chessboard[oldY][oldX] == null) return false;
            //if (chessboard[oldY][oldX].isWhite !=WhiteMove) return false;
            if(chessboard[newY][newX] !=null) {
                if (chessboard[oldY][oldX].isWhite == chessboard[newY][newX].isWhite) {
                    Log.d("ValidInput --> (chessboard[newY][newX] !=null)&&(chessboard[oldY][oldX].isWhite == chessboard[newY][newX].isWhite)","false");
                    Log.d("actual",chessboard[newY][newX].toString());
                    return false;
                }
            }

            return chessboard[oldY][oldX].validMove(oldX, oldY, newX, newY, chessboard);
    }
    public void AllPossibleValidMove(int position){
     oldY = position/8;
     oldX = position%8;
        Piece[][] chessboard = Mychessboard.getBoard();
        //Log.d("PieceInAllPossibleValidMove",chessboard[oldY][oldX].toString());
    for (int i = 0; i<8;i++){
        for(int j = 0; j<8; j++){
            newY = i;
            newX = j;
            if(ValidInput(chessboard,WhiteMove)){
                GridView gridview = (GridView) findViewById(R.id.gridview);
                int new_position = i*8+j;
                gridview.getChildAt(new_position).setBackgroundColor(Color.GREEN);
            }
        }
    }
    }

    public static boolean Check(int newY, int newX, int KingX, int KingY,  Piece[][]chessboard) {
        if(chessboard[newY][newX].validMove(newX, newY, KingX, KingY, chessboard)) return true;
        return false;
    }

    /**
     * A method to find opponent's King location
     * @param WhiteMove A boolean value to show whether it is white's turn
     * @param chessboard Current chess board
     * @return Return a integer array that store opponent's King location.
     */
    public static int[] FindKingLoc (boolean WhiteMove, Piece[][]chessboard) {
        int KingX = 0;
        int KingY = 0;
        outerloop:
        for(int i = 0 ; i<8; i++) {
            for(int j = 0; j<8; j++) {
                if(chessboard[i][j]!=null && chessboard[i][j] instanceof King && chessboard[i][j].isWhite !=WhiteMove) {
                    KingY = i;
                    KingX = j;
                    break outerloop;
                }
            }
        }
        int KingLoc[] = {KingX, KingY};
        return KingLoc;
    }
    public void Promotion(boolean IsWhite) {
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.promotion,null);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout_main);
        ImageView imageView = findViewById(R.id.imageView);
        popupWindow_promotion = new PopupWindow(container,constraintLayout.getWidth(),imageView.getHeight(),false);
        popupWindow_promotion.showAtLocation(constraintLayout, Gravity.NO_GRAVITY,250,260);
        Button promotion_bishop = (Button) container.findViewById(R.id.promotion_bishop);
        Button promotion_queen = (Button) container.findViewById(R.id.promotion_queen);
        Button promotion_knight = (Button) container.findViewById(R.id.promotion_knight);
        Button promotion_rook = (Button) container.findViewById(R.id.promotion_rook);
        setAllDisable();
        promotion_queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newY*8+newX;
                if(IsWhite){
                    myImgAdapter.changeImage(position,R.drawable.white_queen);
                }else{
                    myImgAdapter.changeImage(position,R.drawable.black_queen);
                }
                Log.d("promotedImg", promotedImg.toString());
                Piece chessboard[][] = Mychessboard.getBoard();
                chessboard[newY][newX] = new Queen(IsWhite);
                setAllEnable();
                popupWindow_promotion.dismiss();
                readyPromote=true;
                justPromoted=true;
                promotedImg = myImgAdapter.getImgID(newY*8+newX);
                lastStepInfo(oldPosImg,newPosImg, promotedImg);
            }
        });
        promotion_bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newY*8+newX;
                if(IsWhite){
                    myImgAdapter.changeImage(position,R.drawable.white_bishop);
                }else{
                    myImgAdapter.changeImage(position,R.drawable.black_bishop);
                }
                Log.d("promotedImg", promotedImg.toString());
                Piece chessboard[][] = Mychessboard.getBoard();
                chessboard[newY][newX] = new Bishop(IsWhite);
                setAllEnable();
                popupWindow_promotion.dismiss();
                readyPromote=true;
                justPromoted=true;
                promotedImg = myImgAdapter.getImgID(newY*8+newX);
                lastStepInfo(oldPosImg,newPosImg, promotedImg);
            }
        });
        promotion_knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newY*8+newX;
                if(IsWhite){
                    myImgAdapter.changeImage(position,R.drawable.white_knight);
                }else{
                    myImgAdapter.changeImage(position,R.drawable.black_knight);
                }

                Log.d("promotedImg", promotedImg.toString());
                Piece chessboard[][] = Mychessboard.getBoard();
                chessboard[newY][newX] = new Knight(IsWhite);
                setAllEnable();
                popupWindow_promotion.dismiss();
                readyPromote=true;
                justPromoted=true;
                promotedImg = myImgAdapter.getImgID(newY*8+newX);
                lastStepInfo(oldPosImg,newPosImg, promotedImg);
            }
        });
        promotion_rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newY*8+newX;
                if(IsWhite){
                    myImgAdapter.changeImage(position,R.drawable.white_rook);
                }else{
                    myImgAdapter.changeImage(position,R.drawable.black_rook);
                }
                Log.d("promotedImg", promotedImg.toString());
                Piece chessboard[][] = Mychessboard.getBoard();
                chessboard[newY][newX] = new Rook(IsWhite);
                setAllEnable();
                popupWindow_promotion.dismiss();
                readyPromote=true;
                justPromoted=true;
                promotedImg = myImgAdapter.getImgID(newY*8+newX);
                lastStepInfo(oldPosImg,newPosImg, promotedImg);
            }
        });
        // if the user does not choose any chess, it will turn to queen automatically

    }

    public void ClearColor(){
        GridView gridview = (GridView) findViewById(R.id.gridview);
        for(int i = 0; i<64; i++){
            gridview.getChildAt(i).setBackground(null);
        }
    }


}
