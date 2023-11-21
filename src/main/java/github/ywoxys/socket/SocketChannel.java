package github.ywoxys.socket;

import github.ywoxys.packet.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketChannel {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketChannel(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public void send(Packet packet) throws IOException {
        outputStream.write(packet.getChannel().getBytes());
        outputStream.write(packet.getData());
    }

    public Packet receivePacket() throws IOException {
        byte[] channelByte = new byte[16];
        byte[] data = new byte[8192];

        int read = inputStream.read(channelByte);
        if (read == -1) {
            return null;
        }

        String channel = new String(channelByte, "UTF-8");

        int readData = 0;
        while (readData < data.length) {
            read = inputStream.read(data, readData, data.length - readData);
            if (read == -1) {
                break;
            }
            readData += read;
        }
        return new Packet(channel, data);
    }
}
