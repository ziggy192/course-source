package com.ziggy192.coursesource.crawler;

import com.ziggy192.coursesource.url_holder.EdumallCategoryUrlHolder;
import com.ziggy192.coursesource.url_holder.EdumallCourseUrlHolder;
import com.ziggy192.coursesource.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;

public class EdumallEachCategoryCrawler implements Runnable{

	public static Logger logger = LoggerFactory.getLogger(EdumallEachCategoryCrawler.class.toString());

	private int categoryId;
	private String categoryUrl;

	public EdumallEachCategoryCrawler(int categoryId, String categoryUrl) {
		this.categoryId = categoryId;
		this.categoryUrl = categoryUrl;
	}

	private int getTotalPageForEachCategory(String categoryUrl) {

//		String categoryUrl = urlHolder.getCategoryURL();

		String beginSign = "class='list-paginate'";
		String endSign = "form class='form-paginate form-inline'";

		String htmlContent = Utils.parseHTML(categoryUrl, beginSign, endSign);

		htmlContent = Utils.addMissingTag(htmlContent);
		System.out.println(htmlContent);
		int pageCount = 1;
		try {
			XMLEventReader staxReader = Utils.getStaxReader(htmlContent);
			boolean insidePaginationDiv = false;
			while (staxReader.hasNext()) {
				XMLEvent event = staxReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					Iterator<Attribute> attributes = startElement.getAttributes();

					if (!insidePaginationDiv) {
						if (startElement.getName().getLocalPart().equals("div")) {

							Attribute attributeClass = startElement.getAttributeByName(new QName("class"));
							if (attributeClass != null && attributeClass.getValue().equals("pagination")) {
								insidePaginationDiv = true;
							}
						}
					}
//					while (attributes.hasNext()) {
//						Attribute attribute = attributes.next();
//						if (!insideMainMenu) {
//							if (attribute.getName().toString().equals("class") && attribute.getValue().equals("main-menu")) {
//								//inside the main menu div
//
//								insideMainMenu = true;
//							}
//						}
//
//
//
//					}
					if (insidePaginationDiv) {

						//todo get all <em class="current"> or <a href="...&page=[number]> content


						if (Utils.checkAttributeEqualsKey(startElement, "class", "current")
								|| Utils.getAttributeByName(startElement, "href").contains("page=")
						) {

							String pageContent = Utils.getContentAndJumpToEndElement(staxReader, startElement);
							try {
								int pageNumber = Integer.parseInt(pageContent);

								pageCount = Math.max(pageCount, pageNumber);
							} catch (NumberFormatException e) {
//								e.printStackTrace();
								//not a page number
							}
						}
					}
				}

			}

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return pageCount;
	}

	@Override
	public void run() {
		logger.info("start thread");
		logger.info(categoryUrl);

		try {
			int pageNumber = getTotalPageForEachCategory(categoryUrl);
			logger.info("totalPageNumber="+pageNumber);

			//for each page
			if (categoryUrl.contains("page=")) {
				categoryUrl = categoryUrl.substring(0,categoryUrl.indexOf("page=")-1); //?page= or &page=

			}

			synchronized (BaseThread.getInstance()) {
				while (BaseThread.getInstance().isSuspended()) {
					BaseThread.getInstance().wait();
				}
			}


			for (int i = 1; i <= pageNumber; i++) {
				String eachPageUri = categoryUrl + "&page=" + i;

				Thread courseInEachCategoryPageCrawler = new Thread(new EdumallCourseInEachCategoryPageCrawler(eachPageUri, categoryId));
				//todo thread execute
				BaseThread.getInstance().getExecutor().execute(courseInEachCategoryPageCrawler);

//				courseInEachCategoryPageCrawler.start();


				//check issuspend
				synchronized (BaseThread.getInstance()) {
					while (BaseThread.getInstance().isSuspended()) {
						BaseThread.getInstance().wait();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		logger.info("END THREAD");

	}
}
