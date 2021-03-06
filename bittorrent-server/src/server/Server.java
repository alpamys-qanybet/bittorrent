package server;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server
{
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	
	public Server() {}
	
	public void connect (int port)
	{
		try
		{ serverSocket = new ServerSocket(port); }
		
		catch(IOException e)
		{ e.printStackTrace(); }
	
		
		while (true)
		{
			try
			{
				clientSocket = serverSocket.accept();
				
				ClientThread clientThread = new ClientThread( clientSocket );
				clientThread.start();
			}
			
			catch (Exception e)
			{ e.printStackTrace(); }
		}
	}
}