package com.ziggy192.coursesource.dao;

import com.ziggy192.coursesource.util.DBUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDAO<T, PK> implements IGeneticDAO<T,PK>{
	protected Class<T> entityClass;

	public BaseDAO() {
		ParameterizedType geneticSupperClass = (ParameterizedType) this.getClass().getGenericSuperclass();
		entityClass = (Class<T>) geneticSupperClass.getActualTypeArguments()[0];

	}

	@Override
	public void persist(T t) {
		EntityManager entityManager = DBUtils.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
	}

	@Override
	public T findByID(PK id) {
		EntityManager entityManager = DBUtils.getEntityManager();
		return entityManager.find(entityClass, id);
	}

	@Override
	public void delete(T t) {
		EntityManager entityManager = DBUtils.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(t);
		entityManager.getTransaction().commit();	}

	@Override
	public List<T> getAll(String queryName) {
		EntityManager entityManager = DBUtils.getEntityManager();
		Query namedQuery = entityManager.createNamedQuery(queryName);
		return namedQuery.getResultList();
	}
}
