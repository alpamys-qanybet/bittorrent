package bittorrent.server;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendThread extends Thread
{
	private Socket clientSocket = null;
	private ObjectOutputStream os = null;
	private boolean stop;
	
	public SendThread (Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		
		try
		{
			os = new ObjectOutputStream( clientSocket.getOutputStream() );
		}
		
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
			
			os.close();
			clientSocket.close();
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}
	
	public void sendData()
	{
		try
		{
			System.out.println("TorrentServer Sending...");
			File file = new File("/home/alpamys/a.in");
			os.writeObject(file);
			System.out.println("TorrentServer Sent!");
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}
}
