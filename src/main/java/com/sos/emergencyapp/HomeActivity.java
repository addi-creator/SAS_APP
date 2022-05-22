package com.sos.emergencyapp;

import static android.Manifest.permission.CALL_PHONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    TextView textView;
    Button add, send;
    EmergencyContactHelperDB db;
    ArrayList<String> phoneList = new ArrayList<>();

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    Button get;
    TextView locationText;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);


        textView = (TextView) findViewById(R.id.textView);
        SharedPreferences getShared = getSharedPreferences("user", MODE_PRIVATE);
        String name = getShared.getString("userName", "User");
        textView.setText("Welcome " + name);

        SharedPreferences sharedPreferences=getSharedPreferences("profileUser",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userName",name);
        editor.apply();

        add = (Button) findViewById(R.id.add);
        send = (Button) findViewById(R.id.send);
        db = new EmergencyContactHelperDB(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("username", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", name);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), add_contact.class);
                startActivity(intent);
            }
        });
      send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              loadData();
          }
      });
    }

    public void loadData(){
        SharedPreferences getShared = getSharedPreferences("user", MODE_PRIVATE);
        String name = getShared.getString("userName", "User");
        Cursor data=db.getListContents(name);
        if(data.getCount()==0){
            Toast.makeText(this, "No Contact Added", Toast.LENGTH_SHORT).show();
            return;
        }
        while(data.moveToNext()){
            phoneList.add(data.getString(1));
            }
        if(ContextCompat.checkSelfPermission(HomeActivity.this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
            sendMessage(phoneList);
        }else{
            ActivityCompat.requestPermissions(HomeActivity.this,new String[] {Manifest.permission.SEND_SMS},100);
        }

    }
    private void sendMessage(ArrayList<String> phoneList) {
        String msg="Hi, I am in trouble, please help me by reaching to below location.\n" +
                "Google Map Location: https://www.google.com/maps/search/?api=1&query=28.6835295,77.3753176\n" +
                "95, Loni Road, Between Mohannagar &, Hindon Air Force Station, Ghaziabad, Uttar Pradesh 201007, India";
        for(String phone: phoneList){
            if(!phone.equals("") && !msg.equals("")){
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phone,null,msg,null,null);
                Toast.makeText(this, ""+phone+" "+msg, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Sending SMS Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            sendMessage(phoneList);
        }else{
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id=item.getItemId();
        if(item_id==R.id.profile){
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
        }
       /* else if(item_id==R.id.setting){
            Toast.makeText(this, "This is Setting", Toast.LENGTH_SHORT).show();
        }*/
        else if(item_id==R.id.logout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        /*else if(item_id==R.id.share){
            Toast.makeText(this, "This is Share ", Toast.LENGTH_SHORT).show();
        }*/
        else if(item_id==R.id.about){
            Intent intent = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent);
        }
        else if(item_id==R.id.contact){
            Intent intent = new Intent(getApplicationContext(), ContactUs.class);
            startActivity(intent);
        }
        return true;
    }
}