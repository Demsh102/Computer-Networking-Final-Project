  
public class ChatClient
{

//Creates and runs instances of the multicast chat clients
  public static void main (String[] args)
  {
	MulticastClient client = new MulticastClient();
	client.run();
  }
 }