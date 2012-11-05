package server;

/**
 * @author about.me/alpamys.kanibetov
 */

public class Main
{
	static Server server;
	static DBManager dbmanager;
	
	public static void main(String [] args)
	{
		dbmanager = new DBManager();
		server = new Server();
		
		dbmanager.connect();
		dbmanager.createTables();
		
		server.connect(7122);
	}
}