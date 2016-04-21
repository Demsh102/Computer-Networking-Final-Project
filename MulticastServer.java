import java.io.*;
import java.net.*;

public class MulticastServer
{
  final static int PORT = 8888;
  //final static int IP = 1;

  public static void main(String args[]) throws Exception
  {
    DatagramSocket socket = null;
    DatagramPacket output = null;
    byte[] buffer;
    InetAddress IPAddress = InetAddress.getByName("localhost");

    try
    {
      socket = new DatagramSocket();
      String msg = "Hello World";
      buffer = msg.getBytes();
      output = new DatagramPacket(buffer, buffer.length, IPAddress, PORT);

      socket.send(output);

      System.out.println("Message sent");


    try
    {
         Thread.sleep(500);
    }
    catch (InterruptedException ie)
    {
    }

    }

    catch (IOException ioe)
    {
    System.out.println(ioe);
    }

  }
}
