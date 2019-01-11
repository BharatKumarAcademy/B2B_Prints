package com.login2print.b2bprintz.webServiceHandlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.login2print.b2bprintz.interfaceClass.TaskCompletedInterface;
import org.json.JSONObject;


//Implement Cancel Async Task Code Also Later

public class WSHttpClientAsyncTaskHandler extends AsyncTask<String, Void, String> {

    private ProgressDialog pDialog;
    private String debugTAG = "debugger";
    private Context passedContext;
    private String WebServiceURL;
    private String loaderMessage;
    JSONObject jsonObject;

    private TaskCompletedInterface mCallback;


    public WSHttpClientAsyncTaskHandler(Context passedContext, String WebServiceURL, JSONObject jsonObject, String loaderMessage) {

        this.passedContext = passedContext;
        this.WebServiceURL = WebServiceURL;
        this.jsonObject = jsonObject;
        this.loaderMessage = loaderMessage;
        mCallback = (TaskCompletedInterface) passedContext;

        Log.d("debugTAG",WebServiceURL);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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



        HttpClientHandler sh = new HttpClientHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(WebServiceURL, jsonObject);
        Log.e("bharat", "Response from url: " + jsonStr);
        return jsonStr;

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