package com.pingan.crawler.p2p.craw;

import static com.pingan.crawler.p2p.utils.FileUtil.write2File;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pingan.crawler.p2p.PinganConstants;
import com.pingan.crawler.p2p.model.CjPlatform;
import com.pingan.crawler.p2p.model.WdPlatform;
import com.pingan.crawler.p2p.utils.FileUtil;

public class AppMain {
	public static void main(String[] args) {
		init();
		List<CjPlatform> caijingList = process();
		writeResutl(caijingList);
		
		// reGetResult();
	}

	private static void reGetResult() {
		List<CjPlatform> cjList = new ArrayList<CjPlatform>();
		for (Object object : FileUtil.readFileList(PinganConstants.ROOT_OBJECT_PATH)) {
			if (object instanceof CjPlatform) {
				cjList.add((CjPlatform) object);
			}
		}

		List<WdPlatform> wdList = new ArrayList<WdPlatform>();
		for (Object object : FileUtil.readFileList(PinganConstants.PROBLEM_PATH + "problem")) {
			if (object instanceof WdPlatform) {
				wdList.add((WdPlatform) object);
			}
		}

		getComList(cjList, wdList);
		writeResutl(cjList);
	}

	private static void init() {
		FileUtil.mkRootPath(PinganConstants.ROOT_TXT_PATH);
		FileUtil.mkRootPath(PinganConstants.ROOT_OBJECT_PATH);
		FileUtil.mkRootPath(PinganConstants.PROBLEM_PATH);
		CjProcessor.getCaijingMaxPageNum();
	}

	private static List<CjPlatform> process() {
		List<CjPlatform> caijingList = CjProcessor.getCaiJingContent();
		List<WdPlatform> wangdaiList = WdProcessor.getWangDaiContent();
		getComList(caijingList, wangdaiList);

		return caijingList;
	}

	private static void getComList(List<CjPlatform> caijingList, List<WdPlatform> wangdaiList) {
		for (CjPlatform record : caijingList) {
			if (record.getStatus().equals(PinganConstants.Status.PROBLEM.getStatus())) {
				for (WdPlatform problem : wangdaiList) {
					if (record.getPlatformName().equals(problem.getPlatformName())
							&& FileUtil.parseCjTime(record.getOnlineTime()).equals(problem.getOnlineTime())) {
						record.setProblemTime(problem.getProblemTime());
						record.setProblemType(problem.getProblemType());
					}
				}
			}
		}
	}

	private static void writeResutl(List caijingList) {
		write2File(caijingList, PinganConstants.RESULT_PATH + "result.txt", true);
	}
}
