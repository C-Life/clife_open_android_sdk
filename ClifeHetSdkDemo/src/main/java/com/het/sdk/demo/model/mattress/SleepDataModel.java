package com.het.sdk.demo.model.mattress;

import java.io.Serializable;
import java.util.List;


/**
 * 床垫 
 * @author 李钊平
 *
 */
public class SleepDataModel  implements Serializable {

	private static final long serialVersionUID = 1L;
	String mattressId;
	String dataTime;  //数据所在日期（yyyy-MM-dd）
	String sleepDuration; //睡眠时长（分钟）
	String fallSleepDuration; //入睡耗时（分钟）
	String lightSleepDuration; //	浅睡时长（分钟）
	String deepSleepDuration; //	深睡时长（分钟）
	String asleepDuration; //	睡着时长（分钟）
	String wakeTimes; //	觉醒次数
	String lightSleepTimes; //	浅睡次数
	String deepSleepTimes; //	深睡次数
	String heartRate; //	心率
	String breathRate; //	呼吸率
	String apneaTimes; //	呼吸暂停次数
	String snoreTimes; //	打呼次数
	String turnOverTimes; //	翻身次数
	String score; //	睡眠打分
	String beatPercent; //	击败所有用户的百分比
	
	String sleepEfficiency;//睡眠效率
	
	
	List<sleepStatusModel> sleepStatusList;
	
	
	public static class sleepStatusModel implements Serializable{
		public String startTime;	//睡眠状态开始时间（yyyy-MM-dd HH:mm:ss）
		public String status; //	睡眠状态（1-上床，2-入睡，3-浅睡，4-深睡，5-觉醒，6-懒床，7-起床）
	}
	
	public String getMattressId() {
		return mattressId;
	}

	public void setMattressId(String mattressId) {
		this.mattressId = mattressId;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getSleepDuration() {
		return sleepDuration;
	}

	public void setSleepDuration(String sleepDuration) {
		this.sleepDuration = sleepDuration;
	}

	public String getFallSleepDuration() {
		return fallSleepDuration;
	}

	public void setFallSleepDuration(String fallSleepDuration) {
		this.fallSleepDuration = fallSleepDuration;
	}

	public String getLightSleepDuration() {
		return lightSleepDuration;
	}

	public void setLightSleepDuration(String lightSleepDuration) {
		this.lightSleepDuration = lightSleepDuration;
	}

	public String getDeepSleepDuration() {
		return deepSleepDuration;
	}

	public void setDeepSleepDuration(String deepSleepDuration) {
		this.deepSleepDuration = deepSleepDuration;
	}

	public String getAsleepDuration() {
		return asleepDuration;
	}

	public void setAsleepDuration(String asleepDuration) {
		this.asleepDuration = asleepDuration;
	}

	public String getWakeTimes() {
		return wakeTimes;
	}

	public void setWakeTimes(String wakeTimes) {
		this.wakeTimes = wakeTimes;
	}

	public String getLightSleepTimes() {
		return lightSleepTimes;
	}

	public void setLightSleepTimes(String lightSleepTimes) {
		this.lightSleepTimes = lightSleepTimes;
	}

	public String getDeepSleepTimes() {
		return deepSleepTimes;
	}

	public void setDeepSleepTimes(String deepSleepTimes) {
		this.deepSleepTimes = deepSleepTimes;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getBreathRate() {
		return breathRate;
	}

	public void setBreathRate(String breathRate) {
		this.breathRate = breathRate;
	}

	public String getApneaTimes() {
		return apneaTimes;
	}

	public void setApneaTimes(String apneaTimes) {
		this.apneaTimes = apneaTimes;
	}

	public String getSnoreTimes() {
		return snoreTimes;
	}

	public void setSnoreTimes(String snoreTimes) {
		this.snoreTimes = snoreTimes;
	}

	public String getTurnOverTimes() {
		return turnOverTimes;
	}

	public void setTurnOverTimes(String turnOverTimes) {
		this.turnOverTimes = turnOverTimes;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getBeatPercent() {
		return beatPercent;
	}

	public void setBeatPercent(String beatPercent) {
		this.beatPercent = beatPercent;
	}

	public List<sleepStatusModel> getSleepStatusList() {
		return sleepStatusList;
	}

	public void setSleepStatusList(List<sleepStatusModel> sleepStatusList) {
		this.sleepStatusList = sleepStatusList;
	}

	public String getSleepEfficiency() {
		return sleepEfficiency;
	}

	public void setSleepEfficiency(String sleepEfficiency) {
		this.sleepEfficiency = sleepEfficiency;
	}

	@Override
	public String toString() {
		return "SleepDataModel [mattressId=" + mattressId + ", dataTime="
				+ dataTime + ", sleepDuration=" + sleepDuration
				+ ", fallSleepDuration=" + fallSleepDuration
				+ ", lightSleepDuration=" + lightSleepDuration
				+ ", deepSleepDuration=" + deepSleepDuration
				+ ", asleepDuration=" + asleepDuration + ", wakeTimes="
				+ wakeTimes + ", lightSleepTimes=" + lightSleepTimes
				+ ", deepSleepTimes=" + deepSleepTimes + ", heartRate="
				+ heartRate + ", breathRate=" + breathRate + ", apneaTimes="
				+ apneaTimes + ", snoreTimes=" + snoreTimes
				+ ", turnOverTimes=" + turnOverTimes + ", score=" + score
				+ ", beatPercent=" + beatPercent + ", sleepEfficiency="
				+ sleepEfficiency + ", sleepStatusList=" + sleepStatusList
				+ "]";
	}


}
