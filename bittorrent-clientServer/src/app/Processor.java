package app;

import bittorrent.server.TorrentServer;

public class Processor
{
	private TorrentServer torrentServer;
	
	public Processor(){}
	
	public void createTorrentServer()
	{
		torrentServer = new TorrentServer();
		torrentServer.connect();
	}
}
