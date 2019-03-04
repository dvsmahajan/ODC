package com.xworkz.odc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xworkz.odc.sqliteHelper.SQLiteDBHelper;

public class RegisterUser extends AppCompatActivity {

    EditText userName;
    EditText password;
    EditText email;
    Button sumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        sumbit=findViewById(R.id.submitUser);
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=findViewById(R.id.user_name);
                password=findViewById(R.id.password);
                email=findViewById(R.id.email);
                if(userName!=null && password!=null && email!=null && userName.length()>0 && password.length()>0 && email.length()>0 ){
                    SQLiteDBHelper sqLiteDBHelper= new SQLiteDBHelper(getApplicationContext());
                    boolean res=sqLiteDBHelper.registerUser(String.valueOf(userName.getText()),String.valueOf(password.getText()),String.valueOf(email.getText()));
                    if(res){
                        startActivity(new Intent(RegisterUser.this,UserHomeScreen.class));
                        finish();
                        Toast.makeText(getApplicationContext(),"Registered Successfully...",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(RegisterUser.this,"Please try again......",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(RegisterUser.this,"Please Fill All Details........................",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
