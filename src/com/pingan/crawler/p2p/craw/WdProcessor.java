package com.pingan.crawler.p2p.craw;

import static com.pingan.crawler.p2p.utils.FileUtil.write2File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pingan.crawler.p2p.PinganConstants;
import com.pingan.crawler.p2p.model.WdPlatform;

public class WdProcessor {
	private static final String WANGDAI_ADDR = "http://shuju.wangdaizhijia.com/problem-1.html";

	public static List<WdPlatform> getWangDaiContent() {
		List<WdPlatform> list = new ArrayList<WdPlatform>();

		try {
			Document doc = Jsoup.connect(WANGDAI_ADDR).timeout(PinganConstants.TIMEOUT).get();
			Elements trElements = doc.select("table.data_table").select("tbody").first().getElementsByTag("tr");
			for (Element trElement : trElements) {
				Elements tdElements = trElement.getElementsByTag("td");
				String name = tdElements.get(1).text();
				String problemTime = tdElements.get(2).text();
				String onlineTime = tdElements.get(3).text();
				String type = tdElements.get(8).text();

				WdPlatform platform = new WdPlatform();
				platform.setPlatformName(name).setOnlineTime(onlineTime).setProblemTime(problemTime)
						.setProblemType(type);

				list.add(platform);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		write2File(list, PinganConstants.PROBLEM_PATH + "problem.txt", true);
		write2File(list, PinganConstants.PROBLEM_PATH + "problem", false);
		System.out.println("问题数据抓取完毕。");

		return list;
	}
}
