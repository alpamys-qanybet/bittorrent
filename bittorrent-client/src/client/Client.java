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
	
	public void sendRequestForFile(String fileName, int part)
	{
		sendThread.sendRequestForFile(fileName, part);
		System.out.println("Sent file request : " + fileName + " part " + part);
		
		ipAddress = receiveThread.receiveIpAddress(); // ipAddresses with parts, partial downloading(receiving data)
		if (ipAddress == null)
			return;
		
		TorrentClient torrentClient = new TorrentClient();
		torrentClient.connect(ipAddress);
		torrentClient.sendRequest(fileName, part);
		torrentClient.receiveData(fileName, part);
		informDownload(ipAddress, fileName, part);
		
		sendRequestForFile(fileName, ++part);
		// need to disconnect
	}
	
	public void sendFileInfo(String fileName, int chunks)
	{
		sendThread.sendFileInfo(fileName + "/" +chunks);
		System.out.println("Sent file info : " + fileName + "/" + chunks);
	}
	
	public void informDownload(String ipAddress, String fileName, int part)
	{
		sendThread.informDownload(ipAddress, fileName, part);
		System.out.println("inform download : " + fileName + "/" + part);
	}
}
