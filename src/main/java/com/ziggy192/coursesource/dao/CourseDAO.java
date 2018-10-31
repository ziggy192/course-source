package com.ziggy192.coursesource.dao;

import com.ziggy192.coursesource.DummyDatabase;
import com.ziggy192.coursesource.entity.CourseEntity;
import com.ziggy192.coursesource.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CourseDAO extends BaseDAO<CourseEntity,Integer> {
	private static Logger logger = LoggerFactory.getLogger(CourseDAO.class.toString());

	private static CourseDAO instance;
	private static Object LOCK = new Object();
	public CourseDAO() {

	}
	public static CourseDAO getInstance() {
		synchronized (LOCK) {
			if (instance == null) {
				instance = new CourseDAO();
			}
		}
		return instance;
	}


	public void checkIfExistOrInsertToDB(CourseEntity courseEntity) {
//		DBUtils.getEntityManager()
		List<CourseEntity> resultList = DBUtils.getEntityManager().createNamedQuery("CourseEntity.findCourseByHashing")
				.setParameter("hash", courseEntity.getHash())
				.getResultList();

		if (!resultList.isEmpty()) {
			//set correct id to courseEntity
			courseEntity.setId(resultList.get(0).getId());
		}

		//then save to db
		this.persist(courseEntity);

	}

	public void validateCourseAndSaveToDB(CourseEntity courseEntity) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(courseEntity.getClass());
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

			jaxbContext.createMarshaller().marshal(courseEntity, byteOutputStream);
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			File courseXSDFile = new File(DummyDatabase.class.getClassLoader().getResource("Course.xsd").getFile());

			Schema schema = schemaFactory.newSchema(courseXSDFile);
			logger.info(courseEntity.toString());
			Validator validator = schema.newValidator();
			validator.validate(new SAXSource(new InputSource(byteInputStream)));

			logger.info(String.format("Validated, saving to db | Course=%s", courseEntity));

			checkIfExistOrInsertToDB(courseEntity);

			//save to database
//			this.persist(courseEntity);




		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
			logger.error(String.format("NOT VALID | Course=%s", courseEntity));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
