import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPClient {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		DatagramSocket socket = new DatagramSocket();
		socket.connect(new InetSocketAddress("157.160.37.44", 9930));
		System.out.println("Connected: Type in exit to quit application.");
		for (String line = input.nextLine(); !line.equals("exit"); line = input.nextLine()) {
			socket.send(new DatagramPacket(line.getBytes(), line.length()));
		}
	}
}