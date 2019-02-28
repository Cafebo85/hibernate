package hibernate;

import java.util.ArrayList;

import iFaces.WatchedListDAO;
import pojos.Watchedlist;

public class WatchedListHibernate implements WatchedListDAO {

	private HibernateAdapter<Watchedlist> adapter = new HibernateAdapter<Watchedlist>("FROM Watchedlist");
	@Override
	public Watchedlist getItem(int itemId) {
		return adapter.readObject(Watchedlist.class, itemId);
	}

	@Override
	public Integer addItem(Watchedlist watchedlist) {
		return adapter.addObject(watchedlist);
	}

	@Override
	public Watchedlist deleteItem(Integer itemId) {
		return adapter.deleteObject(Watchedlist.class, itemId);
	}

	@Override
	public int updateItem(Watchedlist watchedlist) {
		return adapter.updateObject(watchedlist);
	}

	@Override
	public ArrayList<Watchedlist> getWatchedlist() {
		return adapter.readAll();
	}
	
	@Override
	public ArrayList<Watchedlist> getAllForUser(String userId) {
		String sql = "From Watchedlist where userId LIKE '" + userId + "'";
		return adapter.getQuery(sql);
	}
	
}
