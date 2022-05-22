package com.sos.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    TextView e1;
    EditText e2,e3;
    Button b1;

    StringBuffer buffer=new StringBuffer();
    StringBuffer buffer1=new StringBuffer();
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        e1=(TextView) findViewById(R.id.n);
        e2=(EditText) findViewById(R.id.e);
        e3=(EditText) findViewById(R.id.c1);

        /*b1=(Button) findViewById(R.id.updateBtn);*/

        db=new DBHelper(this);

        SharedPreferences getShared = getSharedPreferences("profileUser", MODE_PRIVATE);
        String name = getShared.getString("userName", "User");
        e1.setText("Name : " + name);
        loadData();
        e2.setText(buffer.toString());
        e3.setText(buffer1.toString());

        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=e1.getText().toString();
                String email=e2.getText().toString();
                String phone=e3.getText().toString();
                if(name.equals("") || email.equals("")|| phone.equals("")){
                    Toast.makeText(Profile.this, "Please enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                            Boolean update=db.updateData(name,email,phone);
                            if(update==true){
                                Toast.makeText(Profile.this, "Updated Successfully...", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Profile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                }
        });*/
    }

    private void loadData() {
        SharedPreferences getShared = getSharedPreferences("profileUser", MODE_PRIVATE);
        String name = getShared.getString("userName", "User");
        Cursor data=db.getListContents(name);
        if(data.getCount()==0){
            Toast.makeText(this, "User not exist", Toast.LENGTH_SHORT).show();
            return;
        }
        while(data.moveToNext()){
            buffer.append("Email : "+data.getString(2));
            buffer1.append("Phone Number : "+data.getString(3));
        }

    }

}