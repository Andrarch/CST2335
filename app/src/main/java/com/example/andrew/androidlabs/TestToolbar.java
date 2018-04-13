package com.example.andrew.androidlabs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    String message="You selected Item 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Andrew James Archibald", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        int id=mi.getItemId();

        switch (id){
            case R.id.action_one:
                Log.d("ToolBar", "option 1");
                Snackbar.make(getWindow().getDecorView().getRootView(),message, Snackbar.LENGTH_LONG)
                        .setAction("action",null).show();
                break;


            case R.id.action_two:
                Log.d("ToolBar", "option 2");
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.goBack);
// Add the buttons
                builder.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.action_three:
                Log.d("ToolBar", "option 3");
                Dialog dialogAlert = createDialog();
                dialogAlert.show();


                break;
            case R.id.action_four:
                Log.d("ToolBar", "option 4");
                Toast toast = Toast.makeText(TestToolbar.this, "Version 1.0 by Andrew Archibald", Toast.LENGTH_LONG); //this is the ListActivity
                toast.show();
                break;
        }

        return true;
    }
    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View inflated=inflater.inflate(R.layout.custom_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflated)
                // Add action buttons
                .setPositiveButton(R.string.setMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        TextView text=inflated.findViewById(R.id.customText);
                        if(text!=null){
                        message=text.getText().toString(); }
                    }
                })
                .setNegativeButton(R.string.cancelMessage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }});
        return builder.create();
    }


}
