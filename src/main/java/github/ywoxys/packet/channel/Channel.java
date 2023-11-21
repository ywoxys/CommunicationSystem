package github.ywoxys.packet.channel;

import github.ywoxys.packet.Packet;
import github.ywoxys.socket.SocketChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Channel {

    private String name;
    private List<Packet.PacketListener> listeners;
    @Setter
    private SocketChannel socketChannel;

    public Channel(String name) {
        this.name = name;
        this.listeners = new ArrayList<>();
    }

    public void subscribe(Packet.PacketListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(Packet.PacketListener listener) {
        listeners.remove(listener);
    }

    public void publish(Packet packet) {
        for (Packet.PacketListener listener : listeners) {
            listener.onPacket(packet);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class RegisteredChannel {
        private Channel channel;
        private SocketChannel socketChannel;
    }
}