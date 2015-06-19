package com.neusoft.service;

import java.util.List;

import com.neusoft.entity.UserData;
/**
 * @author wtaisi
 */
public interface UserDataService {
	public UserData findOne( long id);

    public List<UserData> findAll();

    public void create(final UserData entity);

    public UserData update(final UserData entity);

    public void delete(final UserData entity);

    public void deleteById(long entityId) ;
	

}
