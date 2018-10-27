package com.ziggy192.coursesource;

import com.ziggy192.coursesource.crawler.EdumallCourseDetailCrawler;
import com.ziggy192.coursesource.crawler.EdumallCourseInEachCategoryPageCrawler;
import com.ziggy192.coursesource.entity.CategoryEntity;
import com.ziggy192.coursesource.entity.DomainEntity;
import com.ziggy192.coursesource.url_holder.EdumallCourseUrlHolder;
import com.ziggy192.coursesource.util.DBUtils;
import jaxb.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		testInsertDomain(Constants.EDUMALL_DOMAIN_NAME, Constants.EDUMALL_DOMAIN);
//		testValidate();

//			testGetCourseDetail();

//		testJDBCConnection();
//		testValidate();

		testGetCourseDetail();
	}


	private static void testJDBCConnection() {
		String jdbcURL = "jdbc:mysql://localhost:3306/course_source";
		String user = "root";
		String password = "12345678";
		try (Connection con = DriverManager.getConnection(jdbcURL, user, password)) {

			System.out.println("Connected");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to connect");

		}


	}


	private static void testGetCourseListFromEachPage() {

		String uri = "https://edumall.vn/courses/filter?categories[]=phong-thuy-nhan-tuong-hoc";


		new Thread(new EdumallCourseInEachCategoryPageCrawler(uri, 1)).start();

	}

	private static void testGetCourseDetail() {

		EdumallCourseUrlHolder dummyCourseUrlHolder = new EdumallCourseUrlHolder("Guitar đệm hát trong 30 ngày"
				, "http://d1nzpkv5wwh1xf.cloudfront.net/640/k-5768aeb1047c995f75fdbf6b/20170817-anhdaidienmoi_thinh/hiennt01.png"
				, "edumall.vn/course/tao-slide-trinh-bay-an-tuong-voi-prezi-google-trinh-chieu-va-power-point");
//				, "https://edumall.vn/course/dao-tao-ky-thuat-truong-cửa-hang-gas");
//				, "https://edumall.vn/course/tao-slide-trinh-bay-an-tuong-voi-prezi-google-trinh-chieu-va-power-point");


		new Thread(new EdumallCourseDetailCrawler(dummyCourseUrlHolder, 1)).start();

//		getCourseDetail(dummyCourseUrlHolder);

	}

	public static void testValidate() {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CategoryEntity.class);
			CategoryEntity category = new CategoryEntity();
			category.setId(1);
			category.setName("Luyện thi");


			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

			jaxbContext.createMarshaller().marshal(category, byteOutputStream);
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());

			DummyDatabase.validateXMLBeforeSaveToDB(byteInputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void testInsertDomain(String domainName, String domainURL) {


		EntityManager entitymanager = DBUtils.getEntityManager();
		entitymanager.getTransaction().begin();

		DomainEntity domainEntity = new DomainEntity();
		domainEntity.setDomainUrl(domainURL);
		domainEntity.setName(domainName);

		entitymanager.persist(domainEntity);
		entitymanager.getTransaction().commit();

		entitymanager.close();
	}
}
