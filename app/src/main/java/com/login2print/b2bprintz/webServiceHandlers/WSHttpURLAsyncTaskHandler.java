package com.login2print.b2bprintz.webServiceHandlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.login2print.b2bprintz.interfaceClass.TaskCompletedInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



//Implement Cancel Async Task Code Also Later
//at com.android.okhttp.internal.huc.HttpURLConnectionImpl.getInputStream
//File Not Found Exception Occurs Some Times Check Becas of  line 102
//Beware Response from server comes with start and end double quotes (besure to replace quotes with blank)
//Time Out Exception add
public class WSHttpURLAsyncTaskHandler extends AsyncTask<String, Void, String> {

    private ProgressDialog pDialog;
    private String debugTAG = "debugger";
    private Context passedContext;
    private String WebServiceURL,loaderMessage;
    private TaskCompletedInterface mCallback;
    private String sendRequestType;

    public WSHttpURLAsyncTaskHandler(Context passedContext, String WebServiceURL, String sendRequestType, String loaderMessage) {

        this.passedContext = passedContext;
        this.WebServiceURL = WebServiceURL;
        this.loaderMessage = loaderMessage;
        this.sendRequestType = sendRequestType;


        mCallback = (TaskCompletedInterface) passedContext;

        Log.d(debugTAG,WebServiceURL);

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(passedContext);
        pDialog.setMessage(loaderMessage);
        pDialog.setCancelable(false);
        pDialog.show();

        /*customProgressDialog = new Dialog(passedActivity);
        customProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customProgressDialog.setContentView(R.layout.progress_dialog);
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();*/

    }

    @Override
    protected String doInBackground(String... params) {


        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String responeFromServer = null;

        try {

            URL url = new URL(WebServiceURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(sendRequestType);
            urlConnection.setConnectTimeout(15000); //set timeout to 15 seconds
            // adding WSParams Var post data here serially
           /*for (Map.Entry<String,String> entry : WSParams .entrySet()) {
               String key= entry.getKey();
               String val= entry.getValue();
               urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
               Log.d("KEYY",key);
               Log.d("VALL",val);
            }*/

            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            responeFromServer = buffer.toString();
            return responeFromServer;

        } catch (java.net.SocketTimeoutException e) {
            Log.d(debugTAG, "Error: ", e);
            return null;
        }
        catch (IOException e) {
            Log.d(debugTAG, "Error: ", e);
            // If the code didn't successfully get the response data, there's no point in attempting
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(debugTAG, "Error Closing Stream", e);
                }
            }
        }



    }

    @Override
    protected void onPostExecute(String responseResult) {
        super.onPostExecute(responseResult);
        // Dismiss the progress dialog
       if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        /*if(customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }*/


           mCallback.onTaskComplete(responseResult);


    }

}


//Implement Cancel Async Task Code Also Later