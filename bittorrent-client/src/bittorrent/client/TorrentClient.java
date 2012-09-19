package bittorrent.client;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.IOException;
import java.net.Socket;

import app.Main;

public class TorrentClient
{
	private Socket clientSocket;
	private ReceiveThread receiveThread;
	
	public TorrentClient() {}
	
	public void connect(String host)
	{
		try
		{
			clientSocket = new Socket(host, Main.TORRENTSERVERPORT);
			
			receiveThread = new ReceiveThread(clientSocket);
			receiveThread.start();
			
			receiveThread.receiveData();
		}
		
		catch(IOException e)
		{ e.printStackTrace(); }
	}
}
