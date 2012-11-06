package bittorrent.client;

/**
 * @author about.me/alpamys.kanibetov
 */

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
	
	public void sendRequest(String fileName, int part)
	{
		try
		{
			String [] content = new String[2];
			content[0] = fileName;
			content[1] = Integer.toString(part);
			os.writeObject( content );
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}
}
