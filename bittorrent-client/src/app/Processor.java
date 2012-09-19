package app;

import client.Client;

public class Processor
{
	private Client client;
	
	public Processor(){}
	
	public void createClient()
	{
		client = new Client();
		client.connect("0.0.0.0");
	}
}
