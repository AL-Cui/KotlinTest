package com.makeinfo.flowerpi.bean;

/**
 * Created by cuiduo on 2017/5/5.
 */

public class BodyPost {
    private String controlKey;
    private String deviceId;
    private String userOpenId;
    private String deviceKind;
    private String sessionId;
    public  BodyPost(){

    }
    public BodyPost(String controlKey,String deviceId,String userOpenId,String deviceKind,String sessionId){
        this.controlKey = controlKey;
        this.deviceId = deviceId;
        this.deviceKind = deviceKind;
        this.userOpenId = userOpenId;
        this.sessionId = sessionId;

    }

    public String getControlKey() {
        return controlKey;
    }

    public void setControlKey(String controlKey) {
        this.controlKey = controlKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public String getDeviceKind() {
        return deviceKind;
    }

    public void setDeviceKind(String deviceKind) {
        this.deviceKind = deviceKind;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
