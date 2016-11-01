package com.het.sdk.demo.model.mattress;

import java.io.Serializable;

/**
 * 睡眠带子model
 */
public class MattressModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5652064470893674670L;
	
	byte heartBeat; //心跳
	byte breathe; //呼吸
	byte snore; //打呼
	byte turnOver; //翻身
	byte isBed;//是否上床
	byte timezone;
	byte power; //电量 0-100表示电量，255表示正在充电
	
	public byte getHeartBeat() {
		return heartBeat;
	}
	public void setHeartBeat(byte heartBeat) {
		this.heartBeat = heartBeat;
	}
	public byte getBreathe() {
		return breathe;
	}
	public void setBreathe(byte breathe) {
		this.breathe = breathe;
	}
	public byte getSnore() {
		return snore;
	}
	public void setSnore(byte snore) {
		this.snore = snore;
	}
	public byte getTurnOver() {
		return turnOver;
	}
	public void setTurnOver(byte turnOver) {
		this.turnOver = turnOver;
	}
	public byte getTimezone() {
		return timezone;
	}
	public void setTimezone(byte timezone) {
		this.timezone = timezone;
	}
	public byte getPower() {
		return power;
	}
	public void setPower(byte power) {
		this.power = power;
	}

	public byte getIsBed() {
		return isBed;
	}

	public void setIsBed(byte isBed) {
		this.isBed = isBed;
	}

	@Override
	public String toString() {
		return "RealTimeDataModel{" +
				"heartBeat=" + heartBeat +
				", breathe=" + breathe +
				", snore=" + snore +
				", turnOver=" + turnOver +
				", isBed=" + isBed +
				", timezone=" + timezone +
				", power=" + power +
				'}';
	}

}
