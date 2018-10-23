package com.ziggy192.coursesource;

import com.ziggy192.coursesource.util.Utils;

public class EdumallHTMLParser {


	public static void main(String[] args) {
		String uri = "https://edumall.vn/";
//		String fileName = Constants.FILE_EDUMALL_HOME;

		String filePath = Constants.FILE_EDUMALL_HOME;
		System.out.println(filePath);
//		String filePath = EdumallHTMLParser.class.getClassLoader().getResource("edumall.vn.html").getFile();
//		String filePath = EdumallHTMLParser.class.getClassLoader().p.getCodeSource().getLocation().getPath()+"/../"
		Utils.parseHTML(filePath, uri);
	}
}
