package com.ziggy192.coursesource;

import com.ziggy192.coursesource.model.Provider;

import java.util.PrimitiveIterator;

public class DummyDatabase {
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

	public static Provider getProviderByName(String domainName) {
		return new Provider(1, Constants.EDUMALL_DOMAIN_NAME, Constants.EDUMALL_DOMAIN);
	}

	public static void insertProvider(String name, String domainURL) {

	}

	public static void insertCategoryByNameIfNotExist(String name) {

	}

}
