package client;

/**
 * @author about.me/alpamys.kanibetov
 */

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
	
	public void receiveServerData()
	{
		try
		{
			System.out.println("Receiving...");
			
			String msg = (String) is.readObject();
			
			System.out.println("Received" + msg);
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
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