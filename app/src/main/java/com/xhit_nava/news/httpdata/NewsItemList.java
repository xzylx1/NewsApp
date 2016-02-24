package com.xhit_nava.news.httpdata;

import java.util.List;

/**
 * Created by xhit-nava on 2016/1/12.
 */
public class NewsItemList
{
    private List<NewsItem> T1348647909107;
    private List<NewsItem> T1348648517839;
    private List<NewsItem> T1348649079062;
    private List<NewsItem> T1348648756099;
    private List<NewsItem> T1348649580692;

    public List<NewsItem> getTheData(String str)
    {
        switch (str){
            case "T1348647909107":
                return T1348647909107;
            case "T1348648517839":
                return T1348648517839;
            case "T1348649079062":
                return T1348649079062;
            case "T1348648756099":
                return T1348648756099;
            case "T1348649580692":
                return T1348649580692;
        }
        return null;
    }

    public void setTheData(List<NewsItem> mNewsItem)
    {
        T1348647909107 = mNewsItem;
    }
}
