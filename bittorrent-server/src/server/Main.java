package server;

/**
 * @author about.me/alpamys.kanibetov
 */

public class Main
{
	public static void main(String [] args)
	{
		Server server = new Server();
		server.connect(7122);
	}
}