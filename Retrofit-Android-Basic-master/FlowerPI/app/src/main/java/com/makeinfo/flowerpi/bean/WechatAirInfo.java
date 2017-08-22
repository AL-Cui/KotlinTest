package com.makeinfo.flowerpi.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuiduo on 2017/5/12.
 */

public class WechatAirInfo {
    private String models;
    private String indoor;
    private String pm;
    private String humidity;
    private String outside;
    private String dust;
    private String smell;

    public void setModels(String models) {
        this.models = models;
    }

    public void setIndoor(String indoor) {
        this.indoor = indoor;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setOutside(String outside) {
        this.outside = outside;
    }

    public void setDust(String dust) {
        this.dust = dust;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getModels() {

        return models;
    }

    public String getIndoor() {
        return indoor;
    }

    public String getPm() {
        return pm;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getOutside() {
        return outside;
    }

    public String getDust() {
        return dust;
    }

    public String getSmell() {
        return smell;
    }
}
