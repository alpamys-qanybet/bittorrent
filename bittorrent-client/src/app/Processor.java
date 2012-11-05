package app;

import client.Client;

public class Processor
{
	private Client client;
	private Frame frame;
	
	public Processor(){}
	
	public void createClient()
	{
		client = new Client();
		client.connect("0.0.0.0");
	}
	
	public void showApp()
	{
		frame = new Frame();
		frame.setVisible(true);
	}
	
	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}
}
