package com.login2print.b2bprintz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class VisitingCardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make activity full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //connect layout
        setContentView(R.layout.activity_visiting_cards);

       /* if(supportLibs.isConnectedToInternet(this)) {
            String appCurrentVersion = supportLibs.currentAppVersion(this);
            new printers.choice.in.supportClasses.GetLatestVersion(this, appCurrentVersion).execute();
        }else {
            showSplash(Boolean.valueOf(getString(R.string.ShowSplash)));
        }*/
        //showSplash(Boolean.valueOf(getString(R.string.ShowSplash)));
    }



    private void showSplash(Boolean showSplash) {


        if (showSplash) {
            final Handler handler = new Handler();
            final Runnable runner = new Runnable() {
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };

            handler.postDelayed(runner,getResources().getInteger(R.integer.SplashHoldTime)); // 2 sec

        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

    }
}
