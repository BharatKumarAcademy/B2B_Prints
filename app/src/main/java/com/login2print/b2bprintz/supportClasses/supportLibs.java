package com.login2print.b2bprintz.supportClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;




public class supportLibs {
        static String imageSelectorStatus=null;
        private static String MyPREFERENCES = "CookiePrefs" ;
        private static String debugTAG = "debugger";
    //share method
  /*  public static void share(Activity activity){
        String shareBody = activity.getString(R.string.app_name)+" "+activity.getString(R.string.share_tagLine)+" "+activity.getString(R.string.share_googlePlayStoreURL);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.share_subject));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
        //sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }*/

    //exit alert box
   /* public static void exitApp(final Activity activity){

        new AlertDialog.Builder(activity)
                .setIcon(R.mipmap.exit)
                //Alert Title
                .setTitle("Exit App?")
                //Alert Message
                .setMessage("Confirm Exit Application?")
                //Yes Button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                             activity.finish();

                    }

                })
                //No Button
                .setNegativeButton("No", null)
                .show();
    }*/

    //Optional method to open email client
   /* public static void sendEmail(Activity activity) {
        //Log.i("Send email", "");

        String toMail=activity.getString(R.string.mail_to);
        String CCMail=activity.getString(R.string.mail_to_CC);
        String subjectMail=activity.getString(R.string.mail_subject);
        String chooserIntentTitle=activity.getString(R.string.mail_intent_chooser_title);

        String[] TO = {toMail};
        String[] CC = {CCMail};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectMail);
        //emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            activity.startActivity(Intent.createChooser(emailIntent, chooserIntentTitle));
            //activity.finish();
            //Log.i("Finished sending email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }*/

   /* public static boolean netConnection(Activity activity){
        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean stat=true;
        if (connectivity != null)
        {
           // NetworkInfo[] info = connectivity.getAllNetworkInfo();
            /*if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }*/
            /*NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();

            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    //Toast.makeText(activity, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    //Toast.makeText(activity, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                }
            } else {
                // not connected to the internet
                stat=false;
            }

        } else{
            Toast.makeText(activity, "Error in Connectivity Service?...", Toast.LENGTH_SHORT).show();
            stat=false;
        }
        return stat;
    }*/

    /*public static void customAlertDialog(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        dialog.setContentView(R.layout.client_dialog);

        // Set dialog title
        //dialog.setTitle("Custom Dialog");

        // set values for custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
        image.setImageResource(R.mipmap.ic_launcher);


        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText(R.string.dialogTitle);


        TextView dialogDesc = (TextView) dialog.findViewById(R.id.dialogDesc);
        dialogDesc.setText(R.string.dialogDesc);
        dialogDesc.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();

       Button declineBtn = (Button) dialog.findViewById(R.id.declineButton);
        //  if decline button is clicked, close the custom dialog
        declineBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //  Close dialog;
                dialog.dismiss();
            }
        });

    }*/

    public static void openURLinBrowser(String URL, Activity activity) {
        Uri uri = Uri.parse(URL);
        PackageManager packageManager = activity.getPackageManager();
        activity.startActivity(attachBrowserIntent(uri, packageManager));
    }

    public static void openURLinBrowserNotificationClick(String URL, Context myContext) {
        Uri uri = Uri.parse(URL);
        PackageManager packageManager = myContext.getPackageManager();
        myContext.startActivity(attachBrowserIntent(uri, packageManager));
    }


    private static Intent attachBrowserIntent(Uri uri, PackageManager packageManager){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setDataAndType(uri, "text/html");
        List<ResolveInfo> list = packageManager.queryIntentActivities(browserIntent, 0);
        for (ResolveInfo resolveInfo : list) {
            String activityName = resolveInfo.activityInfo.name;
            if (activityName.contains("BrowserActivity")) {
                //if Default Browser Found Open URL Directly In Browser
                browserIntent =
                        packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);
                ComponentName comp =
                        new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);

                browserIntent.setComponent(comp);

                //Log.d("Browser Status", "Found");
            } else {
                //Log.d("Browser Status", "NotFound");
                //Other wise Open Chooser Option to choose Browser From List of Browsers
            }


        }
        browserIntent.setAction(Intent.ACTION_VIEW);
        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        browserIntent.setData(uri);
        return browserIntent;
    }

    public static String escapeBackSlash(String s){
        String escapedString;

        //first single quote

            escapedString=s.replace("\\'","'");

        //second double quote
            escapedString=escapedString.replace("\\\"","\"");

        //third back slash itself

            escapedString=escapedString.replace("\\\\","\\");

        return escapedString;

    }

    public void showToast(String msg, Context context){

                Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void hideKeyboard(){
       // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(ed1.getWindowToken(), 0);
    }

    public static boolean checkNetConnection(Activity activity){
        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean stat=true;
        if (connectivity != null)
        {
            // NetworkInfo[] info = connectivity.getAllNetworkInfo();
            /*if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }*/
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();

            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    //Toast.makeText(activity, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    //Toast.makeText(activity, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                }
            } else {
                // not connected to the internet
                stat=false;
            }

        } else{
            Toast.makeText(activity, "Error in Connectivity Service?...", Toast.LENGTH_SHORT).show();
            stat=false;
        }
        return stat;
    }


    //Check if internet is present or not
    public static boolean isConnectedToInternet(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }




    private static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

   /* public static void customImageViewerDialog(Activity activity, String imagePath) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        dialog.setContentView(R.layout.custom_image_viewer_dialog);
        dialog.setCancelable(false);
        // Set dialog title
        //dialog.setTitle("Custom Dialog");

        // set values for custom dialog components - text, image and button
        ImageView imageViewer = (ImageView) dialog.findViewById(R.id.imageViewer);
        //image.setImageResource(R.mipmap.ic_launcher);
        Picasso.with(activity).load(imagePath).placeholder(R.drawable.empty_photo).into(imageViewer);





        dialog.show();

        Button closeBtn = (Button) dialog.findViewById(R.id.closeButton);
        //  if decline button is clicked, close the custom dialog
        closeBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //  Close dialog;
                dialog.dismiss();
            }
        });

    }*/




   public static String currentAppVersion(Context passedContext){
       String currentVersion="";
       try {
           currentVersion = passedContext.getPackageManager().getPackageInfo(passedContext.getPackageName(), 0).versionName;
           Log.d("updateGetLatestVersion", "Current version " + currentVersion);
       } catch (PackageManager.NameNotFoundException e) {
           //e.printStackTrace();
           Toast.makeText(passedContext,e.getMessage(), Toast.LENGTH_SHORT).show();
       }
       return currentVersion;
   }


    public static String showExifInfoOfImage(String photoPath){
       String returnData=null;
        try {
            ExifInterface exif = new ExifInterface(photoPath);
            StringBuilder builder = new StringBuilder();

            builder.append("Date & Time: " + getExifTag(exif,ExifInterface.TAG_DATETIME) + "nn");
            builder.append("Flash: " + getExifTag(exif,ExifInterface.TAG_FLASH) + "n");
            builder.append("Focal Length: " + getExifTag(exif,ExifInterface.TAG_FOCAL_LENGTH) + "nn");
            builder.append("GPS Datestamp: " + getExifTag(exif,ExifInterface.TAG_FLASH) + "n");
            builder.append("GPS Latitude: " + getExifTag(exif,ExifInterface.TAG_GPS_LATITUDE) + "n");
            builder.append("GPS Latitude Ref: " + getExifTag(exif,ExifInterface.TAG_GPS_LATITUDE_REF) + "n");
            builder.append("GPS Longitude: " + getExifTag(exif,ExifInterface.TAG_GPS_LONGITUDE) + "n");
            builder.append("GPS Longitude Ref: " + getExifTag(exif,ExifInterface.TAG_GPS_LONGITUDE_REF) + "n");
            builder.append("GPS Processing Method: " + getExifTag(exif,ExifInterface.TAG_GPS_PROCESSING_METHOD) + "n");
            builder.append("GPS Timestamp: " + getExifTag(exif,ExifInterface.TAG_GPS_TIMESTAMP) + "nn");
            builder.append("Image Length: " + getExifTag(exif,ExifInterface.TAG_IMAGE_LENGTH) + "n");
            builder.append("Image Width: " + getExifTag(exif,ExifInterface.TAG_IMAGE_WIDTH) + "nn");
            builder.append("Camera Make: " + getExifTag(exif,ExifInterface.TAG_MAKE) + "n");
            builder.append("Camera Model: " + getExifTag(exif,ExifInterface.TAG_MODEL) + "n");
            builder.append("Camera Orientation: " + getExifTag(exif,ExifInterface.TAG_ORIENTATION) + "n");
            builder.append("Camera White Balance: " + getExifTag(exif,ExifInterface.TAG_WHITE_BALANCE) + "n");

            //TextView info = (TextView)findViewById(R.id.exifinfo);

            //info.setText(builder.toString());
            returnData = builder.toString();
            builder = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnData;
    }
    private static String getExifTag(ExifInterface exif,String tag){
        String attribute = exif.getAttribute(tag);

        return (null != attribute ? attribute : "");
    }

    public static String filterResponse(String responseFromServer){
        Log.d(debugTAG, responseFromServer);
        //filter JSON with single and double quotes
        //Remove Start and End Double Quotes From Response String
        responseFromServer = responseFromServer.substring(1);
        responseFromServer = responseFromServer.substring(0, responseFromServer.length() - 2);
        responseFromServer = responseFromServer.replace("\\\"", "\"");
        return responseFromServer;
    }
}
