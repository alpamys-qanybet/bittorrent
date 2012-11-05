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
		}
		
		catch(IOException e)
		{ e.printStackTrace(); }
	}
	
	public void sendRequestForFile(String fileName)
	{
		sendThread.sendRequestForFile(fileName);
		System.out.println("Sent file request : " + fileName);
		
		ipAddress = receiveThread.receiveIpAddress(); // ipAddresses with parts, partial downloading(receiving data)
		TorrentClient torrentClient = new TorrentClient();
		torrentClient.connect(ipAddress);
	}
	
	public void sendFileInfo(String fileName, int chunks)
	{
		sendThread.sendFileInfo(fileName + "/" +chunks);
		System.out.println("Sent file info : " + fileName + "/" + chunks);
	}
}
