package com.example.androidchess;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidchess.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartingPageActivity extends AppCompatActivity {




    private Button recordingButton;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);



        recordingButton = (Button) findViewById(R.id.recordingButton);
        recordingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGameRecord();
            }
        });

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChessBoard();
            }
        });
    }

    public void openGameRecord(){
        Intent intent1 = new Intent(this, GameRecordActivity.class);
        startActivity(intent1);

    }
    public void openChessBoard(){
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }



}