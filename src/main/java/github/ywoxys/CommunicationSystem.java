package github.ywoxys;

import github.ywoxys.packet.Packet;
import github.ywoxys.packet.channel.Channel;
import github.ywoxys.packet.dispatcher.PacketDispatcher;
import github.ywoxys.socket.SocketChannel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class CommunicationSystem {

    private static CommunicationSystem instance;
    private PacketDispatcher packetDispatcher;

    private CommunicationSystem() {
        packetDispatcher = new PacketDispatcher();
    }

    public static CommunicationSystem getInstance() {
        if (instance == null) {
            instance = new CommunicationSystem();
        }
        return instance;
    }

    public void registerChannel(Channel channel) {
        try {
            packetDispatcher.registerChannel(channel);
        } catch (IOException e) {
            System.out.println("Error on register channel:");
            System.out.println(e);
        }
    }

    public void publish(Packet packet) {
        try {
            packetDispatcher.publish(packet);
        } catch (IOException e) {
            System.out.println("Error on publish global packet:");
            System.out.println(e);
        }
    }

    public static ServerSocket serverSocket;
    public static Socket socket;
    public static SocketChannel socketChannel;

    public static void main(String[] args) throws IOException {
        Channel channel = new Channel("state-channel");

        channel.subscribe(new Packet.PacketListener() {
            @Override
            public void onPacket(Packet packet) {
                System.out.println(Arrays.toString(packet.getData()));
            }
        });

        serverSocket = new ServerSocket(8080);
        socket = serverSocket.accept();
        socketChannel = new SocketChannel(socket);
        CommunicationSystem.getInstance().registerChannel(channel);
        CommunicationSystem.getInstance().publish(new Packet("state", "Teste!".getBytes()));
    }
}
