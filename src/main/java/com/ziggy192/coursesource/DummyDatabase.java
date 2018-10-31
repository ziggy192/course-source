package com.ziggy192.coursesource;

import com.ziggy192.coursesource.dao.CategoryDAO;
import com.ziggy192.coursesource.entity.CategoryMapping;
import com.ziggy192.coursesource.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
