package bittorrent.client;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceiveThread extends Thread
{
	private Socket clientSocket;
	private InputStream is = null;
	private FileOutputStream fos;
	private BufferedOutputStream bos;
	private int bufferSize;
	private boolean stop;
	
	public ReceiveThread (Socket clientSocket)
	{
		fos = null;
	    bos = null;
	    bufferSize = 0;
		
		setClientSocket(clientSocket);
		
		try
		{
			is = clientSocket.getInputStream();
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
			
			clientSocket.close();
		}
		
		catch(Exception e)
		{ e.printStackTrace(); }
	}
	
	public void receiveData()
	{
		try
		{
			System.out.println("Torrent Receiving...");
			
			bufferSize = clientSocket.getReceiveBufferSize();
	        System.out.println("Buffer size: " + bufferSize);
		 
			fos = new FileOutputStream("Hope-copy.wmv");
		    bos = new BufferedOutputStream(fos);
		    
		    byte[] bytes = new byte[bufferSize];

		    int count;

		    while ((count = is.read(bytes)) > 0) {
		        bos.write(bytes, 0, count);
		    }

		    bos.flush();
		    bos.close();
		    is.close();
			
		    System.out.println("Torrent Received");
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