package com.sos.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class add_contact extends AppCompatActivity {

    EmergencyContactHelperDB db;
    EditText name,phone,udPhone;
    Button add,delete,view;

    SQLiteOpenHelper s1;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        name=(EditText) findViewById(R.id.name);
        phone=(EditText) findViewById(R.id.phone);
        add=(Button) findViewById(R.id.add);
        delete=(Button) findViewById(R.id.delete);
        view=(Button) findViewById(R.id.view);
        udPhone=(EditText) findViewById(R.id.phn);



        db=new EmergencyContactHelperDB(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm=name.getText().toString();
                String phn=phone.getText().toString();
                if(nm.equals("") || phn.equals("")){
                    Toast.makeText(add_contact.this, "Please enter All Fields", Toast.LENGTH_SHORT).show();
                }else {
                        Boolean checkuser=db.checkusernamePassword(phn);
                        if(checkuser==false){
                            SharedPreferences getShared=getSharedPreferences("username",MODE_PRIVATE);
                            String name1=getShared.getString("userName","User");
                            Boolean insert=db.insertData(nm,phn,name1);
                            if(insert==true){
                                Toast.makeText(add_contact.this, "Contact Added Successfully...", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(add_contact.this, "Contact Adding Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(add_contact.this, "Contact Already added.. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = udPhone.getText().toString();

                if (ph.equals("")) {
                    Toast.makeText(add_contact.this, "Please Enter Phone Number first...", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean check = db.checkPhone(ph);
                    if (check == true) {
                        Boolean status=db.deletePhone(ph);
                        if(status==true){
                            Toast.makeText(add_contact.this, "Contact Deleted Successfully..", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(add_contact.this, "Contact not Deleted..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(add_contact.this, "Phone Number not exist..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void loadData(){
        SharedPreferences getShared=getSharedPreferences("username",MODE_PRIVATE);
        String name1=getShared.getString("userName","User");
        Cursor data=db.getListContents(name1);
        if(data.getCount()==0){
            Toast.makeText(this, "No Contact Added", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(data.moveToNext()){
            buffer.append("Name : "+data.getString(0)+"\n");
            buffer.append("Phone Number : "+data.getString(1)+"\n\n");
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(add_contact.this);
        builder.setCancelable(true);
        builder.setTitle("Contact Details");
        builder.setMessage(buffer.toString());
        builder.show();

    }



}