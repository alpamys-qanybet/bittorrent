package client;
public class Main
{
	public static void main(String [] args)
	{
		Client client = new Client();
		client.connect("0.0.0.0", 7122);
	}
}
