package bittorrent.server;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import app.Main;

public class TorrentServer
{
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private TorrentClientThread torrentClientThread;
	
	public TorrentServer() {}
	
	public void connect ()
	{
		try
		{ serverSocket = new ServerSocket(Main.TORRENTSERVERPORT); }
		
		catch(IOException e)
		{ e.printStackTrace(); }
	
		
		while (true)
		{
			try
			{
				clientSocket = serverSocket.accept();
				
				System.out.println("Torrent Server/ port: " + serverSocket.getLocalPort() );
				
				torrentClientThread = new TorrentClientThread( clientSocket );
				torrentClientThread.start();
				torrentClientThread.receiveRequest();
			}
			
			catch (Exception e)
			{ e.printStackTrace(); }
		}
	}
}