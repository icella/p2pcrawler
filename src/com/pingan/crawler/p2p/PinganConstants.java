package com.pingan.crawler.p2p;

public class PinganConstants {
	public static final int TIMEOUT = 50000;

	public static final String RESULT_PATH = "E:\\p2p\\";
	public static final String ROOT_TXT_PATH = "E:\\p2p\\txt\\";
	public static final String ROOT_OBJECT_PATH = "E:\\p2p\\object\\";
	public static final String PROBLEM_PATH = "E:\\p2p\\problem\\";
	
	public enum Status {
		NORMAL("正常"), PROBLEM("问题");
		private final String status;

		Status(String status) {
			this.status = status;
		}

		public final String getStatus() {
			return status;
		}
	}
}
