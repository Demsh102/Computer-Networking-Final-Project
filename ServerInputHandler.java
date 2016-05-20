import java.io.*;
import java.net.*;
import java.util.*;

public class ServerInputHandler implements Runnable
{

	//Variables to keep track of sockets and socket info
	private PrintWriter b;
	
	//Variables to keep track of channel connected to, and if the listener is listening to a server
	private int channel;
	
	private boolean isServerListener = false;

	//Class is constructed with a multicast IP address, which it then connects to
	ServerInputHandler(PrintWriter a)
	{
		b = a;
	}
	
	public void setChannel(int c)
	{
		channel = c;
		isServerListener = true;
	}

	//Main run method simply displays all text that the multicast group recieves
	public void run()
	{
	Scanner reader = new Scanner(System.in); 
	while(true)
	{
		String temp = reader.nextLine();
		if (temp.equals("End"))
		{
			b.close();
			System.out.println("Chat logging ending.");
		}
	}
} // ClientListener for MTClient
}