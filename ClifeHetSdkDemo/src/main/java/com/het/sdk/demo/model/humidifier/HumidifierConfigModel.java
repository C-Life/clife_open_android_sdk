package com.het.sdk.demo.model.humidifier;

import java.io.Serializable;

/**
 * 加湿机配置信息
 * @author sunny
 * @date 2015年4月14日 上午10:31:27
 */
public class HumidifierConfigModel implements Serializable, Cloneable{

    private static final long serialVersionUID = 1L;

    private String color="0";
    private String mist="2";
    private String light="3";
    private String level="1";
    private String updateFlag;
    private String timerPresetTime="0";
    private String appointmentBootTime="0";
    private String appointmentOffTime="0";

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
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
    public String getUpdateFlag() {
        return updateFlag;
    }
    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }
    public String getTimerPresetTime() {
        return timerPresetTime;
    }
    public void setTimerPresetTime(String timerPresetTime) {
        this.timerPresetTime = timerPresetTime;
    }
    public String getAppointmentBootTime() {
        return appointmentBootTime;
    }
    public void setAppointmentBootTime(String appointmentBootTime) {
        this.appointmentBootTime = appointmentBootTime;
    }
    public String getAppointmentOffTime() {
        return appointmentOffTime;
    }
    public void setAppointmentOffTime(String appointmentOffTime) {
        this.appointmentOffTime = appointmentOffTime;
    }

//    @Override
//    public byte[] paserM2B(Object object) {
//        byte[] bytes = HumidifierOutPacket.toConfigBytes((HumidifierConfigModel) object);
//        return bytes;
//    }
//
//    @Override
//    public Object paserB2M(int cmd, byte[] b) {
//        Object obj = null;
//        if (cmd == 1) {
//            obj = HumidifierInPacket.toConfigModel(b);
//        }else if(cmd ==2 ){
//            obj = HumidifierInPacket.toRunModel(b);
//        }
//        return obj;
//    }
}
