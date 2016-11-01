package com.het.sdk.demo.biz.hunidifie.packet;

import android.content.Context;

import com.het.sdk.demo.model.humidifier.HumidifierConfigModel;
import com.het.sdk.demo.model.humidifier.HumidifierRunDataModel;

import java.util.List;

/**
 * wifi设备子类之加湿器
 *
 * @author Lee 李钊平 特性： 1，获取各种属性，包括从云端和本地数据库获取 2，空调可操控动作的封装，包括： 设置开关 设置亮度 设置计划关机
 *
 */
public class HumidifierDev {

    private static final long serialVersionUID = 1988617843721787054L;
    private HumidifierConfigModel config;
    private HumidifierRunDataModel runData;

    public HumidifierDev() {
        super();
    }

    public HumidifierConfigModel getConfig() {
        return config;
    }

    public void setConfig(HumidifierConfigModel config) {
        this.config = config;
    }

    public HumidifierRunDataModel getRunData() {
        return runData;
    }

    public void setRunData(HumidifierRunDataModel runData) {
        this.runData = runData;
    }

    /*****************************************************************************************
     * 以下是操控设备的方法
     */

    public static enum Brightness {
        BRIGHTNESS_FULL, BRIGHTNESS_HALF, BRIGHTNESS_CLOSE
    }

    static final String VALUE_POWER_ON = "1";
    static final String VALUE_POWER_OFF = "2";
    static final String VALUE_BRIGHTNESS_FULL = "1";
    static final String VALUE_BRIGHTNESS_HALF = "2";
    static final String VALUE_BRIGHTNESS_CLOSE = "3";

    /**
     * 打开设备
     */
    public HumidifierConfigModel openDevice(List<Integer> list) {
        if (config != null) {
            config.setMist(VALUE_POWER_ON);
            config.setLevel("1");
            config.setLight("1");

            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        return config;
    }

    /**
     * 关闭设备
     */
    public HumidifierConfigModel closeDevice(Context context, List<Integer> list) {
        if (config != null) {
            config.setMist(VALUE_POWER_OFF);
            config.setLevel("0");
            config.setLight(VALUE_BRIGHTNESS_CLOSE);
            config.setTimerPresetTime("0");
//            SharePreferencesUtil.removeKey(context, "HumidifierOrderTime");
//            SharePreferencesUtil.removeKey(context, "HumidifierExitTime");
            // config.setAppointmentOffTime("0");

            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        return config;
    }



    /**
     * 设置喷雾
     */
    public HumidifierConfigModel setMist(String mist,List<Integer> list) {
        if (config != null) {
            config.setMist(mist);
            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));


        }
        return config;
    }

    /**
     * 设置喷雾
     */
    public HumidifierConfigModel setlevel(String level) {
        if (config != null) {
            config.setLevel(level);
        }
        return config;
    }

    public HumidifierConfigModel setFlag(List<Integer> list){
        if (config != null) {
            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        return config;
    }




    /**
     * 设置定时时间
     *
     * @param time
     *            分钟
     */
    public HumidifierConfigModel setTimerPresetTime(String time,
                                                    List<Integer> list) {
        if (config != null) {
            config.setTimerPresetTime(time);
            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        return config;
    }

    /**
     * @Title: setHumidifierColor
     * @Description: 设置加湿器颜色
     * @param: @param color
     * @param: @param listener
     * @return: void
     * @throws
     */
    public HumidifierConfigModel setHumidifierColor(String color,
                                                    List<Integer> list) {
        if (config != null) {
            config.setColor(color);
            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        return config;
    }

    /**
     * 设置预约开机时间
     *
     * @param time
     *            分钟
     */
    public HumidifierConfigModel setAppointmentBootTime(String time,
                                                        List<Integer> list) {
        if (config != null) {
            config.setAppointmentBootTime(time);
            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        return config;
    }

    /**
     * 设置预约关机时间
     *
     * @param time
     *            分钟
     */
    public HumidifierConfigModel setAppointmentOffTime(String time,
                                                       List<Integer> list) {
        if (config != null) {
            config.setAppointmentOffTime(time);
            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        return config;
    }

    public HumidifierConfigModel setBrightness(Brightness lightLevel,
                                               List<Integer> list) {
        if (config != null) {
            byte userBehavior = HumidifierOutPacket.getByteBit(list);
            config.setUpdateFlag(String.valueOf(userBehavior));
        }
        switch (lightLevel) {
            case BRIGHTNESS_FULL:
                if (config != null) {
                    config.setLight(VALUE_BRIGHTNESS_FULL);
                }
                break;
            case BRIGHTNESS_HALF:
                if (config != null) {
                    config.setLight(VALUE_BRIGHTNESS_HALF);
                }
                break;
            case BRIGHTNESS_CLOSE:
                if (config != null) {
                    config.setLight(VALUE_BRIGHTNESS_CLOSE);
                }
                break;
            default:
                break;
        }
        return config;
    }
}
