package com.login2print.b2bprintz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.login2print.b2bprintz.Adapters.homeIconsAdapter;
import com.login2print.b2bprintz.Orders.OrdersDashboardActivity;
import com.login2print.b2bprintz.Payments.PaymentsActivity;
import com.login2print.b2bprintz.Payments.PaymentsApprovedActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String activity = "Profile1Activity";
        Intent i = null;
       /* try {
            i = new Intent(this, Class.forName(getPackageName()+"."+activity));
            startActivity(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        startActivity(i);*/
        getSupportActionBar().setIcon(R.drawable.logo);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new homeIconsAdapter(this));



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadSlider();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.Register:
                Intent regIntent = new Intent(this, RegisterActivity.class);
                startActivity(regIntent);
                return true;
            case R.id.Login:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                return true;
            case R.id.Logout:


                //supportLibs.logoutSession(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }







    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if(id == R.id.navPayments){
            Intent profileIntent = new Intent(this, PaymentsActivity.class);
            startActivity(profileIntent);
        } else if(id == R.id.navOrders){
            Intent profileIntent = new Intent(this, OrdersDashboardActivity.class);
            startActivity(profileIntent);
        }else if(id == R.id.navProfile){
           Intent profileIntent = new Intent(this, Profile1Activity.class);
           startActivity(profileIntent);
       }else if(id == R.id.navTransactions){
           Intent profileIntent = new Intent(this, TransactionsActivity.class);
           startActivity(profileIntent);
       }else if(id == R.id.navPricelist){
           Intent profileIntent = new Intent(this, PricelistActivity.class);
           startActivity(profileIntent);
       }

       /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadSlider(){
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();

        file_maps.put("Slider 1",R.drawable.slider);

        file_maps.put("Slider 2",R.drawable.slider);

        file_maps.put("Slider 3",R.drawable.slider);

        file_maps.put("Slider 4", R.drawable.slider);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }


    @Override
    protected void onStop() {

        mDemoSlider.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
