package client;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendThread extends Thread
{
	private Socket clientSocket;
	private ObjectOutputStream os;
	private boolean stop;
	
	public SendThread (Socket clientSocket)
	{
		setClientSocket(clientSocket);
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
			
			clientSocket.close();
		}
		
		catch(Exception e)
		{ e.printStackTrace(); }
	}
	
	public void sendFileInfo(String fileInfo)
	{
		try
		{
			String [] content = new String[2];
			content[0] = "upload";
			content[1] = fileInfo;
			os.writeObject( content );
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}

	public void sendRequestForFile(String fileName)
	{
		try
		{
			String [] content = new String[2];
			content[0] = "download";
			content[1] = fileName;
			os.writeObject( content );
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
