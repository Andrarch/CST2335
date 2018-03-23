package com.example.andrew.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
        String windSpeed,tempCurrent,tempMin,tempMax,bitMap;
        Bitmap weatherImage;

        @Override
        protected String doInBackground(String... strings) {
            //http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric
            HttpURLConnection conn;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
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
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();
                tempCurrent=parser.getName();
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    // Starts by looking for the entry tag
                    if (name.equalsIgnoreCase("temperature")) {

                            tempCurrent = "Current: "+parser.getAttributeValue(null, "value");
                            publishProgress(20);
                            tempMin = "Min: "+parser.getAttributeValue(null, "min");
                            publishProgress(40);
                            tempMax = "Max: "+parser.getAttributeValue(null, "max");
                            publishProgress(60);
                    }

                    if (name.equalsIgnoreCase("speed")){
                            windSpeed="Speed: "+parser.getAttributeValue(null,"value");
                    }

                    if (name.equalsIgnoreCase("clouds")) {

                        bitMap = "http://openweathermap.org/img/w/sunny.png";
                        publishProgress(80);
                    }

                    parser.next();




                    }

                }catch(Exception e){
                    tempMax="failure to get temps";
            }
            setBitmap(bitMap);
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
    public void setBitmap(String url){
        ImageView imageViewer=findViewById(R.id.weatherImage);
        imageViewer.setImageBitmap(HttpUtils.getImage(url));


    }

    /**
     * @author Terry E-mail: yaoxinghuo at 126 dot com
     * @version create: 2010-10-21 ??01:40:03
     */
    static class HttpUtils {
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public static Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }

}
