package com.ziggy192.coursesource.crawler;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.ziggy192.coursesource.url_holder.EdumallCourseUrlHolder;
import com.ziggy192.coursesource.util.Formater;
import com.ziggy192.coursesource.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.java2d.xr.MutableInteger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EdumallCourseDetailCrawler implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(EdumallCourseDetailCrawler.class.toString());


	private EdumallCourseUrlHolder courseDetailUrlHolder;
	private int categoryId;

	public EdumallCourseDetailCrawler(EdumallCourseUrlHolder courseDetailUrlHolder, int categoryId) {
		this.courseDetailUrlHolder = courseDetailUrlHolder;
		this.categoryId = categoryId;
	}

	@Override
	public void run() {

		try {
			logger.info("start thread");
//		String uri = "https://edumall.vn/course/guitar-dem-hat-trong-30-ngay";
//		String uri = "https://edumall.vn/v4/course/microsoft-word-co-ban-va-hieu-qua";
			String uri = courseDetailUrlHolder.getCourseUrl();

			String beginSign = "<div class='wrapper'";
			String endSign = "<section class='section_rating";


			// add '/v4' to uri because that's what edumall.vn do
			// ---- no need anymore because edumall.vn fixed it - well-played assholes
//
//			int index = uri.indexOf("edumall.vn/course/") + "edumall.vn/course/".length();
//			if (!uri.substring(index).startsWith("v4")) {
//				uri = "https://edumall.vn/course/v4/" + uri.substring(index);
//
//			}


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

				//if no exception then save to data base
				//todo save to db here
				//get name, image, url from holder
				//get the rest except tag from above
				//get categoryid

				//todo get providerid from EdumallMainCrawler static or singleton
				//get providerid = 'edumall'



			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
			logger.info("END THREAD");
		} catch (Exception e) {
			e.printStackTrace();

		}


	}
}
