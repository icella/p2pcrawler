package com.pingan.crawler.p2p.model;

import java.io.Serializable;

public class CjPlatform extends Platform implements Serializable, Comparable {

	private Integer no;
	private String platformName;
	private String domain;
	private String onlineTime;
	private String corporation;
	private String registeredCapital;
	private String registeredAddress;
	private String legalRepresentative;
	private String status;
	private String problemTime;
	private String problemType;

	public CjPlatform() {
	}

	public CjPlatform(Integer no, String platformName, String domain, String onlineTime, String corporation,
			String registeredCapital, String registeredAddress, String legalRepresentative, String status,
			String problemTime, String problemType) {
		this.no = no;
		this.platformName = platformName;
		this.domain = domain;
		this.onlineTime = onlineTime;
		this.corporation = corporation;
		this.registeredCapital = registeredCapital;
		this.registeredAddress = registeredAddress;
		this.legalRepresentative = legalRepresentative;
		this.status = status;
		this.problemTime = problemTime;
		this.problemType = problemType;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProblemTime() {
		return problemTime == null ? "" : problemTime;
	}

	public void setProblemTime(String problemTime) {
		this.problemTime = problemTime;
	}

	public String getProblemType() {

		return problemType == null ? "" : problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getNo()).append("\t")
			  .append(getPlatformName()).append("\t")
			  .append(getDomain()).append("\t")
			  .append(getOnlineTime()).append("\t")
			  .append(getCorporation()).append("\t")
			  .append(getRegisteredCapital()).append("\t")
			  .append(getRegisteredAddress()).append("\t")
			  .append(getLegalRepresentative()).append("\t")
			  .append(getStatus()).append("\t")
			  .append(getProblemTime()).append("\t")
			  .append(getProblemType());

		return buffer.toString();
	}

	@Override
	public int compareTo(Object o) {
		return this.getNo().compareTo(((CjPlatform) o).getNo());
	}

}
