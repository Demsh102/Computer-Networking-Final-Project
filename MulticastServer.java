import java.io.*;
import java.net.*;

//Code modified from the Red-Blue Assignment
class MulticastServer {
    public static void main(String args[]) throws Exception
    {
        MulticastSocket serverSocket = null;
        serverSocket = new MulticastSocket(8888);
		DatagramSocket clientSocket = null;
		clientSocket = new DatagramSocket(9876);
		
		byte[] buffer = new byte[256];
        int port = 0, port1 = 0, port2 = 0;
		InetAddress IPAddress = InetAddress.getByName("224.2.2.3");
        String message = "";
        String response = "";
        DatagramPacket receivePacket = null;
        DatagramPacket sendPacket = null;
        int state = 2;
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        byte[] messageBytes = new byte[1024];
		
		serverSocket.joinGroup(IPAddress);
		
        while (state < 3){
            receiveData = new byte[1024];
            sendData = new byte[1024];
            switch (state){

                 case 2: // state 2: Chat mode
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
                    message = new String(receivePacket.getData());
					String temp = message.substring(0,1);
					//Displays the message and channel broadcast to.  This is currently debug mostly, and shall be polished later
					System.out.println(temp);
					System.out.println(message);
					
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