package com.ziggy192.coursesource;

import com.ziggy192.coursesource.url_holder.EdumallCategoryUrlHolder;
import com.ziggy192.coursesource.url_holder.EdumallCourseUrlHolder;
import com.ziggy192.coursesource.util.Formater;
import com.ziggy192.coursesource.util.Utils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.java2d.xr.MutableInteger;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.*;
import java.security.DomainCombiner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class EdumallCrawler {

	public static Logger logger = LoggerFactory.getLogger(EdumallCrawler.class.toString());
	public static XMLEventAllocator allocator;


	public static void main(String[] args) {
		// crawl from edumall

//		System.out.println(Utils.addMissingTag(
//				"<div>" +
//						"<img>" +
//						"<div>" +
//						"</div>" +
//						"<input>" +
//						"<div>" +
//						"<h3>" +
//						"</div>" +
//						"</div>"
//		));
//

//		getCategories();
//		testGetCourseListFromEachPage();

//		testGetCourseDetail();

		//test split method
//		String[] s = "teacher_name teacher teacher_info".split(" ");
//		for (String s1 : s) {
//			System.out.println("'" + s1 + "'");
//		}
//		System.out.println();

//		String attValue = attribute.getValue();
//		List<String> attValueItem = Arrays.asList("teacher_name teacher_date teacher_info".split(" "));


//
//

//				Arrays.asList(keys).contains()
//		String[] keys = {"teacher_name"};
//		boolean flag = true;
//		for (String key : keys) {
//			if (!attValueItem.contains(key)) {
////				System.out.println(false);
//				flag = false;
//				break;
//
//			}
//		}
//		System.out.println(flag);
//
//		List<EdumallCategoryUrlHolder> categories = getCategories();
//		for (EdumallCategoryUrlHolder category : categories) {
//			getAllCoursesFromCategory(category);
//		}

		testGetCourseListFromEachPage();

//		testGetPageNumber();
	}

	public static void testGetPageNumber() {
		String uri = "https://edumall.vn/courses/filter?categories[]=luyen-thi&page=2";

		EdumallCategoryUrlHolder categoryUrlHolder = new EdumallCategoryUrlHolder("Luyện thi", uri);
		//test get total pages
		int page = getTotalPageForEachCategory(categoryUrlHolder);
		System.out.println("Total page =" + page);

	}

	private static void testGetCourseDetail() {

		EdumallCourseUrlHolder dummyCourseUrlHolder = new EdumallCourseUrlHolder("Guitar đệm hát trong 30 ngày"
				, "http://d1nzpkv5wwh1xf.cloudfront.net/640/k-5768aeb1047c995f75fdbf6b/20170817-anhdaidienmoi_thinh/hiennt01.png"
				, "https://edumall.vn/course/v4/guitar-dem-hat-trong-30-ngay");
		getCourseDetail(dummyCourseUrlHolder);

	}


	public static void getCourseDetail(EdumallCourseUrlHolder courseUrlHolder) {
		System.out.println("Detail=======================");
//		String uri = "https://edumall.vn/course/guitar-dem-hat-trong-30-ngay";
//		String uri = "https://edumall.vn/v4/course/microsoft-word-co-ban-va-hieu-qua";
		String uri = courseUrlHolder.getCourseUrl();

		String beginSign = "<div class='wrapper'";
		String endSign = "<section class='section_rating";


		//todo add '/v4' to uri because that's what edumall.vn do

		int index = uri.indexOf("edumall.vn/course/") + "edumall.vn/course/".length();
		if (!uri.substring(index).startsWith("v4")) {
			uri = "https://edumall.vn/course/v4/" + uri.substring(index);

		}

		System.out.println(uri);


		String htmlContent = Utils.parseHTML(uri, beginSign, endSign);
//		System.out.println(htmlContent);
		htmlContent = Utils.addMissingTag(htmlContent);
//		System.out.println(newContent);



		String overviewDescription = "";
		try {
			XMLEventReader staxReader = Utils.getStaxReader(htmlContent);
			while (staxReader.hasNext()) {
				XMLEvent event = staxReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					Iterator<Attribute> attributes = startElement.getAttributes();


					//todo check inside courseListDiv
					//<div class='list-courses-filter'
//					if (!insideContainerDiv) {
//						{
//							if (startElement.getName().getLocalPart().equals("div")
//									&& Utils.checkAttributeContainsKey(startElement, "class", "col-lg-8")) {
//								insideContainerDiv = true;
//							}
//						}
//					}
//					if (insideContainerDiv) {


					//todo get VideoURL

					//<iframe allowfullscreen="" frameborder="0" height="100%" src="https://www.youtube.com/embed/cyGq22d1sbk?modestbranding=0&amp;amp;rel=0&amp;amp;showinfo=0" width="100%"></iframe>
					if (startElement.getName().getLocalPart().equals("iframe")
//								&& Utils.checkAttributeContainsKey(startElement, "class", "ytp-title-link")
//								&& Utils.checkAttributeContainsKey(startElement, "class", "yt-uix-sessionlink")
					) {

						Attribute srcAtt = startElement.getAttributeByName(new QName("src"));

						if (srcAtt != null) {
							String videoUrl = srcAtt.getValue();
							logger.info("VideURL=" + videoUrl);

						}

					}


					//todo getcost
					//<p class="col-md-12 col-xs-12 price" style="" xpath="1">699,000đ</p>

					if (startElement.getName().getLocalPart().equals("p")
							&& Utils.checkAttributeContainsKey(startElement, "class", new String[]{
							"col-md-12", "col-xs-12", "price"
					})
					) {
						String cost = Utils.getContentAndJumpToEndElement(staxReader, startElement);
						if (!cost.isEmpty()) {

							//get int value from cost
							int costValue = 0;
							for (int i = 0; i < cost.length(); i++) {
								if (cost.charAt(i) >= '0' && cost.charAt(i) <= '9') {
									int charValue = cost.charAt(i) - '0';
									costValue = costValue * 10 + charValue;
								}
							}
							logger.info("Cost=" + costValue);
						}
					}

					//todo get duration
					//<div class="form-layout col-xs-12" xpath="1">
					//<i class="fas fa-clock pull-left padding_icon"></i>
					//<span class="pull-left">Thời lượng video</span>
					//<span class="pull-right">05:58:48</span>
					//</div>
					if (startElement.getName().getLocalPart().equals("div")
							&& Utils.checkAttributeContainsKey(startElement, "class", "prop")) {
						Utils.nextStartEvent(staxReader, "i", new String[]{
								"fas", "fa-clock"
						});
						startElement = Utils.nextStartEvent(staxReader, "span", new String[]{"pull-right"}).asStartElement();
						String duration = Utils.getContentAndJumpToEndElement(staxReader, startElement);
						if (!duration.isEmpty()) {
							logger.info("Duration=" + duration);
						}
					}

					//todo get author name
//					if (startElement.getName().getLocalPart().equals("span")
//							&& Utils.checkAttributeContainsKey(startElement, "class", "name_teacher")) {
//						String authorName = Utils.getContentAndJumpToEndElement(staxReader, startElement);
//						if (!authorName.isEmpty()) {
//							logger.info("Author="+authorName);
//						}
//					}

					/*
					<section class="section_author" id="general-author-tab">
					<div class="info_author" style="" xpath="1">
					<div class="pull-left image_author">
					<img src="//d303ny97lru840.cloudfront.net/kelley-57bfb0d3ce4b1438274ba1fd/20160829-hiennt02-edumall/533079_556033297754623_542734647_n.jpg">
					</div>
					<div class="pull-left name_author" style="">
					<p class="author" style="font-size: 16px;">Giảng viên</p>
					<div class="name" style="">Nguyễn Thượng Hiển</div>

					</div>
					</div>*/
					if (startElement.getName().getLocalPart().equals("section")
							&& Utils.checkAttributeContainsKey(startElement, "id", "general-author-tab")) {
						//traverse to end section
						String authorInfo = Formater.toHeading2("Tiểu sử");

						int stackCount = 1;

						boolean insideAuthorInfo = false;

//						List<String> contentList = new ArrayList<>();
						while (stackCount > 0) {
							event = staxReader.nextEvent();
							if (event.isStartElement()) {
								stackCount++;
								startElement = event.asStartElement();

								if (startElement.getName().getLocalPart().equals("div")
										&& Utils.checkAttributeContainsKey(startElement, "class", "name")) {
									String authorName = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--;
									if (!authorName.isEmpty()) {
										logger.info("Author=" + authorName);
									}
								}


								//<img src="//d303ny97lru840.cloudfront.net/kelley-57bfb0d3ce4b1438274ba1fd/20160829-hiennt02-edumall/533079_556033297754623_542734647_n.jpg" style="">
								if (startElement.getName().getLocalPart().equals("img")) {
									String imageUrl = startElement.getAttributeByName(new QName("src")).getValue();
									if (imageUrl.startsWith("//")) {
										imageUrl = imageUrl.substring(2);
									}

									logger.info("AuthorImage=" + imageUrl);
								}
								if (Utils.checkAttributeContainsKey(startElement, "id", "author_info")) {
									insideAuthorInfo = true;

								}
								if (insideAuthorInfo) {
									if (startElement.getName().getLocalPart().equals("li")) {
										String content = Utils.getContentAndJumpToEndElement(staxReader, startElement);
										stackCount--;
										content = Formater.toParagraph(content);
										authorInfo += content;
									}
								}
							}
							if (event.isEndElement()) {
								stackCount--;

							}

						}


						logger.info("AuthorInfo=" + authorInfo);
					}

					// TODO: get overview description

					//loi ich tu khoa hoc
					/*<section class="section_benefit section-setting" id="general-info-tab" data-gtm-vis-first-on-screen-8263996_483="1091" data-gtm-vis-total-visible-time-8263996_483="5000" data-gtm-vis-has-fired-8263996_483="1" style="" xpath="1">
					<p class="title" style="">Lợi ích từ khóa học</p>
					<div class="content" style="">
					<div class="content_benefit">
					<i class="fas fa-check-circle"></i>
					<span> Nắm vững nhạc lý: Cách đọc tọa độ, bấm hợp âm, tiết tấu; Cách rải âm và quạt chả.</span>
					</div>
					<div class="content_benefit">
					<i class="fas fa-check-circle"></i>
					<span> Thành thạo các điệu cơ bản: Surf nhanh - chậm, Disco, Blue, Ballad, Báo, Fox, Valse, Bolero, Slow Rock,...</span>
					</div>
					<div class="content_benefit">
					<i class="fas fa-check-circle"></i>
					<span> Thành thạo cách dò các nhịp, điệu của một bài hát, bắt nhịp và chọn điệu, bắt tông cho ca sĩ, đánh intro và outro, search hợp âm chuẩn,...</span>
					</div>
					<div class="content_benefit">
					<i class="fas fa-check-circle"></i>
					<span> Biết cách chọn đàn sao cho phù hợp với mục đích, túi tiền và phong cách nhưng vẫn phải đảm bảo những yêu tố thiết yếu.</span>
					</div>
					</div>
					<div class="clear"></div>
					</section>*/
					if (startElement.getName().getLocalPart().equals("section")
							&& Utils.checkAttributeContainsKey(startElement, "id", "general-info-tab")) {

						//traverse to end section
						int stackCount = 1;
						List<String> contentList = new ArrayList<>();
						while (stackCount > 0) {
							event = staxReader.nextEvent();
							if (event.isStartElement()) {
								stackCount++;
								startElement = event.asStartElement();

								if (Utils.checkAttributeContainsKey(startElement, "class", "title")) {
									//title
									String title = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement

									title = Formater.toHeading1(title);

									overviewDescription = overviewDescription + title;


								}

								if (Utils.checkAttributeContainsKey(startElement, "class", "content_benefit")) {
									//content benefit
									startElement = Utils.nextStartEvent(staxReader, "span").asStartElement();
									MutableInteger mutableStack = new MutableInteger(stackCount);

									String contentBenefit = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement
									contentList.add(contentBenefit);
								}

							}
							if (event.isEndElement()) {
								stackCount--;

							}

						}
						overviewDescription += Formater.toList((contentList.toArray(new String[contentList.size()])));
						logger.info("OverviewPar1=" + overviewDescription);

					}


					//Doi tuong muc tieu && Yeu cau khoa hoc
					/*
					*<section class="section_requirement section-setting" data-gtm-vis-first-on-screen-8263996_503="1091" data-gtm-vis-total-visible-time-8263996_503="5000" data-gtm-vis-has-fired-8263996_503="1" style="" xpath="1">
						<p class="title">Đối tượng mục tiêu</p>
						<ul class="required">
						<div class="cover-require col-md-12">
						<li class="require"> Yêu thích âm nhạc và có cảm hứng đặc biệt với những cây đàn Guitar.</li>
						</div>
						<div class="clear"></div>
						<div class="cover-require col-md-12">
						<li class="require"> Muốn học Guitar đệm hát nhưng chưa biết bắt đầu từ đâu</li>
						</div>
						<div class="clear"></div>
						<div class="cover-require col-md-12">
						<li class="require"> Muốn học Guitar nhưng bị giới hạn về thời gian và tài chính</li>
						</div>
						<div class="clear"></div>
						<div class="cover-require col-md-12">
						<li class="require"> Học đệm hát để chơi các bài hát mà mình yêu thích</li>
						</div>
						<div class="clear"></div>
						</ul>
						</section>
					*/
					if (startElement.getName().getLocalPart().equals("section")
							&& Utils.checkAttributeContainsKey(startElement, "class", "section_requirement")
					) {
						//traverse to end section
						int stackCount = 1;
						List<String> contentList = new ArrayList<>();

						while (stackCount > 0) {
							event = staxReader.nextEvent();
							if (event.isStartElement()) {
								stackCount++;
								startElement = event.asStartElement();

								//title
								if (Utils.checkAttributeContainsKey(startElement, "class", "title")) {
									String title = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement

									overviewDescription += Formater.toHeading1(title);

								}

								//list
								if (startElement.getName().getLocalPart().equals("li")) {
									String listItem = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement

									contentList.add(listItem);
								}


							}
							if (event.isEndElement()) {
								stackCount--;

							}

						}

						overviewDescription += Formater.toList(contentList.toArray(new String[contentList.size()]));
						logger.info("OverviewPart2" + overviewDescription);


					}

					//Tong quat
					/*<section class="section_description section-setting" data-gtm-vis-first-on-screen-8263996_504="1619" data-gtm-vis-total-visible-time-8263996_504="5000" data-gtm-vis-has-fired-8263996_504="1" xpath="1">
					<div class="title">Tổng quát</div>
					<div class="description-content show-more-content" id="show_more_des">
					<div class="text_description"><p>Khóa học gồm :<br>- 6 học phần<br>- 77 bài giảng được hướng dẫn cụ thể từ giảng viên<br>- 5 cấp độ từ cơ bản đến nâng cao<br>- Hệ thống tài liệu chi tiết cho từng học phần.</p></div>
					</div>
					<div class="show_more" id="show_more_des_button">XEM THÊM</div>
					</section>
					*/

					if (startElement.getName().getLocalPart().equals("section")
							&& Utils.checkAttributeContainsKey(startElement, "class", "section_description")) {
						//traverse to end section
						int stackCount = 1;

						while (stackCount > 0) {
							event = staxReader.nextEvent();
							if (event.isStartElement()) {
								stackCount++;
								startElement = event.asStartElement();

								//title
								if (Utils.checkAttributeContainsKey(startElement, "class", "title")) {
									String title = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--; // getContentAndJumpToEndElement() traverse to EndElement

									overviewDescription += Formater.toHeading1(title);

								}

								//content
								if (startElement.getName().getLocalPart().equals("div")
										&& Utils.checkAttributeContainsKey(startElement, "class", "text_description")) {
									String htmlDescriptionContent = Utils.getAllHtmlContentAndJumpToEndElement(staxReader, startElement);
									stackCount--;
									overviewDescription += htmlDescriptionContent;
								}
							}
							if (event.isEndElement()) {
								stackCount--;

							}
						}

						logger.info("OverviewPart3=" + overviewDescription);

					}


					//todo get syllabus
					/*
					 * <div id='section_lecture'>
					 * 		<li class='menu'>
					 * 			<div class='menu-title>
					 *
					 *
					 *			<div class='lecture-title>
					 *
					 * -->    <h3>title</h3>
					 * 		<ul>
					 * 			<li>bai 1</li>
					 * */


					if (Utils.checkAttributeContainsKey(startElement, "id", "section_lecture")) {
						//traverse
						String syllabus = "";
						int stackCount = 1;
						List<String> lectureNameList = new ArrayList<>();
						while (stackCount > 0) {
							event = staxReader.nextEvent();

							if (event.isStartElement()) {
								stackCount++;
								startElement = event.asStartElement();
								if (Utils.checkAttributeContainsKey(startElement, "class", "menu-title")) {

									String content = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--;
									content = Formater.toHeading3(content);

									if (lectureNameList.size() > 0) {
										syllabus += Formater.toList(lectureNameList.toArray(new String[lectureNameList.size()]));
										lectureNameList = new ArrayList<>();
									}


									syllabus += content;

								}
								if (Utils.checkAttributeContainsKey(startElement, "class", "lecturetitle")) {
									String lectureName = Utils.getContentAndJumpToEndElement(staxReader, startElement);
									stackCount--;
									lectureNameList.add(lectureName);
								}

							}
							if (event.isEndElement()) {
								stackCount--;

							}

						}

						//dequeue the last lectureNamelist

						if (lectureNameList.size() > 0) {
							syllabus += Formater.toList(lectureNameList.toArray(new String[lectureNameList.size()]));
						}
						logger.info("Syllabus=" + syllabus);

					}
					//lecture-name

					//todo get rating && rating number
					//div class="intro_course'
					//<div class="star-rating" xpath="1">
					//<div class="back-stars">

					//<div class="front-stars" style="width: 100.0%">

					//</div>
					//</div>
					//<b style="padding-left:10px; padding-right:10px;">5.0</b>
					//(153)
					//</div>
					if (startElement.getName().getLocalPart().equals("div")
							&& Utils.checkAttributeContainsKey(startElement, "class", "intro_course")) {
//						startElement = nextStartEvent(staxReader, "div", new String[]{"star-rating"}).asStartElement();
//					}
//					if (startElement.getName().getLocalPart().equals("div")
//							&& Utils.checkAttributeContainsKey(startElement, "class", "star-rating")
//					) {
						startElement = Utils.nextStartEvent(staxReader, "b").asStartElement();

						String rating = Utils.getContentAndJumpToEndElement(staxReader, startElement);
						logger.info("Rating=" + rating);
						event = staxReader.nextEvent();
						if (event.isCharacters()) {
							String ratingNumberStr = event.asCharacters().getData();
							ratingNumberStr = ratingNumberStr.substring(1, ratingNumberStr.length() - 1);
							int ratingNumber = 0;
							try {
								ratingNumber = Integer.parseInt(ratingNumberStr);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
							logger.info("RatingNumber=" + ratingNumber);
						}
					}


				}
			}

//			}

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

	}

	private static void testGetCourseListFromEachPage() {

		String uri = "https://edumall.vn/courses/filter?categories[]=luyen-thi&page=2";

		EdumallCategoryUrlHolder categoryUrlHolder = new EdumallCategoryUrlHolder("Luyện thi", uri);
		//test get total pages
		int page = getTotalPageForEachCategory(categoryUrlHolder);
		System.out.println("Total page =" + page);

		List<EdumallCourseUrlHolder> cousreListFromContent = getCourseListFromEachPage(categoryUrlHolder);

		System.out.println("========Done getting course list =========");
		for (EdumallCourseUrlHolder courseUrlHolder : cousreListFromContent) {
			System.out.println(courseUrlHolder);
		}
	}

//	public static List<EdumallCourseUrlHolder> getCourseList(EdumallCategoryUrlHolder categoryUrlHolder,) {
//
//	}

	public static int getTotalPageForEachCategory(EdumallCategoryUrlHolder urlHolder) {

		String uri = urlHolder.getCategoryURL();

		String beginSign = "section class='area-display-courses'";
		String endSign = "form class='form-paginate form-inline'";

		String htmlContent = Utils.parseHTML(uri, beginSign, endSign);

		htmlContent = Utils.addMissingTag(htmlContent);
		System.out.println(htmlContent);
		int pageCount = -1;
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

	public static List<EdumallCategoryUrlHolder> getCategories() {
		List<EdumallCategoryUrlHolder> categories = new ArrayList<>();

		String uri = Constants.EDUMALL_DOMAIN;

		String beginSign = "col-xs col-sm col-md col-lg main-header-v4--content-c-header-left";
		String endSign = "col-xs col-sm col-md col-lg main-header-v4--content-c-header-search";

		String htmlContent = Utils.parseHTML(uri, beginSign, endSign);
		String newContent = Utils.addMissingTag(htmlContent);

//		System.out.println(newContent);;
		logger.info(newContent);


		try {
			XMLEventReader staxReader = Utils.getStaxReader(newContent);
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
							startElement = Utils.nextStartEvent(staxReader).asStartElement();

							if (startElement.getName().getLocalPart().equals("a")) {
								String href = startElement.getAttributeByName(new QName("href")).getValue();
								//exclude the All Category tag
								if (href.contains("categories") && !href.contains("sub_categories")) {

									String categoryURL = href;
									categoryURL = Constants.EDUMALL_DOMAIN + categoryURL;

									startElement = Utils.nextStartEvent(staxReader, "span").asStartElement();
									String categoryName = Utils.getContentAndJumpToEndElement(staxReader, startElement);


//									logger.info(String.format("categoryURL=%s | categoryName=%s", categoryURL,categoryName));
									EdumallCategoryUrlHolder categoryUrlHolder = new EdumallCategoryUrlHolder(categoryName, categoryURL);
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


	public static List<EdumallCourseUrlHolder> getCourseListFromEachPage(EdumallCategoryUrlHolder categoryUrlHolder) {

//		String uri = "https://edumall.vn/courses/filter&page=2";

		String uri = categoryUrlHolder.getCategoryURL();
		String beginSign = "section class='area-display-courses'";
		String endSign = "form class='form-paginate form-inline'";

		String htmlContent = Utils.parseHTML(uri, beginSign, endSign);
		htmlContent = Utils.addMissingTag(htmlContent);


		List<EdumallCourseUrlHolder> courseList = new ArrayList<>();

		boolean insideCourseListDiv = false;
		try {
			XMLEventReader staxReader = Utils.getStaxReader(htmlContent);
			while (staxReader.hasNext()) {
				XMLEvent event = staxReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					Iterator<Attribute> attributes = startElement.getAttributes();


					//todo check inside courseListDiv
					//<div class='list-courses-filter'
					if (!insideCourseListDiv) {
						{
							if (Utils.checkAttributeContainsKey(startElement, "class", "list-courses-filter")) {
								insideCourseListDiv = true;
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
//					}					int d
					if (insideCourseListDiv) {


						//todo traverse each div with  <div class='col-4'>
						if (startElement.getName().getLocalPart().equals("div")
								&& Utils.checkAttributeContainsKey(startElement, "class", "col-4")) {
//							courseCount++;
//							XMLEvent articleElement = nextStartEvent(staxReader, "article");


							EdumallCourseUrlHolder courseUrlHolder = new EdumallCourseUrlHolder();
							courseList.add(courseUrlHolder);

						}
						//todo get thumnail image in
						// <div class="course-header img-thumb gtm_section_recommendation"
						// data-src="//d1nzpkv5wwh1xf.cloudfront.net/320/k-57b67d6e60af25054a055b20/20170817-tungnt9image1708/thanhnd04.png"

						if (startElement.getName().getLocalPart().equals("div")
								&& Utils.checkAttributeContainsKey(startElement, "class", "course-header")
								&& Utils.checkAttributeContainsKey(startElement, "class", "img-thumb")
								&& Utils.checkAttributeContainsKey(startElement, "class", "gtm_section_recommendation")) {
							Attribute thumnailAttribute = startElement.getAttributeByName(new QName("data-src"));
							if (thumnailAttribute != null) {

								String thumnaillUrl = thumnailAttribute.getValue();
								if (thumnaillUrl.startsWith("//")) {
									thumnaillUrl = thumnaillUrl.substring(2);
								}
								EdumallCourseUrlHolder courseUrlHolder = courseList.get(courseList.size() - 1);
								courseUrlHolder.setCourseThumbnailUrl(thumnaillUrl);
								logger.info(String.format("course number=%s || thumnail=%s", courseList.size() - 1, thumnaillUrl));
							}
						}


						//todo get CourseName
						//	<h5 class="gtm_section_recommendation course-title" xpath="1">Microsoft Word cơ bản và hiệu quả</h5>
						if (startElement.getName().getLocalPart().contains("h")
								&& Utils.checkAttributeContainsKey(startElement, "class", "gtm_section_recommendation")
								&& Utils.checkAttributeContainsKey(startElement, "class", "course-title")) {
							String title = Utils.getContentAndJumpToEndElement(staxReader, startElement);
							EdumallCourseUrlHolder lastCourse = courseList.get(courseList.size() - 1);
							lastCourse.setCourseName(title);
							logger.info(String.format("course number=%s || courseName =%s", courseList.size() - 1, title));

						}
						//todo get courseUrl
						//<a class='gtm_section_recommendation's

						if (startElement.getName().getLocalPart().equals("a")
								&& Utils.checkAttributeContainsKey(startElement, "class", "gtm_section_recommendation")) {
							EdumallCourseUrlHolder lastCourse = courseList.get(courseList.size() - 1);
							if (lastCourse.getCourseUrl() == null || lastCourse.getCourseUrl().isEmpty()) {
								//check for dupplicate a tags
								Attribute hrefAttribute = startElement.getAttributeByName(new QName("href"));

								if (hrefAttribute != null) {
									String hrefValue = hrefAttribute.getValue();
									hrefValue = Constants.EDUMALL_DOMAIN + hrefValue;
									lastCourse.setCourseUrl(hrefValue);
									logger.info(String.format("course number=%s || coureUrl=%s", courseList.size() - 1, hrefValue));

								}
							}
						}


					}
				}

			}

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return courseList;
	}

	public static List<EdumallCourseUrlHolder> getAllCoursesFromCategory(EdumallCategoryUrlHolder categoryUrlHolder) {
		List<EdumallCourseUrlHolder> courseList = new ArrayList<>();


		int pageNumber = getTotalPageForEachCategory(categoryUrlHolder);
		System.out.println("pageNumber="+pageNumber);
		//todo for each page
		List<EdumallCategoryUrlHolder> categoryUrlForEachPage = new ArrayList<>();

		String uri = categoryUrlHolder.getCategoryURL();
		//validate uri, remove ?=page form uri
		if (uri.contains("page=")) {
			uri = uri.substring(uri.indexOf("page=")-1);
			//?page= or &page=
		}

		//todo debugging then change to pageNumber
		for (int i = 1; i <= 1; i++) {
			String eachPageUri = uri + "&page=" + i;
			EdumallCategoryUrlHolder categoryHolderForEachPage = new EdumallCategoryUrlHolder(categoryUrlHolder.getCategoryName(), uri);
			List<EdumallCourseUrlHolder> courseListFromEachPage = getCourseListFromEachPage(categoryHolderForEachPage);
			for (EdumallCourseUrlHolder courseUrlHolder : courseListFromEachPage) {
				getCourseDetail(courseUrlHolder);
			}
		}

		return courseList;


	}


	public static void oldMain() {

		String desElement = "div";
		String desAttributeName = "class";
		String desAttributeValue = "main-menu";
		try {

			StreamSource streamSource = Utils.parseHTML("https://edumall.vn/");

			System.out.println("done parsing html");

//			XMLStreamReader staxReader = getStaxReaderNotValidating(Constants.FILE_EDUMALL_HOME);
			XMLStreamReader staxReader = Utils.getStaxReaderNotValidating(streamSource.getInputStream());

			while (staxReader.hasNext()) {
				try {
					int next = staxReader.next();
					if (next == XMLStreamConstants.START_ELEMENT) {
//						if (event.isStartElement()) {
						XMLEvent event = allocator.allocate(staxReader);

						StartElement startElement = event.asStartElement();
						System.out.println("startElement: " + startElement.getName().toString());
						if (startElement.getName().toString().equals("div")) {
							Attribute attribute = startElement.getAttributeByName(new QName(desAttributeName));
							if (attribute != null) {
								String value = attribute.getValue();
								if (value.equals(desAttributeValue)) {
									// found the destination tag ( beginSign)
									System.out.println("Found the destination tag");

									Iterator<XMLEvent> xmlEventIterator = Utils.traverseToEndTagAndAddMissingTags(
											Utils.transformToEventReader(staxReader));

									Utils.printData(xmlEventIterator);
									break;
								}
							}
						}
					}

				} catch (XMLStreamException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
					break;
				}


			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


}
