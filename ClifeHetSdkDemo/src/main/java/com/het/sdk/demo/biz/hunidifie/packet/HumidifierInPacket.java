package com.het.sdk.demo.biz.hunidifie.packet;


import com.het.sdk.demo.model.humidifier.HumidifierConfigModel;
import com.het.sdk.demo.model.humidifier.HumidifierRunDataModel;

import java.nio.ByteBuffer;

/**
 * Created by admin on 2015/6/6.
 */
public class HumidifierInPacket {

    /**
     * 设备控制数据解析
     *
     * @param confBytes
     * @return
     */
    public static HumidifierConfigModel toConfigModel(byte[] confBytes) {
        if (confBytes != null) {
            ByteBuffer buf = ByteBuffer.allocate(confBytes.length);
            HumidifierConfigModel conf = new HumidifierConfigModel();
            buf.put(confBytes);
            buf.flip();
            parseConfig(conf, buf);
            return conf;
        }
        return null;
    }

    /**
     * 设备运行数据解析
     *
     * @param runBytes
     * @return
     */
    public static HumidifierRunDataModel toRunModel(byte[] runBytes) {
        if (runBytes != null) {
            ByteBuffer buf = ByteBuffer.allocate(runBytes.length);
            HumidifierRunDataModel run = new HumidifierRunDataModel();
            buf.put(runBytes);
            buf.flip();
            parseRun(run, buf);
            return run;
        }
        return null;
    }

    /**
     * 解析控制数据 13
     */
    private static void parseConfig(HumidifierConfigModel conf, ByteBuffer buf) {
        // 1.2. 标识app
        buf.get();
        buf.get();
        // 3.4. 发包时间
        buf.get();
        buf.get();
        // 5 数据类型设置0x01
        buf.get();
        // 6 数据序列号 0x00
        buf.get();
        // 7 MIST键设置
        int mistInt = buf.get();
        String mist = String.valueOf(mistInt);
        conf.setMist(mist);
        // 8 LIGHT键设置
        int lightInt = buf.get();
        String light = String.valueOf(lightInt);
        conf.setLight(light);
        // 9 HIGH/LOW
        int levelInt = buf.get();
        String level = String.valueOf(levelInt);
        conf.setLevel(level);
        // 10 加湿定时预设时间（小时）
        int timerPresetTimeHours = buf.get();
        // 11 加湿定时预设时间（分钟）
        int timerPresetTimeMinutes = buf.get();
        int minutes = (timerPresetTimeHours * 60) + timerPresetTimeMinutes;
        conf.setTimerPresetTime(String.valueOf(minutes));
        // 12 加湿预约开机时间设置（小时）
        int appointmentBootTimeHours = buf.get();
        // 13 加湿预约开机时间设置（分钟）
        int appointmentBootTimeMinute = buf.get();
        int appointmentBootTimeMinutes = (appointmentBootTimeHours * 60)
                + appointmentBootTimeMinute;
        conf.setAppointmentBootTime(String.valueOf(appointmentBootTimeMinutes));
        // 14 加湿预约关机时间设置（小时）
        int appointmentOffTimeHours = buf.get();
        // 15 加湿预约关机时间设置（分钟）
        int appointmentOffTimeMinute = buf.get();
        int appointmentOffTimeMinutes = (appointmentOffTimeHours * 60)
                + appointmentOffTimeMinute;
        conf.setAppointmentOffTime(String.valueOf(appointmentOffTimeMinutes));
        // 16 保留
        buf.get();
        // 17 保留
        buf.get();
        // 18 保留
        buf.get();
        // 19 保留
        buf.get();
        // 20 七彩灯的颜色选择（仅0x03机型有用，0x02机型发0x00）
        int colorInt = buf.get();
        String color = String.valueOf(colorInt);
        conf.setColor(color);
        // 21 保留
        buf.get();
        // 22 用户行为统计
        buf.get();
    }

    /**
     * 解析运行数据 24
     */
    private static void parseRun(HumidifierRunDataModel run, ByteBuffer buf) {
        // 1 返回工作模式
        byte mode = buf.get();
        run.setMode(String.valueOf(mode));
        // 2 返回工作状态
        byte workStatus = buf.get();
        run.setWorkStatus(String.valueOf(workStatus));
        // 3 返回运行状态
        buf.get();
        // 4 返回设定工作时间小时
        buf.get();
        // 5 返回设定工作时间小时
        buf.get();
        // 6 返回剩余时间小时
        buf.get();
        // 7 返回剩余时间分钟
        byte remainingTime = buf.get();
        run.setRemainingTime(String.valueOf(remainingTime));
        // 8 返回报警状态1
        byte warningStatus = buf.get();
        run.setWarningStatus(String.valueOf(warningStatus));
        // 9 返回报警状态2
        buf.get();
        // 10 保留
        buf.get();
        // 11 返回预约开机时间设置(小时)
        buf.get();
        // 12 返回预约开机时间设置(分钟)
        buf.get();
        // 13 返回预约开机剩余时间(小时)
        buf.get();
        // 14 返回预约开机剩余时间(分钟)
        buf.get();
        // 15 返回预约关机时间设置(小时)
        buf.get();
        // 16 返回预约关机时间设置(分钟)
        buf.get();
        // 17 返回预约关机剩余时间(小时)
        buf.get();
        // 18 返回预约关机剩余时间(分钟)
        buf.get();
        // 19 LIGHT灯状态
        int light = buf.get() & 0xFF;
        run.setLight(String.valueOf(light));
        // 20 七彩灯的颜色选择（仅0x03机型有用 0x02机型发0x00）
        byte color = buf.get();
        run.setColor(String.valueOf(color));
        // 21 保留
        buf.get();
        // 22 输出负载状态1
        byte outputLoad1 = buf.get();
        run.setOutputLoad1(String.valueOf(outputLoad1));
        // 23 输出负载状态2
        byte outputLoad2 = buf.get();
        run.setOutputLoad2(String.valueOf(outputLoad2));
        // 24 MIST状态
        int mist = buf.get() & 0xFF;
        run.setMist(String.valueOf(mist));
        // 25 HIGH/LOW状态
        byte level = buf.get();
        run.setLevel(String.valueOf(level));
        // 26 Reserved
        buf.get();
        // 27 Reserved
        buf.get();
        // 28 累积工作时间高位-小时
        buf.get();
        // 29 累积工作时间中位-小时
        buf.get();
        // 30 累积工作时间低位-小时
        buf.get();
        // 31 累积工作时间-分钟
        buf.get();
        // 32 累积工作次数高位
        buf.get();
        // 33 累积工作次数中位
        buf.get();
        // 34 累积工作次数低位
        buf.get();
        // 35 保留
        buf.get();
    }

}
