package org.quanphong.cinema.dao;

import java.util.List;

import org.quanphong.cinema.model.Snack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class SnackDAO implements iSnackdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Snack> getAllSnacks() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		List<Snack> snackList = session.createQuery("from Snack").list();
		return snackList;
	}

	public Snack getSnack(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Snack snack = (Snack) session.get(Snack.class, new Integer(id));
		return snack;
	}

	public Snack addSnack(Snack snack) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.persist(snack);
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		}
		return snack;
	}

	public void updateSnack(Snack snack) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(snack);
	}
	
	public Snack updateSnackREST(Snack snack) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(snack);
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		};
		return snack;
	}

	public void deleteSnack(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Snack p = (Snack) session.load(Snack.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		if (!trans.getStatus().equals(TransactionStatus.ACTIVE)){
			trans.commit();
		}
	}	
}
