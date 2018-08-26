package igc.dist.client;

import com.google.protobuf.GeneratedMessageV3;
import igc.dist.client.codec.ProtocolDecoder;
import igc.dist.client.codec.ProtocolEncoder;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

  private static final Map<Integer, GeneratedMessageV3> MESSAGES = PacketConfig.messages();
  private static final Map<Class, Integer> MESSAGE_IDS = PacketConfig.messageIds();
  private final ChannelHandlerAdapter adapter;

  @Override
  protected void initChannel(final SocketChannel ch) {
    var pipeline = ch.pipeline();
    pipeline
        .addLast("protocolDecoder", new ProtocolDecoder(MESSAGES))
        .addLast("protocolEncoder", new ProtocolEncoder(MESSAGE_IDS))
        .addLast("loginPacketHandler", adapter);
  }
}