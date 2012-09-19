package bittorrent.client;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveThread extends Thread
{
	private Socket clientSocket;
	ObjectInputStream is = null;
	private boolean stop;
	
	public ReceiveThread (Socket clientSocket)
	{
		setClientSocket(clientSocket);
		
		try
		{ is = new ObjectInputStream( clientSocket.getInputStream() ); }
		
		catch (IOException e)
		{ e.printStackTrace(); }
	}
	
	public void run()
	{
		try
		{
			stop = false;
			while (!stop)
				Thread.sleep(500);
			
			clientSocket.close();
		}
		
		catch(Exception e)
		{ e.printStackTrace(); }
	}
	
	public File receiveData()
	{
		File file = null;
		try
		{
			System.out.println("Receiving...");
			
			file = (File) is.readObject();
			
			System.out.println("Received " + file.getName() + " path: " + file.getAbsolutePath());
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
		
		return file;
	}
	
	public Socket getClientSocket()
	{
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
}