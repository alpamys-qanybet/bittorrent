package app;

/**
 * @author about.me/alpamys.kanibetov
 */

public class Main
{
	public static int TORRENTSERVERPORT = 1696;
	
	public static void main(String [] args)
	{
		Processor processor = new Processor();
		processor.createTorrentServer();
	}
}
