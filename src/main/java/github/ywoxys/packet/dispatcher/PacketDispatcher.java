package github.ywoxys.packet.dispatcher;

import github.ywoxys.packet.Packet;
import github.ywoxys.packet.channel.Channel;
import github.ywoxys.socket.SocketChannel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PacketDispatcher {

    SocketChannel socketChannel;

    private Map<String, Channel.RegisteredChannel> channels;

    public PacketDispatcher() {
        channels = new HashMap<>();
    }

    public void registerChannel(Channel channel) throws IOException {
        Channel.RegisteredChannel registeredChannel = new Channel.RegisteredChannel(channel, channel.getSocketChannel());
        channels.put(channel.getName(), registeredChannel);
    }

    public void publish(Packet packet) throws IOException {
        Channel.RegisteredChannel registeredChannel = channels.get(packet.getChannel());
        if (registeredChannel != null) {
            registeredChannel.getSocketChannel().send(packet);
        }
    }
}
