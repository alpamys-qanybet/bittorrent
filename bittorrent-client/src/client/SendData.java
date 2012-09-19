package client;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendData implements Runnable
{
	private Socket clientSocket;
	private ObjectOutputStream out;
	
	public SendData (Socket clientSocket)
	{
		setClientSocket(clientSocket);
	}
	
	public void run()
	{
		try
		{
			try
			{
				out = new ObjectOutputStream( clientSocket.getOutputStream() );
				
				while (true)
				{
					String fileName = "music.mp3";
					
					System.out.println("Sending...");
					
					out.writeObject( fileName );
					
					System.out.println("Sent");
					
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
