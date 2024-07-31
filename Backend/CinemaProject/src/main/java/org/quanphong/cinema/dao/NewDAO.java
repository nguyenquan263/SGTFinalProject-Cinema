package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.New;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class NewDAO implements iNewdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public List<New> getAllNews() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		List<New> new1List = session.createQuery("from New").list();
		return new1List;
	}

	public New getNew(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		New new1 = (New) session.get(New.class, new Integer(id));
		return new1;
	}

	public New addNew(New new1) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.persist(new1);
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		};
		return new1;
	}

	public void updateNew(New new1) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(new1);
	}
	
	public New updateNewREST(New new1) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(new1);
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		};
		return new1;
	}

	public void deleteNew(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		New p = (New) session.load(New.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		};
	}	
}
