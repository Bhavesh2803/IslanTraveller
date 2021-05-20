package com.islantraveller.DealInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Dashboard.Adapter.EventAdapter;
import com.islantraveller.DealInfo.Model.AddFavouriteDTO;
import com.islantraveller.DealInfo.Model.DealInfoModel.DealInfoDTO;
import com.islantraveller.DealInfo.manager.AddFavouriteManager;
import com.islantraveller.DealInfo.manager.AddSaveEventManager;
import com.islantraveller.DealInfo.manager.DealInfoManager;
import com.islantraveller.Login.LoginActivity;
import com.islantraveller.Login.manager.LoginManager;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.basic.BaseActivity;
import com.islantraveller.R;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.xingliuhua.xlhratingbar.XLHRatingBar;

public class DealInfo extends BaseActivity implements ApiCallback.DealInfoManagerCallback, ApiCallback.AddFavManagerCallback, ApiCallback.SaveEventManagerCallback {

    ImageView back_button, iv_like, left_scroll, right_scroll;
    TextView tv_offer, tv_price, rating_num, tv_validity, tv_timing, tv_max_person, tv_description, deal_name, tv_add_to_save;

    private MyViewPagerAdapter myViewPagerAdapter;
    ViewPager viewPager;
    boolean is_favourite = false;
    boolean is_save = false;
    Context ct = this;
    String deal_id = "";
    TextView mon, tue, wed, thus, fri, sat, sun,everyday;
    private SpringDotsIndicator spring_dots_indicator;

    ImageView iv_direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_info);

        iv_direction = (ImageView) findViewById(R.id.iv_direction);
        left_scroll = (ImageView) findViewById(R.id.left_scroll);
        right_scroll = (ImageView) findViewById(R.id.right_scroll);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        back_button = (ImageView) findViewById(R.id.back_button);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_offer = (TextView) findViewById(R.id.tv_offer);
        deal_name = (TextView) findViewById(R.id.deal_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_validity = (TextView) findViewById(R.id.tv_validity);
        tv_timing = (TextView) findViewById(R.id.tv_timing);
        tv_max_person = (TextView) findViewById(R.id.tv_max_person);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_add_to_save = (TextView) findViewById(R.id.tv_add_to_save);
        rating_num = (TextView) findViewById(R.id.rating_num);
        mon = (TextView) findViewById(R.id.mon);
        tue = (TextView) findViewById(R.id.tue);
        wed = (TextView) findViewById(R.id.wed);
        thus = (TextView) findViewById(R.id.thus);
        fri = (TextView) findViewById(R.id.fri);
        sat = (TextView) findViewById(R.id.sat);
        sun = (TextView) findViewById(R.id.sun);
        everyday = (TextView) findViewById(R.id.everyday);
        spring_dots_indicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);


        iv_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String srcLat = AppSession.getValue(ct, Constants.LATTITUDE);
                    String srcLng = AppSession.getValue(ct, Constants.LONGITUDE);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + srcLat + "," + srcLng + "&daddr=" + dealAllDTO.getData().getDealAddressLat() + "," + dealAllDTO.getData().getDealAddressLong()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        String token = AppSession.getValue(ct, Constants.ACCESS_TOKEN);
        deal_id = AppSession.getValue(ct, Constants.DEAL_ID);
        new DealInfoManager(DealInfo.this).callDealInfoApi(token, deal_id);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_add_to_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_save) {
                    is_save = false;
                    new AddSaveEventManager(DealInfo.this).callDeleteSaveApi(token, deal_id);
                } else {
                    is_save = true;
                    new AddSaveEventManager(DealInfo.this).callAddSaveApi(token, deal_id);
                }

            }
        });
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_favourite) {
                    is_favourite = false;
                    iv_like.setImageResource(R.drawable.fav_unsel);
                    new AddFavouriteManager(DealInfo.this).callDeleteFavouriteApi(token, deal_id);
                } else {
                    is_favourite = true;
                    iv_like.setImageResource(R.drawable.fav_sel);
                    new AddFavouriteManager(DealInfo.this).callAddFavouriteApi(token, deal_id);
                }
            }
        });

        left_scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        right_scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
    public void onSuccessAddFav(AddFavouriteDTO addFavouriteDTO) {
        Toast.makeText(ct, "" + addFavouriteDTO.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessDeleteFav(AddFavouriteDTO addFavouriteDTO) {
        Toast.makeText(ct, "" + addFavouriteDTO.getMessage(), Toast.LENGTH_SHORT).show();
    }

    DealInfoDTO dealAllDTO;

    @Override
    public void onSuccessDealInfo(DealInfoDTO dealAllDTO1) {
        dealAllDTO = dealAllDTO1;
        if (dealAllDTO.getData() != null) {
           /* ImageAdapter imageAdapter = new ImageAdapter(ct, dealAllDTO);
            rcyl_image.setLayoutManager(new LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false));
            rcyl_image.setAdapter(imageAdapter);*/


            myViewPagerAdapter = new MyViewPagerAdapter();
            viewPager.setAdapter(myViewPagerAdapter);

            spring_dots_indicator.setViewPager(viewPager);
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


            if (dealAllDTO.getData().getIsFavourite().equalsIgnoreCase("1")) {
                is_favourite = true;
                iv_like.setImageResource(R.drawable.fav_sel);
            }

            if (dealAllDTO.getData().getSaveEvent().equalsIgnoreCase("1")) {
                is_save = true;
                tv_add_to_save.setText("REMOVE FROM SAVED");
                // tv_add_to_save.setTextColor(getResources().getColor(R.color.black));

            }

            tv_price.setText("$" + dealAllDTO.getData().getDealPrice());
            tv_offer.setText(dealAllDTO.getData().getDealDetail());
            deal_name.setText(dealAllDTO.getData().getDealTitle());
            tv_validity.setText("Valid upto : " + dealAllDTO.getData().getDealValidityTo());
            tv_timing.setText(dealAllDTO.getData().getDealTiming());
            tv_description.setText(Html.fromHtml(dealAllDTO.getData().getDealDescription()));
           // tv_description.setText(dealAllDTO.getData().getDealDescription());
            tv_max_person.setText("It's valid for " + dealAllDTO.getData().getDealPersonValid() + " persons");
            tv_timing.setText("Open " + dealAllDTO.getData().getDeal_start_time() + " to " + dealAllDTO.getData().getDeal_end_time());
            rating_num.setText(" " + dealAllDTO.getData().getDeal_rating());

            if (dealAllDTO.getData().getDealAddressLat() != null && !dealAllDTO.getData().getDealAddressLat().equalsIgnoreCase("")) {
                iv_direction.setVisibility(View.VISIBLE);
            } else {
                iv_direction.setVisibility(View.GONE);
            }

            try {
                LinearLayout llContainer = (LinearLayout) findViewById(R.id.linear);
                XLHRatingBar xlhRatingBar = new XLHRatingBar(this);
                xlhRatingBar.setNumStars(5);
                xlhRatingBar.setRating(Float.parseFloat("" + dealAllDTO.getData().getDeal_rating()));
                xlhRatingBar.setEnabled(false);//can not change rating by touch
                //  xlhRatingBar.setRatingViewClassName("com.example.xlhratingbar.MyRatingView4");
                xlhRatingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
                    @Override
                    public void onChange(float rating, int numStars) {
                        Toast.makeText(getApplicationContext(), "rating:" + rating, Toast.LENGTH_SHORT).show();
                    }
                });
                llContainer.addView(xlhRatingBar);
            } catch (Exception e) {
                e.printStackTrace();
            }

            checkDays();
        }
    }

    private void checkDays() {
        try {
            if(dealAllDTO.getData().getDeal_check_all()!= null && dealAllDTO.getData().getDeal_check_all().equalsIgnoreCase("1"))
            {

                everyday.setVisibility(View.VISIBLE);
                mon.setVisibility(View.GONE);
                tue.setVisibility(View.GONE);
                wed.setVisibility(View.GONE);
                thus.setVisibility(View.GONE);
                fri.setVisibility(View.GONE);
                sat.setVisibility(View.GONE);
                sun.setVisibility(View.GONE);


            }
            else
            {
                if (dealAllDTO.getData().getDeal_valid_days() != null && dealAllDTO.getData().getDeal_valid_days().size() > 0)
                {
                    for (int i = 0; i <dealAllDTO.getData().getDeal_valid_days().size() ; i++)
                    {
                        if(dealAllDTO.getData().getDeal_valid_days().get(i).contains("Mon"))
                        {
                            mon.setVisibility(View.VISIBLE);

                        }else if(dealAllDTO.getData().getDeal_valid_days().get(i).contains("Tue"))
                        {
                            tue.setVisibility(View.VISIBLE);
                        }else if(dealAllDTO.getData().getDeal_valid_days().get(i).contains("Wed"))
                        {
                            wed.setVisibility(View.VISIBLE);
                        }else if(dealAllDTO.getData().getDeal_valid_days().get(i).contains("Thu"))
                        {
                            thus.setVisibility(View.VISIBLE);
                        }else if(dealAllDTO.getData().getDeal_valid_days().get(i).contains("Fri"))
                        {
                            fri.setVisibility(View.VISIBLE);
                        }else if(dealAllDTO.getData().getDeal_valid_days().get(i).contains("Sat"))
                        {
                            sat.setVisibility(View.VISIBLE);
                        }else if(dealAllDTO.getData().getDeal_valid_days().get(i).contains("Sun"))
                        {
                            sun.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessSave(AddFavouriteDTO addFavouriteDTO) {
        tv_add_to_save.setText("REMOVE FROM SAVED");
        Toast.makeText(ct, "Event saved successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessDelete(AddFavouriteDTO addFavouriteDTO) {
        tv_add_to_save.setText("SAVE EVENT");
        Toast.makeText(ct, "Event removed from save", Toast.LENGTH_SHORT).show();
        //  tv_add_to_save.setTextColor(getResources().getColor(R.color.theme_text));
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        ImageView image;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.deal_info_img_item, container, false);
            container.addView(view);
            image = view.findViewById(R.id.iv_image);
            Picasso.with(ct).load(dealAllDTO.getData().getImages().get(position).getDeal_image()).into(image);


            return view;
        }

        @Override
        public int getCount() {
            return dealAllDTO.getData().getImages().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            // positions = position;
            container.removeView(view);
        }

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
           /* positions = arg0;
            if (arg0 < 2) {
                tv_next.setText("Next");
                tv_skip.setVisibility(View.VISIBLE);
            } else {
                tv_next.setText("Done");
                tv_skip.setVisibility(View.GONE);
            }
*/
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}