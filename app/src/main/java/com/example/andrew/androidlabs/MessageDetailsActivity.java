package com.example.andrew.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MessageFragment();
        Bundle data = new Bundle();
        data.putString("message", getIntent().getStringExtra("message"));
        data.putDouble("id", getIntent().getDoubleExtra("id",-100));
        data.putBoolean("chatFragment",getIntent().getBooleanExtra("chatFragment",true));
        fragment.setArguments(data);

        fragmentTransaction.add(R.id.detailsFrame, fragment);
        fragmentTransaction.commit();


    }
}
