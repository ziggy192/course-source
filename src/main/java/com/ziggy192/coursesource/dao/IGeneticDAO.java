package com.ziggy192.coursesource.dao;

import java.util.List;

public interface IGeneticDAO<T, PK> {
	void persist(T t);

	T findByID(PK id);


	void delete(T t);


	List<T> getAll(String queryName);

}
