import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastClientListener implements Runnable
{

	//Variables to keep track of sockets and socket info
	private MulticastSocket connectionSock = null;
	private InetAddress IPAddress;
    private DatagramPacket inPacket = null;
    byte[] buffer = new byte[256];
	
	//Variables to keep track of channel connected to, and if the listener is listening to a server
	private int channel;
	
	private boolean isServerListener = false;

	//Class is constructed with a multicast IP address, which it then connects to
	MulticastClientListener(InetAddress a)
	{
		try
		{
			this.IPAddress = a;
			connectionSock = new MulticastSocket(8888);
			connectionSock.joinGroup(IPAddress);
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	public void setChannel(int c)
	{
		channel = c;
		isServerListener = true;
	}

	//Main run method simply displays all text that the multicast group recieves
	public void run()
	{
	while(true)
	{
		try
		{
			//If the listener is listening for messages from a server, executes slightly different procedure
			if (isServerListener)
			{
				inPacket = new DatagramPacket(buffer, buffer.length);
				connectionSock.receive(inPacket);
				String msg = new String(buffer, 0, inPacket.getLength());
				
				//Sees if the int appended to the front of the message matches the channel the listener is listening to
				//If so, displays that text
				//Since this integer is (currently) only ever between 0 and 9, there should not be any issues with input muddling the channel info
				if (Integer.parseInt(msg.substring(0,1)) == channel)
				{
					System.out.println("From " + "Anonymous" + " Msg : " + msg.substring(1, msg.length()));
				}
			}
			else
			{
				inPacket = new DatagramPacket(buffer, buffer.length);
				connectionSock.receive(inPacket);
				String msg = new String(buffer, 0, inPacket.getLength());
				System.out.println("From " + "Anonymous" + " Msg : " + msg);
			}
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	}
} // ClientListener for MTClient