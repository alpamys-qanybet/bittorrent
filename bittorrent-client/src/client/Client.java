package client;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.IOException;
import java.net.Socket;

import app.Main;
import bittorrent.client.TorrentClient;

public class Client
{
	private Socket clientSocket;
	private SendThread sendThread;
	private ReceiveThread receiveThread;
	
	private String ipAddress; // na poka
	
	public Client() {}
	
	public void connect(String host)
	{
		try
		{
			clientSocket = new Socket(host, Main.SERVERPORT);
			
			sendThread = new SendThread(clientSocket);
			sendThread.start();
			
			receiveThread = new ReceiveThread(clientSocket);
			receiveThread.start();
			
			sendThread.sendRequestForFile("a.txt");
			ipAddress = receiveThread.receiveIpAddress(); // ipAddresses with parts, partial downloading(receiving data)
			
			TorrentClient torrentClient = new TorrentClient();
			torrentClient.connect(ipAddress);
		}
		
		catch(IOException e)
		{ e.printStackTrace(); }
	}
}
