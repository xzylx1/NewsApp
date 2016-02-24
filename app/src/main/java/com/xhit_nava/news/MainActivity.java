package com.xhit_nava.news;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;
import com.xhit_nava.news.tools.ViewPagerIndicator;
import com.xhit_nava.news.tools.getBitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends SlidingActivity
{
    private static final String ARG = "Key_Url";
    private int tabWidth;
    private Fragment fragment;
    private LinearLayout mLayout;
    private ViewPager mViewPager;
    private ImageView head_icon_login;
    private ImageView add_news_item_ima;
    private NewsFragmentAdapter mAdapter;
    private ViewPagerIndicator mIndicator;
    private HorizontalScrollView horizontalScrollView;
    private List<View> mDatas= new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = Arrays.asList("头条新闻", "娱乐新闻", "体育新闻", "财经新闻","科技新闻");
    private String[] newsUrl = {"T1348647909107", "T1348648517839", "T1348649079062", "T1348648756099", "T1348649580692"};

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.item_listview_layout);
        setBehindContentView(R.layout.fragment_menu_layout);

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        mLayout = (LinearLayout) findViewById(R.id.news_list_llout);
        mViewPager = (ViewPager) findViewById(R.id.news_list_viewpaper);
        add_news_item_ima = (ImageView) findViewById(R.id.add_news_item_imageview);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.news_list_indicator);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.news_list_HSV);
        add_news_item_ima.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),MyService.class);
                intent.putExtra("Url","下载数据");
                startService(intent);
            }
        });
                tabWidth = this.getResources().getDimensionPixelOffset(R.dimen.tab_width);

        for (int i = 0; i < list.size(); i++) {
    //        mDatas.add(new View(this));
            TextView textView = new TextView(this);
            textView.setText(list.get(i));
            Log.e("-----------------------", "onCreate: " + list.get(i));
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    tabWidth, LinearLayout.LayoutParams.FILL_PARENT);
            mLayout.addView(textView,layoutParams);
        }
        Log.e("===========", "onCreate: "+mDatas.toString());
        mIndicator.setTabItemTitles(mDatas);
        mIndicator.setChildViewWidth(tabWidth);
        mIndicator.setHorizontalScrollView(horizontalScrollView);

        for (String str:newsUrl) {
            Bundle bundle = new Bundle();
            bundle.putString(ARG, str);
            fragment = fragment_Listview.newInstance();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        mAdapter = new NewsFragmentAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mIndicator.setViewPager(mViewPager, 0);

        head_icon_login = (ImageView) findViewById(R.id.head_icon_login);
        head_icon_login.setImageBitmap(getBitmap.getCircleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.head_icon)));
    }


    public static class NewsFragmentAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> list = new ArrayList<>();

        public NewsFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            list = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
