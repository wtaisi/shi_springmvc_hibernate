package com.neusoft.dao;

import java.util.List;
import java.util.Set;

import com.neusoft.entity.UserData;
/**
 * 
 * @author wtaisi
 *
 */
public interface UserDataDao {
    
	UserData findOne( long id);

    List<UserData> findAll();

    void create( UserData entity);

    UserData update( UserData entity);

    void delete( UserData entity);

    void deleteById( long entityId);


}