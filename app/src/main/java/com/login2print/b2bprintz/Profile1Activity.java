package com.login2print.b2bprintz;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile1Activity extends AppCompatActivity {

    private TextView mTextMessage;
    private ImageView profileImage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile_photo:
                   // mTextMessage.setText(R.string.title_home);
                    profileImage.setImageResource(R.drawable.profile2);
                    return true;
                case R.id.navigation_profile:
                    //mTextMessage.setText(R.string.title_dashboard);
                    profileImage.setImageResource(R.drawable.profile1);
                    return true;
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        //mTextMessage = (TextView) findViewById(R.id.message);
        profileImage = (ImageView) findViewById(R.id.profileImage);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       /*mTextMessage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });*/
    }

}
