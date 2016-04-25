package com.example.gwygw_000.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Revised by kevin on 3/9/2016.
 */
public class MyUtility {

    // Download an image using HTTP Get Request
    public static Bitmap downloadImageusingHTTPGetRequest(String urlString) {
        Bitmap image=null, line;

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                image = getImagefromStream(stream);
            }
            httpConnection.disconnect();
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in sendHttpGetRequest");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in sendHttpGetRequest");
            ex.printStackTrace();
        }
        return image;
    }

    // Get an image from the input stream
    private static Bitmap getImagefromStream(InputStream stream) {
        Bitmap bitmap = null;
        if(stream!=null) {
            bitmap = BitmapFactory.decodeStream(stream);
            try {
                stream.close();
            }catch (IOException e1) {
                Log.d("MyDebugMsg", "IOException in getImagefromStream()");
                e1.printStackTrace();
            }
        }
        return bitmap;
    }


    // Download JSON data (string) using HTTP Get Request
    public static String downloadJSONusingHTTPGetRequest(String urlString) {
        Log.d("enter utitly", "qewr");
        String jsonString=null;

        try {
            Log.d("create url", "adsfas");
            URL url = new URL(urlString);
            Log.d("before open connect", urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            Log.d("test 1","ads");
            httpConnection.setReadTimeout(500);
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("test2", "adsf");
                InputStream stream = httpConnection.getInputStream();
                Log.d("before get string", "adf");
                jsonString = getStringfromStream(stream);
                Log.d("after get string", "asdf");
            }
            httpConnection.disconnect();
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in downloadJSONusingHTTPGetRequest");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in downloadJSONusingHTTPGetRequest");
            ex.printStackTrace();

        }
//        finally {
//            return jsonString;
//        }
        return jsonString;
    }

    // Get a string from an input stream
    private static String getStringfromStream(InputStream stream){
        String line, jsonString=null;
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder out = new StringBuilder();
            try {
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                jsonString = out.toString();
            } catch (IOException ex) {
                Log.d("MyDebugMsg", "IOException in downloadJSON()");
                ex.printStackTrace();
            }
        }
        return jsonString;
    }

    // Load JSON string from a local file (in the asset folder)
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null, line;
        try {
            InputStream stream = context.getAssets().open(fileName);
            if (stream != null) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder out = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                json = out.toString();
            }
        } catch (IOException ex) {
            Log.d("MyDebugMsg", "IOException in loadJSONFromAsset()");
            ex.printStackTrace();
        }
        return json;
    }


    // Send json data via HTTP POST Request
    public static void sendHttPostRequest(String urlString, JSONObject json){
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoOutput(true);
            httpConnection.setChunkedStreamingMode(0);

            OutputStreamWriter out = new OutputStreamWriter(httpConnection.getOutputStream());
            out.write(json.toString());
            out.close();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d("MyDebugMsg:PostRequest", line);  // for debugging purpose
                }
                reader.close();
                Log.d("MyDebugMsg:PostRequest", "POST request returns ok");
            } else Log.d("MyDebugMsg:PostRequest", "POST request returns error");
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in sendHttpPostRequest");
            ex.printStackTrace();
        }

        if (httpConnection != null) httpConnection.disconnect();
    }

}
