package client;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.IOException;
import java.net.Socket;

public class Client
{
	private Socket clientSocket;
	private SendThread sendThread;
	private ReceiveThread receiveThread;
	
	public Client() {}
	
	public void connect(String host, int port)
	{
		try
		{
			clientSocket = new Socket(host, port);
			
			sendThread = new SendThread(clientSocket);
			sendThread.start();
			
			receiveThread = new ReceiveThread(clientSocket);
			receiveThread.start();
			
			sendThread.sendRequestForFile("music.mp3");
			receiveThread.receiveServerData();
		}
		
		catch(IOException e)
		{ e.printStackTrace(); }
	}
}
