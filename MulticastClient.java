import java.io.*;
import java.net.*;

public class MulticastClient
{
  public static void main (String[] args)
  {
    MulticastSocket socket = null;
    DatagramPacket inPacket = null;
    byte[] buffer = new byte[256];

    try
    {
    socket = new MulticastSocket(8888);
    InetAddress IPAddress = InetAddress.getByName("224.2.2.3");
    socket.joinGroup(IPAddress);

    while(true)
    {
    inPacket = new DatagramPacket(buffer, buffer.length);
    socket.receive(inPacket);
    String msg = new String(buffer, 0, inPacket.getLength());
    System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
    }
  }
    catch (IOException ioe)
    {
    System.out.println(ioe);
    }

  }

}
