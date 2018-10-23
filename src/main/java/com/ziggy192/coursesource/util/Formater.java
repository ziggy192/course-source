package com.ziggy192.coursesource.util;

public class Formater {

	/*
	* Vi sao dung formater
	* Moi domain deu su dung 1 format rieng
	* Can mot format chung de bao dam tinh consistent giua cac trang crawl ve
	* Tham khao cac tag co ban cua html va MicrosoftWord -> dam bao nhu cau toi thieu ve flexibility cua format
	* dung css cho class 'crawled'
	* */

	private static final String CSS_CLASS_ATTRIBUTE_NAME = "crawled";
	public static String toHeading1(String content) {
		return String.format("<h1 class='%s'>%s</h1>", CSS_CLASS_ATTRIBUTE_NAME, content);
	}
	public static String toHeading2(String content) {
		return String.format("<h2 class='%s'>%s</h2>", CSS_CLASS_ATTRIBUTE_NAME, content);
	}
	public static String toHeading3(String content) {
		return String.format("<h3 class='%s'>%s</h3>", CSS_CLASS_ATTRIBUTE_NAME, content);
	}

	public static String toHeading4(String content) {
		return String.format("<h4 class='%s'>%s</h4>", CSS_CLASS_ATTRIBUTE_NAME, content);
	}

	public static String toStrong(String content) {
		return String.format("<strong class='%s'>%s</strong>", CSS_CLASS_ATTRIBUTE_NAME, content);
	}



	public static String toList(String[] listItem) {
		String result = "";
		result += String.format("<ul class='%s'>", CSS_CLASS_ATTRIBUTE_NAME);
		for (String item : listItem) {
			result += String.format("<li class='%s'>%s</li>", CSS_CLASS_ATTRIBUTE_NAME, item);
		}
		result += String.format("</ul class='%s'>", CSS_CLASS_ATTRIBUTE_NAME);

		return result;
	}


	public static String toParagraph(String content) {
		return String.format("<p class='%s'>%s</p>", CSS_CLASS_ATTRIBUTE_NAME, content);
	}

	public static String addNextLine(String content) {
		return content + "<br/>";
	}


	public static String toListItem(String item) {
		return String.format("<li class='%s'>%s</li>", CSS_CLASS_ATTRIBUTE_NAME, item);
	}



	public static String addBeginList(String content) {
		return String.format("%s<ul class='%s'>", content, CSS_CLASS_ATTRIBUTE_NAME);

	}

	public static String addEndList(String content) {
		return content + "</ul>";
	}

}
