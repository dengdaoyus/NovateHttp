package com.ddy.novatehttp.novatehttpdemo.bean;

import java.util.List;

/**
 * SecretMode 秘密
 * Created by Administrator
 * on 2017/3/3 0003.
 */

public class SecretMode {

    private int userId;
    private String location;
    private String secretTitle;
    private List<SecretContentInsertModel> contents;

    public String getLocation() {
        return location;
    }

    public String getSecretTitle() {
        return secretTitle;
    }

    public int getUserId() {
        return userId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSecretTitle(String secretTitle) {
        this.secretTitle = secretTitle;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContents(List<SecretContentInsertModel> contents) {
        this.contents = contents;
    }


    public List<SecretContentInsertModel> getContents() {
        return contents;
    }



    public static class  SecretContentInsertModel {
        private String textOrPicture;
        private int sorting;
        private int contentType;
        private String extendData;

        public void setExtendData(String extendData) {
            this.extendData = extendData;
        }

        public String getExtendData() {
            return extendData;
        }

        public String getTextOrPicture() {
            return textOrPicture;
        }

        public int getContentType() {
            return contentType;
        }

        public void setTextOrPicture(String textOrPicture) {
            this.textOrPicture = textOrPicture;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public int getSorting() {
            return sorting;
        }

    }

}
