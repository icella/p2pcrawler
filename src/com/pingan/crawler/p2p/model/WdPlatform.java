package com.pingan.crawler.p2p.model;

import java.io.Serializable;

public class WdPlatform extends Platform implements Serializable{
	private String platformName;
	private String onlineTime;
	private String problemTime;
	private String problemType;

	public WdPlatform() {
	}

	public WdPlatform(String platformName, String onlineTime, String problemTime, String problemType) {
		this.platformName = platformName;
		this.onlineTime = onlineTime;
		this.problemTime = problemTime;
		this.problemType = problemType;
	}

	public String getPlatformName() {
		return platformName == null ? "" : platformName;
	}

	public WdPlatform setPlatformName(String platformName) {
		this.platformName = platformName;
		return this;
	}

	public String getOnlineTime() {
		return onlineTime == null ? "" : onlineTime;
	}

	public WdPlatform setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
		return this;
	}

	public String getProblemTime() {
		return problemTime == null ? "" : problemTime;
	}

	public WdPlatform setProblemTime(String problemTime) {
		this.problemTime = problemTime;
		return this;
	}

	public String getProblemType() {
		return problemType == null ? "" : problemType;
	}

	public WdPlatform setProblemType(String problemType) {
		this.problemType = problemType;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getPlatformName()).append("\t")
			  .append(getOnlineTime()).append("\t")
		      .append(getProblemTime()).append("\t")
		      .append(getProblemType());
		
		return buffer.toString();
	}
}
