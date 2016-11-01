package com.het.sdk.demo.model.aroma;

import java.io.Serializable;

/**
 * Created by UUXIA on 2015/7/8.
 */
public class AromaRunModel  implements Serializable {
    private String deviceId;
    private int workMode;
    private int workStatus;
    private int setTimeH;
    private int setTimeM;
    private int remainingTimeH;
    private int remainingTimeM;
    private int warningStatus1;
    private int warningStatus2;
    private int presetStartupTimeH;
    private int presetStartupTimeM;
    private int presetStartupTimeLeftH;
    private int presetStartupTimeLeftM;
    private int presetShutdownTimeH;
    private int presetShutdownTimeM;
    private int presetShutdownTimeLeftH;
    private int presetShutdownTimeLeftM;
    private int light;
    private int color;
    private int outputLoad1;
    private int outputLoad2;
    private int mist;
    private int cumulativeTime1;
    private int cumulativeTime2;
    private int cumulativeTime3;
    private int cumulativeTime4;
    private int cumulativeWorkTimes1;
    private int cumulativeWorkTimes2;
    private int cumulativeWorkTimes3;

    public AromaRunModel() {
    }

    public int getPresetStartupTimeLeftH() {
        return presetStartupTimeLeftH;
    }

    public void setPresetStartupTimeLeftH(int presetStartupTimeLeftH) {
        this.presetStartupTimeLeftH = presetStartupTimeLeftH;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        this.workMode = workMode;
    }

    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus) {
        this.workStatus = workStatus;
    }

    public int getSetTimeH() {
        return setTimeH;
    }

    public void setSetTimeH(int setTimeH) {
        this.setTimeH = setTimeH;
    }

    public int getSetTimeM() {
        return setTimeM;
    }

    public void setSetTimeM(int setTimeM) {
        this.setTimeM = setTimeM;
    }

    public int getRemainingTimeH() {
        return remainingTimeH;
    }

    public void setRemainingTimeH(int remainingTimeH) {
        this.remainingTimeH = remainingTimeH;
    }

    public int getRemainingTimeM() {
        return remainingTimeM;
    }

    public void setRemainingTimeM(int remainingTimeM) {
        this.remainingTimeM = remainingTimeM;
    }

    public int getWarningStatus1() {
        return warningStatus1;
    }

    public void setWarningStatus1(int warningStatus1) {
        this.warningStatus1 = warningStatus1;
    }

    public int getWarningStatus2() {
        return warningStatus2;
    }

    public void setWarningStatus2(int warningStatus2) {
        this.warningStatus2 = warningStatus2;
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

    public int getPresetStartupTimeLeftM() {
        return presetStartupTimeLeftM;
    }

    public void setPresetStartupTimeLeftM(int presetStartupTimeLeftM) {
        this.presetStartupTimeLeftM = presetStartupTimeLeftM;
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

    public int getPresetShutdownTimeLeftH() {
        return presetShutdownTimeLeftH;
    }

    public void setPresetShutdownTimeLeftH(int presetShutdownTimeLeftH) {
        this.presetShutdownTimeLeftH = presetShutdownTimeLeftH;
    }

    public int getPresetShutdownTimeLeftM() {
        return presetShutdownTimeLeftM;
    }

    public void setPresetShutdownTimeLeftM(int presetShutdownTimeLeftM) {
        this.presetShutdownTimeLeftM = presetShutdownTimeLeftM;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getOutputLoad1() {
        return outputLoad1;
    }

    public void setOutputLoad1(int outputLoad1) {
        this.outputLoad1 = outputLoad1;
    }

    public int getOutputLoad2() {
        return outputLoad2;
    }

    public void setOutputLoad2(int outputLoad2) {
        this.outputLoad2 = outputLoad2;
    }

    public int getMist() {
        return mist;
    }

    public void setMist(int mist) {
        this.mist = mist;
    }

    public int getCumulativeTime1() {
        return cumulativeTime1;
    }

    public void setCumulativeTime1(int cumulativeTime1) {
        this.cumulativeTime1 = cumulativeTime1;
    }

    public int getCumulativeTime2() {
        return cumulativeTime2;
    }

    public void setCumulativeTime2(int cumulativeTime2) {
        this.cumulativeTime2 = cumulativeTime2;
    }

    public int getCumulativeTime3() {
        return cumulativeTime3;
    }

    public void setCumulativeTime3(int cumulativeTime3) {
        this.cumulativeTime3 = cumulativeTime3;
    }

    public int getCumulativeTime4() {
        return cumulativeTime4;
    }

    public void setCumulativeTime4(int cumulativeTime4) {
        this.cumulativeTime4 = cumulativeTime4;
    }

    public int getCumulativeWorkTimes1() {
        return cumulativeWorkTimes1;
    }

    public void setCumulativeWorkTimes1(int cumulativeWorkTimes1) {
        this.cumulativeWorkTimes1 = cumulativeWorkTimes1;
    }

    public int getCumulativeWorkTimes2() {
        return cumulativeWorkTimes2;
    }

    public void setCumulativeWorkTimes2(int cumulativeWorkTimes2) {
        this.cumulativeWorkTimes2 = cumulativeWorkTimes2;
    }

    public int getCumulativeWorkTimes3() {
        return cumulativeWorkTimes3;
    }

    public void setCumulativeWorkTimes3(int cumulativeWorkTimes3) {
        this.cumulativeWorkTimes3 = cumulativeWorkTimes3;
    }

    @Override
    public String toString() {
        return "AromaRunModel{" +
                "deviceId='" + deviceId + '\'' +
                ", workMode=" + workMode +
                ", workStatus=" + workStatus +
                ", setTimeH=" + setTimeH +
                ", setTimeM=" + setTimeM +
                ", remainingTimeH=" + remainingTimeH +
                ", remainingTimeM=" + remainingTimeM +
                ", warningStatus1=" + warningStatus1 +
                ", warningStatus2=" + warningStatus2 +
                ", presetStartupTimeH=" + presetStartupTimeH +
                ", presetStartupTimeM=" + presetStartupTimeM +
                ", presetStartupTimeLeftH=" + presetStartupTimeLeftH +
                ", presetStartupTimeLeftM=" + presetStartupTimeLeftM +
                ", presetShutdownTimeH=" + presetShutdownTimeH +
                ", presetShutdownTimeM=" + presetShutdownTimeM +
                ", presetShutdownTimeLeftH=" + presetShutdownTimeLeftH +
                ", presetShutdownTimeLeftM=" + presetShutdownTimeLeftM +
                ", light=" + light +
                ", color=" + color +
                ", outputLoad1=" + outputLoad1 +
                ", outputLoad2=" + outputLoad2 +
                ", mist=" + mist +
                ", cumulativeTime1=" + cumulativeTime1 +
                ", cumulativeTime2=" + cumulativeTime2 +
                ", cumulativeTime3=" + cumulativeTime3 +
                ", cumulativeTime4=" + cumulativeTime4 +
                ", cumulativeWorkTimes1=" + cumulativeWorkTimes1 +
                ", cumulativeWorkTimes2=" + cumulativeWorkTimes2 +
                ", cumulativeWorkTimes3=" + cumulativeWorkTimes3 +
                '}';
    }
}
