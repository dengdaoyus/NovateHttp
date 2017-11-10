package com.ddy.novatehttp.novatehttpdemo.bean;

import java.util.List;

/**
 * Created by Administrator
 * on 2017/1/18 0018.
 */

public class TalkRequestModel {

    /**
     * content : hahahah
     * longitude : 11.1
     * latitude : 1.2
     * creatorId : 5
     * city : 成都
     * talkResource : [{"url":"aaaa","picOrder":"1"},{"url":"bbbb","picOrder":"2"}]
     */

    private String content;
    private String longitude;
    private String latitude;
    private String creatorId;
    private String city;
    private String relateTalkId;
    private String relateState;
    private String mediaType; //2  视频   3  图片

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    private List<TalkResourceBean> talkResource;

    public String getContent() {
        return content;
    }

    public String getRelateTalkId() {
        return relateTalkId;
    }

    public void setRelateTalkId(String relateTalkId) {
        this.relateTalkId = relateTalkId;
    }

    public String getRelateState() {
        return relateState;
    }

    public void setRelateState(String relateState) {
        this.relateState = relateState;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<TalkResourceBean> getTalkResource() {
        return talkResource;
    }

    public void setTalkResource(List<TalkResourceBean> talkResource) {
        this.talkResource = talkResource;
    }

    public static class TalkResourceBean {
        /**
         * url : aaaa
         * picOrder : 1
         */

        private String url;
        private String picOrder;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicOrder() {
            return picOrder;
        }

        public void setPicOrder(String picOrder) {
            this.picOrder = picOrder;
        }

        @Override
        public String toString() {
            return "TalkResourceBean{" +
                    "url='" + url + '\'' +
                    ", picOrder='" + picOrder + '\'' +
                    '}';
        }
    }
}
