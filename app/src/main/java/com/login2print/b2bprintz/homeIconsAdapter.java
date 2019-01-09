package com.login2print.b2bprintz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class homeIconsAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    private String[] iconTitles;
    private String[] iconImages;
    private String[] iconBGColor;
    private String[] activities;

    private LruCache<String, Bitmap> mMemoryCache;
    private Context mContext;




    public homeIconsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext  = context;
        iconTitles  = context.getResources().getStringArray(R.array.homeIconsTitle);
        iconImages  = context.getResources().getStringArray(R.array.homeIconsImages);
        iconBGColor  = context.getResources().getStringArray(R.array.homeIconsBGColor);
        activities  = context.getResources().getStringArray(R.array.homeIconsActivities);
        //projectURLS    = context.getResources().getStringArray(R.array.homeIconsURL);

        for(int i=0; i<iconImages.length;i++) {
            int resourceID = context.getResources().getIdentifier(iconImages[i], "drawable", context.getPackageName());
            mItems.add(new Item(iconTitles[i],   resourceID, iconBGColor[i], activities[i]));
        }

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    private static class Item {
        public final String name;
        public final int drawableId;
        public final String bgColor;
        public final String Activities;

        Item(String name, int drawableId, String bgColor, String Activities) {
            this.name = name;
            this.drawableId = drawableId;
            this.bgColor = bgColor;
            this.Activities = Activities;
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        final ImageView picture;
        TextView name;
        FrameLayout frame;
        Spring spring;
        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.frame, v.findViewById(R.id.frame));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);
        frame = (FrameLayout) v.getTag(R.id.frame);

        Item item = getItem(i);

        name.setText(item.name);
        //frame.setBackgroundColor(Color.parseColor(item.bgColor));
        Picasso.with(mContext).load(item.drawableId).into(picture);
        final SpringSystem springSystem = SpringSystem.create();
        spring =  springSystem.createSpring();

        attachSpringEffect(spring, picture, i);
        return v;
    }


    private void attachSpringEffect(final Spring spring, final ImageView picture, final int position){
        picture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // When pressed start solving the spring to 1.
                        spring.setEndValue(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        //playBellSound();
                        Intent intent=null;
                        try {
                            intent = new Intent(mContext,  Class.forName(mContext.getPackageName()+"."+activities[position]));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        mContext.startActivity(intent);
                        /*switch(position){
                            case 0:
                                intent = new Intent(mContext, AboutSchoolActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(mContext, ContactUsActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(mContext, FacilitiesActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(mContext, ManagementActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 4:
                                intent = new Intent(mContext, AchievementsActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 5:
                                intent = new Intent(mContext, VisionMissionActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 6:
                                intent = new Intent(mContext, ParentEscortProfileActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 7:
                                intent = new Intent(mContext, PrincipalMessageActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 8:
                                intent = new Intent(mContext, CircularNoticeActivity.class);
                                mContext.startActivity(intent);
                                //intent.putExtra("URL",projectURLS[position]);

                                break;
                            case 9:
                                intent = new Intent(mContext, TimeTableActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 10:
                                intent = new Intent(mContext, HomeWorkActivity.class);
                                mContext.startActivity(intent);
                                break;

                            case 12:
                                intent = new Intent(mContext, PhotoGalleryActivity.class);
                                mContext.startActivity(intent);
                                break;

                        }*/



                    case MotionEvent.ACTION_CANCEL:
                        // When released start solving the spring to 0.
                        spring.setEndValue(0);
                        break;
                }
                return true;
            }
        });
        // Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.2f);
                picture.setScaleX(scale);
                picture.setScaleY(scale);
            }
        });

    }

}