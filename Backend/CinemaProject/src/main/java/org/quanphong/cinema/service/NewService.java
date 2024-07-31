package org.quanphong.cinema.service;
import java.util.List;
import org.quanphong.cinema.dao.NewDAO;
import org.quanphong.cinema.model.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("newService")
public class NewService {

	@Autowired
	NewDAO newDao;
	
	public NewDAO getNewDao() {
		return newDao;
	}

	public void setNewDao(NewDAO newDao) {
		this.newDao = newDao;
	}

	@Transactional
	public List<New> getAllNews() {
		return newDao.getAllNews();
	}

	@Transactional
	public New getNew(int id) {
		return newDao.getNew(id);
	}

	@Transactional
	public New addNew(New NEW) {
		return newDao.addNew(NEW);
	}
	
	@Transactional
	public New addNewREST(New NEW) {
		return newDao.addNew(NEW);
	}

	@Transactional
	public void updateNew(New NEW) {
		newDao.updateNew(NEW);

	}
	
	@Transactional
	public New updateNewREST(New NEW) {
		return newDao.updateNewREST(NEW);

	}

	@Transactional
	public void deleteNew(int id) {
		newDao.deleteNew(id);
	}
}