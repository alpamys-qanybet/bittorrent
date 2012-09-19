package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 
 * @author about.me/alpamys.kanibetov
 */

public class Server
{
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private LinkedList <ClientThread> thread_list;
	
	public Server() 
	{
		thread_list = new LinkedList<ClientThread>();
	}
	
	public void connect (int port)
	{
		try
		{ serverSocket = new ServerSocket(port); }
		
		catch(IOException e)
		{ e.printStackTrace(); }
	
		
		while (true)
		{
			try
			{
				clientSocket = serverSocket.accept();
				
				ClientThread clientThread = new ClientThread( clientSocket, thread_list );
				thread_list.add( clientThread );
				clientThread.start();
			}
			
			catch (Exception e)
			{ e.printStackTrace(); }
		}
	}
	
/*	
  	public void connect()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(7122);
			
//			System.out.println( serverSocket.getInetAddress() );
//			System.out.println( serverSocket.getLocalSocketAddress() );
			
			Socket client;
			while(true)
			{
				client = serverSocket.accept();
				
				OutputStream os = client.getOutputStream();
				PrintStream out = new PrintStream(os);
				
				InputStream is = client.getInputStream();
				Scanner in = new Scanner(is);
				
				String fileName = in.nextLine(); 
				System.out.println( fileName );
				
				is.close();
				
				out.println( fileName + " not found");
				
				System.out.println(client.getInetAddress());
				client.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
*/
}