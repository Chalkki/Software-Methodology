package com.example.androidchess;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;

public class DataSet implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String fileName = "gameRecord.txt";

    private ArrayList<Game> game_list = new ArrayList<Game>() ;

    public void save(DataSet dataset, Activity activity) throws IOException {
        File filePath = activity.getFilesDir();

//        FileOutputStream fos =  activity.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
//        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(dataset);
//            oos.close();
//        }



        FileOutputStream fos =  new FileOutputStream(new File(filePath,fileName));
        Log.d("data saved to", filePath.getAbsolutePath()+"/"+fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(dataset);
            oos.close();
        }

//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(dataset);
//            oos.close();



    }

    public DataSet load(Activity activity) {
        File filePath = activity.getApplicationContext().getFilesDir();
        File file = new File(filePath,fileName);
        DataSet dataset = new DataSet();
        if (file.length() != 0) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois;
                ois = new ObjectInputStream(fis);
                dataset = (DataSet) ois.readObject();
                ois.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return dataset;

    }


    public ArrayList<Game> getRecordStack_list() {
        return game_list;
    }


    public void setRecordStack_list(ArrayList<Game> game_list) {
        this.game_list = game_list;
    }
}
