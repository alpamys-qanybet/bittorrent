package client;

import java.io.IOException;
import java.net.Socket;

public class Client
{
	private Socket clientSocket;
	private Thread sendThread, receiveThread;
	private SendData sendData;
	private ReceiveData receiveData;
	
	public Client() {}
	
	public void connect(String host, int port)
	{
		try
		{
			clientSocket = new Socket(host, port);
			
			sendThread = new Thread( sendData = new SendData(clientSocket) );
			sendThread.start();
			
			receiveThread = new Thread( receiveData = new ReceiveData(clientSocket) );
			receiveThread.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

/*
  	public void connect(String host, int port)
 
	{
		try
		{
			Socket socket = new Socket(host, port);
			
			OutputStream os = socket.getOutputStream();
			PrintStream out = new PrintStream(os);
			
			out.println("some-music.mp3");
			
			InputStream is = socket.getInputStream();
			Scanner in = new Scanner(is);
			
			System.out.println(in.nextLine());
			
			is.close();
			
			socket.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
*/
}
