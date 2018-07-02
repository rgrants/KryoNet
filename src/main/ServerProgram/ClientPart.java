import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientPart extends Listener {
    static boolean messageReceived = false;

    public static void Client() throws Exception {
        System.out.println("Connecting to client");
        int tcpPort = 54555;
        int updPort = 54777;
        String ip = "localhost";

        Client client = new Client();
        client.getKryo().register(PacketMessage.class);
        // Client starts before connecting
        client.start();
        client.connect(5000, ip, tcpPort, updPort);
        client.addListener(new ClientPart());
        System.out.println("Client is now waiting for packet to arrive");
        while(!messageReceived) {
            Thread.sleep(1000);
        }

        System.out.println("Client will now exit");
        System.exit(0);
    }


    public void received(Connection c, Object o) {
        if(o instanceof PacketMessage) {
            PacketMessage packet = (PacketMessage) o;
            System.out.println("Received message from host: " + packet.message);
            messageReceived = true;
        }

    }
}
