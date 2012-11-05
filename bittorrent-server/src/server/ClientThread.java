package server;

/**
 * @author about.me/alpamys.kanibetov
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
				String[] content = (String[]) is.readObject();
				
				if (content[0].equals("upload"))
				{
					String fileInfo = content[1];
					System.out.println("File upload "+fileInfo);
					
					String fileName = fileInfo.substring(0, fileInfo.indexOf('/'));
					int chunks = Integer.parseInt( fileInfo.substring(fileInfo.indexOf('/') + 1) );
					
					System.out.println("client ipaddress " + clientSocket.getInetAddress().getHostAddress());
					
					int userId = Main.dbmanager.insertUser(clientSocket.getInetAddress().getHostAddress());
					
					for (int i=0; i<=chunks; i++)
					{
						Main.dbmanager.insertFile(userId, fileName, i);
						System.out.println(i);
					}
				}
				else if (content[0].equals("download"))
				{
					
				}
				
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