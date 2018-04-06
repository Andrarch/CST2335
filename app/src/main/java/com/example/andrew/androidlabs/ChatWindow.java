package com.example.andrew.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    ChatDatabaseHelper databaseHelp;
    boolean chatFragment;
    SQLiteDatabase database;
    Cursor cursor;
  //  private int ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelp=new ChatDatabaseHelper(this);
        database= databaseHelp.getWritableDatabase();
        cursor=database.rawQuery("SELECT "+ChatDatabaseHelper.KEY_MESSAGE+", "+ChatDatabaseHelper.KEY_ID+ " FROM "+ChatDatabaseHelper.getTableName(),new String[]{});
        cursor.moveToFirst();
        int column=cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        while(!cursor.isAfterLast() ){
            Log.i("ChatWindow", "SQL Message:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            javaMessages.add(cursor.getString(column));
            cursor.moveToNext();
        }

        Log.i("ChatWindow", "Cursor’s  column count =" + cursor.getColumnCount() );


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        javaListView=(ListView)findViewById(R.id.listView);
        javaSendButton=(Button)findViewById(R.id.sendButton);
        ChatAdapter messageAdapter =new ChatAdapter( this );
        javaListView.setAdapter (messageAdapter);
        javaText=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ContentValues cValues=new ContentValues();
        javaSendButton.setOnClickListener(temp->{
            String tempString=javaText.getText().toString();
            javaMessages.add(tempString);
            messageAdapter.notifyDataSetChanged();
            javaText.setText("");
            cValues.put(ChatDatabaseHelper.KEY_MESSAGE,tempString);
            database.insert(ChatDatabaseHelper.getTableName(),ChatDatabaseHelper.KEY_MESSAGE,cValues);
            cursor=database.rawQuery("SELECT "+ChatDatabaseHelper.KEY_MESSAGE+", "+ChatDatabaseHelper.KEY_ID+ " FROM "+ChatDatabaseHelper.getTableName(),new String[]{});

        });
        chatFragment= (findViewById(R.id.frameLayout)!=null);

    }
    @Override
    protected void onDestroy() {
        databaseHelp.close();
        database.close();
        cursor.close();
        super.onDestroy();
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
            message.setOnClickListener((t)->{

                if(chatFragment){
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new MessageFragment();
                Bundle data = new Bundle();
                data.putString("message", getItem(position));
                data.putDouble("id", getID(position));
                data.putBoolean("chatFragment",chatFragment);


                fragmentTransaction.add(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
                }
                else{
                    Intent intent = new Intent(ChatWindow.this,MessageDetailsActivity.class);
                    intent.putExtra("message", getItem(position));
                    intent.putExtra("id", Double.valueOf(getID(position)));
                    intent.putExtra("chatFragment",chatFragment);
                    startActivityForResult(intent,position);

                }
            });
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }
        public long getID(int position){
            cursor.moveToPosition(position);
            long temp=cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));

            return(temp);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String delete;
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            delete = String.valueOf(extras.getDouble("delete"));
            database.delete(ChatDatabaseHelper.getTableName(), ChatDatabaseHelper.KEY_ID+"=?", new String[] {delete} );
            ChatAdapter messageAdapter =new ChatAdapter( this );
            messageAdapter.notifyDataSetChanged();
        }


    }
}
