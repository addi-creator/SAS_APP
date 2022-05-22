package com.sos.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot_Page extends AppCompatActivity {
    EditText email;
    Button reset;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_page);
        db=new DBHelper(this);
        email=(EditText) findViewById(R.id.emailForget);
        reset=(Button) findViewById(R.id.forget3);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=email.getText().toString();
                Boolean check=db.checkusername(em);
                if(check==true){
                    SharedPreferences sharedPreferences=getSharedPreferences("email2",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("userEmail",em);
                    editor.apply();
                    Intent intent=new Intent(getApplicationContext(),Reset.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Forgot_Page.this, "Invalid Email..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}