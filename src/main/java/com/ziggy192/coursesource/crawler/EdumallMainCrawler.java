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

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EdumallMainCrawler implements Runnable {

	public static Logger logger = LoggerFactory.getLogger(EdumallMainCrawler.class.toString());

	public static int domainId;


	public static void main(String[] args) throws InterruptedException {

		DummyDatabase.applicaionInit();

		Thread edumallCrawlerThread = new Thread(new EdumallMainCrawler());
		BaseThread.getInstance().getExecutor().execute(edumallCrawlerThread);


//		edumallCrawlerThread.start();

//		try suspend and resume
//		try {
//			Thread.sleep(15000);
//			BaseThread.getInstance().suspendThread();
//			Thread.sleep(30000);
//			BaseThread.getInstance().resumeThread();
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}



//		testGetCourseListFromEachPage();


	}






	public static void testGetAllCourse() {
		String uri = "https://edumall.vn/courses/filter?categories[]=luyen-thi&page=2";

		CategoryUrlHolder categoryUrlHolder = new CategoryUrlHolder("Luyện thi", uri);

//		getAllCoursesFromCategory(categoryUrlHolder);

//		for (CourseUrlHolder courseUrlHolder : allCoursesFromCategory) {
//			logger.info("Detail=======================");
//			getCourseDetail(courseUrlHolder);
//
//		}
	}

	public static void testGetPageNumber() {
		String uri = "https://edumall.vn/courses/filter?categories[]=luyen-thi&page=2";

		CategoryUrlHolder categoryUrlHolder = new CategoryUrlHolder("Luyện thi", uri);
		//test get total pages
//		int page = getTotalPageForEachCategory(categoryUrlHolder);
//		System.out.println("Total page =" + page);

	}




//	public static void getCourseDetail(CourseUrlHolder courseUrlHolder) {
//		System.out.println("Detail=======================");
////		String uri = "https://edumall.vn/course/guitar-dem-hat-trong-30-ngay";
////		String uri = "https://edumall.vn/v4/course/microsoft-word-co-ban-va-hieu-qua";
//		String uri = courseUrlHolder.getCourseUrl();
//
//		String beginSign = "<div class='wrapper'";
//		String endSign = "<section class='section_rating";
//
//
//		//todo add '/v4' to uri because that's what edumall.vn do
//
//		int index = uri.indexOf("edumall.vn/course/") + "edumall.vn/course/".length();
//		if (!uri.substring(index).startsWith("v4")) {
//			uri = "https://edumall.vn/course/v4/" + uri.substring(index);
//
//		}
//
//		System.out.println(uri);
//
//
//		String htmlContent = ParserUtils.parseHTML(uri, beginSign, endSign);
////		System.out.println(htmlContent);
//		htmlContent = ParserUtils.addMissingTag(htmlContent);
////		System.out.println(newContent);
//
//
//
//		String overviewDescription = "";
//		try {
//			XMLEventReader staxReader = ParserUtils.getStaxReader(htmlContent);
//			while (staxReader.hasNext()) {
//				XMLEvent event = staxReader.nextEvent();
//				if (event.isStartElement()) {
//					StartElement startElement = event.asStartElement();
//					Iterator<Attribute> attributes = startElement.getAttributes();
//
//
//					//todo check inside courseListDiv
//					//<div class='list-courses-filter'
////					if (!insideContainerDiv) {
////						{
////							if (startElement.getName().getLocalPart().equals("div")
////									&& ParserUtils.checkAttributeContainsKey(startElement, "class", "col-lg-8")) {
////								insideContainerDiv = true;
////							}
////						}
////					}
////					if (insideContainerDiv) {
//
//
//					//todo get VideoURL
//
//					//<iframe allowfullscreen="" frameborder="0" height="100%" src="https://www.youtube.com/embed/cyGq22d1sbk?modestbranding=0&amp;amp;rel=0&amp;amp;showinfo=0" width="100%"></iframe>
//					if (startElement.getName().getLocalPart().equals("iframe")
////								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "ytp-title-link")
////								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "yt-uix-sessionlink")
//					) {
//
//						Attribute srcAtt = startElement.getAttributeByName(new QName("src"));
//
//						if (srcAtt != null) {
//							String videoUrl = srcAtt.getValue();
//							logger.info("VideURL=" + videoUrl);
//
//						}
//
//					}
//
//
//					//todo getcost
//					//<p class="col-md-12 col-xs-12 price" style="" xpath="1">699,000đ</p>
//
//					if (startElement.getName().getLocalPart().equals("p")
//							&& ParserUtils.checkAttributeContainsKey(startElement, "class", new String[]{
//							"col-md-12", "col-xs-12", "price"
//					})
//					) {
//						String cost = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//						if (!cost.isEmpty()) {
//
//							//get int value from cost
//							int costValue = 0;
//							for (int i = 0; i < cost.length(); i++) {
//								if (cost.charAt(i) >= '0' && cost.charAt(i) <= '9') {
//									int charValue = cost.charAt(i) - '0';
//									costValue = costValue * 10 + charValue;
//								}
//							}
//							logger.info("Cost=" + costValue);
//						}
//					}
//
//					//todo get duration
//					//<div class="form-layout col-xs-12" xpath="1">
//					//<i class="fas fa-clock pull-left padding_icon"></i>
//					//<span class="pull-left">Thời lượng video</span>
//					//<span class="pull-right">05:58:48</span>
//					//</div>
//					if (startElement.getName().getLocalPart().equals("div")
//							&& ParserUtils.checkAttributeContainsKey(startElement, "class", "prop")) {
//						ParserUtils.nextStartEvent(staxReader, "i", new String[]{
//								"fas", "fa-clock"
//						});
//						startElement = ParserUtils.nextStartEvent(staxReader, "span", new String[]{"pull-right"}).asStartElement();
//						String duration = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//						if (!duration.isEmpty()) {
//							logger.info("Duration=" + duration);
//						}
//					}
//
//					//todo get author name
////					if (startElement.getName().getLocalPart().equals("span")
////							&& ParserUtils.checkAttributeContainsKey(startElement, "class", "name_teacher")) {
////						String authorName = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
////						if (!authorName.isEmpty()) {
////							logger.info("Author="+authorName);
////						}
////					}
//
//					/*
//					<section class="section_author" id="general-author-tab">
//					<div class="info_author" style="" xpath="1">
//					<div class="pull-left image_author">
//					<img src="//d303ny97lru840.cloudfront.net/kelley-57bfb0d3ce4b1438274ba1fd/20160829-hiennt02-edumall/533079_556033297754623_542734647_n.jpg">
//					</div>
//					<div class="pull-left name_author" style="">
//					<p class="author" style="font-size: 16px;">Giảng viên</p>
//					<div class="name" style="">Nguyễn Thượng Hiển</div>
//
//					</div>
//					</div>*/
//					if (startElement.getName().getLocalPart().equals("section")
//							&& ParserUtils.checkAttributeContainsKey(startElement, "id", "general-author-tab")) {
//						//traverse to end section
//						String authorInfo = Formater.toHeading2("Tiểu sử");
//
//						int stackCount = 1;
//
//						boolean insideAuthorInfo = false;
//
////						List<String> contentList = new ArrayList<>();
//						while (stackCount > 0) {
//							event = staxReader.nextEvent();
//							if (event.isStartElement()) {
//								stackCount++;
//								startElement = event.asStartElement();
//
//								if (startElement.getName().getLocalPart().equals("div")
//										&& ParserUtils.checkAttributeContainsKey(startElement, "class", "name")) {
//									String authorName = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--;
//									if (!authorName.isEmpty()) {
//										logger.info("Author=" + authorName);
//									}
//								}
//
//
//								//<img src="//d303ny97lru840.cloudfront.net/kelley-57bfb0d3ce4b1438274ba1fd/20160829-hiennt02-edumall/533079_556033297754623_542734647_n.jpg" style="">
//								if (startElement.getName().getLocalPart().equals("img")) {
//									String imageUrl = startElement.getAttributeByName(new QName("src")).getValue();
//									if (imageUrl.startsWith("//")) {
//										imageUrl = imageUrl.substring(2);
//									}
//
//									logger.info("AuthorImage=" + imageUrl);
//								}
//								if (ParserUtils.checkAttributeContainsKey(startElement, "id", "author_info")) {
//									insideAuthorInfo = true;
//
//								}
//								if (insideAuthorInfo) {
//									if (startElement.getName().getLocalPart().equals("li")) {
//										String content = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//										stackCount--;
//										content = Formater.toParagraph(content);
//										authorInfo += content;
//									}
//								}
//							}
//							if (event.isEndElement()) {
//								stackCount--;
//
//							}
//
//						}
//
//
//						logger.info("AuthorInfo=" + authorInfo);
//					}
//
//					// TODO: get overview description
//
//					//loi ich tu khoa hoc
//					/*<section class="section_benefit section-setting" id="general-info-tab" data-gtm-vis-first-on-screen-8263996_483="1091" data-gtm-vis-total-visible-time-8263996_483="5000" data-gtm-vis-has-fired-8263996_483="1" style="" xpath="1">
//					<p class="title" style="">Lợi ích từ khóa học</p>
//					<div class="content" style="">
//					<div class="content_benefit">
//					<i class="fas fa-check-circle"></i>
//					<span> Nắm vững nhạc lý: Cách đọc tọa độ, bấm hợp âm, tiết tấu; Cách rải âm và quạt chả.</span>
//					</div>
//					<div class="content_benefit">
//					<i class="fas fa-check-circle"></i>
//					<span> Thành thạo các điệu cơ bản: Surf nhanh - chậm, Disco, Blue, Ballad, Báo, Fox, Valse, Bolero, Slow Rock,...</span>
//					</div>
//					<div class="content_benefit">
//					<i class="fas fa-check-circle"></i>
//					<span> Thành thạo cách dò các nhịp, điệu của một bài hát, bắt nhịp và chọn điệu, bắt tông cho ca sĩ, đánh intro và outro, search hợp âm chuẩn,...</span>
//					</div>
//					<div class="content_benefit">
//					<i class="fas fa-check-circle"></i>
//					<span> Biết cách chọn đàn sao cho phù hợp với mục đích, túi tiền và phong cách nhưng vẫn phải đảm bảo những yêu tố thiết yếu.</span>
//					</div>
//					</div>
//					<div class="clear"></div>
//					</section>*/
//					if (startElement.getName().getLocalPart().equals("section")
//							&& ParserUtils.checkAttributeContainsKey(startElement, "id", "general-info-tab")) {
//
//						//traverse to end section
//						int stackCount = 1;
//						List<String> contentList = new ArrayList<>();
//						while (stackCount > 0) {
//							event = staxReader.nextEvent();
//							if (event.isStartElement()) {
//								stackCount++;
//								startElement = event.asStartElement();
//
//								if (ParserUtils.checkAttributeContainsKey(startElement, "class", "title")) {
//									//title
//									String title = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement
//
//									title = Formater.toHeading1(title);
//
//									overviewDescription = overviewDescription + title;
//
//
//								}
//
//								if (ParserUtils.checkAttributeContainsKey(startElement, "class", "content_benefit")) {
//									//content benefit
//									startElement = ParserUtils.nextStartEvent(staxReader, "span").asStartElement();
//									MutableInteger mutableStack = new MutableInteger(stackCount);
//
//									String contentBenefit = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement
//									contentList.add(contentBenefit);
//								}
//
//							}
//							if (event.isEndElement()) {
//								stackCount--;
//
//							}
//
//						}
//						overviewDescription += Formater.toList((contentList.toArray(new String[contentList.size()])));
//						logger.info("OverviewPar1=" + overviewDescription);
//
//					}
//
//
//					//Doi tuong muc tieu && Yeu cau khoa hoc
//					/*
//					*<section class="section_requirement section-setting" data-gtm-vis-first-on-screen-8263996_503="1091" data-gtm-vis-total-visible-time-8263996_503="5000" data-gtm-vis-has-fired-8263996_503="1" style="" xpath="1">
//						<p class="title">Đối tượng mục tiêu</p>
//						<ul class="required">
//						<div class="cover-require col-md-12">
//						<li class="require"> Yêu thích âm nhạc và có cảm hứng đặc biệt với những cây đàn Guitar.</li>
//						</div>
//						<div class="clear"></div>
//						<div class="cover-require col-md-12">
//						<li class="require"> Muốn học Guitar đệm hát nhưng chưa biết bắt đầu từ đâu</li>
//						</div>
//						<div class="clear"></div>
//						<div class="cover-require col-md-12">
//						<li class="require"> Muốn học Guitar nhưng bị giới hạn về thời gian và tài chính</li>
//						</div>
//						<div class="clear"></div>
//						<div class="cover-require col-md-12">
//						<li class="require"> Học đệm hát để chơi các bài hát mà mình yêu thích</li>
//						</div>
//						<div class="clear"></div>
//						</ul>
//						</section>
//					*/
//					if (startElement.getName().getLocalPart().equals("section")
//							&& ParserUtils.checkAttributeContainsKey(startElement, "class", "section_requirement")
//					) {
//						//traverse to end section
//						int stackCount = 1;
//						List<String> contentList = new ArrayList<>();
//
//						while (stackCount > 0) {
//							event = staxReader.nextEvent();
//							if (event.isStartElement()) {
//								stackCount++;
//								startElement = event.asStartElement();
//
//								//title
//								if (ParserUtils.checkAttributeContainsKey(startElement, "class", "title")) {
//									String title = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement
//
//									overviewDescription += Formater.toHeading1(title);
//
//								}
//
//								//list
//								if (startElement.getName().getLocalPart().equals("li")) {
//									String listItem = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement
//
//									contentList.add(listItem);
//								}
//
//
//							}
//							if (event.isEndElement()) {
//								stackCount--;
//
//							}
//
//						}
//
//						overviewDescription += Formater.toList(contentList.toArray(new String[contentList.size()]));
//						logger.info("OverviewPart2" + overviewDescription);
//
//
//					}
//
//					//Tong quat
//					/*<section class="section_description section-setting" data-gtm-vis-first-on-screen-8263996_504="1619" data-gtm-vis-total-visible-time-8263996_504="5000" data-gtm-vis-has-fired-8263996_504="1" xpath="1">
//					<div class="title">Tổng quát</div>
//					<div class="description-content show-more-content" id="show_more_des">
//					<div class="text_description"><p>Khóa học gồm :<br>- 6 học phần<br>- 77 bài giảng được hướng dẫn cụ thể từ giảng viên<br>- 5 cấp độ từ cơ bản đến nâng cao<br>- Hệ thống tài liệu chi tiết cho từng học phần.</p></div>
//					</div>
//					<div class="show_more" id="show_more_des_button">XEM THÊM</div>
//					</section>
//					*/
//
//					if (startElement.getName().getLocalPart().equals("section")
//							&& ParserUtils.checkAttributeContainsKey(startElement, "class", "section_description")) {
//						//traverse to end section
//						int stackCount = 1;
//
//						while (stackCount > 0) {
//							event = staxReader.nextEvent();
//							if (event.isStartElement()) {
//								stackCount++;
//								startElement = event.asStartElement();
//
//								//title
//								if (ParserUtils.checkAttributeContainsKey(startElement, "class", "title")) {
//									String title = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement
//
//									overviewDescription += Formater.toHeading1(title);
//
//								}
//
//								//content
//								if (startElement.getName().getLocalPart().equals("div")
//										&& ParserUtils.checkAttributeContainsKey(startElement, "class", "text_description")) {
//									String htmlDescriptionContent = ParserUtils.getAllHtmlContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--;
//									overviewDescription += htmlDescriptionContent;
//								}
//							}
//							if (event.isEndElement()) {
//								stackCount--;
//
//							}
//						}
//
//						logger.info("OverviewPart3=" + overviewDescription);
//
//					}
//
//
//					//todo get syllabus
//					/*
//					 * <div id='section_lecture'>
//					 * 		<li class='menu'>
//					 * 			<div class='menu-title>
//					 *
//					 *
//					 *			<div class='lecture-title>
//					 *
//					 * -->    <h3>title</h3>
//					 * 		<ul>
//					 * 			<li>bai 1</li>
//					 * */
//
//
//					if (ParserUtils.checkAttributeContainsKey(startElement, "id", "section_lecture")) {
//						//traverse
//						String syllabus = "";
//						int stackCount = 1;
//						List<String> lectureNameList = new ArrayList<>();
//						while (stackCount > 0) {
//							event = staxReader.nextEvent();
//
//							if (event.isStartElement()) {
//								stackCount++;
//								startElement = event.asStartElement();
//								if (ParserUtils.checkAttributeContainsKey(startElement, "class", "menu-title")) {
//
//									String content = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--;
//									content = Formater.toHeading3(content);
//
//									if (lectureNameList.size() > 0) {
//										syllabus += Formater.toList(lectureNameList.toArray(new String[lectureNameList.size()]));
//										lectureNameList = new ArrayList<>();
//									}
//
//
//									syllabus += content;
//
//								}
//								if (ParserUtils.checkAttributeContainsKey(startElement, "class", "lecturetitle")) {
//									String lectureName = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//									stackCount--;
//									lectureNameList.add(lectureName);
//								}
//
//							}
//							if (event.isEndElement()) {
//								stackCount--;
//
//							}
//
//						}
//
//						//dequeue the last lectureNamelist
//
//						if (lectureNameList.size() > 0) {
//							syllabus += Formater.toList(lectureNameList.toArray(new String[lectureNameList.size()]));
//						}
//						logger.info("Syllabus=" + syllabus);
//
//					}
//					//lecture-name
//
//					//todo get rating && rating number
//					//div class="intro_course'
//					//<div class="star-rating" xpath="1">
//					//<div class="back-stars">
//
//					//<div class="front-stars" style="width: 100.0%">
//
//					//</div>
//					//</div>
//					//<b style="padding-left:10px; padding-right:10px;">5.0</b>
//					//(153)
//					//</div>
//					if (startElement.getName().getLocalPart().equals("div")
//							&& ParserUtils.checkAttributeContainsKey(startElement, "class", "intro_course")) {
////						startElement = nextStartEvent(staxReader, "div", new String[]{"star-rating"}).asStartElement();
////					}
////					if (startElement.getName().getLocalPart().equals("div")
////							&& ParserUtils.checkAttributeContainsKey(startElement, "class", "star-rating")
////					) {
//						startElement = ParserUtils.nextStartEvent(staxReader, "b").asStartElement();
//
//						String rating = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//						logger.info("Rating=" + rating);
//						event = staxReader.nextEvent();
//						if (event.isCharacters()) {
//							String ratingNumberStr = event.asCharacters().getData();
//							ratingNumberStr = ratingNumberStr.substring(1, ratingNumberStr.length() - 1);
//							int ratingNumber = 0;
//							try {
//								ratingNumber = Integer.parseInt(ratingNumberStr);
//							} catch (NumberFormatException e) {
//								e.printStackTrace();
//							}
//							logger.info("RatingNumber=" + ratingNumber);
//						}
//					}
//
//
//				}
//			}
//
////			}
//
//		} catch (XMLStreamException e) {
//			e.printStackTrace();
//		}
//
//	}


//	public static List<CourseUrlHolder> getCourseList(CategoryUrlHolder categoryUrlHolder,) {
//
//	}

//	public static int getTotalPageForEachCategory(CategoryUrlHolder urlHolder) {
//
//		String uri = urlHolder.getCategoryURL();
//
//		String beginSign = "section class='area-display-courses'";
//		String endSign = "form class='form-paginate form-inline'";
//
//		String htmlContent = ParserUtils.parseHTML(uri, beginSign, endSign);
//
//		htmlContent = ParserUtils.addMissingTag(htmlContent);
//		System.out.println(htmlContent);
//		int pageCount = -1;
//		try {
//			XMLEventReader staxReader = ParserUtils.getStaxReader(htmlContent);
//			boolean insidePaginationDiv = false;
//			while (staxReader.hasNext()) {
//				XMLEvent event = staxReader.nextEvent();
//				if (event.isStartElement()) {
//					StartElement startElement = event.asStartElement();
//					Iterator<Attribute> attributes = startElement.getAttributes();
//
//					if (!insidePaginationDiv) {
//						if (startElement.getName().getLocalPart().equals("div")) {
//
//							Attribute attributeClass = startElement.getAttributeByName(new QName("class"));
//							if (attributeClass != null && attributeClass.getValue().equals("pagination")) {
//								insidePaginationDiv = true;
//							}
//						}
//					}
////					while (attributes.hasNext()) {
////						Attribute attribute = attributes.next();
////						if (!insideMainMenu) {
////							if (attribute.getName().toString().equals("class") && attribute.getValue().equals("main-menu")) {
////								//inside the main menu div
////
////								insideMainMenu = true;
////							}
////						}
////
////
////
////					}
//					if (insidePaginationDiv) {
//
//						//todo get all <em class="current"> or <a href="...&page=[number]> content
//
//
//						if (ParserUtils.checkAttributeEqualsKey(startElement, "class", "current")
//								|| ParserUtils.getAttributeByName(startElement, "href").contains("page=")
//						) {
//
//							String pageContent = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//							try {
//								int pageNumber = Integer.parseInt(pageContent);
//
//								pageCount = Math.max(pageCount, pageNumber);
//							} catch (NumberFormatException e) {
////								e.printStackTrace();
//								//not a page number
//							}
//						}
//					}
//				}
//
//			}
//
//		} catch (XMLStreamException e) {
//			e.printStackTrace();
//		}
//		return pageCount;
//	}

	public List<CategoryUrlHolder> getCategories() {
		List<CategoryUrlHolder> categories = new ArrayList<>();

		String uri = Constants.EDUMALL_DOMAIN;

		String beginSign = "col-xs col-sm col-md col-lg main-header-v4--content-c-header-left";
		String endSign = "col-xs col-sm col-md col-lg main-header-v4--content-c-header-search";

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
					Iterator<Attribute> attributes = startElement.getAttributes();

					if (!insideMainMenu) {
						Attribute attributeClass = startElement.getAttributeByName(new QName("class"));
						if (attributeClass != null && attributeClass.getValue().equals("main-menu")) {
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
						if (startElement.getName().getLocalPart().equals("li")) {
							startElement = ParserUtils.nextStartEvent(staxReader).asStartElement();

							if (startElement.getName().getLocalPart().equals("a")) {
								String href = startElement.getAttributeByName(new QName("href")).getValue();
								//exclude the All Category tag
								if (href.contains("categories") && !href.contains("sub_categories")) {

									String categoryURL = href;
									categoryURL = Constants.EDUMALL_DOMAIN + categoryURL;

									startElement = ParserUtils.nextStartEvent(staxReader, "span").asStartElement();
									String categoryName = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);


//									logger.info(String.format("categoryURL=%s | categoryName=%s", categoryURL,categoryName));
									CategoryUrlHolder categoryUrlHolder = new CategoryUrlHolder(categoryName, categoryURL);
									categories.add(categoryUrlHolder);
									logger.info(categoryUrlHolder.toString());

								}


							}


						}
					}
				}

			}

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return categories;

	}


//	public static List<CourseUrlHolder> getCourseListFromEachPage(CategoryUrlHolder categoryUrlHolder) {
//
////		String uri = "https://edumall.vn/courses/filter&page=2";
//
//		String uri = categoryUrlHolder.getCategoryURL();
//		String beginSign = "section class='area-display-courses'";
//		String endSign = "form class='form-paginate form-inline'";
//
//		String htmlContent = ParserUtils.parseHTML(uri, beginSign, endSign);
//		htmlContent = ParserUtils.addMissingTag(htmlContent);
//
//
//		List<CourseUrlHolder> courseList = new ArrayList<>();
//
//		boolean insideCourseListDiv = false;
//		try {
//			XMLEventReader staxReader = ParserUtils.getStaxReader(htmlContent);
//			while (staxReader.hasNext()) {
//				XMLEvent event = staxReader.nextEvent();
//				if (event.isStartElement()) {
//					StartElement startElement = event.asStartElement();
//					Iterator<Attribute> attributes = startElement.getAttributes();
//
//
//					//todo check inside courseListDiv
//					//<div class='list-courses-filter'
//					if (!insideCourseListDiv) {
//						{
//							if (ParserUtils.checkAttributeContainsKey(startElement, "class", "list-courses-filter")) {
//								insideCourseListDiv = true;
//							}
//						}
//					}
////					while (attributes.hasNext()) {
////						Attribute attribute = attributes.next();
////						if (!insideMainMenu) {
////							if (attribute.getName().toString().equals("class") && attribute.getValue().equals("main-menu")) {
////								//inside the main menu div
////
////								insideMainMenu = true;
////							}
////						}
////
////
////
////					}					int d
//					if (insideCourseListDiv) {
//
//
//						//todo traverse each div with  <div class='col-4'>
//						if (startElement.getName().getLocalPart().equals("div")
//								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "col-4")) {
////							courseCount++;
////							XMLEvent articleElement = nextStartEvent(staxReader, "article");
//
//
//							CourseUrlHolder courseUrlHolder = new CourseUrlHolder();
//							courseList.add(courseUrlHolder);
//
//						}
//						//todo get thumnail image in
//						// <div class="course-header img-thumb gtm_section_recommendation"
//						// data-src="//d1nzpkv5wwh1xf.cloudfront.net/320/k-57b67d6e60af25054a055b20/20170817-tungnt9image1708/thanhnd04.png"
//
//						if (startElement.getName().getLocalPart().equals("div")
//								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "course-header")
//								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "img-thumb")
//								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "gtm_section_recommendation")) {
//							Attribute thumnailAttribute = startElement.getAttributeByName(new QName("data-src"));
//							if (thumnailAttribute != null) {
//
//								String thumnaillUrl = thumnailAttribute.getValue();
//								if (thumnaillUrl.startsWith("//")) {
//									thumnaillUrl = thumnaillUrl.substring(2);
//								}
//								CourseUrlHolder courseUrlHolder = courseList.get(courseList.size() - 1);
//								courseUrlHolder.setCourseThumbnailUrl(thumnaillUrl);
//								logger.info(String.format("course number=%s || thumnail=%s", courseList.size() - 1, thumnaillUrl));
//							}
//						}
//
//
//						//todo get CourseName
//						//	<h5 class="gtm_section_recommendation course-title" xpath="1">Microsoft Word cơ bản và hiệu quả</h5>
//						if (startElement.getName().getLocalPart().contains("h")
//								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "gtm_section_recommendation")
//								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "course-title")) {
//							String title = ParserUtils.getContentAndJumpToEndElement(staxReader, startElement);
//							CourseUrlHolder lastCourse = courseList.get(courseList.size() - 1);
//							lastCourse.setCourseName(title);
//							logger.info(String.format("course number=%s || courseName =%s", courseList.size() - 1, title));
//
//						}
//						//todo get courseUrl
//						//<a class='gtm_section_recommendation's
//
//						if (startElement.getName().getLocalPart().equals("a")
//								&& ParserUtils.checkAttributeContainsKey(startElement, "class", "gtm_section_recommendation")) {
//							CourseUrlHolder lastCourse = courseList.get(courseList.size() - 1);
//							if (lastCourse.getCourseUrl() == null || lastCourse.getCourseUrl().isEmpty()) {
//								//check for dupplicate a tags
//								Attribute hrefAttribute = startElement.getAttributeByName(new QName("href"));
//
//								if (hrefAttribute != null) {
//									String hrefValue = hrefAttribute.getValue();
//									hrefValue = Constants.EDUMALL_DOMAIN + hrefValue;
//									lastCourse.setCourseUrl(hrefValue);
//									logger.info(String.format("course number=%s || coureUrl=%s", courseList.size() - 1, hrefValue));
//
//								}
//							}
//						}
//
//
//					}
//				}
//
//			}
//
//		} catch (XMLStreamException e) {
//			e.printStackTrace();
//		}
//		return courseList;
//	}

//	public static void getAllCoursesFromCategory(CategoryUrlHolder categoryUrlHolder)  {
////		List<CourseUrlHolder> courseList = new ArrayList<>();
//
//
//		int pageNumber = getTotalPageForEachCategory(categoryUrlHolder);
//		System.out.println("pageNumber="+pageNumber);
//		//todo for each page
//		String uri = categoryUrlHolder.getCategoryURL();
//		//validate uri, remove ?=page form uri
//		if (uri.contains("page=")) {
//			uri = uri.substring(0,uri.indexOf("page=")-1);
//			//?page= or &page=
//		}
//
//		//todo debugging then change to pageNumber
//		for (int i = 1; i <= 1; i++) {
//			String eachPageUri = uri + "&page=" + i;
//			CategoryUrlHolder categoryHolderForEachPage =
//					new CategoryUrlHolder(categoryUrlHolder.getCategoryName(), eachPageUri);
//			List<CourseUrlHolder> courseListFromEachPage =
//					getCourseListFromEachPage(categoryHolderForEachPage);
//			//todo get course detail here right after get course list from a category page
//			for (CourseUrlHolder courseUrlHolder : courseListFromEachPage) {
//				getCourseDetail(courseUrlHolder);
//			}
//		}
//
////		return courseList;
//
//
//	}


	@Override
	public void run() {

		logger.info("start thread");

		try {
			// todo insert domain to db if not yet availabe
			if (DomainDAO.getInstance().getDomainByName(Constants.EDUMALL_DOMAIN_NAME) == null) {
				//insert to database
//				DummyDatabase.insertDomain(Constants.EDUMALL_DOMAIN_NAME, Constants.EDUMALL_DOMAIN);
				DomainEntity domainEntity = new DomainEntity();
				domainEntity.setName(Constants.EDUMALL_DOMAIN_NAME);
				domainEntity.setDomainUrl(Constants.EDUMALL_DOMAIN);
				DomainDAO.getInstance().persist(domainEntity);
			}
			domainId = DomainDAO.getInstance().getDomainByName(Constants.EDUMALL_DOMAIN_NAME).getId();

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

				CategoryMapping categoryMapping = CategoryMapping.mapEdumall(edumallCategoryName);

				//get categoryId from database
				int categoryId = CategoryDAO.getInstance().getCategoryId(categoryMapping);


				Thread edumallEachCategoryCrawler = new Thread(new EdumallEachCategoryCrawler(categoryId, categoryUrlHolder.getCategoryURL()));


				//todo thread executor
				BaseThread.getInstance().getExecutor().execute(edumallEachCategoryCrawler);
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
