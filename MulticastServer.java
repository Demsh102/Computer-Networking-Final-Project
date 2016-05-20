import java.io.*;
import java.net.*;
import java.util.*;

//Code modified from the Red-Blue Assignment
class MulticastServer {

	private static void logIn(String b, InetAddress a)
	{
		String temp[] = b.split(":");
		String userName = temp[1];
		String password = temp[2];
		byte[] buffer = new byte[256];
		
		if (userName.equals("a") && password.equals("b"))
		{
			System.out.println("User succesfully logged in");
			DatagramSocket clientSocket = null;
			buffer = "login Successful".getBytes();
			try{
			DatagramPacket output = new DatagramPacket(buffer, buffer.length, a, 9876);
			DatagramSocket serverSocket = new DatagramSocket();
			serverSocket.send(output);
			}
			
			catch(IOException ioe){
			
			}
			
		}
		else	
		{
			buffer = "login UnSuccessful".getBytes();
			try {
			DatagramPacket output = new DatagramPacket(buffer, buffer.length, a, 9876);
			DatagramSocket serverSocket = new DatagramSocket();
			serverSocket.send(output);
			}
			catch(IOException ioe) {
			
			}
		}
		
	}

    public static void main(String args[]) throws Exception
    {
        MulticastSocket serverSocket = null;
        serverSocket = new MulticastSocket(8888);
		DatagramSocket clientSocket = null;
		clientSocket = new DatagramSocket(9876);
		
		Scanner reader = new Scanner(System.in); 
		System.out.println("Enter an ip between 224.0.0.0 to 239.255.255.255 inclusive.  You shall create a server on this multicast ip");
		
		String tempIP = reader.nextLine();
		InetAddress IPAddress = InetAddress.getByName(tempIP);
		
		PrintWriter writer = new PrintWriter("chatlog.txt", "UTF-8");
		
		byte[] buffer = new byte[256];
        int port = 0, port1 = 0, port2 = 0;
        String message = "";
        String response = "";
        DatagramPacket receivePacket = null;
        DatagramPacket sendPacket = null;
        int state = 2;
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        byte[] messageBytes = new byte[1024];
		
		serverSocket.joinGroup(IPAddress);
		
		ServerInputHandler A = new ServerInputHandler(writer);
		Thread theThread = new Thread(A);
		theThread.start();
		
		System.out.println("Type End to end and save chat logging.  Make sure to do this before closing the server, or the logs will not be saved.");
		
        while (state < 3){
            receiveData = new byte[1024];
            sendData = new byte[1024];
            switch (state){

                 case 2: // state 2: Chat mode
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
                    message = new String(receivePacket.getData());
					
					
					String temp = message.substring(0,1);
					//Displays the message and channel broadcast to.  This is currently debug 
					//System.out.println(temp);
					System.out.println(message);
					writer.println(message);
					
					try
					{
						buffer = message.getBytes();
						sendPacket = new DatagramPacket(buffer, buffer.length, IPAddress, 8888);
						serverSocket.send(sendPacket);
					}
					catch (IOException ioe)
					{
						System.out.println(ioe);
					}
					
                    //serverSocket.send(sendPacket);
 //stay in state 2
 break;
 } //end switch
 } //end while
 //send Goodbye messages to both clients

 //close the socket
 }
 }