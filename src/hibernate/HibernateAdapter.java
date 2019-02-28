package hibernate;

import java.io.Serializable;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import connections.SessionFactoryUtil;
/**Clase genèrica amb la que podem implementar el crud amb hibernate.
* Fent una instancia d'un objecte d'aquesta classe en cadascuna de les classes on volguem implementar
* el crud. El constructor rep un Object String que utilitzarem per fer la query que ens retorna tots els
* registres d'una taula. En els mètodes en els que necessitem un id, utiliztzem un objexte Serializable, que és el que
* demana el mètode del hibernate
*/ 
public class HibernateAdapter<T> {

	private Session session;
	private Transaction tx;
	private String hql;

	public HibernateAdapter(String hql){
		this.hql = hql;
	}
	private void openSession() {
		session = SessionFactoryUtil.getSessionFactory().openSession();
		tx = null;
		try {
			tx = session.beginTransaction();
		}catch(HibernateException e) {
			if(null != tx) tx.rollback();
		}
	}

	public T readObject(Class<T> objectClass, Serializable id) {
		openSession();
		T object = null;
		try {
			object = session.get(objectClass, id);
			tx.commit();
		}catch(HibernateException e) {
			if(null != tx) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return object;
	}

	public Integer addObject(T object) {
		openSession();
		try {
			session.save(object);
			tx.commit();
		}catch(HibernateException e) {
			if(null != tx) tx.rollback();
			return 0;
		}finally {
			session.close();
		}
		return 1;
	}

	public Integer updateObject(T object) {
		openSession();
		try {
			session.update(object);
			tx.commit();
		}catch(HibernateException e) {
			if(null != tx) tx.rollback();
			return 0;
		}finally {
			session.close();
		}
		return 1;
	}

	public T deleteObject(Class<T> objectClass, Serializable id ) {
		openSession();
		T object = null;
		try {
			object = session.get(objectClass, id);
			if(object != null)
			session.delete(object);
			tx.commit();
		}catch(HibernateException e) {
			if(null != tx) tx.rollback();
		}finally {
			session.close();
		}
		return object;
	}
	
	public ArrayList<T> readAll() {
		openSession();
		ArrayList<T> objects = new ArrayList<T>();
		try {
			objects  = (ArrayList<T>) session.createQuery(hql).list();
			tx.commit();
		}catch(HibernateException e) {
			if(null != tx) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return objects;
	}

	public ArrayList<T> getQuery(String string){
		openSession();
		ArrayList<T> objects = new ArrayList<T>();
		try {
			objects  = (ArrayList<T>) session.createQuery(string).list();
			tx.commit();
		}catch(HibernateException e) {
			if(null != tx) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return objects;
	}

}
