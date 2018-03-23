package com.example.andrew.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class WeatherForecast extends Activity {
    ProgressBar weatherProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        weatherProgress=findViewById(R.id.weatherProgressBar);
        weatherProgress.setVisibility(View.VISIBLE);
        ForecastQuery forecast=new ForecastQuery();
        forecast.execute();

    }


    public class ForecastQuery extends AsyncTask<String, Integer, String>{
        String windSpeed,tempCurrent,tempMin,tempMax;
        Bitmap weatherImage;

        @Override
        protected String doInBackground(String... strings) {
            //http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric
            HttpURLConnection conn;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric\n");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
            }
            catch (Exception e){
                tempCurrent="Failed to connect";
                return null;
            }
            XmlPullParser parser = Xml.newPullParser();
            try{


                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
            }catch (Exception e){
                tempMin="failure to construct parser";
            }
            try {
                parser.require(XmlPullParser.START_TAG, null, "temperature");
                tempCurrent = parser.getAttributeValue(null, "value");
                publishProgress(20);
                tempMin = parser.getAttributeValue(null, "min");
                publishProgress(40);
                tempMax = parser.getAttributeValue(null, "max");
                publishProgress(60);
            }catch(Exception e){
                    tempMax="failure to get temps";
            }
            try{
                parser.require(XmlPullParser.START_TAG,null,"speed");
                windSpeed=parser.getAttributeValue(null,"value");
                publishProgress(80);
            }catch (Exception e){
                windSpeed="failure to get wind speed";
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            weatherProgress.setVisibility(View.VISIBLE);
            weatherProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView tempText;
            tempText=findViewById(R.id.weatherCurTemp);
            tempText.setText(tempCurrent);
            tempText=findViewById(R.id.weatherMaxTemp);
            tempText.setText(tempMax);
            tempText=findViewById(R.id.weatherMinTemp);
            tempText.setText(tempMin);
            tempText=findViewById(R.id.weatherWind);
            tempText.setText(windSpeed);
            weatherProgress.setVisibility(View.INVISIBLE);


        }
    }

}
