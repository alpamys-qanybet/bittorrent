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
	private SendThread sendThread;
	private ReceiveThread receiveThread;
	
	public TorrentClient() {}
	
	public void connect(String host)
	{
		try
		{
			clientSocket = new Socket(host, Main.TORRENTSERVERPORT);
			
			sendThread = new SendThread(clientSocket);
			receiveThread = new ReceiveThread(clientSocket);
			
			sendThread.start();
			receiveThread.start();
		}
		
		catch(IOException e)
		{ e.printStackTrace(); }
	}
	
	public void sendRequest(String fileName, int part)
	{
		sendThread.sendRequest(fileName, part);
	}
	
	public void receiveData(String fileName, int part)
	{
		receiveThread.receiveData(fileName, part);
	}
}
