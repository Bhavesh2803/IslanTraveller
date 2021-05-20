package com.islantraveller.Dashboard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.Manifest;
import android.app.slice.Slice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.islantraveller.Calendar.CalendarCustomView;
import com.islantraveller.Calendar.EventObjects;
import com.islantraveller.ContactUs.ContactUs;
import com.islantraveller.Dashboard.Activity.Model.CategoryAll.CategoryAllDTO;
import com.islantraveller.Dashboard.Activity.Model.DealAll.Datum;
import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Dashboard.Activity.Model.Locations.LocationsList;
import com.islantraveller.Dashboard.Activity.Model.LogoutDTO;

import com.islantraveller.Dashboard.Activity.Model.SavedEvents.SavedDealsDTO;
import com.islantraveller.Dashboard.Activity.manager.CategoryAllManager;
import com.islantraveller.Dashboard.Activity.manager.DealAllManager;
import com.islantraveller.Dashboard.Activity.manager.GetAllDealParameter;
import com.islantraveller.Dashboard.Activity.manager.LogOutManager;
import com.islantraveller.Dashboard.Activity.manager.SaveEventDealAllManager;
import com.islantraveller.Dashboard.Activity.manager.UpdateLatLongDTO;
import com.islantraveller.Dashboard.Activity.manager.UpdateLatLongParameter;
import com.islantraveller.Dashboard.Adapter.CategoryAdapter;
import com.islantraveller.Dashboard.Adapter.EventAdapter;
import com.islantraveller.Dashboard.Adapter.LocationClickListner;
import com.islantraveller.Dashboard.Adapter.SavedEventAdapter;
import com.islantraveller.DealInfo.DealInfo;
import com.islantraveller.Login.LoginActivity;
import com.islantraveller.Login.manager.LoginManager;
import com.islantraveller.MyFavourite.MyFavourite;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.basic.BaseActivity;
import com.islantraveller.PrivacyPolicy.PrivacyPolicy;
import com.islantraveller.R;
import com.islantraveller.Dashboard.Adapter.WeatherAdapter;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;
import com.islantraveller.webviews.WebviewTermsCondition;
import com.squareup.picasso.Picasso;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, WheelPicker.OnItemSelectedListener, View.OnClickListener, ApiCallback.LogOutManagerCallback, ApiCallback.CategoryAllManagerCallback, ApiCallback.DealAllManagerCallback, ApiCallback.UserSaveEventListManagerCallback, LocationClickListner, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    RecyclerView rcyl_weather;

    RecyclerView rcyl_event;
    RelativeLayout rl_calendar;
    RelativeLayout rl_saved_calendar;
    RelativeLayout seekbar_layout;

    LinearLayout setting_options;
    LinearLayout ll_category;
    LinearLayout ll_home, ll_saved_event, ll_weather, ll_screen_2, ll_indicatore, event_listing;
    ImageView iv_home, iv_saved_event, iv_category, iv_setting, iv_setting_selected, iv_location;
    TextView tv_home, tv_category, tv_saved_event, tv_setting, tv_location, tv_date, tv_calendar_done, tv_hide_cal, tv_sel_location, tv_sel_date_range;
    final int REQUEST_CODE_ASK_PERMISSIONS = 11;
    Date start, end;
    LinearLayout layoutCalender;
    LinearLayout layoutSavedCalender;

    Date initialDate, lastDate;
    LinearLayout menu_button;
    TextView km_text;
    DrawerLayout drawer;
    boolean is_weather_show = false;
    private WheelPicker wheelCenter;
    private Integer gotoBtnItemIndex;
    String token = "";
    Context ct = this;
    DealAllDTO dealAllDTO;
    IndicatorSeekBar seekbar;
    boolean is_from_date = false;

    String lattitude = "", longitude = "";
    String cat_id = "";
    String start_date = "", end_date = "";
    String show_start_date = "", show_end_date = "";
    int prog = 25;
    private GoogleApiClient mGoogleApiClient;
    GifImageView gif_ltr;
    SlidingDrawer slidingDrawer;

    int left_right_count ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu_button = (LinearLayout) findViewById(R.id.menu_button);
        seekbar = (IndicatorSeekBar) findViewById(R.id.seekbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
        gif_ltr = (GifImageView) findViewById(R.id.gif_ltr);

        String l_t_r = AppSession.getValue(ct,Constants.LEFT_RIGHT_COUNT);
        if(l_t_r == null){
            left_right_count = 0;
            gif_ltr.setVisibility(View.VISIBLE);
        }
        else {
            left_right_count = Integer.parseInt(l_t_r);
            if(left_right_count < 6){
                gif_ltr.setVisibility(View.VISIBLE);
            }
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if(left_right_count < 6){
                    gif_ltr.setVisibility(View.VISIBLE);
                }


                AppSession.save(ct, Constants.LEFT_RIGHT_COUNT,""+left_right_count );
                // Do whatever you want here
                //iv_setting_selected.setVisibility(View.GONE);
                // iv_setting.setVisibility(View.VISIBLE);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                left_right_count++;
                gif_ltr.setVisibility(View.GONE);
                // Do whatever you want here
                // iv_setting_selected.setVisibility(View.VISIBLE);
                // iv_setting.setVisibility(View.GONE);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        try {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            //navigationView.setNavigationItemSelectedListener(this);

            View headerview = navigationView.getHeaderView(0);
            init(headerview);
            initDrawerOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openSideMenuDrawer();
                if (slidingDrawer.isOpened()) {
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                    slidingDrawer.animateClose();
                } else {
                    iv_setting_selected.setVisibility(View.VISIBLE);
                    iv_setting.setVisibility(View.GONE);
                    slidingDrawer.animateOpen();
                }


            }
        });

        seekbar_layout = (RelativeLayout) findViewById(R.id.seekbar_layout);
        event_listing = (LinearLayout) findViewById(R.id.event_listing);
        setting_options = (LinearLayout) findViewById(R.id.setting_options);
        ll_indicatore = (LinearLayout) findViewById(R.id.ll_indicatore);
        ll_screen_2 = (LinearLayout) findViewById(R.id.ll_screen_2);
        ll_weather = (LinearLayout) findViewById(R.id.ll_weather);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_saved_event = (LinearLayout) findViewById(R.id.ll_saved_event);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_saved_event = (ImageView) findViewById(R.id.iv_saved_event);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting_selected = (ImageView) findViewById(R.id.iv_setting_selected);
        iv_location = (ImageView) findViewById(R.id.iv_location);
        iv_category = (ImageView) findViewById(R.id.iv_category);
        tv_calendar_done = (TextView) findViewById(R.id.tv_calendar_done);
        tv_hide_cal = (TextView) findViewById(R.id.tv_hide_cal);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_saved_event = (TextView) findViewById(R.id.tv_saved_event);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_date = (TextView) findViewById(R.id.tv_date);
        km_text = (TextView) findViewById(R.id.km_text);
        tv_sel_location = (TextView) findViewById(R.id.tv_sel_location);
        tv_sel_date_range = (TextView) findViewById(R.id.tv_sel_date_range);
        rcyl_weather = (RecyclerView) findViewById(R.id.rcyl_weather);

        rcyl_event = (RecyclerView) findViewById(R.id.rcyl_event);
        rl_calendar = (RelativeLayout) findViewById(R.id.rl_calendar);
        rl_saved_calendar = (RelativeLayout) findViewById(R.id.rl_saved_calendar);

        ll_category = (LinearLayout) findViewById(R.id.ll_category);


        token = AppSession.getValue(ct, Constants.ACCESS_TOKEN);

        tv_hide_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_saved_calendar.setVisibility(View.GONE);
            }
        });
        tv_calendar_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_calendar.setVisibility(View.GONE);
                if (start_date != null && !start_date.equalsIgnoreCase("")) {
                    // prog = 25;
                    //lattitude = "";
                    // longitude = "";
                    cat_id = "";

                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MMM");


                    try {
                        Date startDate = new Date();
                        Date endDate = new Date();
                        startDate = dateFormat1.parse(show_start_date);
                        endDate = dateFormat1.parse(show_end_date);

                        if (startDate.after(endDate)) {
                            tv_sel_date_range.setText("" + show_end_date + " to " + show_start_date);
                        } else {
                            tv_sel_date_range.setText("" + show_start_date + " to " + show_end_date);
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    tv_sel_date_range.setVisibility(View.VISIBLE);

                    GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
                    getAllDealParameter.setCategoryId("");
                    getAllDealParameter.setSubCategoryId("");
                    getAllDealParameter.setFromDate("" + start_date);
                    getAllDealParameter.setToDate("" + end_date);
                    getAllDealParameter.setStart_time("");
                    getAllDealParameter.setEnd_time("");
                    getAllDealParameter.setLat("" + lattitude);
                    getAllDealParameter.setLng("" + longitude);
                    getAllDealParameter.setDistance(0);
                    new DealAllManager(MainActivity.this).callDealAllApi(token, getAllDealParameter);
                }


               /* ll_weather.setVisibility(View.VISIBLE);
                ll_screen_2.setVisibility(View.GONE);
                ll_indicatore.setVisibility(View.GONE);
                categories_view.setVisibility(View.VISIBLE);
                seekbar_layout.setVisibility(View.GONE);
                setting_options.setVisibility(View.GONE);
                iv_location.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                tv_location.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
                tv_date.setTextColor(ct.getResources().getColor(R.color.grey_text_color));*/

            }
        });
        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkGPS();


            }
        });


        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_options.setVisibility(View.GONE);
                ll_weather.setVisibility(View.VISIBLE);

                ll_indicatore.setVisibility(View.GONE);
                rl_calendar.setVisibility(View.GONE);
                seekbar_layout.setVisibility(View.GONE);
                if (!is_weather_show) {
                    is_weather_show = true;
                    ll_weather.setVisibility(View.VISIBLE);
                    tv_location.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
                } else {
                    is_weather_show = false;
                    ll_weather.setVisibility(View.GONE);
                    tv_location.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                }
                iv_location.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                tv_date.setTextColor(ct.getResources().getColor(R.color.grey_text_color));

                new SaveEventDealAllManager(MainActivity.this).callLocationListApi(token);
            }
        });
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInitializations();
                setCalenderView();
                is_from_date = false;

                setting_options.setVisibility(View.GONE);
                ll_weather.setVisibility(View.GONE);
                ll_screen_2.setVisibility(View.GONE);
                ll_indicatore.setVisibility(View.GONE);
                ll_weather.setVisibility(View.GONE);
                rl_calendar.setVisibility(View.VISIBLE);
                rl_saved_calendar.setVisibility(View.GONE);
                seekbar_layout.setVisibility(View.GONE);
                iv_location.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                tv_location.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                tv_date.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
            }
        });
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselected_icon();
                ll_weather.setVisibility(View.VISIBLE);
                setting_options.setVisibility(View.GONE);
                ll_screen_2.setVisibility(View.GONE);
                seekbar_layout.setVisibility(View.GONE);
                ll_weather.setVisibility(View.GONE);
                rl_calendar.setVisibility(View.GONE);
                rl_saved_calendar.setVisibility(View.GONE);

                tv_sel_date_range.setText("");
                tv_sel_location.setText("");

                start_date = "";
                end_date = "";
                lattitude = "";
                longitude = "";
                cat_id = "";
                tv_location.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                tv_home.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
                tv_date.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                iv_home.setColorFilter(ContextCompat.getColor(ct, R.color.theme_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                iv_location.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
                getAllDealParameter.setCategoryId("");
                getAllDealParameter.setSubCategoryId("");
                getAllDealParameter.setFromDate("");
                getAllDealParameter.setToDate("");
                getAllDealParameter.setStart_time("");
                getAllDealParameter.setEnd_time("");
                getAllDealParameter.setLat("");
                getAllDealParameter.setLng("");
                getAllDealParameter.setDistance(0);
                lattitude = "";
                longitude = "";
                new DealAllManager(MainActivity.this).callDealAllApi(token, getAllDealParameter);
            }
        });

        ll_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_category.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
                iv_category.setColorFilter(ContextCompat.getColor(ct, R.color.theme_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_category.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                        iv_category.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                }, 200);
            }
        });

        ll_saved_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselected_icon();

                setting_options.setVisibility(View.GONE);

                ll_screen_2.setVisibility(View.VISIBLE);
                rl_calendar.setVisibility(View.GONE);
                seekbar_layout.setVisibility(View.GONE);
                ll_weather.setVisibility(View.GONE);

                tv_sel_date_range.setText("");
                tv_sel_location.setText("");

                is_from_date = true;
                setSavedInitializations();
                setSavedCalenderView();
                new SaveEventDealAllManager(MainActivity.this).callSaveDealAllApi(token);
                rl_saved_calendar.setVisibility(View.VISIBLE);

                tv_home.setTextColor(ct.getResources().getColor(R.color.grey_text_color));


                tv_location.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                tv_saved_event.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
                tv_date.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                iv_saved_event.setColorFilter(ContextCompat.getColor(ct, R.color.theme_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                iv_location.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });


        seekbar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                prog = seekBar.getProgress();
                if (prog <= 0) {
                    prog = 1;
                    seekBar.setProgress(prog);
                    //  Toast.makeText(ct, "Minimum distance should be 1 KM.", Toast.LENGTH_SHORT).show();
                }

                km_text.setText(prog + " Km");

                GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
                getAllDealParameter.setCategoryId(cat_id);
                getAllDealParameter.setSubCategoryId("");
                getAllDealParameter.setFromDate("");
                getAllDealParameter.setToDate("");
                getAllDealParameter.setStart_time("");
                getAllDealParameter.setEnd_time("");
                getAllDealParameter.setLat("" + lattitude);
                getAllDealParameter.setLng("" + longitude);
                getAllDealParameter.setDistance(prog);
                new DealAllManager(MainActivity.this).callDealAllApi(token, getAllDealParameter);

            }
        });
      /*  ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselected_icon();
                categories_view.setVisibility(View.GONE);
                setting_options.setVisibility(View.VISIBLE);
                tv_setting.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
                iv_setting.setColorFilter(ContextCompat.getColor(ct, R.color.theme_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });*/

        event_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, DealInfo.class));
            }
        });

        new CategoryAllManager(MainActivity.this).callCategoryAllApi(token);

        checkGPS1();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("--------------------------");
                            System.out.println(" " + task.getException());
                            System.out.println("--------------------------");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        AppSession.save(getApplicationContext(), Constants.DEVICE_TOKEN, token);
                        // Log
                        String msg = "GET TOKEN " + token;
                        System.out.println("--------------------------");
                        System.out.println(" " + msg);
                        System.out.println("--------------------------");

                    }
                });


    }


    @Override
    public void onConnected(Bundle bundle) {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                if (mLastLocation != null) {

                    double longitude1 = mLastLocation.getLongitude();
                    double latitude1 = mLastLocation.getLatitude();

                    AppSession.save(ct, Constants.LATTITUDE, "" + latitude1);
                    AppSession.save(ct, Constants.LONGITUDE, "" + longitude1);


                    UpdateLatLongParameter updateLatLongParameter = new UpdateLatLongParameter();
                    String device_token = AppSession.getValue(ct, Constants.DEVICE_TOKEN);
                    updateLatLongParameter.setFcm_token("" + device_token);
                    updateLatLongParameter.setDevice_type("1");
                    updateLatLongParameter.setLat("" + latitude1);
                    updateLatLongParameter.setLng("" + longitude1);
                    new DealAllManager(MainActivity.this).callUpdateLatLongApi(token, updateLatLongParameter);

                    //  Toast.makeText(ct,"Lat : "+latitude + "Long : "+longitude ,Toast.LENGTH_SHORT).show();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    private void randomlySetGotoBtnIndex() {
        gotoBtnItemIndex = (int) (Math.random() * wheelCenter.getData().size());
        //  gotoBtn.setText("Goto '" + wheelCenter.getData().get(gotoBtnItemIndex) + "'");
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        String text = "";
        switch (picker.getId()) {
            case R.id.main_wheel_left:
                text = "Value:";
                break;


        }

        try {


            tv_category.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
            iv_category.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
            cat_id = categoryAllDTOglobal.getData().get(position % categoryAllDTOglobal.getData().size()).getCategoryId();
            GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
            getAllDealParameter.setCategoryId("" + cat_id);
            getAllDealParameter.setSubCategoryId("");
            getAllDealParameter.setFromDate("" + start_date);
            getAllDealParameter.setToDate("" + end_date);
            getAllDealParameter.setStart_time("");
            getAllDealParameter.setEnd_time("");
            getAllDealParameter.setLat("" + lattitude);
            getAllDealParameter.setLng("" + longitude);
            getAllDealParameter.setDistance(prog);
            new DealAllManager(MainActivity.this).callDealAllApi(token, getAllDealParameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //   Toast.makeText(this, text + String.valueOf(data), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        wheelCenter.setSelectedItemPosition(gotoBtnItemIndex);
        randomlySetGotoBtnIndex();
    }

    public void initDrawerOptions() {
        LinearLayout favourite = (LinearLayout) findViewById(R.id.favourite);
        TextView user_name = (TextView) findViewById(R.id.user_name);

        user_name.setText("" + AppSession.getValue(ct, Constants.FIRST_NAME));

        String image = AppSession.getValue(ct, Constants.IMAGE_URL);
        if (image != null) {
            ImageView user_image = (ImageView) findViewById(R.id.user_image);
            Picasso.with(ct).load(image).into(user_image);
        }
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                MyFavourite.is_from_nearme = "Favourite Deals";
                startActivity(new Intent(ct, MyFavourite.class));
            }
        });

        LinearLayout refer_friend = (LinearLayout) findViewById(R.id.refer_friend);

        refer_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/
                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                shareApp(MainActivity.this);
            }
        });

        LinearLayout near_me = (LinearLayout) findViewById(R.id.near_me);

        near_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                MyFavourite.is_from_nearme = "Near me";
                startActivity(new Intent(ct, MyFavourite.class));
            }
        });

        LinearLayout top_rated = (LinearLayout) findViewById(R.id.top_rated);

        top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                MyFavourite.is_from_nearme = "Top Rated";
                startActivity(new Intent(ct, MyFavourite.class));
            }
        });

        LinearLayout ll_contact = (LinearLayout) findViewById(R.id.ll_contact);

        ll_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                ContactUs.contact_title = "Contact Us";
                startActivity(new Intent(ct, ContactUs.class));
            }
        });
        LinearLayout home = (LinearLayout) findViewById(R.id.home);
        LinearLayout privacy_policy = (LinearLayout) findViewById(R.id.privacy_policy);
        LinearLayout terms_condtion = (LinearLayout) findViewById(R.id.terms_condtion);
        LinearLayout logout = (LinearLayout) findViewById(R.id.logout);

        LinearLayout partener = (LinearLayout) findViewById(R.id.partener);

        partener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                ContactUs.contact_title = "Partner with  us";
                startActivity(new Intent(ct, ContactUs.class));
            }
        });
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                PrivacyPolicy.is_from_terms = false;
                startActivity(new Intent(ct, PrivacyPolicy.class));

            }
        });
        terms_condtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                PrivacyPolicy.is_from_terms = true;
                startActivity(new Intent(ct, WebviewTermsCondition.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
                String token = AppSession.getValue(ct, Constants.ACCESS_TOKEN);
                new LogOutManager(MainActivity.this).callLogOutApi(token);
                startActivity(new Intent(ct, LoginActivity.class));
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    iv_setting_selected.setVisibility(View.GONE);
                    iv_setting.setVisibility(View.VISIBLE);
                }*/

                iv_setting_selected.setVisibility(View.GONE);
                iv_setting.setVisibility(View.VISIBLE);
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                } else {
                    slidingDrawer.animateOpen();
                }
            }
        });
    }

    public void init(View nav_menu) {

        wheelCenter = (WheelPicker) nav_menu.findViewById(R.id.main_wheel_left);
    }

    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public void openSideMenuDrawer() {
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                iv_setting.setVisibility(View.VISIBLE);
                iv_setting_selected.setVisibility(View.GONE);
            } else {
                drawer.openDrawer(GravityCompat.START);
                iv_setting.setVisibility(View.GONE);
                iv_setting_selected.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unselected_icon() {
        tv_home.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
        iv_home.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        tv_saved_event.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
        iv_saved_event.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        tv_setting.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
        // iv_setting.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onMapReady(GoogleMap goleMap) {
        try {


            googleMap = goleMap;
            googleMap.clear();
            if (ActivityCompat.checkSelfPermission(ct, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ct, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
                return;
            }

            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                    //  googleMap.clear();
                    //latlng = googleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                    //new GetAddressFromLatlong().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            });

            //  int[] markerIcons = {R.drawable.blue_map_icon, R.drawable.sky_blue_map_icon, R.drawable.sky_blue_map_icon, R.drawable.red_map_icon, R.drawable.green_map_icon};
            if (dealAllDTO.getData() != null) {
                for (int i = 0; i < dealAllDTO.getData().size(); i++) {
                    if (dealAllDTO.getData().get(i).getDealAddressLat() != null) {
                        Double lat = Double.parseDouble(dealAllDTO.getData().get(i).getDealAddressLat());
                        Double lng = Double.parseDouble(dealAllDTO.getData().get(i).getDealAddressLong());
                        LatLng TutorialsPoint = new LatLng(lat, lng);

                        CameraPosition cameraPosition = null;
                        // if (prog > 20)
                        cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(14).build();
      /*                  else if (prog > 15)
                            cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(13).build();
                        else if (prog > 10)
                            cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(14).build();
                        else if (prog > 5)
                            cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(15).build();
                        else if (prog >= 1)
                            cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(16).build();*/

                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        //googleMap.addMarker(marker);

                        Log.v("COLORS", "Lat" + TutorialsPoint.latitude);

                        //MarkerOptions marker = new MarkerOptions().position(TutorialsPoint).title(dealAllDTO.getData().get(i).getDealTitle());
                        MarkerOptions marker = new MarkerOptions()
                                .position(TutorialsPoint)
                                .visible(true).snippet(String.valueOf(i))
                                .icon(BitmapDescriptorFactory.fromBitmap(changeBitmapColor(dealAllDTO.getData().get(i).getCategory_id())));

                        marker.title(dealAllDTO.getData().get(i).getDealTitle() + "#" + dealAllDTO.getData().get(i).getCategory_id() + "#" + i);

                        googleMap.addMarker(marker);


                        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                            @Override
                            public View getInfoWindow(Marker arg0) {
                                // TODO Auto-generated method stub
                                // Getting view from the layout file info_window_layout
                                View v = getLayoutInflater().inflate(R.layout.layout_info_window, null);
                                TextView event_name = v.findViewById(R.id.event_name);
                                if (arg0.getTitle().contains("#")) {
                                    String[] splt_arra = arg0.getTitle().split("#");
                                    event_name.setText("" + splt_arra[0]);

                                    int color = 0;
                                    String deal_id = splt_arra[1];
                                    for (int i = 0; i < cate_id.size(); i++) {

                                        if (cate_id.get(i).equalsIgnoreCase("" + deal_id)) {
                                            color = Color.parseColor("" + WheelPicker.colors.get(i));
                                            event_name.setTextColor(color);
                                            break;
                                        }
                                    }

                                    try {
                                        int index = Integer.parseInt(splt_arra[2]);

                                        DealAllDTO dealAllDTO1 = new DealAllDTO();

                                        dealAllDTO1.setData(new ArrayList<>());

                                        dealAllDTO1.getData().add(dealAllDTO.getData().get(index));

                                        EventAdapter eventAdapter = new EventAdapter(ct, dealAllDTO1);
                                        rcyl_event.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false));
                                        rcyl_event.setAdapter(eventAdapter);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }



                              /*  int color = 0;
                                String deal_id = "";
                                for (int i = 0; i < cate_id.size(); i++) {

                                    if (cate_id.get(i).equalsIgnoreCase("" + deal_id)) {
                                        color = Color.parseColor("" + WheelPicker.colors.get(i));
                                        break;
                                    }
                                }*/

                                return v;
                            }

                            @Override
                            public View getInfoContents(Marker arg0) {
                                return null;
                            }
                        });

                    }
                }
            } else {
                if (lattitude != null && longitude != null) {
                    CameraPosition cameraPosition = null;

                    Double lat = Double.parseDouble(lattitude);
                    Double lng = Double.parseDouble(longitude);
                    LatLng TutorialsPoint = new LatLng(lat, lng);

                    cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(14).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }


            }


            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker0) {

                    ll_weather.setVisibility(View.GONE);
                    ll_screen_2.setVisibility(View.VISIBLE);
                    // ll_indicatore.setVisibility(View.VISIBLE);
                    rl_calendar.setVisibility(View.GONE);

                    tv_location.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                    tv_date.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                    marker0.showInfoWindow();

                    return true;

                }
            });

            googleMap.setBuildingsEnabled(false);
            googleMap.setIndoorEnabled(false);
            // googleMap.setMyLocationEnabled(false);
            googleMap.setTrafficEnabled(false);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRestaurents() {
        ll_screen_2.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            buildGoogleApiClient();
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestPermission();


        try {
            String fromNotification = AppSession.getValue(ct, Constants.FROM_NOTIFICATION);
            if (fromNotification != null && fromNotification.equalsIgnoreCase(Constants.TRUE)) {
                AppSession.save(ct, Constants.FROM_NOTIFICATION, Constants.FALSE);
                startActivity(new Intent(ct, DealInfo.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    synchronized void buildGoogleApiClient() {


        try {
            if (mGoogleApiClient != null)
                mGoogleApiClient = null;
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void setSavedInitializations() {


        layoutSavedCalender = (LinearLayout) findViewById(R.id.layoutSavedCalender);
    }

    public void setSavedCalenderView() {
        //Custom Events
        // ViewGroup parent = (ViewGroup) custom_view.getParent();
        // parent.removeView(custom_view);
        layoutSavedCalender.removeAllViews();
        layoutSavedCalender.setOrientation(LinearLayout.VERTICAL);
        calendarsaveddCustomView = new CalendarCustomView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        calendarsaveddCustomView.setLayoutParams(layoutParams);
        layoutSavedCalender.addView(calendarsaveddCustomView);
        calendarsaveddCustomView.calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                try {
                    Date selected_date = (Date) adapterView.getAdapter().getItem((int) l);
                    filterSavedDate(selected_date);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }

    private void setInitializations() {

        layoutCalender = (LinearLayout) findViewById(R.id.layoutCalender);
    }


    CalendarCustomView calendarCustomView;
    CalendarCustomView calendarsaveddCustomView;

    public void setCalenderView() {
        //Custom Events
        // ViewGroup parent = (ViewGroup) custom_view.getParent();
        // parent.removeView(custom_view);
        layoutCalender.removeAllViews();
        layoutCalender.setOrientation(LinearLayout.VERTICAL);
        calendarCustomView = new CalendarCustomView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        calendarCustomView.setLayoutParams(layoutParams);
        layoutCalender.addView(calendarCustomView);
        calendarCustomView.calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getAdapter().getView((int) l, null, null).getAlpha() == 0.4f) {
                    Log.d("hello", "hello");
                } else {
                    Calendar today = Calendar.getInstance();
                    today.setTime(new java.util.Date());

                    Calendar tapedDay = Calendar.getInstance();
                    tapedDay.setTime((Date) adapterView.getAdapter().getItem((int) l));
                    boolean sameDay = tapedDay.get(Calendar.YEAR) == tapedDay.get(Calendar.YEAR) &&
                            today.get(Calendar.DAY_OF_YEAR) == tapedDay.get(Calendar.DAY_OF_YEAR);
                    if (today.after(tapedDay) && !sameDay) {
                        Toast.makeText(MainActivity.this, "You can't select previous date.", Toast.LENGTH_LONG).show();
                    } else {
                        if (initialDate == null && lastDate == null) {
                            initialDate = lastDate = (Date) adapterView.getAdapter().getItem((int) l);
                        } else {
                            initialDate = lastDate;
                            lastDate = (Date) adapterView.getAdapter().getItem((int) l);
                        }
                        if (initialDate != null && lastDate != null) {
                            // start_date =
                            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                            start_date = dateFormat.format(initialDate);
                            end_date = dateFormat.format(lastDate);

                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MMM");
                            show_start_date = dateFormat1.format(initialDate);

                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MMM");
                            show_end_date = dateFormat2.format(lastDate);

                            //show_end_date = ""+ lastDate.getDay() + lastDate.getMonth();


                            calendarCustomView.setRangesOfDate(makeDateRanges());
                        }

                        if (!initialDate.equals(lastDate)) {
                            initialDate = null;
                            lastDate = null;

                        }
                    }

                    /*Date selectedDate = (Date) adapterView.getAdapter().getItem((int) l);


                    selectedDateList = new DealAllDTO();
                    selectedDateList.setData(new ArrayList<Datum>());
                    for (int j = 0; j < dealAllDTO.getData().size(); j++) {
                        GregorianCalendar gcal = new GregorianCalendar();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = format.parse(dealAllDTO.getData().get(j).getDealValidityTo());

                            Calendar today = Calendar.getInstance();
                            today.setTime(date);

                            Calendar tapedDay = Calendar.getInstance();
                            tapedDay.setTime(selectedDate);
                            if (tapedDay.get(Calendar.YEAR) == today.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == tapedDay.get(Calendar.DAY_OF_YEAR) && today.get(Calendar.MONTH) == tapedDay.get(Calendar.MONTH)) {
                                selectedDateList.getData().add(dealAllDTO.getData().get(j));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                    ll_screen_2.setVisibility(View.VISIBLE);
                    SavedEventAdapter eventAdapter = new SavedEventAdapter(ct, selectedDateList);
                    rcyl_event.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false));
                    rcyl_event.setAdapter(eventAdapter);*/


                }
                try {
                    //Toast.makeText(MainActivity.this, "Start Date: " + initialDate.toString() + "\n End Date: " + lastDate.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public List<EventObjects> makeDateRanges() {
        if (lastDate.after(initialDate)) {
            start = initialDate;
            end = lastDate;
        } else {
            start = lastDate;
            end = initialDate;
        }

        List<EventObjects> eventObjectses = new ArrayList<>();
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTime(start);

        while (!gcal.getTime().after(end)) {
            Date d = gcal.getTime();
            EventObjects eventObject = new EventObjects("Hello", d);
            eventObject.setColor(getResources().getColor(R.color.theme_text_color));
            eventObjectses.add(eventObject);
            gcal.add(Calendar.DATE, 1);
        }
        return eventObjectses;
    }


    public List<EventObjects> getExpiryDates() {

        List<EventObjects> eventObjectses = new ArrayList<>();
        for (int i = 0; i < dealAllDTO.getData().size(); i++) {
            GregorianCalendar gcal = new GregorianCalendar();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date from_datedate = format.parse(dealAllDTO.getData().get(i).getDealValidityFrom());
                Date to_date = format.parse(dealAllDTO.getData().get(i).getDealValidityTo());

                int num_day = findDaysDiff(from_datedate.getTime(), to_date.getTime());

                for (int j = 0; j < num_day; j++) {
                    Calendar today = Calendar.getInstance();
                    // today.set
                    Calendar to_date_validate = Calendar.getInstance();

                    to_date_validate.setTimeInMillis(to_date.getTime());
                    Calendar fromday = Calendar.getInstance();
                    fromday.setTimeInMillis(from_datedate.getTime());
                    fromday.add(Calendar.DAY_OF_MONTH, j);

                    long total_milliseconds = today.getTime().getHours() * 3600000 + 3600000;

                    total_milliseconds = today.getTimeInMillis() - total_milliseconds;

                    if (fromday.getTimeInMillis() >= total_milliseconds) {
                        int day_name = fromday.DAY_OF_WEEK;

                        SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
                        String goal = outFormat.format(fromday.getTime());
                        for (int k = 0; k < dealAllDTO.getData().get(i).getDeal_valid_days().size(); k++) {
                            Log.v("DateD", "fday_name " + goal);
                            if (dealAllDTO.getData().get(i).getDeal_valid_days().get(k).contains("" + goal)) {


                                // Log.v("DateD","J "+j+" date "+ fromday.getTime().getDate()+"from "+dealAllDTO.getData().get(i).getDealValidityFrom()+" to "+dealAllDTO.getData().get(i).getDealValidityTo() + " num_day "+num_day);
                                gcal.setTime(fromday.getTime());
                                Date d = gcal.getTime();
                                EventObjects eventObject = new EventObjects("Hello", d);
                                eventObject.setColor(getResources().getColor(R.color.theme_text_color));
                                eventObjectses.add(eventObject);
                            }
                        }


                    }


                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return eventObjectses;
    }


    private void filterSavedDate(Date selected_date) {
        List<Datum> allevents = new ArrayList<>();


        for (int i = 0; i < dealAllDTO.getData().size(); i++) {
            GregorianCalendar gcal = new GregorianCalendar();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date from_datedate = format.parse(dealAllDTO.getData().get(i).getDealValidityFrom());
                Date to_date = format.parse(dealAllDTO.getData().get(i).getDealValidityTo());


                Calendar today = Calendar.getInstance();
                today.setTimeInMillis(selected_date.getTime());
                // today.set
                Calendar to_date_validate = Calendar.getInstance();

                to_date_validate.setTimeInMillis(to_date.getTime());
                Calendar fromday = Calendar.getInstance();
                fromday.setTimeInMillis(from_datedate.getTime());


                long total_milliseconds = today.getTime().getHours() * 3600000 + 3600000;

                total_milliseconds = today.getTimeInMillis() - total_milliseconds;

                if (fromday.getTimeInMillis() <= total_milliseconds && total_milliseconds <= to_date_validate.getTimeInMillis()) {
                    int day_name = fromday.DAY_OF_WEEK;

                    SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
                    String goal = outFormat.format(fromday.getTime());
                    for (int k = 0; k < dealAllDTO.getData().get(i).getDeal_valid_days().size(); k++) {
                        Log.v("DateD", "fday_name " + goal);
                        if (dealAllDTO.getData().get(i).getDeal_valid_days().get(k).contains("" + goal)) {
                            allevents.add(dealAllDTO.getData().get(i));
                            break;
                            // Log.v("DateD","J "+j+" date "+ fromday.getTime().getDate()+"from "+dealAllDTO.getData().get(i).getDealValidityFrom()+" to "+dealAllDTO.getData().get(i).getDealValidityTo() + " num_day "+num_day);

                        }
                    }


                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (allevents != null && allevents.size() > 0) {
            rl_saved_calendar.setVisibility(View.GONE);
            dealAllDTO.getData().clear();
            dealAllDTO.getData().addAll(allevents);


            ll_screen_2.setVisibility(View.VISIBLE);
            EventAdapter eventAdapter = new EventAdapter(ct, dealAllDTO);
            rcyl_event.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false));
            rcyl_event.setAdapter(eventAdapter);


            try {
                mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private static int findDaysDiff(long unixStartTime, long unixEndTime) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(unixStartTime);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(unixEndTime);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        return (int) ((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / (24 * 60 * 60 * 1000));

    }

    @Override
    public void onSuccessLogOut(LogoutDTO logoutDTO) {
        AppSession.save(ct, Constants.ACCESS_TOKEN, "");
        Toast.makeText(ct, "" + logoutDTO.getMessage(), Toast.LENGTH_SHORT).show();
    }

    static boolean is_token_issue = true;

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(ct, "" + errorMessage, Toast.LENGTH_SHORT).show();
        if (errorMessage.equalsIgnoreCase("Invalid Token") && is_token_issue) {
            is_token_issue = false;
            AppSession.save(ct, Constants.ACCESS_TOKEN, "");
            startActivity(new Intent(ct, LoginActivity.class));
            finishAffinity();
        }
    }

    @Override
    public void onShowLoader() {
        showLoader();
    }

    @Override
    public void onHideLoader() {
        hideLoader();
    }

    CategoryAllDTO categoryAllDTOglobal;

    public static List<String> cate_id = new ArrayList<>();

    @Override
    public void onSuccessCategoryAll(CategoryAllDTO categoryAllDTO) {
        try {
            categoryAllDTOglobal = categoryAllDTO;
            WheelPicker.colors.clear();
            cate_id.clear();
            Constants.indicator_array.clear();
            if (categoryAllDTO.getData() != null && categoryAllDTO.getData().size() > 0) {
                for (int i = 0; i < categoryAllDTO.getData().size(); i++) {
                    Constants.indicator_array.add("" + categoryAllDTO.getData().get(i).getCategoryTitle());
                    WheelPicker.colors.add("" + categoryAllDTO.getData().get(i).getCategory_color());
                    cate_id.add(categoryAllDTO.getData().get(i).getCategoryId());
                }

                wheelCenter.setOnItemSelectedListener(this);
                wheelCenter.setData(Constants.indicator_array);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
        getAllDealParameter.setCategoryId("");
        getAllDealParameter.setSubCategoryId("");
        getAllDealParameter.setFromDate("");
        getAllDealParameter.setToDate("");
        getAllDealParameter.setStart_time("");
        getAllDealParameter.setEnd_time("");
        getAllDealParameter.setLat("");
        getAllDealParameter.setLng("");
        getAllDealParameter.setDistance(0);
        new DealAllManager(MainActivity.this).callDealAllApi(token, getAllDealParameter);

    }

    @Override
    public void onSuccessDealAll(DealAllDTO dealAllDTO1) {
        dealAllDTO = dealAllDTO1;
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dealAllDTO.getData() != null) {
            ll_screen_2.setVisibility(View.VISIBLE);
            EventAdapter eventAdapter = new EventAdapter(ct, dealAllDTO);
            rcyl_event.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false));
            rcyl_event.setAdapter(eventAdapter);
        } else {
            googleMap.clear();
            ll_screen_2.setVisibility(View.GONE);
            Toast.makeText(ct, "No data found", Toast.LENGTH_SHORT).show();
        }

        try {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessUpdateLatLong(UpdateLatLongDTO updateLatLongDTO) {
        if (updateLatLongDTO != null) {

        }
    }


    DealAllDTO savedDealsDTO;
    DealAllDTO selectedDateList;

    @Override
    public void onSuccessUserSaveEvent(DealAllDTO dealAllDTO1) {
        dealAllDTO = dealAllDTO1;
        if (is_from_date) {
            if (dealAllDTO != null && dealAllDTO.getData() != null && dealAllDTO.getData().size() > 0) {
                ll_screen_2.setVisibility(View.VISIBLE);
                calendarsaveddCustomView.setRangesOfDate(getExpiryDates());
                EventAdapter eventAdapter = new EventAdapter(ct, dealAllDTO);
                rcyl_event.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false));
                rcyl_event.setAdapter(eventAdapter);
            }


        } else {
            ll_screen_2.setVisibility(View.VISIBLE);
            EventAdapter eventAdapter = new EventAdapter(ct, dealAllDTO);
            rcyl_event.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false));
            rcyl_event.setAdapter(eventAdapter);
        }

        try {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccessLocationsList(LocationsList dealAllDTO) {
        if (dealAllDTO != null) {
            WeatherAdapter weatherAdapter = new WeatherAdapter(ct, dealAllDTO, this);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(ct);
            rcyl_weather.setLayoutManager(mLayoutManager);
            rcyl_weather.setAdapter(weatherAdapter);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSelectLocation(LocationsList.Datum datum) {
        unselected_icon();
        ll_weather.setVisibility(View.VISIBLE);
        setting_options.setVisibility(View.GONE);

        // categories_view.setVisibility(View.VISIBLE);

        seekbar_layout.setVisibility(View.GONE);
        ll_weather.setVisibility(View.GONE);
        rl_calendar.setVisibility(View.GONE);
        tv_location.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
        tv_home.setTextColor(ct.getResources().getColor(R.color.theme_text_color));
        tv_date.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
        iv_home.setColorFilter(ContextCompat.getColor(ct, R.color.theme_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        iv_location.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        is_weather_show = false;

        lattitude = datum.getLocationLat();
        longitude = datum.getLocationLong();

        tv_sel_location.setText("" + datum.getLocationName());
        tv_sel_location.setVisibility(View.VISIBLE);

        prog = 1;
        GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
        getAllDealParameter.setCategoryId("");
        getAllDealParameter.setSubCategoryId("");
        getAllDealParameter.setFromDate(start_date);
        getAllDealParameter.setToDate(end_date);
        getAllDealParameter.setStart_time("");
        getAllDealParameter.setEnd_time("");
        getAllDealParameter.setLat("" + lattitude);
        getAllDealParameter.setLng("" + longitude);
        getAllDealParameter.setDistance(prog);
        new DealAllManager(MainActivity.this).callDealAllApi(token, getAllDealParameter);


    }


    private Bitmap changeBitmapColor(String deal_id) {


        int color = 0;
        for (int i = 0; i < cate_id.size(); i++) {

            if (cate_id.get(i).equalsIgnoreCase("" + deal_id)) {
                color = Color.parseColor("" + WheelPicker.colors.get(i));
                break;
            }
        }

        Log.v("COLORS", "" + color);
        Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.sky_blue_map_icon);
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);

        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 0);
        p.setColorFilter(filter);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);

        int height = getScreenWidth() / 9;
        int width = getScreenWidth() / 7;
        Bitmap resultBitmap1 = Bitmap.createScaledBitmap(resultBitmap, width, height, false);

        return resultBitmap1;
    }

    int REQUESTCODE_TURNON_GPS = 12345;

    void checkGPS() {
        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("GPS_main", "OnSuccess");
                setting_options.setVisibility(View.GONE);
                ll_weather.setVisibility(View.GONE);
                tv_date.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                ll_indicatore.setVisibility(View.GONE);
                rl_calendar.setVisibility(View.GONE);
                seekbar_layout.setVisibility(View.VISIBLE);

                tv_location.setTextColor(ct.getResources().getColor(R.color.grey_text_color));
                iv_location.setColorFilter(ContextCompat.getColor(ct, R.color.theme_text_color), android.graphics.PorterDuff.Mode.SRC_IN);


                lattitude = AppSession.getValue(ct, Constants.LATTITUDE);
                longitude = AppSession.getValue(ct, Constants.LONGITUDE);
                GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
                getAllDealParameter.setCategoryId("");
                getAllDealParameter.setSubCategoryId("");
                getAllDealParameter.setFromDate("");
                getAllDealParameter.setToDate("");
                getAllDealParameter.setStart_time("");
                getAllDealParameter.setEnd_time("");
                getAllDealParameter.setLat("" + lattitude);
                getAllDealParameter.setLng("" + longitude);
                getAllDealParameter.setDistance(25);
                new DealAllManager(MainActivity.this).callDealAllApi(token, getAllDealParameter);

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.d("GPS_main", "GPS off");
                // GPS off
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MainActivity.this, REQUESTCODE_TURNON_GPS);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    void checkGPS1() {
        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("GPS_main", "OnSuccess");


            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.d("GPS_main", "GPS off");
                // GPS off
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MainActivity.this, REQUESTCODE_TURNON_GPS);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void turnGPSOn() {
        String provider = Settings.Secure.getString(ct.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            ct.sendBroadcast(poke);

        }

    }

}
