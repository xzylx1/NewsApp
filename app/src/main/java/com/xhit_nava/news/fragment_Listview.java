package com.xhit_nava.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.Indicators.PagerIndicator;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.warmtel.android.xlistview.XListView;
import com.xhit_nava.news.httpdata.AdsItem;
import com.xhit_nava.news.httpdata.NewsItem;
import com.xhit_nava.news.httpdata.NewsItemList;
import com.xhit_nava.news.tools.ContentListAdapter;
import cz.msebera.android.httpclient.Header;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_Listview extends Fragment
{
    private static final String ARG = "Key_Url";
    private String news_type_id;
    private int pageNo =0, pageSize=10;
    private List<NewsItem> mNewsItem;
    private XListView mListView;
    private ProgressBar mProgressBar;
    private SliderLayout mSliderLayout;
    private String mHttpUrl;
    private ContentListAdapter mContentListAdapter;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static fragment_Listview newInstance() {
        fragment_Listview fragment = new fragment_Listview();
        return fragment;
    }

    private void seturlHttpPage(int num){
        pageNo = num;
        if (news_type_id == "T1348647909107") {
            mHttpUrl = "http://c.m.163.com/nc/article/headline/" + news_type_id + "/" + pageNo * pageSize + "-" + pageSize + "" + ".html";}
        else {mHttpUrl = "http://c.m.163.com/nc/article/list/" + news_type_id + "/" + pageNo * pageSize + "-" + pageSize + "" + ".html";}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_news, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar = (ProgressBar) getView().findViewById(R.id.merchant_progressbar);
        mListView = (XListView) getView().findViewById(R.id.xlist_listview);
        mListView.setDividerHeight(0);

        View sliderHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.sliding_image_layout, null);
        mSliderLayout = (SliderLayout) sliderHeaderView.findViewById(R.id.slider_imager);
        mListView.addHeaderView(sliderHeaderView);

        news_type_id = getArguments().getString(ARG);
        seturlHttpPage(0);
        getAsyscDataList();

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setClickable(true);
        mSliderLayout.setCustomIndicator((PagerIndicator) getView().findViewById(R.id.custom_pagerindicator));

        mContentListAdapter = new ContentListAdapter(getActivity());
        mListView.setAdapter(mContentListAdapter);
        mListView.setEmptyView(mProgressBar);

        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(new XListView.IXListViewListener()
        {
            @Override
            public void onRefresh() {
                seturlHttpPage(pageNo);
                getAsyscDataList();
            }

            @Override
            public void onLoadMore() {
                pageNo = pageNo+1;
                seturlHttpPage(pageNo);
                getAsyscDataList();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSliderLayout != null) {
            mSliderLayout.stopAutoCycle();
            mSliderLayout = null;
        }
    }

    private void setData(List<AdsItem> mAdsItem)
    {
        for (final AdsItem mItems : mAdsItem)
        {
            String url = mItems.getImgsrc();
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(mItems.getTitle());
            textSliderView.image(url);
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener()
            {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView)
                {
                    Toast.makeText(getActivity(), mItems.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            mSliderLayout.addSlider(textSliderView);
        }

    }

    public void getAsyscDataList()
    {
        asyncHttpClient.get(mHttpUrl, new TextHttpResponseHandler()
        {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                mListView.stopRefresh();
                mListView.stopLoadMore();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s)
            {
                Gson gson = new Gson();
                NewsItemList mNewsItemList = gson.fromJson(s, NewsItemList.class);
                if (pageNo == 0)
                {
                    Log.e("-------------------", "第一次加载: "+news_type_id);
                    mSliderLayout.removeAllSliders();
                    mNewsItem = mNewsItemList.getTheData(news_type_id);
                    setData(mNewsItem.get(0).getAds());
                    Log.e("====================", "数据源大小: " + mNewsItem.size());
                }
                else
                {
                    Log.e("====================", "第二次加载: "+news_type_id);
                    mNewsItem.addAll(mNewsItemList.getTheData(news_type_id));
                    Log.e("====================", "数据源大小: " + mNewsItem.size());
                }
                mContentListAdapter.setList(mNewsItem);
                if(pageNo!=0){
                    Toast.makeText(getContext(),"更新成功！",Toast.LENGTH_SHORT).show();
                }
                mListView.setRefreshTime(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis()));
                mListView.stopRefresh();
                mListView.stopLoadMore();
            }
        });
    }
}
