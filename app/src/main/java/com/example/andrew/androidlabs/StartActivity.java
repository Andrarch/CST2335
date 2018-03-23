package com.example.andrew.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {


    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 //   Intent data;
                //int requestCode=0,responseCode=0;
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);

                startActivityForResult(intent,50);

            }});


        Button weatherButton=(Button)findViewById(R.id.startWeatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //   Intent data;
                //int requestCode=0,responseCode=0;
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);

                startActivity(intent);

            }});


        Button chatButton=(Button)findViewById(R.id.chatButton);
            chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME,"User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);

                startActivity(intent);
            }});
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
       if(requestCode==50) Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        if (responseCode==Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(StartActivity.this, messagePassed, Toast.LENGTH_LONG); //this is the ListActivity
            toast.show(); //display your message box
        }
    }

}
