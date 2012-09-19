package client;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveData implements Runnable
{
	private Socket clientSocket;
	ObjectInputStream in = null;
	
	public ReceiveData (Socket clientSocket)
	{
		setClientSocket(clientSocket);
	}
	
	public void run()
	{
		try
		{
			try
			{
				in = new ObjectInputStream( clientSocket.getInputStream() );
				
				while (true)
				{
					System.out.println("Receiving...");
					
					String msg = (String) in.readObject();
					
					System.out.println("Received" + msg);
					
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
	
	public Socket getClientSocket()
	{
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
}