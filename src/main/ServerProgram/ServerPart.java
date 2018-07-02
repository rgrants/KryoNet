import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Date;

public class ServerPart extends Listener {
    public static void main(String[] args) throws Exception {
        int tcpPort = 54555;
        int updPort = 54777;

        System.out.println("Creating server ...");
        // Create server
        Server server = new Server();
        // Register Packet
        server.getKryo().register(PacketMessage.class);
        // Bind ports
        server.bind(tcpPort,updPort );
        // Start server
        server.start();
        // Server Listener
        server.addListener(new ServerPart());
        System.out.println("Server has started");
        ClientPart.Client();

    }

    public void connected(Connection c) {
        System.out.println("Received  a connection from " + c.getRemoteAddressTCP().getHostName());
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.message = "Hello current time is: " + new Date().toString();
        c.sendTCP(packetMessage);
    }

    public void disconnected(Connection c) {
        System.out.println("A client disconnected ");
    }

}
