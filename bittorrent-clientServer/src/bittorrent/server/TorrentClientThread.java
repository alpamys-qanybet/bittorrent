package bittorrent.server;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TorrentClientThread extends Thread
{
	private Socket clientSocket = null;
	private ObjectInputStream is = null;
	private OutputStream os = null;
	private boolean stop;
	
	public TorrentClientThread (Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		
		try
		{
			is = new ObjectInputStream(clientSocket.getInputStream());
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
	
	public void receiveRequest()
	{
		try
		{
			String[] content = (String[]) is.readObject();
			String fileName = content[0];
			int part = Integer.parseInt(content[1]);
			
			sendData(fileName, part);
		}
		catch (Exception e)
		{ e.printStackTrace(); }
	}
	
	public void sendData(String fileName, int part)
	{
		try
		{
			System.out.println("TorrentServer Sending...");
			
			File file = new File("/home/alpamys/dev/soft/bittorrent/upload/" + fileName + "/" + part);
		    // Get the size of the file
		    long length = file.length();
		    
		    if (length > Integer.MAX_VALUE)
		        System.out.println("File is too large.");
		    
		    byte[] bytes = new byte[(int) length];
		    FileInputStream fis = new FileInputStream(file);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());

		    int count;

		    while ((count = bis.read(bytes)) > 0)
		        out.write(bytes, 0, count);
		    
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
