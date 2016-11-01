package com.het.sdk.demo.model.humidifier;

import java.io.Serializable;

/**
 * 加湿机运行数据
 * @author sunny
 * @date 2015年4月14日 上午10:38:38
 */
public class HumidifierRunDataModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    private String humidifierId;
    private String recordTime;
    private String mode;
    private String color;
    private String workMode;
    private String workStatus;
    private String timing;
    private String outputLoad1;
    private String outputLoad2;
    private String warningStatus;
    private String remainingTime;
    private String cumulativeWorkTimes;
    private String cumulativeTime;
    private String connStatus;
    private String mist;
    private String light;
    private String level;

    /**
     * @Title: getHumidifierId
     * @Description: TODO
     * @return String
     */
    public String getHumidifierId() {
        return humidifierId;
    }
    /**
     * @Title: setHumidifierId
     * @Description: TODO
     * @return: String
     */
    public void setHumidifierId(String humidifierId) {
        this.humidifierId = humidifierId;
    }
    /**
     * @Title: getRecordTime
     * @Description: TODO
     * @return String
     */
    public String getRecordTime() {
        return recordTime;
    }
    /**
     * @Title: setRecordTime
     * @Description: TODO
     * @return: String
     */
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
    /**
     * @Title: getMode
     * @Description: TODO
     * @return String
     */
    public String getMode() {
        return mode;
    }
    /**
     * @Title: setMode
     * @Description: TODO
     * @return: String
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
    /**
     * @Title: getColor
     * @Description: TODO
     * @return String
     */
    public String getColor() {
        return color;
    }
    /**
     * @Title: setColor
     * @Description: TODO
     * @return: String
     */
    public void setColor(String color) {
        this.color = color;
    }
    /**
     * @Title: getWorkMode
     * @Description: TODO
     * @return String
     */
    public String getWorkMode() {
        return workMode;
    }
    /**
     * @Title: setWorkMode
     * @Description: TODO
     * @return: String
     */
    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }
    /**
     * @Title: getWorkStatus
     * @Description: TODO
     * @return String
     */
    public String getWorkStatus() {
        return workStatus;
    }
    /**
     * @Title: setWorkStatus
     * @Description: TODO
     * @return: String
     */
    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }
    /**
     * @Title: getTiming
     * @Description: TODO
     * @return String
     */
    public String getTiming() {
        return timing;
    }
    /**
     * @Title: setTiming
     * @Description: TODO
     * @return: String
     */
    public void setTiming(String timing) {
        this.timing = timing;
    }
    /**
     * @Title: getOutputLoad1
     * @Description: TODO
     * @return String
     */
    public String getOutputLoad1() {
        return outputLoad1;
    }
    /**
     * @Title: setOutputLoad1
     * @Description: TODO
     * @return: String
     */
    public void setOutputLoad1(String outputLoad1) {
        this.outputLoad1 = outputLoad1;
    }
    /**
     * @Title: getOutputLoad2
     * @Description: TODO
     * @return String
     */
    public String getOutputLoad2() {
        return outputLoad2;
    }
    /**
     * @Title: setOutputLoad2
     * @Description: TODO
     * @return: String
     */
    public void setOutputLoad2(String outputLoad2) {
        this.outputLoad2 = outputLoad2;
    }
    /**
     * @Title: getWarningStatus
     * @Description: TODO
     * @return String
     */
    public String getWarningStatus() {
        return warningStatus;
    }
    /**
     * @Title: setWarningStatus
     * @Description: TODO
     * @return: String
     */
    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }
    /**
     * @Title: getRemainingTime
     * @Description: TODO
     * @return String
     */
    public String getRemainingTime() {
        return remainingTime;
    }
    /**
     * @Title: setRemainingTime
     * @Description: TODO
     * @return: String
     */
    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }
    /**
     * @Title: getCumulativeWorkTimes
     * @Description: TODO
     * @return String
     */
    public String getCumulativeWorkTimes() {
        return cumulativeWorkTimes;
    }
    /**
     * @Title: setCumulativeWorkTimes
     * @Description: TODO
     * @return: String
     */
    public void setCumulativeWorkTimes(String cumulativeWorkTimes) {
        this.cumulativeWorkTimes = cumulativeWorkTimes;
    }
    /**
     * @Title: getCumulativeTime
     * @Description: TODO
     * @return String
     */
    public String getCumulativeTime() {
        return cumulativeTime;
    }
    /**
     * @Title: setCumulativeTime
     * @Description: TODO
     * @return: String
     */
    public void setCumulativeTime(String cumulativeTime) {
        this.cumulativeTime = cumulativeTime;
    }
    /**
     * @Title: getConnStatus
     * @Description: TODO
     * @return String
     */
    public String getConnStatus() {
        return connStatus;
    }
    /**
     * @Title: setConnStatus
     * @Description: TODO
     * @return: String
     */
    public void setConnStatus(String connStatus) {
        this.connStatus = connStatus;
    }
    public String getMist() {
        return mist;
    }
    public void setMist(String mist) {
        this.mist = mist;
    }
    public String getLight() {
        return light;
    }
    public void setLight(String light) {
        this.light = light;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @Title: toString
     * @Description: TODO
     * @return
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "HumidifierRunDataModel [id=" + id + ", humidifierId="
                + humidifierId + ", recordTime=" + recordTime + ", mode="
                + mode + ", color=" + color + ", workMode=" + workMode
                + ", workStatus=" + workStatus + ", timing=" + timing
                + ", outputLoad1=" + outputLoad1 + ", outputLoad2="
                + outputLoad2 + ", warningStatus=" + warningStatus
                + ", remainingTime=" + remainingTime + ", cumulativeWorkTimes="
                + cumulativeWorkTimes + ", cumulativeTime=" + cumulativeTime
                + ", connStatus=" + connStatus + ", mist=" + mist + ", light="
                + light + ", level=" + level + "]";
    }
}
