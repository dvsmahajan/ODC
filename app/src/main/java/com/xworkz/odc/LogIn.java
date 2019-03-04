package com.xworkz.odc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xworkz.odc.dto.UserDTO;
import com.xworkz.odc.sqliteHelper.SQLiteDBHelper;

public class LogIn extends AppCompatActivity {
    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        Button button=findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=findViewById(R.id.username);
                password=findViewById(R.id.password);
                SQLiteDBHelper sqLiteDBHelper=new SQLiteDBHelper(getApplicationContext());
                UserDTO userDTO=sqLiteDBHelper.checkLogin(String.valueOf(username.getText()),String.valueOf(password.getText()));
                if(userDTO!=null)
                {
                    startActivity(new Intent(LogIn.this,UserHomeScreen.class));
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Login Password is Wrong......",Toast.LENGTH_LONG).show();
            }
        });
    }
}
