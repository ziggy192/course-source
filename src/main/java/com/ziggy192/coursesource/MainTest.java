package com.ziggy192.coursesource;

import com.ziggy192.coursesource.crawler.EdumallCourseDetailCrawler;
import com.ziggy192.coursesource.crawler.EdumallCourseInEachCategoryPageCrawler;
import com.ziggy192.coursesource.crawler.EdumallMainCrawler;
import com.ziggy192.coursesource.dao.DomainDAO;
import com.ziggy192.coursesource.entity.CategoryEntity;
import com.ziggy192.coursesource.entity.DomainEntity;
import com.ziggy192.coursesource.url_holder.CourseUrlHolder;
import com.ziggy192.coursesource.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainTest {

	private static Logger logger = LoggerFactory.getLogger(MainTest.class.toString());

	public static void main(String[] args) throws UnsupportedEncodingException {
//		testInsertDomain(Constants.EDUMALL_DOMAIN_NAME, Constants.EDUMALL_DOMAIN);
//		testValidate();

//			testGetCourseDetail();

//		testJDBCConnection();
//		testValidate();

//		testGetCourseDetail();
//		testGetDomainByName(Constants.EDUMALL_DOMAIN_NAME);
//		testGetCourseDetail();

	}



	private static void testGetDomainByName(String domainName) {
		DomainEntity domainByName = DomainDAO.getInstance().getDomainByName(domainName);
		System.out.println(domainByName);


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

		CourseUrlHolder dummyCourseUrlHolder = new CourseUrlHolder("Tạo slide trình bày ấn tượng với Prezi, Google trình chiếu và Power Point"
				, "d1nzpkv5wwh1xf.cloudfront.net/640/k-5768aeb1047c995f75fdbf6b/20180626-/14-luu-y-giup-ban-thuyet-trin.png"
				, "edumall.vn/course/tao-slide-trinh-bay-an-tuong-voi-prezi-google-trinh-chieu-va-power-point");
//				, "https://edumall.vn/course/dao-tao-ky-thuat-truong-cửa-hang-gas");
//				, "https://edumall.vn/course/tao-slide-trinh-bay-an-tuong-voi-prezi-google-trinh-chieu-va-power-point");

		EdumallMainCrawler.domainId = 3;

		new Thread(new EdumallCourseDetailCrawler(dummyCourseUrlHolder, 4)).start();

//		getCourseDetail(dummyCourseUrlHolder);

	}


	public static void testValidateCourse() {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CategoryEntity.class);
			CategoryEntity category = new CategoryEntity();
			category.setId(1);
			category.setName("Luyện thi");


			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

			jaxbContext.createMarshaller().marshal(category, byteOutputStream);
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());

//			DummyDatabase.validateXMLBeforeSaveToDB(byteInputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
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
