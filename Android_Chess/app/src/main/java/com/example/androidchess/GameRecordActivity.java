package com.example.androidchess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidchess.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class GameRecordActivity extends AppCompatActivity {
    public static Game currentPlaying;
    private ListView recordlistView;
    private Button backbutton;
    private Button sortTime;
    private Button sortName;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter arrayAdapter;
    private ArrayList<Game> recordStackList;
    private ArrayList<Game> finalRecordStackList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_record);

        backbutton = (Button)findViewById(R.id.gobackButton);
        sortTime = (Button)findViewById(R.id.dateButton);
        sortName = (Button)findViewById(R.id.nameButton);
        recordlistView =(ListView)findViewById(R.id.recordList);


        recordStackList = new ArrayList<>();
        Log.d("data to list...",MainActivity.data.getRecordStack_list().toString());
        recordStackList = MainActivity.data.load(MainActivity.activity).getRecordStack_list();
        Log.d("data to list...",MainActivity.data.getRecordStack_list().toString());

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,recordStackList);
        recordlistView.setAdapter(arrayAdapter);
        finalRecordStackList =  (ArrayList<Game>) recordStackList.clone();


        recordlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentPlaying = finalRecordStackList.get(i);
                openRecordGame();

            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sortName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           sortByName();
           finalRecordStackList =  (ArrayList<Game>) recordStackList.clone();
        arrayAdapter.notifyDataSetChanged();
            }
        });
        sortTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sortByDate();
                finalRecordStackList =  (ArrayList<Game>) recordStackList.clone();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    public void openRecordGame(){
        Intent intent = new Intent(this, Play_record_Activity.class);
        startActivity(intent);
    }


    public void sortByDate(){
Game.sort_type = 1;
Collections.sort(recordStackList);
    }

    public void sortByName()  {
Game.sort_type = 0;
Collections.sort(recordStackList);
    }
}
