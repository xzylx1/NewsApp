package com.xhit_nava.news.tools;

/**
 * Created by xhit-nava on 2016/1/12.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.xhit_nava.news.R;
import com.xhit_nava.news.httpdata.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class ContentListAdapter extends BaseAdapter
{
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private List<NewsItem> mNewsItem = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public ContentListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<NewsItem> mItems) {
        mNewsItem = mItems;

        notifyDataSetChanged(); //通知刷新适配器数据源
    }

    @Override
    public int getCount() {
        return mNewsItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        NewsItem mItem = (NewsItem) getItem(position);
        if(mItem.getImgextra()!=null){
            return TYPE_TWO;
        }else{
            return TYPE_ONE;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == TYPE_TWO){
            return getTypeTwoView(position,convertView,parent);
        }else{
            return getTypeOneView(position,convertView,parent);
        }
    }

    public View getTypeTwoView(int position, View convertView, ViewGroup parent) {
        ViewTwoHodler viewTwoHodler = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_news_multipic_layout,null);

            viewTwoHodler = new ViewTwoHodler();
            viewTwoHodler.title_3pic = (TextView) convertView.findViewById(R.id.list_title_txt_multi);
            viewTwoHodler.icon1Img = (ImageView) convertView.findViewById(R.id.list_icon_img1_multi);
            viewTwoHodler.icon2Img = (ImageView) convertView.findViewById(R.id.list_icon_img2_multi);
            viewTwoHodler.icon3Img = (ImageView) convertView.findViewById(R.id.list_icon_img3_multi);
            convertView.setTag(viewTwoHodler);
        }
        viewTwoHodler = (ViewTwoHodler) convertView.getTag();
        NewsItem mItem = (NewsItem) getItem(position);
        viewTwoHodler.title_3pic.setText(mItem.getTitle());
        Picasso.with(context).load(mItem.getImgextra().get(0).getImgsrc()).into(viewTwoHodler.icon1Img);
        Picasso.with(context).load(mItem.getImgextra().get(1).getImgsrc()).into(viewTwoHodler.icon2Img);
        if(mItem.getImgextra().size()==3){
            Picasso.with(context).load(mItem.getImgextra().get(2).getImgsrc()).into(viewTwoHodler.icon3Img);
        }else {
            Picasso.with(context).load(mItem.getImgextra().get(0).getImgsrc()).into(viewTwoHodler.icon3Img);
        }
        return convertView;
    }

    public View getTypeOneView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if (convertView == null) {
            //一级优化
            convertView = layoutInflater.inflate(R.layout.item_news_singlepic_layout, null);
            //二级优化
            viewHodler = new ViewHodler();
            viewHodler.title = (TextView) convertView.findViewById(R.id.list_title_txt);
            viewHodler.digest = (TextView) convertView.findViewById(R.id.list_digest_txt);
            viewHodler.source = (TextView) convertView.findViewById(R.id.list_source_txt);
            viewHodler.lmodify = (TextView) convertView.findViewById(R.id.list_newstime_txt);
            viewHodler.imgsrc = (ImageView) convertView.findViewById(R.id.list_icon_img);
            convertView.setTag(viewHodler);
        }
        viewHodler = (ViewHodler) convertView.getTag();

        NewsItem mItem = (NewsItem) getItem(position);
        Picasso.with(context).load(mItem.getImgsrc()).into(viewHodler.imgsrc);
        viewHodler.title.setText(mItem.getTitle());
        viewHodler.digest.setText(mItem.getDigest());
        viewHodler.source.setText(mItem.getSource());
        viewHodler.lmodify.setText(mItem.getLmodify());

        return convertView;
    }

    static class ViewHodler {
        TextView title;
        TextView digest;
        TextView source;
        TextView lmodify;
        ImageView imgsrc;
    }

    static class ViewTwoHodler {
        TextView title_3pic;
        ImageView icon1Img;
        ImageView icon2Img;
        ImageView icon3Img;
    }
}
