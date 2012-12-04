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
			
			System.out.println("*** connected client ipaddress " + clientSocket.getInetAddress().getHostAddress());
			
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
					String fileName = content[1];
					int part = Integer.parseInt(content[2]);

					Main.dbmanager.insertUser(clientSocket.getInetAddress().getHostAddress());
					String ipaddress = Main.dbmanager.searchFileHoster(fileName, part);
					os.writeObject(ipaddress);
					
					System.out.println( ipaddress + " contains file " + fileName + ", part " + part );
				}
				else if (content[0].equals("inform_download"))
				{
					String ipAddress = content[1];
					String fileName = content[2];
					int part = Integer.parseInt(content[3]);
					
					String newIpAddress = clientSocket.getInetAddress().getHostAddress();
					Main.dbmanager.changeAvailability(ipAddress, newIpAddress, fileName, part);
				}
			}
			
			is.close();
			os.close();
			clientSocket.close();
		}
		
		catch (Exception e)
		{ e.printStackTrace(); }
	}
}