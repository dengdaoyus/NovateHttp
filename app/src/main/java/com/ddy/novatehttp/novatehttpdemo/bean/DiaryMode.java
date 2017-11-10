package com.ddy.novatehttp.novatehttpdemo.bean;

import java.util.List;

/**
 * 发布说说
 * Created by Administrator
 * on 2017/3/3 0003.
 */

public class DiaryMode {
    private String creatorId;
    private String city;
    private String diaryTitle;
    private List<DiaryContentInsertDomain> contents;
    private int relateDiaryId;//转发的日记的id
    private int relateState;//转发的状态 0正常 1删除

    public void setRelateState(int relateState) {
        this.relateState = relateState;
    }

    public void setRelateDiaryId(int relateDiaryId) {
        this.relateDiaryId = relateDiaryId;
    }

    public int getRelateState() {
        return relateState;
    }

    public int getRelateDiaryId() {
        return relateDiaryId;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    public void setContents(List<DiaryContentInsertDomain> contents) {
        this.contents = contents;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getCity() {
        return city;
    }

    public List<DiaryContentInsertDomain> getContents() {
        return contents;
    }



    public static class  DiaryContentInsertDomain {
        private String textOrPicture;
        private int sorting;
        private int contenType;
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

        public int getContenType() {
            return contenType;
        }

        public void setTextOrPicture(String textOrPicture) {
            this.textOrPicture = textOrPicture;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }

        public void setContenType(int contenType) {
            this.contenType = contenType;
        }

        public int getSorting() {
            return sorting;
        }

    }
}
