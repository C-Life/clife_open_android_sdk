package com.het.sdk.demo.model.aroma;


import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.List;


/**
 * Created by admin on 2015/6/6.
 */
public class AromaOutPacket {

	/**
	 * 封装控制数据报文协议
	 * 
	 * @param conf
	 * @return
	 */
	public static byte[] toConfigBytes(AromaConfigModel conf) {
		if (conf != null) {
			ByteBuffer buf = ByteBuffer.allocate(22+10);//为了满足新力维  就写32字节
			putConfig(conf, buf);
			buf.flip();
			byte[] configBytes = buf.array();
			return configBytes;
		}
		return null;
	}

	/**
	 * 封装配置数据�?
	 * 
	 * @param buf
	 */
	private static void putConfig(AromaConfigModel aromaConfigModel,
			ByteBuffer buf) {
		if (aromaConfigModel != null) {
			// 1.小循环
			buf.put((byte) 0x60);
			Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
			int hour = c.get(Calendar.HOUR_OF_DAY); 
			int minute = c.get(Calendar.MINUTE); 
			int second = c.get(Calendar.SECOND);

			//2.3.4时间戳
			buf.put((byte) hour);
			buf.put((byte) minute);
			buf.put((byte) second);
			
			// 5 数据类型设置0x01
			buf.put((byte) 0x01);
			// 6 数据序列号 0x00
			buf.put((byte) 0x00);
			// 7 MIST键设置
			int mistInt = Integer.valueOf(aromaConfigModel.getMist());
			byte mist = (byte) mistInt;
			buf.put(mist);
			// 8 LIGHT键设置
			int lightInt = Integer.valueOf(aromaConfigModel.getLight());
			byte light = (byte) lightInt;
			buf.put(light);
			// 9 HIGH/LOW
			buf.put((byte) 0x00);
			// 10 加湿定时预设时间（小时）
			int timerPresetTimeHours = aromaConfigModel.getTimeCloseH();
			buf.put((byte) timerPresetTimeHours);
			// 11 加湿定时预设时间（分钟）
			int timerPresetTimeMinutes = aromaConfigModel.getTimeCloseM();
			buf.put((byte) timerPresetTimeMinutes);
			// 12 加湿预约开机时间设置（小时）
			int appointmentBootTimeHours = aromaConfigModel.getPresetStartupTimeH();
			buf.put((byte) appointmentBootTimeHours);
			// 13 加湿预约开机时间设置（分钟）
			int appointmentBootTimeMinutes = aromaConfigModel.getPresetStartupTimeM();
			buf.put((byte) appointmentBootTimeMinutes);
			// 14 加湿预约关机时间设置（小时）
			int hours = aromaConfigModel.getPresetShutdownTimeH();
			buf.put((byte) hours);
			// 15 加湿预约关机时间设置（分钟）
			int minutes = aromaConfigModel.getPresetShutdownTimeM();
			buf.put((byte) minutes);
			// 16 保留
			buf.put((byte) 0x00);
			// 17 保留
			buf.put((byte) 0x00);
			// 18 保留
			buf.put((byte) 0x00);
			// 19 保留
			buf.put((byte) 0x00);
			// 20 七彩灯的颜色选择（仅0x03机型有用，0x02机型发0x00）
			int colorInt = Integer.valueOf(aromaConfigModel.getColor());
			byte color = (byte) colorInt;
			buf.put(color);
			// 21 保留
			buf.put((byte) 0x00);
			// 22 用户行为统计
			int updateFlagInt = Integer.valueOf(aromaConfigModel.getUpdateFlag());
			byte updateFlag = (byte) updateFlagInt;
			buf.put(updateFlag);
		}
	}
	
	
	/*
	 * 用户行为分析
	 */
	public static byte getByteBit(List<Integer> list){
	        int flag = 0;
	        for (int i = 0; i < 8; i++) {
	            switch (i) {
	                case 7:
	                    flag |= (list.contains(7)?(1<<i):(0<<i));
	                    break;
	                case 6:
	                    flag |= (list.contains(6)?(1<<i):(0<<i));
	                    break;
	                case 5:
	                    flag |= (list.contains(5)?(1<<i):(0<<i));
	                    break;
	                case 4:
	                    flag |= (list.contains(4)?(1<<i):(0<<i));
	                    break;
	                case 3:
	                    flag |= (list.contains(3)?(1<<i):(0<<i));
	                    break;
	                case 2:
	                    flag |= (list.contains(2)?(1<<i):(0<<i));
	                    break;
	                case 1:
	                    flag |= (list.contains(1)?(1<<i):(0<<i));
	                    break;
	                case 0:
	                	flag |= (list.contains(0)?(1<<i):(0<<i));
	                    break;
	                default:
	                    break;
	            }
	        }
	        return (byte) flag;
	    }
}
