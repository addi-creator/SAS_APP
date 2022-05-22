package com.sos.emergencyapp;

import static android.content.SharedPreferences.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    DBHelper db;
    EditText name,password1;
    Button signin1,reg,forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name=(EditText) findViewById(R.id.name2);
        password1=(EditText) findViewById(R.id.password1);

        signin1=(Button) findViewById(R.id.btnsignin1);
        reg=(Button) findViewById(R.id.signup);
        forget=(Button) findViewById(R.id.forgotbtn);

        db=new DBHelper(this);

        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=name.getText().toString();
                String pass=password1.getText().toString();
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter all the Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass=db.checkusernamePassword(user,pass);
                    if(checkuserpass==true){
                        Toast.makeText(LoginActivity.this, "Sign in Successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("userName",user);
                        editor.apply();

                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Forgot_Page.class);
                startActivity(intent);
            }
        });
    }
}