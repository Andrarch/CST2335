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
    

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Button logInButton=(Button)findViewById(R.id.LoginButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                TextView Login=(TextView) findViewById(R.id.LoginName);
                String save=Login.getText().toString();
                editor.putString("EMAIL", Login.getText().toString());
                editor.commit();

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

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String Login2 = sharedPreferences.getString("EMAIL", "email@domain.com");
        TextView Login=(TextView) findViewById(R.id.LoginName);
        Login.setText(Login2);
    }

}
