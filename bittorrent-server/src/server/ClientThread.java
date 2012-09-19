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
	private LinkedList <ClientThread> thread_list;
	private Socket clientSocket = null;
	private ObjectInputStream is = null;
	private ObjectOutputStream os = null;
	private boolean stop;
	
	public ClientThread (Socket clientSocket, LinkedList<ClientThread> thread_list)
	{
		this.clientSocket = clientSocket;
		this.thread_list = thread_list;
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
				
				System.out.println( "Server says: " + content + " not found!" );
				
				os.writeObject( "Server says: " + content + " not found!" );
				
				System.out.println( "Server sent!" );
			}
			
			is.close();
			os.close();
			clientSocket.close();
			thread_list.remove(this);
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}
}
