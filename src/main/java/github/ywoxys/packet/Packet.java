package github.ywoxys.packet;

import lombok.Getter;

@Getter
public class Packet {

    private String channel;
    private byte[] data;

    public Packet(String channel, byte[] data) {
        this.channel = channel;
        this.data = data;
    }

    public interface PacketListener {
        void onPacket(Packet packet);
    }
}
