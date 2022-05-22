package com.sos.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    EditText name,password,repassword,email,phone;
    Button signin,signup;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phone);
        password=(EditText) findViewById(R.id.password11);
        repassword=(EditText) findViewById(R.id.repassword);

        signin=(Button) findViewById(R.id.btnsignin);
        signup=(Button) findViewById(R.id.btnsignup);

        db=new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=name.getText().toString();
                String pass=password.getText().toString();
                String rePass=repassword.getText().toString();
                String phn=phone.getText().toString();
                String em=email.getText().toString();

                if(user.equals("") || pass.equals("")|| rePass.equals("") || phn.equals("") || em.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(rePass)){
                        Boolean checkuser=db.checkusername(user);
                        if(checkuser==false){
                            Boolean insert=db.insertData(user,pass,em,phn);
                            System.out.println(insert);
                            if(insert==true){
                                Toast.makeText(MainActivity.this, "Registered Successfully...", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("userName",user);
                                editor.apply();
                                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "User Already exists.. please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}