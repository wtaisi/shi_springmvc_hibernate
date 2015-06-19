package com.neusoft.service;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SessionHelper {
	@Resource
	private SessionFactory sessionFactory;
	
	private final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	public void getExecute(String sql){
		SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
	}

}
