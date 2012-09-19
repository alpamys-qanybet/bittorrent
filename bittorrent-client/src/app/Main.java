package app;

/**
 * @author about.me/alpamys.kanibetov
 */

public class Main
{
	public static int SERVERPORT = 7122;
	public static int TORRENTSERVERPORT = 1696;
	
	public static void main(String [] args)
	{
		Processor processor = new Processor();
		
		processor.createClient();
	}
}
