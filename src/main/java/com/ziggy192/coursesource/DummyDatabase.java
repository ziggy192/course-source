package com.ziggy192.coursesource;

import com.ziggy192.coursesource.crawler.EdumallCourseDetailCrawler;
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

public class DummyDatabase {

	private static Logger logger = LoggerFactory.getLogger(DummyDatabase.class.toString());

	public static int getCategoryId(CategoryMapping categoryMapping) {
		int result;

		switch (categoryMapping) {
			case TEST_PREP:
				result = 1;
				break;
			case MULTIMEDIA:
				result = 2;
				break;
			case DESIGN:
				result = 3;
				break;
			case PERSONAL_DEVELOPMENT:
				result = 4;
				break;
			case OFFICE_PRODUCTIVITY:
				result = 5;
				break;
			case MUSIC:
				result = 6;
				break;
			case MARKETING:
				result = 7;
				break;
			case LIFE_STYLE:
				result = 8;
				break;
			case LANGUAGE:
				result = 9;
				break;
			case IT:
				result = 10;
				break;
			case HEALTH_AND_FITNESS:
				result = 11;
				break;
			case BUSINESS:
				result = 12;
				break;
			case ACADEMICS:
				result = 13;
				break;
			case KIDS:
				result = 14;
				break;
			case OTHER:
				result = 15;
				break;
			default:
				result = 15;
				break;

		}
		return result;
	}

	public static Domain getDomainByName(String domainName) {
		return new Domain(1, Constants.EDUMALL_DOMAIN_NAME, Constants.EDUMALL_DOMAIN);
	}

	public static void insertDomain(String name, String domainURL) {

	}

	public static void insertCategoryByNameIfNotExist(String name) {

	}

	public static void validateCourseAndSaveToDB(CourseDTO courseDTO) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(courseDTO.getClass());
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

			jaxbContext.createMarshaller().marshal(courseDTO, byteOutputStream);
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			File courseXSDFile = new File(DummyDatabase.class.getClassLoader().getResource("Course.xsd").getFile());

			Schema schema = schemaFactory.newSchema(courseXSDFile);
			logger.info(courseDTO.toString());
			Validator validator = schema.newValidator();
			validator.validate(new SAXSource(new InputSource(byteInputStream)));

			logger.info("Validated");
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
			logger.error(String.format("NOT VALID Course %s", courseDTO));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	public static void validateXMLBeforeSaveToDB(InputStream xmlStream) throws  IOException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//		File xsdFile = ResourceUtils.getFile(Thread.currentThread().getClass().getClassLoader().getResource("Category.xsd").getFile());

		System.out.println();


		try {
			File xsdFile = new File(DummyDatabase.class.getClassLoader().getResource("Category.xsd").getFile());

			Schema schema = schemaFactory.newSchema(xsdFile);
			Validator validator = schema.newValidator();
			validator.validate(new SAXSource(new InputSource(xmlStream)));
			System.out.println("Validated");
		} catch (SAXException e) {
			e.printStackTrace();
			System.out.println("File not valid");
		}


	}

	private static void applicaionInit() {

	}


	private static void applicationDestroy() {
		DBUtils.closeEntityFactory();
	}
}
