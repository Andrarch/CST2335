package com.example.andrew.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

   // SharedPreferences preferences = this.getSharedPreferences("CST2355Lab3",MODE_PRIVATE);

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        LoadPreferences();
        Button logInButton=(Button)findViewById(R.id.LoginButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                TextView LoginText=findViewById(R.id.LoginName);
                String LoginSet = LoginText.getText().toString();

                getPreferences(MODE_PRIVATE).edit().putString("EMAIL",LoginSet).commit();


                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
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
    private void LoadPreferences(){

        //SharedPreferences preferences = this.getSharedPreferences("CST2355Lab3",MODE_PRIVATE);
        String email = getPreferences(MODE_PRIVATE).getString("EMAIL","Example@Email.com");


        TextView LoginText=findViewById(R.id.LoginName);
        LoginText.setText(email);
    }

}
