package examples.chat;

import java.net.InetSocketAddress;

import javax.swing.JOptionPane;

import examples.chat.ChatEvent.Type;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListenerAdapter;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.nio.tcp.TcpPipeline;

public class ChatClient extends ClientListenerAdapter 
{

	public static void main(String[] args) 
	{
		ChatClient client = new ChatClient();

		Pipeline pipeline = new TcpPipeline();
		pipeline.setDefaultProtocol(ChatEvent.class);
		pipeline.addProtocol(ChatEvent.class, new ChatProtocol());
		pipeline.setClientListener(client);
		
		client.start( pipeline.connect(new InetSocketAddress("157.160.141.126", 8998)) );
	}
	
	private ChatUser user;
	private ChatWindow window;
	
	public void start(Client end) 
	{
		user = new ChatUser(end);
		window = new ChatWindow(user);
		window.setVisible(true);
		user.send(new ChatEvent(Type.Signon, user.name));
	}
	
	@Override
	public void onClientReceive(Client client, Object data) 
	{
		super.onClientReceive(client, data);
		
		window.display((ChatEvent)data);
	}
	
	@Override
	public void onClientClose(Client client)
	{
		super.onClientClose(client);
		
		JOptionPane.showMessageDialog(window, "Good bye");
		
		window.dispose();

		System.exit(0);
	}

}
