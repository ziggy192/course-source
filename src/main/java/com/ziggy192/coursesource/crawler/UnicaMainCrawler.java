package com.ziggy192.coursesource.crawler;

import com.ziggy192.coursesource.CategoryMapping;
import com.ziggy192.coursesource.Constants;
import com.ziggy192.coursesource.DummyDatabase;
import com.ziggy192.coursesource.dao.CategoryDAO;
import com.ziggy192.coursesource.dao.DomainDAO;
import com.ziggy192.coursesource.entity.DomainEntity;
import com.ziggy192.coursesource.url_holder.CategoryUrlHolder;
import com.ziggy192.coursesource.util.ParserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.ParseUtil;
import sun.tools.jconsole.inspector.Utils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnicaMainCrawler implements Runnable {
	public static Logger logger = LoggerFactory.getLogger(UnicaMainCrawler.class.toString());

	public static int domainId;

	public static void main(String[] args) {

		DummyDatabase.applicaionInit();

		Thread unicaMainCrawler = new Thread(new UnicaMainCrawler());
		BaseThread.getInstance().getExecutor().execute(unicaMainCrawler);

	}


	public List<CategoryUrlHolder> getCategories() {
		List<CategoryUrlHolder> categories = new ArrayList<>();

		String uri = Constants.UNICA_DOMAIN;

		String beginSign = "col-lg-3 col-md-3 col-sm-4 cate-md";
		String endSign = "col-lg-5 col-md-5 col-sm-4 cate-sm";

		String htmlContent = ParserUtils.parseHTML(uri, beginSign, endSign);
		String newContent = ParserUtils.addMissingTag(htmlContent);

//		System.out.println(newContent);;
		logger.info(newContent);


		try {
			XMLEventReader staxReader = ParserUtils.getStaxReader(newContent);
			boolean insideMainMenu = false;
			while (staxReader.hasNext()) {
				XMLEvent event = staxReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (!insideMainMenu) {
						Attribute idAttribute = startElement.getAttributeByName(new QName("id"));
						if (idAttribute != null && idAttribute.getValue().equals("mysidebarmenu")) {
							insideMainMenu = true;
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
					if (insideMainMenu) {

						if (startElement.getName().getLocalPart().equals("a")
								&& !ParserUtils.getAttributeByName(startElement,"title").trim().isEmpty()) {
							String href = ParserUtils.getAttributeByName(startElement, "href");
							String titleAtt = ParserUtils.getAttributeByName(startElement, "title");
							//exclude the All Category tag

							String categoryURL = href;
							categoryURL = Constants.UNICA_DOMAIN + categoryURL;

							String categoryName = titleAtt;

							CategoryUrlHolder categoryUrlHolder = new CategoryUrlHolder(categoryName, categoryURL);
							categories.add(categoryUrlHolder);
							logger.info(categoryUrlHolder.toString());


						}


					}
				}

			}

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return categories;

	}

	@Override
	public void run() {
		logger.info("start thread");

		try {
			// todo insert domain to db if not yet availabe
			if (DomainDAO.getInstance().getDomainByName(Constants.UNICA_DOMAIN_NAME) == null) {
				//insert to database
//				DummyDatabase.insertDomain(Constants.EDUMALL_DOMAIN_NAME, Constants.EDUMALL_DOMAIN);
				DomainEntity domainEntity = new DomainEntity();
				domainEntity.setName(Constants.UNICA_DOMAIN_NAME);
				domainEntity.setDomainUrl(Constants.UNICA_DOMAIN);
				DomainDAO.getInstance().persist(domainEntity);
			}
			domainId = DomainDAO.getInstance().getDomainByName(Constants.UNICA_DOMAIN_NAME).getId();

			//get all category from domain url
			List<CategoryUrlHolder> categories = getCategories();

			//check issuspend
			synchronized (BaseThread.getInstance()) {
				while (BaseThread.getInstance().isSuspended()) {
					BaseThread.getInstance().wait();
				}
			}


			//domain name and url co truoc trong database
			//
			for (CategoryUrlHolder categoryUrlHolder : categories) {

				//map edumall category name -> my general category name -> categoryId
				String edumallCategoryName = categoryUrlHolder.getCategoryName();

				CategoryMapping categoryMapping = CategoryMapping.mapUnica(edumallCategoryName);

				//get categoryId from database
				int categoryId = CategoryDAO.getInstance().getCategoryId(categoryMapping);


				Thread unicalEachCategoryCrawler = new Thread(new UnicaEachCategoryCrawler(categoryId, categoryUrlHolder.getCategoryURL()));


				//todo thread executor
				BaseThread.getInstance().getExecutor().execute(unicalEachCategoryCrawler);
//				edumallEachCategoryCrawler.start();


				//check is suspend
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
