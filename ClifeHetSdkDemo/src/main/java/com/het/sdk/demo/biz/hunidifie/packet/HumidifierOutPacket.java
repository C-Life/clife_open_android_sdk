package com.het.sdk.demo.biz.hunidifie.packet;

import android.text.TextUtils;

import com.het.sdk.demo.model.humidifier.HumidifierConfigModel;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.List;

/**
 * Created by admin on 2015/6/6.
 */
public class HumidifierOutPacket {

    /**
     * 封装控制数据报文协议
     *
     * @param conf
     * @return
     */
    public static byte[] toConfigBytes(HumidifierConfigModel conf) {
        if (conf != null) {
            ByteBuffer buf = ByteBuffer.allocate(22);
            putConfig(conf, buf);
            buf.flip();
            byte[] configBytes = buf.array();
            return configBytes;
        }
        return null;
    }

    /**
     * 封装配置数据
     *
     * @param buf
     */
    private static void putConfig(HumidifierConfigModel humidifierConfigModel,
                                  ByteBuffer buf) {
        if (humidifierConfigModel != null) {
            // 1.小循环
            buf.put((byte) 0x60);
            Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int second = c.get(Calendar.SECOND);

            // 2.3.4时间戳
            buf.put((byte) hour);
            buf.put((byte) minute);
            buf.put((byte) second);

            // 5 数据类型设置0x01
            buf.put((byte) 0x01);
            // 6 数据序列号 0x00
            buf.put((byte) 0x00);
            // 7 MIST键设置
            String mistStr = humidifierConfigModel.getMist();
            if (!TextUtils.isEmpty(mistStr)) {
                int mistInt = Integer.valueOf(mistStr);
                byte mist = (byte) mistInt;
                buf.put(mist);
            } else {
                buf.put((byte) 0x00);
            }
            // 8 LIGHT键设置
            String lightStr = humidifierConfigModel.getLight();
            if (!TextUtils.isEmpty(lightStr)) {
                int lightInt = Integer.valueOf(lightStr);
                byte light = (byte) lightInt;
                buf.put(light);
            } else {
                buf.put((byte) 0x00);
            }
            // 9 HIGH/LOW
            String levelStr = humidifierConfigModel.getLevel();
            if (!TextUtils.isEmpty(levelStr)) {
                int levelInt = Integer.valueOf(levelStr);
                byte level = (byte) levelInt;
                buf.put((byte) level);
            } else {
                buf.put((byte) 0x00);
            }
            // 10 加湿定时预设时间（小时）
            String timerPresetTimeStr = humidifierConfigModel
                    .getTimerPresetTime();
            if (!TextUtils.isEmpty(timerPresetTimeStr)) {
                int timerPresetTimeInt = Integer.valueOf(timerPresetTimeStr);
                int timerPresetTimeHours = timerPresetTimeInt / 60;
                buf.put((byte) timerPresetTimeHours);
                // 11 加湿定时预设时间（分钟）
                int timerPresetTimeMinutes = timerPresetTimeInt % 60;
                buf.put((byte) timerPresetTimeMinutes);
            } else {
                buf.put((byte) 0x00);
                buf.put((byte) 0x00);
            }
            // 12 加湿预约开机时间设置（小时）
            String appointmentBootTimeStr = humidifierConfigModel
                    .getAppointmentBootTime();
            if (!TextUtils.isEmpty(appointmentBootTimeStr)) {
                int appointmentBootTimeInt = Integer
                        .valueOf(appointmentBootTimeStr);
                int appointmentBootTimeHours = appointmentBootTimeInt / 60;
                buf.put((byte) appointmentBootTimeHours);
                // 13 加湿预约开机时间设置（分钟）
                int appointmentBootTimeMinutes = appointmentBootTimeInt % 60;
                buf.put((byte) appointmentBootTimeMinutes);
            } else {
                buf.put((byte) 0x00);
                buf.put((byte) 0x00);
            }
            // 14 加湿预约关机时间设置（小时）
            String appointmentOffTimeStr = humidifierConfigModel
                    .getAppointmentOffTime();
            if (!TextUtils.isEmpty(appointmentOffTimeStr)) {
                int appointmentOffTimeInt = Integer
                        .valueOf(appointmentOffTimeStr);
                int hours = appointmentOffTimeInt / 60;
                buf.put((byte) hours);
                // 15 加湿预约关机时间设置（分钟）
                int minutes = appointmentOffTimeInt % 60;
                buf.put((byte) minutes);
            } else {
                buf.put((byte) 0x00);
                buf.put((byte) 0x00);
            }
            // 16 保留
            buf.put((byte) 0x00);
            // 17 保留
            buf.put((byte) 0x00);
            // 18 保留
            buf.put((byte) 0x00);
            // 19 保留
            buf.put((byte) 0x00);
            // 20 七彩灯的颜色选择（仅0x03机型有用，0x02机型发0x00）
            String colorStr = humidifierConfigModel.getColor();
            if (!TextUtils.isEmpty(colorStr)) {
                int colorInt = Integer.valueOf(colorStr);
                byte color = (byte) colorInt;
                buf.put(color);
            } else {
                buf.put((byte) 0x00);
            }
            // 21 保留
            buf.put((byte) 0x00);
            // 22 用户行为统计
            int updateFlagInt = Integer.valueOf(humidifierConfigModel
                    .getUpdateFlag());
            byte updateFlag = (byte) updateFlagInt;
            buf.put(updateFlag);
        }
    }

    /*
     * 用户行为分析
     */
    public static byte getByteBit(List<Integer> list) {
        int flag = 0;
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 7:
                    flag |= (list.contains(7) ? (1 << i) : (0 << i));
                    break;
                case 6:
                    flag |= (list.contains(6) ? (1 << i) : (0 << i));
                    break;
                case 5:
                    flag |= (list.contains(5) ? (1 << i) : (0 << i));
                    break;
                case 4:
                    flag |= (list.contains(4) ? (1 << i) : (0 << i));
                    break;
                case 3:
                    flag |= (list.contains(3) ? (1 << i) : (0 << i));
                    break;
                case 2:
                    flag |= (list.contains(2) ? (1 << i) : (0 << i));
                    break;
                case 1:
                    flag |= (list.contains(1) ? (1 << i) : (0 << i));
                    break;
                case 0:
                    flag |= (list.contains(0) ? (1 << i) : (0 << i));
                    break;
                default:
                    break;
            }
        }
        return (byte) flag;
    }
}
