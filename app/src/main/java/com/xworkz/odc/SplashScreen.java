package com.xworkz.odc;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.xworkz.odc.dto.UserDTO;
import com.xworkz.odc.sqliteHelper.SQLiteDBHelper;

import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Progressbar
        progressBar=findViewById(R.id.progressId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                processing();
                startApp();
                finish();
            }
        }).start();
    }

    private void processing() {
        for(int process=0;process<=100;process++)
        {
            try {
                Thread.sleep(100);
                progressBar.setProgress(process);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void startApp(){
        SQLiteDBHelper sqLiteDBHelper=new SQLiteDBHelper(this);
        List<UserDTO> list=sqLiteDBHelper.getAllUser();
        Log.d("size","List size "+list.size());
        Intent intent;
        if(list.size()>0){
            intent=new Intent(SplashScreen.this, LogIn.class);
        }else{
            intent=new Intent(SplashScreen.this, RegisterUser.class);
        }


        startActivity(intent);
    }
}
