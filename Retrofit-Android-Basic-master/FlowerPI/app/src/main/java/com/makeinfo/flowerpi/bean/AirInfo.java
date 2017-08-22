package com.makeinfo.flowerpi.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuiduo on 2017/5/5.
 */

public class AirInfo {
    @SerializedName("errcode")
    @Expose
    private String errCode;

    @SerializedName("errmsg")
    @Expose
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
