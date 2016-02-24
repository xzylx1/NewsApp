package com.xhit_nava.news.httpdata;

import java.util.List;

/**
 * Created by xhit-nava on 2016/1/12.
 */
public class NewsItem
{
    private String title;
    private String digest;
    private String source;
    private String lmodify;
    private String imgsrc;
    private List<AdsItem> ads;
    private List<ImgExtra> imgextra;

    public List<ImgExtra> getImgextra()
    {
        return imgextra;
    }

    public void setImgextra(List<ImgExtra> imgextra)
    {
        this.imgextra = imgextra;
    }

    public List<AdsItem> getAds()
    {
        return ads;
    }

    public void setAds(List<AdsItem> ads)
    {
        this.ads = ads;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDigest()
    {
        return digest;
    }

    public void setDigest(String digest)
    {
        this.digest = digest;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getLmodify()
    {
        return lmodify;
    }

    public void setLmodify(String lmodify)
    {
        this.lmodify = lmodify;
    }

    public String getImgsrc()
    {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc)
    {
        this.imgsrc = imgsrc;
    }
}
