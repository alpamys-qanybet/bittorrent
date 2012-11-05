package bittorrent.server;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SendThread extends Thread
{
	private Socket clientSocket = null;
	private OutputStream os = null;
	private boolean stop;
	
	public SendThread (Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		
		try
		{
			os = clientSocket.getOutputStream();
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
			
			File file = new File("Hope.wmv");
		    // Get the size of the file
		    long length = file.length();
		    
		    if (length > Integer.MAX_VALUE)
		        System.out.println("File is too large.");
		    
		    byte[] bytes = new byte[(int) length];
		    FileInputStream fis = new FileInputStream(file);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());

		    int count;

		    while ((count = bis.read(bytes)) > 0) {
		        out.write(bytes, 0, count);
		    }

		    out.flush();
		    out.close();
		    fis.close();
		    bis.close();

			System.out.println("TorrentServer Sent!");
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}
}
