package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.New;
import org.hibernate.SessionFactory;

public interface iNewdao {
	public void setSessionFactory(SessionFactory sf);
	public List<New> getAllNews();
	public New getNew(int id);
	public New addNew(New neww);
	public void updateNew(New neww);
	public void deleteNew(int id);
}
