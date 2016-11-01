package com.het.sdk.demo.model.aroma;

import java.io.Serializable;

/**
 * Created by UUXIA on 2015/7/8.
 */
public class AromaConfigModel  implements Serializable {
    private String deviceId;
    private int sourceFlag;
    /**
     */
    private int mist;
    /**
     */
    private int light;
    /**
     */
    private int timeCloseH;
    /**
     */
    private int timeCloseM;
    /**
     */
    private int presetStartupTimeH;
    /**
     */
    private int presetStartupTimeM;
    /**
     */
    private int presetShutdownTimeH;
    /**
     */
    private int presetShutdownTimeM;
    /**
     */
    private int color;
    /**
     */
    private int updateFlag;

    public AromaConfigModel() {
    }

    public int getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(int sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public int getMist() {
        return mist;
    }

    public void setMist(int mist) {
        this.mist = mist;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getTimeCloseH() {
        return timeCloseH;
    }

    public void setTimeCloseH(int timeCloseH) {
        this.timeCloseH = timeCloseH;
    }

    public int getTimeCloseM() {
        return timeCloseM;
    }

    public void setTimeCloseM(int timeCloseM) {
        this.timeCloseM = timeCloseM;
    }

    public int getPresetStartupTimeH() {
        return presetStartupTimeH;
    }

    public void setPresetStartupTimeH(int presetStartupTimeH) {
        this.presetStartupTimeH = presetStartupTimeH;
    }

    public int getPresetStartupTimeM() {
        return presetStartupTimeM;
    }

    public void setPresetStartupTimeM(int presetStartupTimeM) {
        this.presetStartupTimeM = presetStartupTimeM;
    }

    public int getPresetShutdownTimeH() {
        return presetShutdownTimeH;
    }

    public void setPresetShutdownTimeH(int presetShutdownTimeH) {
        this.presetShutdownTimeH = presetShutdownTimeH;
    }

    public int getPresetShutdownTimeM() {
        return presetShutdownTimeM;
    }

    public void setPresetShutdownTimeM(int presetShutdownTimeM) {
        this.presetShutdownTimeM = presetShutdownTimeM;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "AromaConfigModel{" +
                "deviceId='" + deviceId + '\'' +
                ", sourceFlag=" + sourceFlag +
                ", mist=" + mist +
                ", light=" + light +
                ", timeCloseH=" + timeCloseH +
                ", timeCloseM=" + timeCloseM +
                ", presetStartupTimeH=" + presetStartupTimeH +
                ", presetStartupTimeM=" + presetStartupTimeM +
                ", presetShutdownTimeH=" + presetShutdownTimeH +
                ", presetShutdownTimeM=" + presetShutdownTimeM +
                ", color=" + color +
                ", updateFlag=" + updateFlag +
                '}';
    }
}
