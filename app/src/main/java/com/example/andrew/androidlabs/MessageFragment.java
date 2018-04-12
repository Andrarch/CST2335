package com.example.andrew.androidlabs;



import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Andrew on 4/5/2018.
 */

public class MessageFragment extends Fragment {
    String idString="-10";
    String textString="Test";
    boolean state=true;
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            if(savedInstanceState!=null){
                textString=savedInstanceState.getString("message","Error");
                idString=(Double.toString(savedInstanceState.getDouble("id",-100)));
            }


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View result = inflater.inflate(R.layout.fragment_layout, container, false);
            TextView text=result.findViewById(R.id.fragmentMessage);
            TextView id=result.findViewById(R.id.fragmentID);
            if(getArguments()!=null){
                textString=getArguments().getString("message","Error");
                idString=(Double.toString(getArguments().getDouble("id",-100)));
                state=!getArguments().getBoolean("chatFragment",false);
            }
            text.setText(textString);
            id.setText(idString);
            Button button=result.findViewById(R.id.fragmentDelete);
            Log.i("test", "Before button listener "+state );
            button.setOnClickListener((t)->{
                if(state) {

                    Log.i("DeleteButton", "Delete Button Pressed from Phone Fragment" );
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("delete", getArguments().getDouble("id", -100));

                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }else{
                    ChatWindow.deleteEntry(String.valueOf(getArguments().getDouble("id", -100)));
                    text.setText("");
                    id.setText("");
                }
                Log.i("DeleteButton", "Delete Button Pressed from Fragment" );
            });

            return result;
        }



}
