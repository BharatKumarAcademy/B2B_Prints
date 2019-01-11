package com.login2print.b2bprintz.webServiceHandlers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpClientHandler {

    private static final String TAG = HttpClientHandler.class.getSimpleName();
    private String debugTAG = "debugger";
    public HttpClientHandler() {
    }

    public String makeServiceCall(String reqUrl, JSONObject jsonObject) {
        //String response = null;
        InputStream inputStream = null;
        String responseStr = null;
        try{
            // url where the data will be posted
            String postReceiverUrl = reqUrl;
            Log.v(TAG, "postURL: " + postReceiverUrl);

            // HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            // post header
            HttpPost httpPost = new HttpPost(postReceiverUrl);

            String json = jsonObject.toString();

            if(json!="") {
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
            }

            //*****************
            // add your data
            //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            //nameValuePairs.add(new BasicNameValuePair("firstname", "Mike"));
            //nameValuePairs.add(new BasicNameValuePair("lastname", "Dalisay"));
            //nameValuePairs.add(new BasicNameValuePair("email", "mike@testmail.com"));

            //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //*****************

            //httpPost.setHeader("accept", "json");
            //httpPost.setHeader("Content-type", "json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip");
            // execute HTTP post request
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //HttpEntity resEntity = response.getEntity();
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {

                responseStr = convertInputStreamToString(inputStream);
                Log.d("json","inputStream result"+responseStr);

                // you can add an if statement here and do other actions based on the response
            } else {
                responseStr = "Did not work!";
                Log.d("json","result"+responseStr);

            }

        }
        /*catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        catch (java.net.SocketTimeoutException e) {
            Log.d(debugTAG, "Error: ", e);
            return null;
        }
        catch (IOException e) {
            Log.d(debugTAG, "Error: ", e);
            // If the code didn't successfully get the response data, there's no point in attempting
            // to parse it.
            return null;
        }
        return responseStr;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        StringBuffer buffer = new StringBuffer();
        while((line = bufferedReader.readLine()) != null)
           // result += line;
        buffer.append(line + "\n");

        inputStream.close();
        //return result;
        return  buffer.toString();

    }
}


