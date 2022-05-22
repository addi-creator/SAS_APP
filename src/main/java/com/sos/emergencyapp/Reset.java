package com.sos.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Reset extends AppCompatActivity {

    EditText pass1,pass2;
    Button reset;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        pass1=(EditText) findViewById(R.id.newPass);
        pass2=(EditText) findViewById(R.id.newPass1);
        reset=(Button) findViewById(R.id.resetChange);
        db=new DBHelper(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass1=pass1.getText().toString();
                String newPass2=pass2.getText().toString();
                if(newPass1.equals("") || newPass2.equals("")){
                    Toast.makeText(Reset.this, "Please enter All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(newPass1.equals(newPass2)){
                        SharedPreferences getShared = getSharedPreferences("email2", MODE_PRIVATE);
                        String email = getShared.getString("userEmail", "email");
                        String newPassword=pass1.getText().toString();

                        Boolean checkPassUpdate=db.updatePassword(email,newPassword);
                        if(checkPassUpdate==true){
                            Toast.makeText(Reset.this, "Password Updated Successfully..", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Reset.this, "Password Not Updated", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(Reset.this, "Passwords not matching", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
}