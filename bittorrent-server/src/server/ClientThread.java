package server;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class ClientThread extends Thread
{
	private Socket clientSocket = null;
	private ObjectInputStream is = null;
	private ObjectOutputStream os = null;
	private boolean stop;
	
	public ClientThread (Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
	
	public void run()
	{
		try
		{
			is = new ObjectInputStream( clientSocket.getInputStream() );
			os = new ObjectOutputStream( clientSocket.getOutputStream() );
			stop = false;
			
			while (!stop)
			{
				String content = (String) is.readObject();
				
				System.out.println( "Server read: " + content + "!" );
				
				os.writeObject( "0.0.0.0" );
				
				System.out.println( "Server sent!" );
			}
			
			is.close();
			os.close();
			clientSocket.close();
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}
}