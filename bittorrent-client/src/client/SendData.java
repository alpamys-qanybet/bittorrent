package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendData implements Runnable
{
	private Socket clientSocket;
	private ObjectOutputStream out;
	private boolean stop;
	
	public SendData (Socket clientSocket)
	{
		setClientSocket(clientSocket);
		try
		{
			out = new ObjectOutputStream( clientSocket.getOutputStream() );
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			try
			{
				stop = false;
				
				while (!stop)
				{
					Thread.sleep(500);
				}
			}
			
			catch(Exception e)
			{ e.printStackTrace(); }
			
			finally
			{ clientSocket.close(); }
		}
		
		catch(Exception e)
		{ e.printStackTrace(); }
	}

	public void sendRequestForFile(String fileName)
	{
		try
		{
			System.out.println("Sending...");
		
			out.writeObject( fileName );
			
			System.out.println("Sent");
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
