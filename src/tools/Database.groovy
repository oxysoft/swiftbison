package tools

import server.core.Config

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

/**
 *
 * @author DancesWithOdin
 */
public final class Database {

	private String URL;
	private final Semaphore s;
	private final Properties cProp;
	private final ConcurrentLinkedQueue<Connection> queue;
	public static final Database database = newDatabase("jdbc:mysql://localhost:3306/${Config.Database.DATABASE}", Config.Database.USERNAME, Config.Database.PASSWORD, 16)

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // check first
		} catch (Exception e) {
			System.err.println("SEVERE : SQL Driver has not been located during runtime. Please make sure it is in the correct path.");
			System.exit(0);
		}
	}

	private static Database newDatabase(String url, String user, String pass, int connections) {
		Database db = new Database(connections);
		db.URL = url;
		db.cProp.setProperty("user", user);
		db.cProp.setProperty("password", pass);
		return db;
	}

	private Database(int connections) {
		cProp = new Properties();
		s = new Semaphore(connections, true);
		queue = new ConcurrentLinkedQueue();
	}

	public final Connection getConnection() {
		return getConnection(60000);
	}

	public final Connection getConnection(long timeout) {
		if (URL == null || cProp.isEmpty()) {
			return null;
		}
		try {
			s.tryAcquire(timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			return null;
		}
		Connection c = queue.poll();
		if (c == null) {
			try {
				c = DriverManager.getConnection(URL, cProp);
			} catch (Exception e) {
				s.release();
				return null;
			}
		} else { // validates connection
			try {
				c.getMetaData();
			} catch (Exception e) {
				try {
					c = DriverManager.getConnection(URL, cProp);
				} catch (Exception ex) {
					s.release();
					return null;
				}
			}
		}
		return c;
	}

	public final void release(Connection c) {
		try {
			if (c.isClosed()) {
				return;
			} else {
				queue.add(c);
			}
		} catch (Exception e) {
		} finally {
			s.release();
		}
	}
}
