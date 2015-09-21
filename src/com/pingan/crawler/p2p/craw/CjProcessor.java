package com.pingan.crawler.p2p.craw;

import static com.pingan.crawler.p2p.utils.FileUtil.write2File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pingan.crawler.p2p.PinganConstants;
import com.pingan.crawler.p2p.model.CjPlatform;

public class CjProcessor {
	private static int caijing_start_pageNum = 1;
	private static int caijing_max_pageNum = 1;
	private static final String CAIJING_ADDR = "http://data.01caijing.com/p2p/index.html";
	private static List<Integer> errorPageNum = new ArrayList<Integer>();
	private static List<CjPlatform> list = new ArrayList<CjPlatform>();

	public static void getCaijingMaxPageNum() {
		try {
			Document caijingDoc = Jsoup.connect(CAIJING_ADDR).timeout(PinganConstants.TIMEOUT).get();
			String value = caijingDoc.select("ul.pagination").first().getElementsByTag("li").last().select("a")
					.attr("href");

			int startOff = value.indexOf("(") + 1;
			int endOff = value.indexOf(")");
			caijing_max_pageNum = Integer.parseInt(value.substring(startOff, endOff));

			System.out.println("零壹数据网站的P2P借贷数据总共分页数为：" + caijing_max_pageNum);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("网站数据读取有误，请重新启动程序");
			System.exit(0);
		}
	}

	public static List<CjPlatform> getCaiJingContent() {

		for (int i = caijing_start_pageNum; i <= caijing_max_pageNum; i++) {
			try {
				list.addAll(getRecordByPage(CAIJING_ADDR, i));
			} catch (Exception e) {
				errorPageNum.add(i);
				continue;
			}
		}
		
		processErrorReq();
		Collections.sort(list);
		System.out.println("零壹数据网站的P2P借贷数据抓取完毕。");

		return list;
	}

	private static void processErrorReq() {
		Iterator<Integer> itr = errorPageNum.iterator();
		while (itr.hasNext()) {
			int number = itr.next();
			try {
				list.addAll(getRecordByPage(CAIJING_ADDR, number));
				System.out.println("错误请求页" + number + "已处理");
				itr.remove();
			} catch (Exception e) {
				processErrorReq();
			}
		}
	}

	private static List<CjPlatform> getRecordByPage(String rootUrl, int index) throws Exception {
		List<CjPlatform> result = new ArrayList<CjPlatform>();

		Document root = Jsoup.connect(rootUrl)
							 .data("p", String.valueOf(index))
							 .data("newlist", "1,2,4,5,6,27")
							 .timeout(PinganConstants.TIMEOUT)
							 .post();
		
		Elements platformLink = root.getElementsByClass("list_warp").first().select("tr");
		for (Element linkStr : platformLink) {
			CjPlatform platform = new CjPlatform();

			String serialNo = linkStr.getElementsByClass("serial_num").first().text();
			platform.setNo(Integer.parseInt(serialNo) + (index - 1) * 100);
			platform.setStatus(linkStr.getElementsByClass("td_27").first().text());

			Document platformDoc = Jsoup.parse(linkStr.getElementsByClass("platform_name").toString(), CAIJING_ADDR);
			
			//每个P2P平台记录的详细信息
			Document detailDoc = Jsoup.connect(platformDoc.getElementsByTag("a").attr("abs:href"))
								      .timeout(PinganConstants.TIMEOUT).get();
			
			Elements titleElement = detailDoc.getElementsByClass("platform_title");
			platform.setPlatformName(titleElement.select("span.platform_txt").text());
			platform.setDomain(titleElement.select("a").attr("abs:href"));

			Elements details = detailDoc.select("ul.platform_info").select("li");
			platform.setOnlineTime(getAbbrNameAfterColon(details.get(0).text()));
			platform.setLegalRepresentative(getAbbrNameAfterColon(details.get(1).text()));
			platform.setRegisteredCapital(getAbbrNameAfterColon(details.get(2).text()));
			platform.setRegisteredAddress(getAbbrNameAfterColon(details.get(3).text()));
			platform.setCorporation(getAbbrNameAfterColon(details.get(4).text()));

			result.add(platform);
		}

		write2File(result, PinganConstants.ROOT_TXT_PATH + "page" + index + ".txt", true);
		write2File(result, PinganConstants.ROOT_OBJECT_PATH + "page" + index, false);

		return result;
	}

	private static String getAbbrNameAfterColon(String name) {
		return name.substring(name.indexOf("：") + 1).trim();
	}
}
