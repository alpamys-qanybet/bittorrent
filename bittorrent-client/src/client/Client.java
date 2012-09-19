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
			sendData.sendRequestForFile("music.mp3");
			
			receiveThread = new Thread( receiveData = new ReceiveData(clientSocket) );
			receiveThread.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
