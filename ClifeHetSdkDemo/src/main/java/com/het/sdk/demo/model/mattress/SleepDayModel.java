package com.het.sdk.demo.model.mattress;

import java.io.Serializable;
import java.util.List;

public class SleepDayModel implements Serializable {
	private static final long serialVersionUID = -2705550248957721807L;
	private List<SleepStatusModel> sleepStatusList;
	private List<RawDataModel> rawData;
	private SleepDataModel dayData;
	
	public static class SleepStatusModel implements Serializable {
		public String startTime;	//睡眠状态开始时间（yyyy-MM-dd HH:mm:ss）
		public String status; //	睡眠状态（1-上床，2-入睡，3-浅睡，4-深睡，5-觉醒，6-懒床，7-起床）
		public String dataTime;
		@Override
		public String toString() {
			return "SleepStatusModel [startTime=" + startTime + ", status="
					+ status + ", dataTime=" + dataTime + "]";
		}
		
	}
	public static class RawDataModel implements Serializable {

        public String dataTime;
		public int heartRate;
		public int breathRate;
		public int snoreTimes;
		public int turnOverTimes;
		@Override
		public String toString() {
			return "RawDataModel [dataTime=" + dataTime + ", heartRate="
					+ heartRate + ", breathRate=" + breathRate
					+ ", snoreTimes=" + snoreTimes + ", turnOverTimes="
					+ turnOverTimes + "]";
		}
         
	}
	
	
	
	
	public SleepDataModel getDayData() {
		return dayData;
	}

	public void setDayData(SleepDataModel dayData) {
		this.dayData = dayData;
	}

	public List<SleepStatusModel> getSleepStatusList() {
		return sleepStatusList;
	}

	public void setSleepStatusList(List<SleepStatusModel> sleepStatusList) {
		this.sleepStatusList = sleepStatusList;
	}

	public List<RawDataModel> getRawData() {
		return rawData;
	}

	public void setRawData(List<RawDataModel> rawData) {
		this.rawData = rawData;
	}

	@Override
	public String toString() {
		return "SleepDayModel [sleepStatusList=" + sleepStatusList
				+ ", rawData=" + rawData + ", dayData=" + dayData + "]";
	}

	
	
}
