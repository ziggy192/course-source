package com.ziggy192.coursesource;

import com.ziggy192.coursesource.crawler.BaseThread;
import com.ziggy192.coursesource.crawler.UnicaMainCrawler;

public class UnicaTest {
	public static void main(String[] args) {
	testGetAllCategories();

	}

	public static void testGetAllCategories() {
		Thread unicaMainCrawler = new Thread(new UnicaMainCrawler());
		BaseThread.getInstance().getExecutor().execute(unicaMainCrawler);

	}
}
