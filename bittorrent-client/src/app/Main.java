package app;

/**
 * @author about.me/alpamys.kanibetov
 */

public class Main
{
	public static int SERVERPORT = 7122;
	public static int TORRENTSERVERPORT = 1696;
	public static Processor processor;
	
	public static void main(String [] args)
	{
		processor = new Processor();
		
		processor.createClient();
		processor.showApp();
	}
}
