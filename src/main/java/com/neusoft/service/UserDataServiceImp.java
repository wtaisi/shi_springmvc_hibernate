package com.neusoft.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.dao.UserDataDao;
import com.neusoft.entity.UserData;
/**
 * 
 * @author wtaisi
 *
 */
@Transactional
@Service
public  class UserDataServiceImp  implements UserDataService{
	
    @Resource
    private UserDataDao userDataDao;
    
    @Override
    public UserData findOne( long id) {
        return userDataDao.findOne(id);
    }

    public List<UserData> findAll() {
        return userDataDao.findAll();
    }

    public void create(final UserData entity) {
    	userDataDao.create(entity);
    }

    public UserData update(final UserData entity) {
        return userDataDao.update(entity);
    }


    public void deleteById(long entityId) {
    	userDataDao.deleteById(entityId);
    }

	@Override
	public void delete(UserData entity) {
		userDataDao.delete(entity);
	}

}