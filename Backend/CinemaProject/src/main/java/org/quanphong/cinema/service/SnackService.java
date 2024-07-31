package org.quanphong.cinema.service;
import java.util.List;
import org.quanphong.cinema.dao.SnackDAO;
import org.quanphong.cinema.model.Snack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("snackService")
public class SnackService {

	@Autowired
	SnackDAO snackDao;
	
	
	
	public SnackDAO getSnackDao() {
		return snackDao;
	}

	public void setSnackDao(SnackDAO snackDao) {
		this.snackDao = snackDao;
	}

	@Transactional
	public List<Snack> getAllSnacks() {
		return snackDao.getAllSnacks();
	}

	@Transactional
	public Snack getSnack(int id) {
		return snackDao.getSnack(id);
	}

	@Transactional
	public Snack addSnack(Snack snack) {
		return snackDao.addSnack(snack);
	}
	
	@Transactional
	public Snack addSnackREST(Snack snack) {
		return snackDao.addSnack(snack);
	}

	@Transactional
	public void updateSnack(Snack snack) {
		snackDao.updateSnack(snack);

	}
	
	@Transactional
	public Snack updateSnackREST(Snack snack) {
		return snackDao.updateSnackREST(snack);

	}

	@Transactional
	public void deleteSnack(int id) {
		snackDao.deleteSnack(id);
	}
}