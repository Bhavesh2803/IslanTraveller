package com.islantraveller.MyFavourite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Dashboard.Activity.manager.DealAllManager;
import com.islantraveller.Dashboard.Activity.manager.GetAllDealParameter;
import com.islantraveller.Dashboard.Activity.manager.UpdateLatLongDTO;
import com.islantraveller.Dashboard.Adapter.EventAdapter;
import com.islantraveller.DealInfo.DealInfo;
import com.islantraveller.MyFavourite.Adapter.UserFavouriteAdapter;
import com.islantraveller.MyFavourite.Manager.GetUserFavouriteListManager;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.basic.BaseActivity;
import com.islantraveller.R;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;

import java.util.ArrayList;
import java.util.List;

public class MyFavourite extends BaseActivity implements OnMapReadyCallback, ApiCallback.UserFavouriteListManagerCallback,ApiCallback.DealAllManagerCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    ImageView back_button;
    RecyclerView rcyl_event;
    final int REQUEST_CODE_ASK_PERMISSIONS = 11;
    public static String is_from_nearme = "";
    Context ct = this;
    // LinearLayout event_listing;
    DealAllDTO dealAllDTO;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        title = (TextView) findViewById(R.id.title);
        back_button = (ImageView) findViewById(R.id.back_button);
        //  event_listing = (LinearLayout) findViewById(R.id.event_listing);
        rcyl_event = (RecyclerView) findViewById(R.id.rcyl_event);
       /* event_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, DealInfo.class));
            }

        });*/

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        title.setText(is_from_nearme);
        String token = AppSession.getValue(ct, Constants.ACCESS_TOKEN);
        if(is_from_nearme.equalsIgnoreCase("Top Rated"))
        {
            GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
            getAllDealParameter.setCategoryId("");
            getAllDealParameter.setSubCategoryId("");
            getAllDealParameter.setFromDate("");
            getAllDealParameter.setToDate("");
            getAllDealParameter.setStart_time("");
            getAllDealParameter.setTop_rating(true);
            getAllDealParameter.setEnd_time("");
            new DealAllManager(MyFavourite.this).callDealAllApi(token, getAllDealParameter);


        }
       else if(is_from_nearme.equalsIgnoreCase("Near me"))
        {
            GetAllDealParameter getAllDealParameter = new GetAllDealParameter();
            getAllDealParameter.setCategoryId("");
            getAllDealParameter.setSubCategoryId("");
            getAllDealParameter.setFromDate("");
            getAllDealParameter.setToDate("");
            getAllDealParameter.setStart_time("");
            getAllDealParameter.setTop_rating(false);
            getAllDealParameter.setEnd_time("");
            getAllDealParameter.setLat("" + AppSession.getValue(ct, Constants.LATTITUDE));
            getAllDealParameter.setLng("" + AppSession.getValue(ct, Constants.LONGITUDE));
            getAllDealParameter.setDistance(5);
            new DealAllManager(MyFavourite.this).callDealAllApi(token, getAllDealParameter);

          //  Toast.makeText(ct,"Near me",Toast.LENGTH_SHORT).show();

        }else
        {
            new GetUserFavouriteListManager(MyFavourite.this).callFavouriteAllApi(token);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onMapReady(GoogleMap goleMap) {
        try {
            googleMap = goleMap;
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

            if (dealAllDTO.getData() != null) {
                for (int i = 0; i < dealAllDTO.getData().size(); i++)
                {
                    if (dealAllDTO.getData().get(i).getDealAddressLat() != null) {
                        Double lat = Double.parseDouble(dealAllDTO.getData().get(i).getDealAddressLat());
                        Double lng = Double.parseDouble(dealAllDTO.getData().get(i).getDealAddressLong());
                        LatLng TutorialsPoint = new LatLng(lat, lng);

                        //MarkerOptions marker = new MarkerOptions().position(TutorialsPoint).title(getResources().getString(R.string.app_name));




                        CameraPosition cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                       // googleMap.addMarker(marker);

                        MarkerOptions marker = new MarkerOptions()
                                .position(TutorialsPoint)
                                .visible(true).snippet(String.valueOf(i))
                                .icon(BitmapDescriptorFactory.fromBitmap(changeBitmapColor(dealAllDTO.getData().get(i).getCategory_id())));

                        marker.title(dealAllDTO.getData().get(i).getDealTitle() + "#" + dealAllDTO.getData().get(i).getCategory_id());

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
                                    for (int i = 0; i < MainActivity.cate_id.size(); i++) {

                                        if (MainActivity.cate_id.get(i).equalsIgnoreCase("" + deal_id)) {
                                            color = Color.parseColor("" + WheelPicker.colors.get(i));
                                            event_name.setTextColor(color);
                                            break;
                                        }
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
            }
       /*     LatLng TutorialsPoint = new LatLng(40.906605, -74.095859);
            LatLng TutorialsPoint1 = new LatLng(40.907602, -74.096653);
            LatLng TutorialsPoint2 = new LatLng(40.9075599, -74.100934);
            LatLng TutorialsPoint3 = new LatLng(40.905875, -74.094025);
            LatLng TutorialsPoint4 = new LatLng(40.908056, -74.100355);
            LatLng TutorialsPoint5 = new LatLng(40.908243, -74.094551);

            MarkerOptions marker = new MarkerOptions().position(TutorialsPoint).title(getResources().getString(R.string.app_name));
            MarkerOptions marker1 = new MarkerOptions().position(TutorialsPoint1).title(getResources().getString(R.string.app_name));
            MarkerOptions marker2 = new MarkerOptions().position(TutorialsPoint2).title(getResources().getString(R.string.app_name));
            MarkerOptions marker3 = new MarkerOptions().position(TutorialsPoint3).title(getResources().getString(R.string.app_name));
            MarkerOptions marker4 = new MarkerOptions().position(TutorialsPoint4).title(getResources().getString(R.string.app_name));
            final MarkerOptions marker5 = new MarkerOptions().position(TutorialsPoint5).title(getResources().getString(R.string.app_name));

            int height = getScreenWidth() / 10;
            int width = getScreenWidth() / 8;

            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.green_map_icon);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.blue_map_icon);
            Bitmap b1 = bitmapdraw1.getBitmap();
            Bitmap smallMarker1 = Bitmap.createScaledBitmap(b1, width, height, false);

            BitmapDrawable bitmapdraw2 = (BitmapDrawable) getResources().getDrawable(R.drawable.sky_blue_map_icon);
            Bitmap b2 = bitmapdraw2.getBitmap();
            Bitmap smallMarker2 = Bitmap.createScaledBitmap(b2, width, height, false);

            BitmapDrawable bitmapdraw3 = (BitmapDrawable) getResources().getDrawable(R.drawable.red_map_icon);
            Bitmap b3 = bitmapdraw3.getBitmap();
            Bitmap smallMarker3 = Bitmap.createScaledBitmap(b3, width, height, false);

            BitmapDrawable bitmapdraw4 = (BitmapDrawable) getResources().getDrawable(R.drawable.green_map_icon);
            Bitmap b4 = bitmapdraw4.getBitmap();
            Bitmap smallMarker4 = Bitmap.createScaledBitmap(b4, width, height, false);

            BitmapDrawable bitmapdraw5 = (BitmapDrawable) getResources().getDrawable(R.drawable.yellow_circle);
            Bitmap b5 = bitmapdraw5.getBitmap();
            Bitmap smallMarker5 = Bitmap.createScaledBitmap(b5, width, height, false);


            marker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            googleMap.addMarker(marker);

            marker1.icon(BitmapDescriptorFactory.fromBitmap(smallMarker1));
            googleMap.addMarker(marker1);

            marker2.icon(BitmapDescriptorFactory.fromBitmap(smallMarker2));
            googleMap.addMarker(marker2);

            marker3.icon(BitmapDescriptorFactory.fromBitmap(smallMarker3));
            googleMap.addMarker(marker3);

            marker4.icon(BitmapDescriptorFactory.fromBitmap(smallMarker4));
            googleMap.addMarker(marker4);

            marker5.icon(BitmapDescriptorFactory.fromBitmap(smallMarker5));
            googleMap.addMarker(marker5);
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker0) {


                    // ll_screen_2.setVisibility(View.VISIBLE);


                    return false;

                }
            });


            CameraPosition cameraPosition = new CameraPosition.Builder().target(TutorialsPoint).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    private Bitmap changeBitmapColor(String deal_id) {


       try {
           int color = 0;
           for (int i = 0; i < MainActivity.cate_id.size(); i++) {

               if (MainActivity.cate_id.get(i).equalsIgnoreCase("" + deal_id)) {
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
       catch (Exception e)
       {
           e.printStackTrace();
       }
       return null;
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(MyFavourite.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyFavourite.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        if (ContextCompat.checkSelfPermission(MyFavourite.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyFavourite.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }


    @Override
    public void onError(String errorMessage) {
        Toast.makeText(ct, "" + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowLoader() {
        showLoader();
    }

    @Override
    public void onHideLoader() {
        hideLoader();
    }

    @Override
    public void onSuccessUserfavourite(DealAllDTO dealAllDTO1) {
        dealAllDTO = dealAllDTO1;
        if (dealAllDTO.getData() != null) {
            UserFavouriteAdapter eventAdapter = new UserFavouriteAdapter(ct, dealAllDTO);
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
    public void onSuccessDealAll(DealAllDTO dealAllDTO1) {
        dealAllDTO = dealAllDTO1;



        if (dealAllDTO.getData() != null) {


            UserFavouriteAdapter eventAdapter = new UserFavouriteAdapter(ct, dealAllDTO);
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
    public void onSuccessUpdateLatLong(UpdateLatLongDTO updateLatLongDTO) {

    }
}