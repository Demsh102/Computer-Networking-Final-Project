import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastClient
{
	//Multicast socket to connect to
    private MulticastSocket socket = null;
	//Socket for server connection if connecting to server
	private DatagramSocket serverSocket = null;
	//Variables for handling the datagram packets
    private DatagramPacket output = null;
	
    private byte[] buffer = new byte[256];
	//Keeps track of user's name, which is displayed to other users
	private String name = "";
	private Scanner reader = new Scanner(System.in);  
	private InetAddress IPAddress;
	
	private int channel;
	//Constructor
	MulticastClient()
	{
	
	}
	
	//
	private void peerToPeer()
	{

		try
		{
			//Joins/creates the multicast group of the ip entered.  
			System.out.println("Enter an ip between 224.0.0.0 to 239.255.255.255 inclusive.  You shall join the chat on this multicast ip");
			String temp = reader.nextLine();
			IPAddress = InetAddress.getByName(temp);
			socket = new MulticastSocket(8888);
			socket.joinGroup(IPAddress);
			
			//Opens on a listener to display text sent to the multicast group
			MulticastClientListener listener = new MulticastClientListener(IPAddress);
			Thread theThread = new Thread(listener);
			theThread.start();
			
			//Moves to chat 
			peerChat();
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	private void server()
	{
	
		try
		{
			//Connects to server 
			System.out.println("Enter the IP of the server you wish to connect to.");
			String temp = reader.nextLine();
			IPAddress = InetAddress.getByName(temp);
			
			//Asks for the channel the user wants to join
			System.out.println("What channel would you like to broadcast to? (enter an integer 0-9): ");
			channel = reader.nextInt();
			
			//Opens on a listener to display text sent to the multicast group

			MulticastClientListener listener = new MulticastClientListener(InetAddress.getByName(temp));
			Thread theThread = new Thread(listener);
			listener.setChannel(channel);
			theThread.start();
			
			//Moces to chat
			serverSocket = new DatagramSocket();
			serverChat();
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	//Peer to peer chat component.
	private void peerChat()
	{
		try
		{
	
			while(true)
			{
				//Simply takes in user input, and sends it to the peerToPeer group joined
				System.out.println("Enter a message: ");
				String msg = reader.nextLine();
	
				buffer = msg.getBytes();
				output = new DatagramPacket(buffer, buffer.length, IPAddress, 8888);
				socket.send(output);
			}
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	//Server chat component
	private void serverChat()
	{
		try
		{
		while(true)
		{
			//Takes in user input, and sends it to the server connected to
			System.out.println("Enter a message: ");
			String msg = reader.nextLine();
		
			//Appends the user's channel joined to the front of the message
			//This is used later in the process by the server and listener to determine who recieves the message
			msg = channel + msg;
			
	
			buffer = msg.getBytes();
			output = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 9876);
			serverSocket = new DatagramSocket();
			serverSocket.send(output);
			
			//At this point, the server sends the message out to the multicast group
		}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}

  
  public void run()
  {
	Scanner input = new Scanner(System.in);  
  	
	int choice = 0;
	
	System.out.println("Would you like to connect tp/create a peer-to-peer chat (1) or join a server (2)?");
	choice = input.nextInt();
	
	//Selection menu
	switch(choice)
	{
		case 1:
			peerToPeer();
			break;
		case 2:
			server();
			break;
	}
  }

}