package com.example.andrew.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    ListView javaListView;
    Button javaSendButton;
    ArrayList<String> javaMessages=new ArrayList<String>();
    AutoCompleteTextView javaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        javaListView=(ListView)findViewById(R.id.listView);
        javaSendButton=(Button)findViewById(R.id.sendButton);
        ChatAdapter messageAdapter =new ChatAdapter( this );
        javaListView.setAdapter (messageAdapter);
        javaText=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        javaSendButton.setOnClickListener(temp->{
            String tempString=javaText.getText().toString();
            javaMessages.add(tempString);
            messageAdapter.notifyDataSetChanged();
            javaText.setText("");
        });


    }
    public class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){

            return (javaMessages.size());
        }
        public String getItem(int position){
            return(javaMessages.get(position));
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.textView);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }
        public long getID(int position){
            return(position);
        }

    }
}
