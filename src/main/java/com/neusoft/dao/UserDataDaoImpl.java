package com.neusoft.dao;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.neusoft.entity.UserData;
/**
 * 
 * @author wtaisi
 *
 */
@Repository
public class UserDataDaoImpl implements UserDataDao {

	@Resource
	private SessionFactory sessionFactory;

	private final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	public UserData findOne(long id) {
		UserData user = (UserData) getCurrentSession().get(UserData.class, id);
		return user;
	}

	@Override
	public List<UserData> findAll() {
		return getCurrentSession().createQuery("from " + UserData.class.getName()).list();
	}

	@Override
	public void create(UserData entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public UserData update(UserData entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().update(entity);
		return entity;
	}

	@Override
	public void delete(UserData entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().delete(entity);
	}

	@Override
	public void deleteById(long entityId) {
		UserData entity = findOne(entityId);
		Preconditions.checkState(entity != null);
		delete(entity);
	}

}
