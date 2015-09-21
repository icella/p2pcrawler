package com.pingan.crawler.p2p.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void testParseCjTime() {
		String time1 = "2014-01-29";
		assertEquals("2014.01", FileUtil.parseCjTime(time1));
	}

}
