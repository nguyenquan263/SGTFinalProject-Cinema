package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Snack;
import org.hibernate.SessionFactory;

public interface iSnackdao {
	public void setSessionFactory(SessionFactory sf);
	public List<Snack> getAllSnacks();
	public Snack getSnack(int id);
	public Snack addSnack(Snack snack);
	public void updateSnack(Snack snack);
	public void deleteSnack(int id);
}
