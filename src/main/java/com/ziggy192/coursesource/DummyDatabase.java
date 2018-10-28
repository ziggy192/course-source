package com.ziggy192.coursesource;

import com.ziggy192.coursesource.crawler.EdumallCourseDetailCrawler;
import com.ziggy192.coursesource.dao.CategoryDAO;
import com.ziggy192.coursesource.entity.CourseEntity;
import com.ziggy192.coursesource.model.Domain;
import com.ziggy192.coursesource.util.DBUtils;
import jaxb.Category;
import jaxb.CourseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class DummyDatabase {

	private static Logger logger = LoggerFactory.getLogger(DummyDatabase.class.toString());



	public static void applicaionInit() {

		//turn off log for hibernate
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

// todo (do this in servletInit(): insert category names to db if not yet available
		CategoryMapping[] categoryMappings = CategoryMapping.values();
		for (CategoryMapping value : categoryMappings) {
			CategoryDAO.getInstance().insertCategoryByNameIfNotExist(value.getValue());
		}

	}


	public static void applicationDestroy() {
		DBUtils.closeEntityFactory();
	}
}
